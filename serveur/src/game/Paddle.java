package game;
/**
 * Classe Paddle : Représente la raquette des joueurs côté serveur
 * @author Macky Dieng
 */
public class Paddle {
	/**
	 * La coordonnée x de la raquette
	 */
	private int x;
	
	/**
	 * Constructeur de la classe
	 * @param x la coordonnée x de la raquette
	 */
	public Paddle(int x) {
		this.x = x;
	}

	/**
	 * Renvoie la coordonnée x de la raquette
	 * @return coordonnée x de la raquette
	 */
	public int getX() {
		return x;
	}

	/**
	 * Modifie la coordonnée x de la raquette
	 * @param x nouvelle coordonnée à assigner
	 */
	public void setX(int x) {
		this.x = x;
	}
	
}
