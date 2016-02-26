package main;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import game.*;

public class Server {
	
	/**
	 * Port d'écoute du serveur
	 */
	public static final int PORT = 1337;
	
	/**
	 * La socket serveur 
	 */
	public static ServerSocket ss = null;
	
	/**
	 * List des gestionnaire de joueur
	 */
	public static List<GamerManagement> listGamerManagement = new ArrayList<>();
	
	/**
	 * Méthode principale de la classe permettant de recevoir en continue
	 * les connexions clients
	 * @param args les arguements de la méthode
	 */
	public static void main(String[] args) {
		try {
			ss = new ServerSocket(PORT);
			System.out.println("Le serveur est à l'écoute du port "+ss.getLocalPort());
			while(true) {
				Socket client = ss.accept();
				GamerManagement gm = new GamerManagement(client);
				listGamerManagement.add(gm);
				gm.start();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
