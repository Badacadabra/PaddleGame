import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import game.*;
import communication.*;

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
	private static List<GamerManagement> gamersManagement = new ArrayList<>();
	/**
	 * Référence de la balle
	 */
	private Ball ball;
	/**
	 * Variable de référence de l'objet Emission
	 */
	private static Emission emission;
	/**
	 * Variable de référence de l'objet Reception
	 */
	private static Reception reception;
	
	
	public static void main(String[] args) {
		try {
			ss = new ServerSocket(PORT);
			System.out.println("Le serveur est à l'écoute du port "+ss.getLocalPort());
			while(true) {
				Socket client = ss.accept();
				GamerManagement gm = new GamerManagement(client);
				gamersManagement.add(gm);
				//emission = new Emission(gamersManagement);
				reception = new Reception(gm.getIn());
			}
			
		} catch (IOException e) {
			//System.err.println("Le port "+ss.getLocalPort()+" est déjà utilisé !");
			System.out.println(e.getMessage());
		}
	}
}
