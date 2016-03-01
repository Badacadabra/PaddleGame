package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import network.Output;
import network.Input;

/**
 * Classe GamerManagement : classe permettant de gérer les connexions clients côté serveur
 * @author Macky Dieng
 */
public class GamerManagement extends Thread {
	
	/***
	 * Socket client
	 */
	private Socket client;
	
	/**
	 * Flux d'écriture du client
	 */
	private PrintWriter out;
	
	/**
	 * Flux de lecture du client
	 */
	private BufferedReader in;
	
	/**
	 * La référence du joueur courant
	 */
	private Gamer gamer;
	
	/**
	 * Constructeur de la classe
	 * @param client le client courant en cours de connexion
	 */
	public GamerManagement(Socket client) {
		this.client = client;
		this.gamer = new Gamer(null,0,new Paddle(0));
		try {
			out = new PrintWriter(this.client.getOutputStream());
			in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	/**
	 * Méthode gérant le lancement du gestionnaire
	 */
	public void run() {
		try {
			new Input(this);
			new Output();
		} catch (Exception e) {
			System.out.println("Impossible de démarrer la session"+" "+e.getMessage());
		}
		
	}
	/**
	 * Renvoie le joueur courant associé au gestionnaire
	 * @return Gamer
	 */
	public Gamer getGamer() {
		return gamer;
	}
	/**
	 * Modifie le joueur courant associé au gestionnaire
	 * @param gamer le nouveau joueur à assigner
	 */
	public void setGamer(Gamer gamer) {
		this.gamer = gamer;
	}
	/**
	 * Renvoie la sortie standard
	 * @return la sortie standard
	 */
	public PrintWriter getOut() {
		return out;
	}
	/**
	 * Modifie la sortie standard du client courant
	 * @param out nouvelle sortie à assigner
	 */
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	/**
	 * Renvoie l'entrée standard du client courant
	 * @return l'entrée standard
	 */
	public BufferedReader getIn() {
		return in;
	}
	/**
	 * Modifie l'entrée standard du client
	 * @param in nouvelle entrée à assigner
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	
}
