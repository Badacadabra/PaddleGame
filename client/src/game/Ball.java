package game;

/**
 * Classe définissant une balle.
 * Une balle est ici un rond dessiné dans un canevas (un panneau).
 * À noter que le jeu ne doit comporter qu'une seule balle, d'où l'utilisation d'un Singleton.
 * 
 * @author Baptiste Vannesson
 */
public class Ball {

    /** Constante déterminant le diamètre de la balle */
    public static final int BALL_DIAMETER = 20;
    
    /** Instance statique de la classe elle-même (cf. Singleton) */
    private static Ball ball;
    
    /** Position de la balle sur l'axe des x */
    private int x;
    
    /** Position de la balle sur l'axe des y */
    private int y;
    
    /**
     * Constructeur privé (cf. Singleton) prenant en paramètres les coordonnées (x,y) de la balle
     * 
     * @param x Position de la balle sur l'axe des x
     * @param y Position de la balle sur l'axe des y
     */
    private Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Méthode statique d'accès à l'unique instance de la classe.
     * On veut s'assurer qu'il n'y a qu'une seule balle dans l'espace de jeu.
     * Il s'agit de la méthode principale permettant de mettre en œuvre le pattern Singleton.
     * 
     * @param x Position de la balle sur l'axe des x
     * @param y Position de la balle sur l'axe des y
     * @return Objet de type Ball, autrement dit une balle
     */
    public static Ball getBall(int x, int y) {
        if (ball == null) {
            ball = new Ball(x, y);
        }
        return ball;
    }

    /**
     * Accesseur pour la position de la balle sur l'axe des x
     * 
     * @return Position de la balle sur l'axe des x
     */
    public int getX() {
        return x;
    }

    /**
     * Mutateur pour la position de la balle sur l'axe des x
     * 
     * @param x Nouvelle position de la balle sur l'axe des x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Accesseur pour la position de la balle sur l'axe des y
     * 
     * @return Position de la balle sur l'axe des y
     */
    public int getY() {
        return y;
    }

    /**
     * Mutateur pour la position de la balle sur l'axe des y
     * 
     * @param y Nouvelle position de la balle sur l'axe des y
     */
    public void setY(int y) {
        this.y = y;
    }
    
}
