package game;

import java.awt.Color;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * Classe définissant une zone d'affichage pour les scores.
 * Il s'agit ici d'un conteneur avec ascenseur vertical.
 * Utile si le jeu accueille un grand nombre de joueurs (car les scores sortent alors de l'écran)
 *  
 * @author Baptiste Vannesson
 */
public class ScoresZone extends JScrollPane {
    
    /** Constante déterminant la marge interne supérieure */
    public static final byte PADDING_TOP = 0;
    
    /** Constante déterminant la marge interne gauche */
    public static final byte PADDING_LEFT = 0;
    
    /** Constante déterminant la marge interne inférieure */
    public static final byte PADDING_BOTTOM = 0;
    
    /** Constante déterminant la marge interne droite */
    public static final byte PADDING_RIGHT = 3;
    
    /**
     * Constructeur de la zone d'affichage des scores.
     * Ce dernier prend en paramètre un modèle de liste.
     * Lors de la mise à jour de ce modèle, notre vue sera repeinte automatiquement.
     * 
     * @param gamers La liste des joueurs connectés au jeu
     */
    public ScoresZone(JList<Gamer> gamers) {
        super(gamers);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setBorder(new EmptyBorder(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT));
        setBackground(Color.LIGHT_GRAY);
    }
    
}
