package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
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
	 * Le flux de lecture 
	 */
	private BufferedReader in;
	
	/**
	 * La référence du joueur
	 */
	private Gamer gamer;
	
	private List<Gamer> listgamer;
	/**
	 * Message reçu du client
	 */
	private String message = null;
	
	/**
	 * Construteur de la classe
	 */
	public Reception(BufferedReader in) {
		this.in = in;
		this.start();
	}
	
	/**
	 * Permet de lancer le thread
	 */
	public void run() {
		
		try {
			listgamer = new ArrayList<>();
			while(true) {
				message = in.readLine();
				
				if (message.equals(Primitives.SEND_PSEUDO)) {
					String pseudo = message;
					gamer = new Gamer(pseudo,0,new Paddle(30));
					listgamer.add(gamer);
					
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Renvoie le flux de lecture
	 * @return BufferedReader
	 */
	public BufferedReader getIn() {
		return in;
	}

	/**
	 * Modifie le flux de lecture
	 * @param in le nouveau flux à assigner
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}

	/**
	 * Renvoie la référence du joueur
	 * @return Gamer
	 */
	public Gamer getGamer() {
		return gamer;
	}

	/**
	 * Modifie la référence du joueur
	 * @param gamer la nouvelle référence à assigner
	 */
	public void setGamer(Gamer gamer) {
		this.gamer = gamer;
	}

	/**
	 * Renvoie la liste dees joueurs
	 * @return List<Gamer>
	 */
	public List<Gamer> getListgamer() {
		return listgamer;
	}

	/**
	 * Modifie la liste des joueurs
	 * @param listgamer nouvelle liste à assigner
	 */
	public void setListgamer(List<Gamer> listgamer) {
		this.listgamer = listgamer;
	}
	
	
}
