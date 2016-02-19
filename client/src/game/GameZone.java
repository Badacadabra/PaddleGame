package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Classe définissant la zone du jeu proprement dit.
 * Il s'agit ici d'un objet de type JPanel contenant notamment la balle et la raquette.
 * 
 * @author Baptiste Vannesson
 */
public class GameZone extends JPanel implements Runnable, MouseMotionListener {

    /** Constante déterminant la largeur de la zone contenant la balle */
    public static final int BALL_AREA_WIDTH = 600;
    
    /** Constante déterminant la hauteur de la zone contenant la balle */
    public static final int BALL_AREA_HEIGHT = 600;
    
    /** La balle du jeu */
    private Ball ball;
    
    /** Liste des joueurs connectés au jeu */
    private List<Gamer> gamers;
    
    /** Modèle de liste pour affichage des scores */
    private DefaultListModel model;
    
    /**
     * Constructeur de la zone du jeu proprement dit.
     * Ce dernier prend en paramètres une balle et plusieurs raquettes.
     * 
     * @param ball La balle du jeu
     * @param paddles Les raquettes de tous les joueurs
     */
    public GameZone(Ball ball, List<Gamer> gamers, DefaultListModel model) {

        this.ball = ball;
        this.gamers = new ArrayList<Gamer>();
        for (Gamer gamer : gamers) {
            this.gamers.add(gamer);
        }

        // Taille de la zone de jeu
        setPreferredSize(new Dimension(BALL_AREA_WIDTH, BALL_AREA_HEIGHT));
        
        // Modèle de la liste (pour affichage des scores)
        this.model = model;
        
        // On écoute les événements de MouseMotionListener (drag & move)
        this.addMouseMotionListener(this);
        
        // On cache le curseur pour que le jeu soit plus agréable
        this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // On dessine la zone de jeu
        drawGameZone(g2d);
        // On dessine la balle
        drawBall(g2d);
        // On dessine les raquettes
        drawPaddles(g2d);
    }
    
    /**
     * Méthode helper utilisée par « paintComponent » pour dessiner la zone de jeu
     * 
     * @param g2d Objet Graphics2D
     */
    private void drawGameZone(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, BALL_AREA_WIDTH, BALL_AREA_HEIGHT);
    }
    
    /**
     * Méthode helper utilisée par « paintComponent » pour dessiner la balle
     * 
     * @param g2d Objet Graphics2D
     */
    private void drawBall(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillOval(ball.getX(), ball.getY(), ball.BALL_DIAMETER, ball.BALL_DIAMETER);
    }
    
    /**
     * Méthode helper utilisée  par « paintComponent » pour dessiner les raquettes
     * 
     * @param g2d Objet Graphics2D
     */
    private void drawPaddles(Graphics2D g2d) {
        for (Gamer gamer : gamers) {
            Paddle paddle = gamer.getPaddle();
            g2d.setColor(gamer.getColor());
            g2d.fillRoundRect(paddle.getX(), paddle.Y, Paddle.WIDTH, Paddle.HEIGHT, 15, 15);
            Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .4f);
            g2d.setComposite(composite);
        }
    }
    
    /**
     * Méthode lançant le déplacement de la balle.
     * Les coordonnées de la balle viennent du serveur.
     */
    public void moveBall() {
        boolean reverseX = false;
        boolean reverseY = false;
        int ballX = ball.getX();
        int ballY = ball.getY();
        int collisions = 0;
        int earnedPoints = 0;
        Paddle paddle = gamers.get(0).getPaddle();
        while(true) {
            // Gestion des limites horizontales
            if(ballX < 1) {
                reverseX = false;
            }
            if(ballX > getWidth() - Ball.BALL_DIAMETER) {
                reverseX = true;          
            }
            if(!reverseX) {
                ball.setX(++ballX);
            } else {
                ball.setX(--ballX);
            }
            // Gestion des limites verticales
            if(ballY < 1) {
                reverseY = false;
            }
            if(!reverseY) {
                ball.setY(++ballY);
            } else {
                ball.setY(--ballY);
            }
            // Gestion des collisions
            if (ballY + Ball.BALL_DIAMETER == paddle.Y
                    && ballX >= paddle.getX()
                    && ballX <= paddle.getX() + paddle.WIDTH) {
                reverseY = true;
                System.out.println("Collision");
                collisions++;
                earnedPoints += gamers.size();
                gamers.get(0).setScore((earnedPoints / (collisions * gamers.size())) * 100);
                model.set(0, gamers.get(0));
            }
            // Perte de balle
            if (ballY > paddle.Y + Ball.BALL_DIAMETER) {
                ball.setX(0);
                ball.setY(0);
                int answer = JOptionPane.showConfirmDialog(null, "Voulez-vous continuer ?", "Balle perdue !", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
                moveBall(); // Récursivité
            }
            repaint();
            try {
              Thread.sleep(7);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
        }
    }
    
    /**
     * Méthode gérant toutes les actions relatives à MouseMotionListener.
     * À chaque action avec la souris (drag & move), on capte les nouvelles coordonnées.
     * On récupère en particulier le X pour mettre à jour la position de la raquette.
     * 
     * @param e Objet MouseEvent
     */
    private void mouseAction(MouseEvent e) {
        // System.out.println(e.getPoint());
        Paddle paddle = gamers.get(0).getPaddle();
        if (e.getX() < (GameZone.BALL_AREA_WIDTH - Paddle.WIDTH)) {
            paddle.setX(e.getX());
        }
        repaint();
    }
    
    /**
     * Méthode gérant les événements liés au drag de la souris.
     * 
     * @param e Objet MouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent e) {
       mouseAction(e);
    }

    /**
     * Méthode gérant les événements liés au déplacement de la souris.
     * 
     * @param e Objet MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseAction(e);
    }
    
    /**
     * Méthode permettant d'ajouter un joueur à la liste des joueurs.
     * Utile lorsqu'un nouveau client se connecte et veut entamer une partie.
     * 
     * @param La nouvelle raquette à afficher dans la zone de jeu
     */
    public void addPaddle(Gamer gamer) {
        gamers.add(gamer);
    }
    
    /**
     * Méthode permettant de supprimer un joueur de la liste des joueurs.
     * Utile lorsqu'un client quitte le jeu.
     * 
     * @param La raquette à supprimer de la zone de jeu
     */
    public void removePaddle(Gamer gamer) {
        gamers.remove(gamer);
    }

    /**
     * Accesseur pour la balle
     * 
     * @return Un objet de type Ball
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * Mutateur pour la balle
     * 
     * @param ball Nouvelle instance de Ball
     */
    public void setBall(Ball ball) {
        this.ball = ball;
    }

    /**
     * Accesseur pour les joueurs
     * 
     * @return Une liste d'objets de type Paddle
     */
    public List<Gamer> getGamers() {
        return gamers;
    }

    @Override
    public void run() {
       moveBall(); 
    }
    
}
