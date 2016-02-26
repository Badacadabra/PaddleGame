package game;

/**
 * Enumeration GameDimensions : Définit les constantes du jeu
 * @author Macky Dieng
 * @author Baptiste Vannesson
 
public enum GameDimensions {
	
	GAME_ZONE_WIDTH(600),
	GAME_ZONE_HEIGHT(600),
	BALL_DIAMETER(20),
	MIN_BALL_COORD(0);
	/**
	 * Valeur entière
	 *
	private final int value;
	
	GameDimensions (final int val) {
		value = val;
	}
	/**
	 * Renvoie la valeur de la primitive
	 * @return int
	 
	public int getValue() {
		return value; 
	}*
	
}*/

public class GameDimensions {
	public static final int GAME_ZONE_WIDTH = 600;
	public static final int GAME_ZONE_HEIGHT = 600;
	public static final int BALL_DIAMETER = 20;
	public static final int MIN_BALL_COORD = 0;
	public static final int PADDLE_WIDTH = 100;
	public static final int PADDLE_HEIGHT = 15;
	public static final int PADDLE_Y = 550;
}
