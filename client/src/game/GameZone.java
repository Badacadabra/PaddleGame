package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Classe définissant la zone du jeu proprement dit.
 * Il s'agit ici d'un objet de type JPanel contenant notamment la balle et la raquette.
 * 
 * @author Baptiste Vannesson
 */
public class GameZone extends JPanel {

    /** Constante déterminant la largeur de la zone contenant la balle */
    public static final int BALL_AREA_WIDTH = 600;
    
    /** Constante déterminant la hauteur de la zone contenant la balle */
    public static final int BALL_AREA_HEIGHT = 600;
    
    /** La balle du jeu */
    private Ball ball;
    
    /** L'ensemble des raquettes (du joueur et de ses adversaires) */
    private List<Paddle> paddles;
    
    /**
     * Constructeur de la zone du jeu proprement dit.
     * Ce dernier prend en paramètres une balle et plusieurs raquettes.
     * 
     * @param ball La balle du jeu
     * @param paddles Les raquettes de tous les joueurs
     */
    public GameZone(Ball ball, List<Paddle> paddles) {
        setPreferredSize(new Dimension(BALL_AREA_WIDTH, BALL_AREA_HEIGHT));
        this.ball = ball;
        this.paddles = new ArrayList<Paddle>();
        for (Paddle paddle : paddles) {
            this.paddles.add(paddle);
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Zone de jeu
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, BALL_AREA_WIDTH, BALL_AREA_HEIGHT);
        // Balle
        g2d.setColor(Color.BLACK);
        g2d.fillOval(ball.getX(), ball.getY(), ball.BALL_DIAMETER, ball.BALL_DIAMETER);
        // Raquettes
        for (Paddle paddle : paddles) {
            g2d.setColor(paddle.getColor());
            g2d.fillRect(paddle.getX(), paddle.Y, Paddle.WIDTH, Paddle.HEIGHT);
            Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .4f);
            g2d.setComposite(composite);
        }
    }
    
    /**
     * Méthode permettant d'ajouter une raquette à la liste des raquettes.
     * Utile lorsqu'un nouveau client se connecte et veut entamer une partie.
     * 
     * @param La nouvelle raquette à afficher dans la zone de jeu
     */
    public void addPaddle(Paddle paddle) {
        paddles.add(paddle);
    }
    
    /**
     * Méthode permettant de supprimer une raquette de la liste des raquettes.
     * Utile lorsqu'un client quitte le jeu.
     * 
     * @param La raquette à supprimer de la zone de jeu
     */
    public void removePaddle(Paddle paddle) {
        paddles.remove(paddle);
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
     * Accesseur pour les raquettes
     * 
     * @return Une liste d'objets de type Paddle
     */
    public List<Paddle> getPaddles() {
        return paddles;
    }
    
}
