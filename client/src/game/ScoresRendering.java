package game;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ScoresRendering extends JLabel implements ListCellRenderer<Gamer> {
    
    public static final int FONT_SIZE = 17;
    
    @Override
    public Component getListCellRendererComponent(JList<? extends Gamer> list, Gamer gamer, int index,
        boolean isSelected, boolean cellHasFocus) {
        
        String pseudo = gamer.getPseudo();
        setText(gamer.getPseudo() + " : " + gamer.getScore() + " %");
        setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));
        setForeground(gamer.getColor());
        return this;

    }
     
}
