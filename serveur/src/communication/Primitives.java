/**
 * 
 */
package communication;

/**
 * Enum Primitives : permet de gérer les constantes du protocole
 * de communication
 * @author Macky Dieng
 * @author Baptiste Vannesson
 */
public enum Primitives {
	
	SEND_PSEUDO("SEND_PSEUDO"),
	SEND_GAMER_INFO("SEND_GAMER_INFO"),
	SEND_BALL_COORDS("SEND_BALL_COORDS"),
	SEND_NEW_POINTS("SEND_NEW_POINTS"),
	SEND_PADDLE_INFO("SEND_PADDLE_INFO");
	
	/**
	 * Nom de la primitive
	 */
	private final String name;
	/**
	 * Constructeur de la l'unémeration
	 * @param name : le nom de la primitive
	 */
	Primitives (final String name) {
		this.name = name;
	}
	/**
	 * Renvoie le nom de la primitive
	 * @return String
	 */
	public String getName() {
		return name;
	}
}
