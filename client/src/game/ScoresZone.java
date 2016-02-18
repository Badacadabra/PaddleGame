package game;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;

/**
 * Classe définissant une zone d'affichage pour les scores.
 * On part ici du principe qu'il s'agit d'un objet de type JList.
 * 
 * @author Baptiste Vannesson
 */
public class ScoresZone extends JList {

    /** Liste de joueurs à afficher */
    private List<Gamer> gamers = new ArrayList<>();
    
    /**
     * Constructeur de la zone d'affichage des scores.
     * Ce dernier prend en paramètre une liste de joueurs, en l'occurrence tous ceux qui sont connectés...
     * 
     * @param gamers La liste des joueurs connectés au jeu
     */
    public ScoresZone(Object[] gamers) {
        super(gamers);
        setAlignmentY(TOP_ALIGNMENT);
    }
    
}
