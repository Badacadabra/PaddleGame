package game;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

/**
 * Classe définissant des règles d'affichage pour les scores.
 * Le but est d'avoir un meilleur rendu visuel.
 * 
 * @author Baptiste Vannesson
 */
public class ScoresRendering extends JLabel implements ListCellRenderer<Gamer> {
    
    /** Constante déterminant la taille de la police */
    public static final byte FONT_SIZE = 17;
    
    /** Constante déterminant la marge interne supérieure */
    public static final byte PADDING_TOP = 0;
    
    /** Constante déterminant la marge interne gauche */
    public static final byte PADDING_LEFT = 0;
    
    /** Constante déterminant la marge interne inférieure */
    public static final byte PADDING_BOTTOM = 0;
    
    /** Constante déterminant la marge interne droite */
    public static final byte PADDING_RIGHT = 20;
    
    @Override
    public Component getListCellRendererComponent(JList<? extends Gamer> list, Gamer gamer, int index,
        boolean isSelected, boolean cellHasFocus) {
        
        setText(gamer.getPseudo() + " : " + gamer.getScore() + " %");
        setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));
        setForeground(gamer.getColor());
        setBorder(new EmptyBorder(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT));
        return this;

    }
     
}
