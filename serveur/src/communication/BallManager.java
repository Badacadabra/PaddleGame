package communication;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Server;
import game.Ball;
import game.GameDimensions;
import game.Gamer;
import game.GamerManagement;

public class BallManager extends Thread {
	
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
    
    private String primitive;
    private boolean reverseX, reverseY,paddleBackX;
    private int ballX,ballY,paddleX;
    private boolean canSend = true;
    
	public BallManager () {
		this.listGamerManagement = Server.listGamerManagement;
		this.listGamers = new ArrayList<>();
		this.start();
	}
	
	public void run () {
		try {
			createNewBall();
			sendBallInfos();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	public void sendBallInfos() {
		while (canSend){
			for (GamerManagement gm : listGamerManagement) {
				//System.out.println(gm.getGamer());
				if (!listGamers.contains(gm.getGamer())) {
					 listGamers.add(gm.getGamer());
				 }
				String msg = Primitives.SEND_BALL_COORDS;
		 		this.write(msg,gm.out); //On informe que les infos qui vont suivre concerne la balle
		 		this.moveBall();
		 		msg = Integer.toString(ball.getX()); //Le x de la balle
		 		this.write(msg,gm.out);
		     	msg = Integer.toString(ball.getY()); //Le y de la balle
		     	this.write(msg,gm.out);
		     	//msg = Primitives.SEND_GAMERS_INFO; 
		     	//this.write(msg,gm.out); //On informe que les infos qui vont suivre concerne les joueurs
		     	//this.write(Integer.toString(listGamerManagement.size()), gm.out); //Envoi du nombre de joueurs*/
		     	for (Gamer gamer : listGamers) {
		     		msg = gamer.toString(); 
		     		//System.out.println(msg);
			     	this.write(msg,gm.out);//On envoie le message
				}
			}
		}
	}
	private int[] getRandomCoords() {
 		Random r = new Random();
		int low = GameDimensions.MIN_BALL_COORD;
		int heigh = GameDimensions.GAME_ZONE_WIDTH;
		ballX = r.nextInt(heigh - low) + low;
		ballY = r.nextInt(heigh - low) + low;
		int[] coords = {ballX,ballY};
		return coords;
		
 	}
 	private synchronized void createNewBall() {
		int[] coords = this.getRandomCoords();
		ball = Ball.getBall(coords[0], coords[1]);
 	}
 	private void moveBall() {
 		int collisions = 0;
        int earnedPoints = 0;
        //Paddle paddle  = gamer.getPaddle();
 		if(ballX < 1)reverseX = false;
	      if(ballX > GameDimensions.GAME_ZONE_WIDTH - GameDimensions.BALL_DIAMETER)reverseX = true;          
	      if(ballY < 1)reverseY = false;
	      if(ballY > GameDimensions.GAME_ZONE_WIDTH - GameDimensions.BALL_DIAMETER)reverseY = true;
	      if(!reverseX)ball.setX(++ballX);
	      else ball.setX(--ballX);
	      if(!reverseY) ball.setY(++ballY);
	      else ball.setY(--ballY);
	      
	      // Gestion des collisions
	      for (Gamer gamer : listGamers) {
	    	  if (ballY + GameDimensions.BALL_DIAMETER == GameDimensions.PADDLE_Y
	                  && ballX >= gamer.getPaddle().getX()
	                  && ballX <= gamer.getPaddle().getX() + GameDimensions.PADDLE_WIDTH) {
	              reverseY = true;
	              //System.out.println("Collision");
	              collisions++;
	              earnedPoints += listGamerManagement.size();
	              gamer.setScore((earnedPoints / (collisions * listGamerManagement.size())) * 100);
	          }
	      }
          //Perte de la balle 
          if (ballY > GameDimensions.PADDLE_Y + GameDimensions.BALL_DIAMETER) {
        	  int[] coords = this.getRandomCoords();
              ball.setX(coords[0]);
              ball.setY(coords[1]);
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
}
