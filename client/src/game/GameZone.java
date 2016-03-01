package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;

/**
 * Classe définissant la zone du jeu.
 * Il s'agit ici d'un objet de type JPanel contenant la balle et les raquettes.
 * On remarquera notamment que cette classe implémente MouseMotionListener pour écouter les mouvements de souris.
 * 
 * @author Baptiste Vannesson
 */
public class GameZone extends JPanel implements MouseMotionListener {

    /** Constante déterminant la largeur de la zone de jeu */
    public static final short WIDTH = 600;
    
    /** Constante déterminant la hauteur de la zone de jeu */
    public static final short HEIGHT = 600;
    
    /** La balle du jeu */
    private Ball ball;
    
    /** Le joueur courant */
    private Gamer gamer;
    
    /** Map des adversaires connectés au jeu */
    private Map<String,Gamer> opponents;
    
    /** Modèle de liste pour affichage des scores */
    private DefaultListModel<Gamer> scoresModel;
    
    /**
     * Constructeur de la zone du jeu proprement dit.
     * Ce dernier prend en paramètres une balle, le joueur courant, ainsi qu'un modèle de liste pour affichage des scores.
     * 
     * @param ball La balle du jeu
     * @param gamer Le joueur courant
     * @param model Modèle de liste pour affichage des scores
     */
    public GameZone(Ball ball, Gamer gamer, DefaultListModel<Gamer> scoresModel) {
        this.ball = ball;
        this.gamer = gamer;
        this.scoresModel = scoresModel;
        this.opponents = new HashMap<String,Gamer>();
        
        // Taille de la zone de jeu
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        // On écoute les événements de MouseMotionListener (drag & move)
        this.addMouseMotionListener(this);
        
        // On cache le curseur pour que le jeu soit plus agréable
        this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
    }
    
    /**
     * Méthode fondamentale pour dessiner tous les éléments du jeu.
     * La structure de cette méthode s'inspire du pattern Template.
     * 
     * @param g Un objet de type Graphics
     */
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
     * Méthode helper utilisée par « paintComponent » pour dessiner la zone de jeu.
     * 
     * @param g2d Objet Graphics2D
     */
    private void drawGameZone(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
    }
    
    /**
     * Méthode helper utilisée par « paintComponent » pour dessiner la balle.
     * 
     * @param g2d Objet Graphics2D
     */
    private void drawBall(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillOval(ball.getX(), ball.getY(), Ball.DIAMETER, Ball.DIAMETER);
    }
    
    /**
     * Méthode helper utilisée  par « paintComponent » pour dessiner toutes les raquettes.
     * 
     * @param g2d Objet Graphics2D
     */
    private void drawPaddles(Graphics2D g2d) {
        // S'ils existent, on dessine les raquettes des adversaires (avec transparence)
        if (opponents.size() > 0) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .4f));
            for (Gamer opponent : opponents.values()) {
                drawPaddle(g2d, opponent);
            }
        }
        // On dessine la raquette du joueur courant (opaque)
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    	drawPaddle(g2d, gamer);
    }
    
    /**
     * Méthode utilisée par « drawPaddles » pour éviter la redondance de code.
     * Cette méthode dessine la raquette d'un joueur.
     * 
     * @param g2d Objet Graphics2D
     * @param gamer Joueur pour lequel on doit dessiner une raquette
     */
    private void drawPaddle(Graphics g2d, Gamer gamer) {
    	Paddle paddle = gamer.getPaddle();
        g2d.setColor(gamer.getColor());
        g2d.fillRoundRect(paddle.getX(), Paddle.Y, Paddle.WIDTH, Paddle.HEIGHT, 15, 15);
    }
    
    /**
     * Méthode gérant toutes les actions relatives à MouseMotionListener.
     * À chaque action avec la souris (drag & move), on capte les nouvelles coordonnées.
     * On récupère en particulier le x pour mettre à jour la position de la raquette.
     * 
     * @param e Objet MouseEvent
     */
    private void paddleAction(MouseEvent e) {
        Paddle paddle = gamer.getPaddle();
        if (e.getX() < (GameZone.WIDTH - Paddle.WIDTH)) {
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
       paddleAction(e);
    }

    /**
     * Méthode gérant les événements liés au déplacement simple de la souris.
     * 
     * @param e Objet MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        paddleAction(e);
    }
    
    /**
     * Méthode permettant d'ajouter un adversaire à la liste des adversaires.
     * Utile lorsqu'un nouveau client se connecte et veut entamer une partie.
     * 
     * @param Le nouvel adversaire à afficher dans la zone de jeu
     */
    public void addOpponent(Gamer gamer) {
        opponents.put(gamer.getPseudo(), gamer);
    }
    
    /**
     * Méthode permettant de supprimer un adversaire de la liste des adversaires.
     * Utile lorsqu'un client quitte le jeu.
     * 
     * @param La raquette à supprimer de la zone de jeu
     */
    public void removeOpponent(Gamer gamer) {
        opponents.remove(gamer);
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
     * Accesseur pour le joueur courant
     * 
     * @return Le joueur courant
     */
    public Gamer getGamer() {
        return gamer;
    }
    
    /**
     * Accesseur pour les adversaires du joueur courant
     * 
     * @return Une liste d'objets de type Paddle
     */
    public Map<String,Gamer> getOpponents() {
        return opponents;
    }

    /**
     * Accesseur pour le modèle des scores
     * 
     * @return Un objet DefaultListModel
     */
    public DefaultListModel<Gamer> getScoresModel() {
        return scoresModel;
    }
    
}
