package communication;

import java.io.PrintWriter;
import java.util.ArrayList;
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
	 * Référence de la balle
	 */
	private static Ball ball;
     
     /**
      * Message à envoyer au client
      */
     private String message;
     
     /**
      * La liste des gestionnaires de client
      */
     private List<GamerManagement> gamersManagement;
     /**
      * Le gestionnaire de client courant
      */
     private GamerManagement gamerManagement;
     
     /**
      * Le pseudo du client courant
      */
     private String pseudo;
     
     /**
      * Le joueur courant
      */
     //private Gamer gamer;
     
     /**
      * La primitve courante
      */
     private String primitive;
     private boolean backX, backY,paddleBackX;
     private int x,y,paddleX;
     private boolean canSend;
     private ArrayList<String> listPrimitve;
     //private Paddle paddle;
     /**
      * Le constructeur de la classe
      * @param gm le gestionnaire de client courant
      * @param pseudo le pseudo du client courant
      * @param primitive la primitive courante
      */
     public Emission(GamerManagement gm,String primitive) {
    	 this.canSend = true;
    	 this.gamerManagement = gm;
    	 this.gamersManagement = gamerManagement.getGamerManagement();
    	 this.primitive = primitive;
    	 listPrimitve = new ArrayList<>();
    	 listPrimitve.add(Primitives.SEND_PSEUDO);
    	 listPrimitve.add(Primitives.SEND_PADDLE_POSITION);
    	 //this.paddleX = 0;
    	 //this.paddle = new Paddle(paddleX);
    	 //this.gamer = new Gamer(pseudo,0,paddle);
    	 this.start();
     }
     /**
      * Permet de lancer le thread
      */
     public void run () {
    	 //System.out.println(gm.index);
    	 try {
 			
    		 if (listPrimitve.contains(primitive)) {
    			 if (primitive.equals(Primitives.SEND_PSEUDO)) {
    				 this.createNewBall();
    			 }
    			 this.sendInfos();
    		 }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
     }
     /**
 	 * Méthode helper, permettant d'envoyer l'infos des joueurs
 	 * @param gm le gestion de client courant
 	 */
 	private void sendGamerInfos(GamerManagement gm) {
 			//this.canSend = true;
	 		String msg = Primitives.SEND_BALL_COORDS;//On informe que les infos qui vont suivre concerne la balle
	 		Gamer gamer = gm.getGamer(); 
	 		Paddle paddle = gm.getGamer().getPaddle();
	 		this.write(msg,gm.out); 
	 		this.moveBall();
	 		this.movePaddle(paddle);
	 		msg = Integer.toString(ball.getX());
	 		this.write(msg,gm.out);
	     	msg = Integer.toString(ball.getY());
	     	this.write(msg,gm.out);
	     	msg = Integer.toString(paddle.getX()); //Paddle x
	     	this.write(msg,gm.out);
	     	if (ball.getY()==GameDimensions.PADDLE_Y) {
	    		gamer.setScore(gamer.getScore()+2);
	    		//System.out.println("toto");
	    	}
	     	this.write(Integer.toString(gamer.getScore()),gm.out);
	     	//if ()
	 		msg = Primitives.SEND_GAMER_INFO; 
	     	this.write(msg,gm.out); //On informe que les infos qui vont suivre concerne les joueurs
	     	this.write(Integer.toString(gamersManagement.size()), gm.out); //Envoie du nombre de joueurs
	 		message = gamer.toString()+"\n"; 
	     	this.write(message,gm.out);//On envoie le message
 	}
 	private void sendInfos() {
 		while (this.canSend) {
			 for (GamerManagement gm : gamersManagement) {
				 this.sendGamerInfos(gm);
			 }
		 }
 	}
 	private void createNewBall() {
		Random r = new Random();
		int low = GameDimensions.MIN_BALL_COORD;
		int heigh = GameDimensions.GAME_ZONE_WIDTH;
		x = r.nextInt(heigh - low) + low;
		y = r.nextInt(heigh - low) + low;
		ball = Ball.getBall(x, y);
 	}
 	private void moveBall() {
 		if(x < 1)backX = false;
	      if(x > GameDimensions.GAME_ZONE_WIDTH - GameDimensions.BALL_DIAMETER)backX = true;          
	      if(y < 1)backY = false;
	      if(y > GameDimensions.GAME_ZONE_WIDTH - GameDimensions.BALL_DIAMETER)backY = true;
	      if(!backX)ball.setX(++x);
	      else ball.setX(--x);
	      if(!backY) ball.setY(++y);
	      else ball.setY(--y);

	      try {
	        Thread.sleep(7);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
 	} 
 	private void movePaddle(Paddle paddle) {
 		if(paddleX < 1)paddleBackX = false;
	      if(paddleX > GameDimensions.GAME_ZONE_WIDTH - GameDimensions.PADDLE_WIDTH)paddleBackX = true;          
	      if(!paddleBackX)paddle.setX(++paddleX);
	      else paddle.setX(--paddleX);
	      
	      /*try {
	        Thread.sleep(5);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }*/
 	} 
     /**
      * Permet d'écrire les messages dans le flux d'écriture des clients
      * @param message le message à écrire
      */
     private void write(String message,PrintWriter out) {
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
