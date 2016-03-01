package network;

import game.GameZone;

/**
 * Classe abstraite d'entrées/sorties permettant d'éviter la redondance de code.
 * Cette classe est utile pour factoriser les éléments communs à Input et Output.
 * 
 * @author Baptiste Vannesson
 */
public abstract class IO implements Runnable {
    
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
