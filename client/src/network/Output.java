package network;

import java.io.OutputStream;
import java.io.PrintWriter;

import game.Gamer;

/**
 * Classe permettant de gérer les flux sortants de manière plus élégante.
 * Elle agit comme un gestionnaire de requêtes vers le serveur.
 * 
 * @author Baptiste Vannesson
 */
public class Output extends Thread {

    /** Représentation du flux de sortie (pour l'envoi de données au serveur) */
    private PrintWriter out;
    
    /** Objet de type Gamer, désignant ici le joueur courant. */
    private Gamer gamer;
    
    /**
     * Constructeur de la classe.
     * Celui-ci prend en paramètres un objet OutputStream représentant un flux de sortie, ainsi qu'un objet Gamer représentant le joueur courant.
     * Cela est suffisant : le joueur n'envoie au serveur que des informations le concernant (notamment son pseudo, ses points, et la position de sa raquette).
     * À noter que le flux de sortie est décoré d'un PrintWriter lors de l'initialisation...
     * 
     * @param out Objet matérialisant la sortie du client
     * @param gamer Objet matérialisant le joueur courant
     */
    public Output(OutputStream out, Gamer gamer) {
        this.out = new PrintWriter(out); // Pattern Decorator
        this.gamer = gamer;
    }
    
    /**
     * Méthode gérant les interactions effectives avec le serveur, en écriture.
     * 
     * @param primitive Constante de l'énumération définissant les primitives du protocole de communication
     */
    public void write(Primitives primitive) {
        if (primitive.getName().equals("SEND_PSEUDO")) {
            String pseudo = gamer.getPseudo();
            out.println(Primitives.SEND_PSEUDO.getName());
            out.println(pseudo);
        } else if (primitive.getName().equals("SEND_PADDLE_POSITION")) {
            String paddlePosition = Integer.toString(gamer.getPaddle().getX());
            out.println(Primitives.SEND_PADDLE_POSITION.getName());
            out.println(paddlePosition);
        } else if (primitive.getName().equals("SEND_NEW_POINTS")) {
            String newPoints = Integer.toString(gamer.getScore());
            out.println(Primitives.SEND_NEW_POINTS.getName());
            out.println(newPoints);
        } else {
            System.out.println("Primitive inconnue");
        }
        out.flush();
    }
    
    /**
     * Méthode gérant l'écriture dans la sortie standard
     */
    public void run() {
        write(Primitives.SEND_PSEUDO);
        while(true) {
            try {
                write(Primitives.SEND_PADDLE_POSITION);
                // write(Primitives.SEND_NEW_POINTS);
                Thread.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
