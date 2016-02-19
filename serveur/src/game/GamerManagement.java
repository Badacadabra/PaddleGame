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
	
	private int i = 0;
	/**
	 * Constructeur de la classe
	 * @param client
	 */
	public GamerManagement(Socket client) {
		this.client = client;
		this.start();
	}
	@Override
	public void run() {
		try {
			out = new PrintWriter(client.getOutputStream());
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Reception(in,out);
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
	public String toString () {
		return "GameManager_"+i++;
	}
	
}
