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
	 * Nombre de points gagnés par le joueur
	 */
	private int earnedPoints;
	
	/**
	 * Nombre de points idéal que devrait ganger le joueur
	 */
	private int idealPoints;
	/**
	 * Constructeur de la classe
	 * @param pseudo pseudo du joueur
	 * @param score socre du joueur
	 * @param paddle raquette du joueur
	 */
	public Gamer(String pseudo, int score,Paddle paddle ) {
		this.pseudo = pseudo;
		this.score = score;
		this.paddle = paddle;
		this.earnedPoints = 0;
		this.idealPoints = 0;
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
	
	/**
	 * Nombre points gagnés par le joueur
	 * @return int
	 */
	public int getEarnedPoints() {
		return earnedPoints;
	}

	/**
	 * Modifie le nombre de points du joueur 
	 * @param earnedPoints the earnedPoints to set
	 */
	public void setEarnedPoints(int earnedPoints) {
		this.earnedPoints += earnedPoints;
	}

	/**
	 * Renvoie le nombre de points idéal pour le joueur
	 * @return int
	 */
	public int getIdealPoints() {
		return idealPoints;
	}

	/**
	 * Modifie le nombre de points idéal pour le joueur
	 * @param idealPoints the idealPoints to set
	 */
	public void setIdealPoints(int idealPoints) {
		this.idealPoints += idealPoints;
	}

	public String toString() {
		String str = pseudo +"_"+score+"_"+paddle.getX();
		return str;
	}
	
}
