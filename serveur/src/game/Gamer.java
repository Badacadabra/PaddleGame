package game;
/**
 * Class Gamer : Représente les joueurs côté serveur
 * @author Macky Dieng
 */
public class Gamer {
	
	/**
	 * Le pseudo du joueur
	 */
	private String pseudo;
	
	/**
	 * Le score du joueur
	 */
	private int score;
	
	/**
	 * La raquette du joueur
	 */
	private Paddle paddle;
	/**
	 * Constructeur de la classe
	 * @param pseudo pseudo du joueur
	 * @param score socre du joueur
	 * @param paddle raquette du joueur
	 */
	public Gamer(String pseudo, int score, Paddle paddle ) {
		this.pseudo = pseudo;
		this.score = score;
		this.paddle = paddle;
	}

	/**
	 * Renvoie le pseudo du joueur
	 * @return String
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * Permet de modifier le pseudo du joueur
	 * @param pseudo le nouveau pseudo à assigner
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * Renvoie le score du joueur 
	 * @return int
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Modifie le score du joueur
	 * @param score nouveau score à assigner
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Renvoie la raquette du joueur
	 * @return Paddle
	 */
	public Paddle getPaddle() {
		return paddle;
	}

	/**
	 * Modifie la raquette du joueur
	 * @param paddle nouvelle raquette à assigner
	 */
	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}
	
	public String toString() {
		String str = pseudo +"_"+score+"_"+paddle.getX();
		return str;
	}
	
}
