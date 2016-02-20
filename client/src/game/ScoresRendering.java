package game;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

public class ScoresRendering extends JLabel implements ListCellRenderer<Gamer> {
    
    /** Constante déterminant la taille de la police */
    public static final int FONT_SIZE = 17;
    
    /** Constante déterminant la marge interne supérieure */
    public static final int PADDING_TOP = 0;
    
    /** Constante déterminant la marge interne gauche */
    public static final int PADDING_LEFT = 0;
    
    /** Constante déterminant la marge interne inférieure */
    public static final int PADDING_BOTTOM = 0;
    
    /** Constante déterminant la marge interne droite */
    public static final int PADDING_RIGHT = 20;
    
    @Override
    public Component getListCellRendererComponent(JList<? extends Gamer> list, Gamer gamer, int index,
        boolean isSelected, boolean cellHasFocus) {
        
        String pseudo = gamer.getPseudo();
        setText(gamer.getPseudo() + " : " + gamer.getScore() + " %");
        setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));
        setForeground(gamer.getColor());
        setBorder(new EmptyBorder(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT));
        return this;

    }
     
}
