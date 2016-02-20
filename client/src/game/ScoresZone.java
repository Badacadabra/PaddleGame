package game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * Classe définissant une zone d'affichage pour les scores.
 * On part ici du principe qu'il s'agit d'un objet de type JList.
 * 
 * @author Baptiste Vannesson
 */
public class ScoresZone extends JScrollPane {
    
    /** Constante déterminant la marge interne supérieure */
    public static final int PADDING_TOP = 0;
    
    /** Constante déterminant la marge interne gauche */
    public static final int PADDING_LEFT = 0;
    
    /** Constante déterminant la marge interne inférieure */
    public static final int PADDING_BOTTOM = 0;
    
    /** Constante déterminant la marge interne droite */
    public static final int PADDING_RIGHT = 3;
    
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
