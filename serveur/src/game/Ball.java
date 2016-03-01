package game;
/**
 * Classe Ball représentant la balle du jeu 
 * @author Macky Dieng
 */
public class Ball {
	
	/**
	 * Constante gérant la cadence de la balle
	 */
	public static final int SPEED = 20;
	
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
	 * @param x coordonnée x de la balle
	 * @param y coordonnée y de la balle
	 */
	private Ball(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Renvoie la coordonnée x de la balle
	 * @return coordonnée x de la balle
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
	 * @return coordonnée y de la balle
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
	 * Revoie une unique instance de balle
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
	 * Modifie la balle
	 * @param ball nouvelle balle à assigner
	 */
	public static void setBall(Ball ball) {
		Ball.ball = ball;
	}
	
	
}
