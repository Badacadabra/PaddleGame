package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import game.Gamer;

/**
 * Classe permettant de gérer les flux entrants de manière plus élégante.
 * Elle agit comme un gestionnaire de réponses venant du serveur.
 * 
 * @author Baptiste Vannesson
 */
public class Reception implements Runnable {

    /** Représentation du flux d'entrée (pour la réception de données venant du serveur) */
    private BufferedReader in;
    
    /** Liste de tous les joueurs connectés au jeu. */
    private List<Gamer> gamers;
    
    /** Message reçu du serveur */
    private String message = null;
    
    /**
     * Constructeur de la classe.
     * Celui-ci prend en paramètres un objet BufferedReader représentant un flux d'entrée, ainsi qu'une liste d'objets Gamer représentant tous les joueurs.
     */
    public Reception(BufferedReader in, List<Gamer> gamers) {
        this.in = in;
        for (Gamer gamer : gamers) {
            this.gamers.add(gamer);
        }
    }
    
    /**
     * Méthode gérant les interactions effectives avec le serveur.
     * Cette méthode est redéfinie ici car la classe implémente l'interface Runnable.
     * Lorsqu'un Thread est lancé avec une instance de cette classe en paramètre, cette méthode est exécutée.
     */ 
    @Override
    public void run() {
        while(true) {
            try {
                message = in.readLine();
                if (message.equals(Primitives.SEND_GAMER_INFO)) {
                    System.out.println("Réception des informations de tous les joueurs");
                } else if(message.equals(Primitives.SEND_BALL_COORDS)) {
                    System.out.println("Réception de la position de la balle");
                } else {
                    System.out.println("Primitive inconnue");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
