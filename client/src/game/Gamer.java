package game;

import java.awt.Color;

/**
 * JavaBean définissant l'entité joueur.
 * Dans le cadre du jeu, un joueur est représenté par son pseudo, son score, sa raquette, et sa couleur.
 * 
 * @author Baptiste Vannesson
 */
public class Gamer {

    /** Constante déterminant le score par défaut d'un joueur */
    public static final int DEFAULT_SCORE = 0;
    
    /** Chaîne de caractères représentant le pseudo du joueur */
    private String pseudo;
    
    /** Entier entre 0 et 100 représentant le score (en pourcentage) du joueur */
    private int score;
    
    /** Objet représentant la raquette du joueur */
    private Paddle paddle;
    
    /** Couleur attribuée au joueur */
    private Color color;
    
    /**
     * Constructeur par défaut d'un joueur
     */
    public Gamer() {
        this("", DEFAULT_SCORE, null, Color.WHITE);
    }
    
    /**
     * Constructeur principal permettant d'initialiser l'objet joueur
     * 
     * @param pseudo Pseudo du joueur
     * @param score Score du joueur
     * @param paddle Raquette du joueur
     * @param color Couleur du joueur
     */
    public Gamer(String pseudo, int score, Paddle paddle, Color color) {
        this.pseudo = pseudo;
        this.score = score;
        this.paddle = paddle;
        this.color = color;
    }

    /**
     * Accesseur pour le pseudo du joueur
     * 
     * @return Pseudo du joueur
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Mutateur pour le pseudo du joueur
     * 
     * @param pseudo Nouveau pseudo du joueur
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Accesseur pour le score du joueur
     * 
     * @return Score du joueur
     */
    public int getScore() {
        return score;
    }

    /**
     * Mutateur pour le score du joueur
     * 
     * @param score Nouveau score du joueur
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Accesseur pour la raquette du joueur
     * 
     * @return Raquette du joueur
     */
    public Paddle getPaddle() {
        return paddle;
    }

    /**
     * Mutateur pour la raquette du joueur
     * 
     * @param paddle Nouvelle raquette du joueur
     */
    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }
    
    /**
     * Accesseur pour la couleur du joueur
     * 
     * @return color Couleur du joueur
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Mutateur pour la couleur du joueur
     * 
     * @param color Nouvelle couleur du joueur
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    public String toString() {
    	return pseudo+"_"+score+"_"+paddle.getX()+"_"+color;
    }
    
}
