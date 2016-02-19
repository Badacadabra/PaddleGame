package communication;

/**
 * Enum Primitives : permet de gérer les constantes du protocole de communication
 * 
 * @author Macky Dieng
 * @author Baptiste Vannesson
 */
public enum Primitives {

    SEND_PSEUDO("SEND_PSEUDO"),
    SEND_GAMER_INFO("SEND_GAMER_INFO"),
    SEND_BALL_COORDS("SEND_BALL_COORDS"),
    SEND_NEW_POINTS("SEND_NEW_POINTS"),
    SEND_PADDLE_POSITION("SEND_PADDLE_POSITION");
    
    /** Nom de la primitive */
    private String name;
    
    /**
     * Constructeur de l'énumération
     * 
     * @param name Nom de la primitive
     */
    Primitives(String name) {
        this.name = name;
    }
    
    /**
     * Accesseur pour le nom d'une primitive du protocole
     * 
     * @return Nom de la primitive
     */
    public String getName() {
        return name;
    }
}
