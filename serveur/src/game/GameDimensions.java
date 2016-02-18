package game;

/**
 * Enumeration GameDimensions : Définit les constantes du jeu
 * @author Macky Dieng
 * @author Baptiste Vannesson
 */
public enum GameDimensions {
	
	GAME_ZONE_WIDTH(600),
	GAME_ZONE_HEIGHT(600),
	MIN_BALL_COORD(0);
	/**
	 * Valeur entière
	 */
	private final int value;
	
	GameDimensions (final int val) {
		value = val;
	}
	
	public int getValue() { return value; }
	
}
