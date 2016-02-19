package game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

/**
 * Classe définissant une zone d'affichage pour les scores.
 * On part ici du principe qu'il s'agit d'un objet de type JList.
 * 
 * @author Baptiste Vannesson
 */
public class ScoresZone extends JList {
    
    /** Constante déterminant la marge interne supérieure */
    public static final int PADDING_TOP = 10;
    
    /** Constante déterminant la marge interne gauche */
    public static final int PADDING_LEFT = 10;
    
    /** Constante déterminant la marge interne inférieure */
    public static final int PADDING_BOTTOM = 10;
    
    /** Constante déterminant la marge interne droite */
    public static final int PADDING_RIGHT = 10;
    
    /**
     * Constructeur de la zone d'affichage des scores.
     * Ce dernier prend en paramètre un modèle de liste.
     * Lors de la mise à jour de ce modèle, notre vue sera repeinte automatiquement.
     * 
     * @param gamers La liste des joueurs connectés au jeu
     */
    public ScoresZone(DefaultListModel model) {
        super(model);
        setBackground(Color.DARK_GRAY);
        setAlignmentX(CENTER_ALIGNMENT);
        setBorder(new EmptyBorder(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT));
    }
    
}
