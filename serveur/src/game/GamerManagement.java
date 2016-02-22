package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import communication.*;
/**
 * Class GameManagement : classe permettant de gérer les joueurs 
 * côté serveur 
 * @author Macky Dieng
 * http://stackoverflow.com/questions/29635960/sending-a-message-from-server-to-all-clients
 */
public class GamerManagement extends Thread {
	
	/***
	 * Socket cliente
	 */
	private Socket client;
	
	/**
	 * Flux d'écture du client
	 */
	private PrintWriter out;
	
	/**
	 * Flux de lecture du client
	 */
	private BufferedReader in;
	
	/**
	 * La référence de l'objet reception du serveur
	 */
	private Reception reception;
	
	/**
	 * La référence de l'objet Emission du serveur
	 */
	private Emission emission;
	
	private String message;
	private Gamer gamer;
	private static Ball ball; //Une seule balle pour tous les joueurs
	private static List<GamerManagement> gamersManagement = new ArrayList<>();
	private static int index = 0;
	private String pseudo;
	/**
	 * Constructeur de la classe
	 * @param client
	 */
	public GamerManagement(int index,Socket client) {
		//this.client = client;
		this.index  = index;
		this.client = client;
		try {
			out = new PrintWriter(this.client.getOutputStream());
			in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//this.start();
	}
	@Override
	public void run() {
		
		try {
			boolean canRead = true;
	
			while(canRead) {
				//this.write("Bonjour vous êtes le client N°"+index);
				message = in.readLine();
				//System.out.println(message);
				if (message!=null) {
					if (message.equals(Primitives.SEND_PSEUDO)) {
						pseudo  = in.readLine(); //Pseudo du client courant
						if (gamersManagement.size() < 2) { //Si on déjà un joueur, alors on crée plus de ball
							Random r = new Random();
				    		int low = GameDimensions.MIN_BALL_COORD.getValue();
				    		int heigh = GameDimensions.GAME_ZONE_WIDTH.getValue();
				    		int x = r.nextInt(heigh - low) + low;
				    		int y = r.nextInt(heigh - low) + low;
				    		ball = Ball.getBall(x, y);
						}
			    		//this.setBall(ball);
			    		for (GamerManagement gm : gamersManagement) {
				        	this.sendGamerInfos(gm,pseudo);//Les infos sont envoyées à tous les joueurs
			    		}
					}
					//message = in.readLine();
					if (message.equals(Primitives.SEND_PADDLE_POSITION)) {
						for (GamerManagement gm : gamersManagement) {
							//System.out.println(ball.getX());
							ball.setX(ball.getX()+5);
							ball.setY(ball.getY()+5);
							this.sendGamerInfos(gm,pseudo);
						}
					}
					//message = in.readLine();
					if (message.equals(Primitives.SEND_NEW_POINTS)) {
						for (GamerManagement gm : gamersManagement) {
							this.write(in.readLine(), gm.out);
							this.sendGamerInfos(gm,pseudo);
						}
					}
				} else {
					canRead = false;
				}
				 /*try {
					 Thread.sleep(20);
				 } catch (InterruptedException e) {
					 e.printStackTrace();
				 }*/
			} 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the ball
	 */
	public Ball getBall() {
		return ball;
	}
	/**
	 * @param ball the ball to set
	 */
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	/**
	 * Méthode helper, permettant d'envoyer l'infos des joueurs
	 * @param gm le gestion de client courant
	 */
	public void sendGamerInfos(GamerManagement gm,String pseudo) {
		String msg = Primitives.SEND_BALL_COORDS;//On informe que les infos qui vont suivre concerne la balle
		this.write(msg,gm.out); 
		msg = Integer.toString(ball.getX());
		this.write(msg,gm.out);
    	msg = Integer.toString(ball.getY());
    	this.write(msg,gm.out);
		msg = Primitives.SEND_GAMER_INFO; 
    	this.write(msg,gm.out); //On informe que les infos qui vont suivre concerne les joueurs
    	this.write(Integer.toString(gamersManagement.size()), gm.out); //Envoie du nombre de joueurs
    	gamer = new Gamer(pseudo,0,new Paddle(30));
		message = gamer.toString()+"\n"; 
    	this.write(message,gm.out);//On envoie le message
    	//gm.out.flush();
	}
	/**
     * Permet d'écrire les messages dans le flux d'écriture des clients
     * @param message le message à écrire
     */
    public void write(String message,PrintWriter out) {
	   	 out.println(message);
	   	 out.flush();
    }
    public void setGamerManagement(List<GamerManagement> gm){
    	gamersManagement = gm;
    }
	/**
	 * Renvoie la référence Reception du serveur
	 * @return Reception
	 */
	public Reception getReception() {
		return reception;
	}
	/**
	 * Modifie la référence Reception du serveur
	 * @param reception la nouvelle référence à assigner
	 */
	public void setReception(Reception reception) {
		this.reception = reception;
	}
	/**
	 * Renvoie la référence Emission du serveur
	 * @return Emission
	 */
	public Emission getEmission() {
		return emission;
	}
	/**
	 * Modifie la référence Emission du serveur
	 * @param emission nouvelle référence à assigner
	 */
	public void setEmission(Emission emission) {
		this.emission = emission;
	}
	public String toString() {
		String str ="Client N°"+index+"\n";
		str+="NB_Client = "+gamersManagement.size();
		return str;
	}
	
}
