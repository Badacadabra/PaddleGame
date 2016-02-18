package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import communication.*;
/**
 * Class GameManagement : classe permettant de gérer les joueurs 
 * côté serveur 
 * @author Macky Dieng
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
	
	public GamerManagement(Socket client) {
		this.client = client;
		this.start();
	}
	@Override
	public void run() {
		try {
			out = new PrintWriter(client.getOutputStream());
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			this.setIn(in);
			this.setOut(out);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Renvoie le client actuel connecté au serveur
	 * @return Socket
	 */
	public Socket getClient() {
		return this.client;
	}
	/**
	 * Permet de modifier le client actuel
	 * @param client : Nouveau client à assigner
	 * @return void
	 */
	public void setClient(Socket client) {
		this.client = client;
	}
	/**
	 * Renvoie le flux d'écriture du client courant
	 * @return PrintWriter
	 */
	public PrintWriter getOut() {
		return out;
	}
	/**
	 * Modifie le flux d'écriture du client courant
	 * @param out : le nouveau flux à assigner
	 */
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	/**
	 * Renvoie le flux de lecture du client courant
	 * @return BufferedReader
	 */
	public BufferedReader getIn() {
		return in;
	}
	/**
	 * Modifie le flux d'écriture du client courant
	 * @param in : le nouveau flux à assigner
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	
	
}
