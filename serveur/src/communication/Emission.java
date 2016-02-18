package communication;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.w3c.dom.ls.LSInput;

import game.Ball;
import game.GameDimensions;
import game.GamerManagement;
/**
 * Classe Emission représente le flux de sortie du serveur
 * @author Macky Dieng
 */
public class Emission extends Thread {
	
	/**
	 * Flux de sortie du serveur
	 */
	private PrintWriter out;
	
	/**
	 * Référence de la balle
	 */
     private Ball ball;
     /**
      * La référence du scanner
      */
     private Scanner sc;
     
     /**
      * Message à envoyer au client
      */
     private String message;
     
     /**
      * La liste des gestionnaires de client
      */
     private List<GamerManagement> gamersManagement;
     /**
      * Constructeur de la classe
      * @param out
      * @param recption
      */
     public Emission(List<GamerManagement> gm, String message) {
    	 this.gamersManagement = gm;
    	 this.message = message;
    	 this.start();
     }
     /**
      * Permet de lancer le thread
      */
     public void run () {
    	Random r = new Random();
		int low = GameDimensions.MIN_BALL_COORD.getValue();
		int heigh = GameDimensions.GAME_ZONE_WIDTH.getValue();
		int x = r.nextInt(heigh - low) + low;
		int y = r.nextInt(heigh - low) + low;
		Ball ball = Ball.getBall(x, y);
    	sendToAll(message);
     }
     /**
      * Envoie les infos à un seul client (Unicast)
      */
     public void sendToOne(int index, String message) {
    	 out = this.gamersManagement.get(index).getOut();
    	 this.write(message, out);
     }
     /**
      * Envoie les infos à tous les clients (Broadcast)
      */
     public void sendToAll(String message) {
    	 for (GamerManagement gm : gamersManagement) {
    		 out = gm.getOut();
        	 this.write(message, out);
		}
     }
     /**
      * Permet d'écrire les messages
      */
     public void write(String message, PrintWriter out) {
    	 out.println(message);
    	 out.flush();
     }

	/**
	 * Renvoie la référence de ball
	 * @return Ball
	 */
	public Ball getBall() {
		return ball;
	}

	/**
	 * Modifie la référence de Ball
	 * @param ball la nouvelle référence à assigner
	 */
	public void setBall(Ball ball) {
		this.ball = ball;
	}
     
}
