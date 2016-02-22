package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.*;
/**
 * Classe Reception répresente le flux de lecture du serveur
 * @author Macky Dieng
 */
public class Reception extends Thread {
	
	/**
	 * Flux de lecture du client
	 */
	private BufferedReader in;
	/**
	 * Flux de d'écriture du client
	 */
	private PrintWriter out;
	/**
	 * La référence du joueur
	 */
	private Gamer gamer;
	
	/**
	 * Message à renvoyé aux joueurs
	 */
	private String message;
	
	/**
	 * La primitive courante
	 */
	private String primitive;
	
	/**
	 * Constructeur de la classe
	 * @param in le flux de lecture
	 */
	public Reception(BufferedReader in,PrintWriter out) {
		this.in = in;
		this.out = out;
		this.start();
	}
	
	/**
	 * Permet de lancer le Thread
	 */
	public void run() {
		
		try {
			boolean canRead = true;
	
			while(canRead) {
				message = in.readLine();
				if (message!=null) {
					if (message.equals(Primitives.SEND_PSEUDO)) {
						primitive = message;
						String pseudo  = in.readLine();
						gamer = new Gamer(pseudo,0,new Paddle(30));
						message = " ";
						message += gamer.toString()+"\n"; 
						new Emission(out,message,primitive);
					}
				} else {
					canRead = false;
				}
			} 
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Renvoie le message à envoyer
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Modifie le message à envoyer
	 * @param message nouveau message à assigner
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Renvoie la primitive courante de communication
	 * @return String
	 */
	public String getPrimitive() {
		return primitive;
	}

	/**
	 * Modifie la primitive courante de communication
	 * @param primitive the primitive to set
	 */
	public void setPrimitive(String primitive) {
		this.primitive = primitive;
	}
	
}
