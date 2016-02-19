package communication;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.w3c.dom.ls.LSInput;

import game.*;
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
      * La liste des joueurs
      */
     private List<Gamer> gamers;
     
     private int i = 0;
     /**
      * 
      */
     private String primitive;
     /**
      * Constructeur de la classe
      * @param out
      * @param recption
      */
     public Emission(PrintWriter out,String message,String primitive) {
    	 this.message = message;
    	 this.primitive = primitive;
    	 this.out = out;
    	 this.start();
     }
     /**
      * Permet de lancer le thread
      */
     public void run () {
    	 System.out.println(primitive);
    	 System.out.println(Primitives.SEND_PSEUDO.getName());
    	if (primitive.equals(Primitives.SEND_PSEUDO.getName())) { //Si la reception nous indiques avoir reçu le pseudo
    		//On crée une nouvelle balle aléatoirement
    		Random r = new Random();
    		int low = GameDimensions.MIN_BALL_COORD.getValue();
    		int heigh = GameDimensions.GAME_ZONE_WIDTH.getValue();
    		int x = r.nextInt(heigh - low) + low;
    		int y = r.nextInt(heigh - low) + low;
    		ball = Ball.getBall(x, y);
    		String msg = Primitives.SEND_BALL_COORDS.getName();
    		this.write(msg); //On informe que les infos qui vont suivre concerne la balle
    		msg = "PosX = "+Integer.toString(ball.getX());
    		this.write(msg);
        	msg = "PosY = "+Integer.toString(ball.getY());
        	this.write(msg);
        	msg = Primitives.SEND_GAMER_INFO.getName();
        	this.write(msg); //On informe que les infos qui vont suivre concerne les joueurs
        	this.write(message);//On envoie le message délégué par la Reception
    	}
    	
     }
     /**
      * Permet de déclencher l'envoie des messages au client
      */
     public void startSending() {
    	 //this.start();
     }
     /**
      * Permet d'écrire les messages dans le flux d'écriture des clients
      * @param message le message à écrire
      */
     public void write(String message) {
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
	public String toString () {
		return "Emission_"+i++;
	}
}
