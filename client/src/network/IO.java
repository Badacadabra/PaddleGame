package network;

import game.GameZone;

/**
 * Classe abstraite permet notamment d'éviter la redondance de code.
 * Cette classe est utile pour factoriser les éléments communs à Input et Output.
 * 
 * @author Baptiste Vannesson
 */
public abstract class IO extends Thread {
    
    /** Zone de jeu */
    protected GameZone gameZone;
    
    /** 
     * Constructeur prenant en paramètre la zone de jeu à manipuler
     * 
     * @param gameZone La zone de jeu
     */
    public IO(GameZone gameZone) {
        this.gameZone = gameZone;
    }
    
}
