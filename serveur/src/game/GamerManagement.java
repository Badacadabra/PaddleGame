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
	public PrintWriter out;
	
	/**
	 * Flux de lecture du client
	 */
	public BufferedReader in;
	/**
	 * Liste des gestionnaires de joueurs
	 */
	private List<GamerManagement> gamersManagement = new ArrayList<>();
	/**
	 * L'index du client cour
	 */
	public int index = 0;
	
	/**
	 * La référence du joueur courant
	 */
	private Gamer gamer;
	
	/**
	 * Constructeur de la classe
	 * @param client
	 */
	public GamerManagement(int index,Socket client) {
		this.client = client;
		this.index = index;
		this.gamer = new Gamer(null,0,new Paddle(GameDimensions.PADDLE_X));
		try {
			out = new PrintWriter(this.client.getOutputStream());
			in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		try {
			new Reception(this);
		} catch (Exception e) {
			System.out.println("Impossible de démarrer la session"+" "+e.getMessage());
		}
		
	}
	/**
	 * Modifie la liste des gamers management
	 * @param gm nouvelle liste à assigner
	 */
    public void setGamerManagement(List<GamerManagement> gm){
    	gamersManagement = gm;
    }
    /**
     * Renvoie la liste des gamers management
     * @return List<GamerManagement>
     */
    public List<GamerManagement> getGamerManagement(){
    	return gamersManagement;
    }
    
	/**
	 * Renvoie la référence du joueur courant
	 * @return Gamer
	 */
	public Gamer getGamer() {
		return gamer;
	}
	/**
	 * Modifie la référenc du joueur courant
	 * @param gamer la nouvelle référence à assgner
	 */
	public void setGamer(Gamer gamer) {
		this.gamer = gamer;
	}
	public String toString() {
		String str ="Client N°"+index+"\n";
		str+="NB_Client = "+gamersManagement.size();
		return str;
	}
	
	
}
