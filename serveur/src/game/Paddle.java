package game;
/**
 * Classe Paddle : Représente la raquette des joueurs côté serveur
 * @author Macky dieng
 */
public class Paddle {
	/**
	 * La coordonnée x de la raquette
	 */
	private int x;
	
	/**
	 * Constructeur de la classe
	 * @param x : la coordonnée x de la raquette
	 */
	public Paddle(int x) {
		this.x = x;
	}

	/**
	 * Renvoie la coordoonée x de la raquette
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Modifie la coordonnée x de la raquette
	 * @param x nouvelle coordoée à assigner
	 */
	public void setX(int x) {
		this.x = x;
	}
	
}
