package network;

import java.io.OutputStream;
import java.io.PrintWriter;

import game.GameZone;

/**
 * Classe permettant de gérer les flux sortants de manière plus élégante.
 * Elle agit comme un gestionnaire de requêtes vers le serveur.
 * 
 * @author Baptiste Vannesson
 */
public class Output extends IO {

    /** Représentation du flux de sortie (pour l'envoi de données au serveur) */
    private PrintWriter out;
    
    /**
     * Constructeur de la classe.
     * Celui-ci prend en paramètres un objet OutputStream représentant un flux de sortie, ainsi qu'un objet GameZone gérant toute la partie.
     * Cela est suffisant : le joueur n'envoie au serveur que des informations le concernant (notamment son pseudo et la position de sa raquette).
     * À noter que le flux de sortie est décoré d'un PrintWriter lors de l'initialisation...
     * 
     * @param out Flux de sortie
     * @param gameZone Zone de jeu
     */
    public Output(OutputStream out, GameZone gameZone) {
        super(gameZone);
        this.out = new PrintWriter(out); // Pattern Decorator
    }
    
    /**
     * Méthode gérant les interactions effectives avec le serveur, en écriture.
     * 
     * @param primitive Constante de l'énumération définissant les primitives du protocole de communication
     */
    private void write(Primitives primitive) {
        // Le client envoie son pseudo au serveur
        if (primitive == Primitives.SEND_PSEUDO) {
            String pseudo = gameZone.getGamer().getPseudo();
            out.println(Primitives.SEND_PSEUDO.toString());
            out.println(pseudo);
        }
        // Le client envoie la position de sa raquette au serveur
        if (primitive == Primitives.SEND_PADDLE_POSITION) {
            String paddlePosition = Integer.toString(gameZone.getGamer().getPaddle().getX());
            out.println(Primitives.SEND_PADDLE_POSITION.toString());
            out.println(paddlePosition);
        }
        out.flush();
    }
    
    /**
     * Méthode du thread gérant l'écriture dans la sortie standard
     */
    @Override
    public void run() {
        // Si cette méthode est exécutée, c'est que le thread est lancé, et donc que l'utilisateur a renseigné son pseudo
        write(Primitives.SEND_PSEUDO);
        while(true) {
            try {
                // La position de la raquette est envoyée en continu au serveur
                write(Primitives.SEND_PADDLE_POSITION);
                Thread.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

}
