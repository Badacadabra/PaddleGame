package game;
/**
 * Classe Ball représentant la balle du jeu 
 * @author Macky Dieng
 */
public class Ball extends Thread {
	
	/**
	 * Constante gérant la cadence de la balle
	 */
	public static int SPEED = 20;
	
	/**
	 * Coordonnée x de la balle
	 */
	private int x;
	
	/**
	 * Coordonnée y de la balle
	 */
	private int y;
	
	/**
	 * Référence ball pour le singleton
	 */
	private static Ball ball;
	
	/**
	 * Constructeur de la classe
	 * @param x
	 * @param y
	 */
	private Ball(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Permet de lancer le thread
	 */
	public void run() {
		
	}
	/**
	 * Permet de gérer le mouvement de la balle
	 */
	public void move () {
		//
	}
	/**
	 * Renvoie la coordonnée x de la balle
	 * @return int
	 */
	public int getX() {
		return x;
	}

	/**
	 * Modifie la coordonnée x de la balle
	 * @param x la nouvelle valeur à assigner
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Renvoie la coordonnée y de la balle
	 * @return int
	 */
	public int getY() {
		return y;
	}

	/**
	 * Modifie la coordonnée y de la balle
	 * @param y nouvelle coordonnée à assigner
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Revoie une instance de balle
	 * @return Ball
	 */
	public static Ball getBall(int x, int y) {
		if (ball != null) {
			return ball;
		} else {
			return new Ball(x,y);
		}
	}

	/**
	 * @param ball the ball to set
	 */
	public static void setBall(Ball ball) {
		Ball.ball = ball;
	}
	
	
}
