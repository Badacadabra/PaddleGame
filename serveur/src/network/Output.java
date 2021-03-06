package network;

import game.Ball;
import game.GameDimensions;
import game.Gamer;
import game.GamerManagement;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Server;

/**
 * Classe représentant le flux d'écriture du serveur
 * @author Macky Dieng
 */
public class Output extends Thread {
	
	/**
	 * Référence de la balle
	 */
	private static Ball ball;

	/**
     * La liste des gestionnaires de client
     */
    private List<GamerManagement> listGamerManagement;
    
    /**
     * La liste des joueurs
     */
    private List<Gamer> listGamers;
    
    /**
     * Permet de faire réculer ou anvancer la balle sur l'axe horizontal
     */
    private boolean reverseX;
    
    /**
     * Permet de faire réculer ou anvancer la balle sur l'axe vertical
     */
    private boolean reverseY;
    
    /**
     * Représente la coordonnée X de la balle
     */
    private int ballX;
    
    /**
     * Représente la coordonnée Y de la balle
     */
    private int ballY;
    
    /**
     * Permet de savoir si le Thread peux continuer à envoyer les informations
     */
    private boolean canSend;
    
    /**
     * Constructeur de la classe
     */
	public Output () {
		this.listGamerManagement = Server.listGamerManagement;
		this.listGamers = new ArrayList<>();
        this.canSend = true;
		this.start();
	}
	
	/**
	 *	Méthode génrant l'écriture dans la sortie standard des clients
	 */
	public void run () {
		try {
			createNewBall();
			send();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	
	/**
	 * Méthode gérant l'envoi des informations aux clients
	 */
	private void send() {
		while (canSend){
			for (GamerManagement gm : listGamerManagement) {
				if (!listGamers.contains(gm.getGamer())) {
					 listGamers.add(gm.getGamer());
				 }
				String msg = Primitives.SEND_BALL_COORDS.toString();
		 		this.write(msg,gm.getOut()); //On informe que les infos qui vont suivres concernent la balle
		 		this.moveBall();
		 		msg = Integer.toString(ball.getX()); //Le x de la balle
		 		this.write(msg,gm.getOut());
		     	msg = Integer.toString(ball.getY()); //Le y de la balle
		     	this.write(msg,gm.getOut());
		     	for (Gamer gamer : listGamers) { //Envoi les infos de tout le monde à tous clients
		     		msg = gamer.toString(); 
			     	this.write(msg,gm.getOut());
				}
			}
		}
	}
	/**
	 * Méthode permettant de générer des coordonnées aléatoires pour la balle
	 * @return coodonnées aléatoires
	 */
	private int[] getRandomCoords() {
 		Random r = new Random();
		int low = GameDimensions.MIN_BALL_COORD;
		int heigh = GameDimensions.GAME_ZONE_WIDTH;
		ballX = r.nextInt(heigh - low) + low;
		ballY = r.nextInt(heigh - low) + low;
		int[] coords = {ballX,ballY};
		return coords;
		
 	}
	/**
	 * Méthode qui crée une nouvelle balle
	 */
 	private void createNewBall() {
		int[] coords = this.getRandomCoords();
		ball = Ball.getBall(coords[0], coords[1]);
 	}
 	/**
 	 * Méthode gérant le déplacement de la balle
 	 */
 	private void moveBall() {
 		
 		if (ballX < 1) reverseX = false;
	    if (ballX > GameDimensions.GAME_ZONE_WIDTH - GameDimensions.BALL_DIAMETER) reverseX = true;          
	    if (ballY < 1) reverseY = false;
	    if (ballY > GameDimensions.GAME_ZONE_WIDTH - GameDimensions.BALL_DIAMETER) reverseY = true;
	    
	    // Déplacement en x
	    if (!reverseX) {
	    	ball.setX(++ballX);
	    } else {
	    	ball.setX(--ballX);
	    }
	    
	    // Déplacement en y
	    if (!reverseY) {
	    	ball.setY(++ballY);
	    } else {
	    	ball.setY(--ballY);
	    }
	      
	      // Gestion des collisions
	      int min = Math.abs(ballX - (listGamers.get(0).getPaddle().getX() + (GameDimensions.PADDLE_WIDTH / 2)));
    	  Gamer winner = null;
    	  boolean collisionDetected = false;
	      for (Gamer gamer : listGamers) {
	    	  if (ballY + GameDimensions.BALL_DIAMETER == GameDimensions.PADDLE_Y
	                  && ballX >= gamer.getPaddle().getX()
	                  && ballX <= gamer.getPaddle().getX() + GameDimensions.PADDLE_WIDTH) {
	    		  collisionDetected = true;
	              reverseY = true;
	              int paddleCenter = gamer.getPaddle().getX() + (GameDimensions.PADDLE_WIDTH / 2);
	    		  int diff = Math.abs(ballX - paddleCenter);
	    		  if (diff <= min) {
	    			  min = diff;
	    			  winner = gamer;
	    		  }
	          }
	      }
	      if (collisionDetected) {
	    	  this.calculateScore(true,winner);
	      }
          // Perte de la balle
          if (ballY > GameDimensions.PADDLE_Y + GameDimensions.BALL_DIAMETER + 1) {
        	  int[] coords = this.getRandomCoords();
              ball.setX(coords[0]);
              ball.setY(coords[1]);
              this.calculateScore(false,null);
          }
	      try {
	        Thread.sleep(5);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
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
     * Recalcule automatiquement le score des joueurs
     * @param winnerExist permet de savoir si un vainqueur existe
     * @param winner le vainqueur à qui attribuer le point
     */
    private void calculateScore(boolean winnerExist, Gamer winner) {
    	int points = listGamers.size();
    	for (Gamer gamer : listGamers) {
    		gamer.setIdealPoints(points);
    		if (winnerExist && gamer.equals(winner)) {
    			gamer.setEarnedPoints(points);
    		}
      	  double rawScore = ((double) gamer.getEarnedPoints() / gamer.getIdealPoints());
  		  int percentScore = (int)(rawScore * 100);
  		 gamer.setScore(percentScore);
        }
    }
}
