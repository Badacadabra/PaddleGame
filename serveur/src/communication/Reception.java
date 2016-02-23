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
	 * Message à renvoyé aux joueurs
	 */
	private String primitive;
	
	/**
	 * Le client courant 
	 */
	private GamerManagement gamerManagement;
	
	/**
	 * Le pseudo du client courant
	 */
	private String pseudo;
	
	/**
	 * Constructeur de la classe
	 * @param in le flux de lecture
	 */
	public Reception(GamerManagement gm) {
		this.gamerManagement = gm;
		in = gamerManagement.in;
		this.start();
	}
	@Override
	public void run() {
		
		try {
			boolean canRead = true;
			ArrayList<String> listPrimitve = new ArrayList<>();
			listPrimitve.add(Primitives.SEND_PSEUDO);
			listPrimitve.add(Primitives.SEND_PADDLE_POSITION);
			while(canRead) {
				primitive = in.readLine();
				if (primitive!=null) {
					if (listPrimitve.contains(primitive)) { //Si l'une des primitives match
						if (primitive.equals(Primitives.SEND_PSEUDO)) { //On vérifie s'il s'agit d'un pseudo
							pseudo  = in.readLine();
							gamerManagement.getGamer().setPseudo(pseudo+"_"+gamerManagement.index); //On associe le pseudo au joueur courant
						}
						new Emission(gamerManagement,primitive); //On déclenche l'émission
					}
				} else {
					canRead = false;
				}
			} 
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
