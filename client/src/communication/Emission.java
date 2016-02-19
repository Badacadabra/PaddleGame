package communication;

import java.io.PrintWriter;

import game.Gamer;

/**
 * Classe permettant de gérer les flux sortants de manière plus élégante.
 * Elle agit comme un gestionnaire de requêtes vers le serveur.
 * 
 * @author Baptiste Vannesson
 */
public class Emission implements Runnable {

    /** Représentation du flux de sortie (pour l'envoi de données au serveur) */
    private PrintWriter out;
    
    /** Objet de type Gamer désignant ici le joueur courant. */
    private Gamer gamer;
    
    /** Message à envoyer au serveur */
    private String message;
    
    /**
     * Constructeur de la classe.
     * Celui-ci prend en paramètres un objet PrintWriter représentant un flux de sortie, ainsi qu'un objet Gamer représentant le joueur courant.
     * 
     * @param out Objet matérialisant la sortie du client
     * @param gamer Objet matérialisant le joueur courant
     */
    public Emission(PrintWriter out, Gamer gamer) {
        this.out = out;
        this.gamer = gamer;
    }
    
    /**
     * Méthode gérant les interactions effectives avec le serveur.
     * Cette méthode est redéfinie ici car la classe implémente l'interface Runnable.
     * Lorsqu'un Thread est lancé avec une instance de cette classe en paramètre, cette méthode est exécutée. 
     */
    @Override
    public void run() {
        while(true) {
            message = gamer.getPseudo();
            out.println(Primitives.SEND_PSEUDO.getName());
            out.println(message);
            message = "";
            message = Integer.toString(gamer.getPaddle().getX());
            out.println(Primitives.SEND_PADDLE_POSITION.getName());
            out.println(message);
            out.flush();
        }
    }

}
