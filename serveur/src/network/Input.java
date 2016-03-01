package network;

import game.GamerManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Classe répresentant le flux de lecture du serveur
 * @author Macky Dieng
 */
public class Input extends Thread {
	
	/**
	 * Flux de lecture du client
	 */
	private BufferedReader in;
	
	/**
	 * Message à renvoyer aux joueurs
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
	 * @param in gestionnaire de client
	 */
	public Input(GamerManagement gm) {
		this.gamerManagement = gm;
		in = gamerManagement.getIn();
		this.start();
	}
	@Override
	/**
	 * Méthode gérant la réception des données dans l'entrée standard des clients
	 */
	public void run() {
		read();
	}
	/**
	 * Méthode effectuant une lecture permanente des données 
	 * depuis l'entrée standard des clients
	 */
	private void read() {
		try {
			boolean canRead = true;
			ArrayList<String> listPrimitve = new ArrayList<>();
			listPrimitve.add(Primitives.SEND_PSEUDO.toString());
			listPrimitve.add(Primitives.SEND_PADDLE_POSITION.toString());
			primitive = in.readLine();
			if (primitive.equals(Primitives.SEND_PSEUDO.toString())) {
				pseudo  = in.readLine();
				gamerManagement.getGamer().setPseudo(pseudo); //associe le pseudo au joueur courant
			}
			while(canRead) {
				primitive = in.readLine();
				if (primitive!=null) {
					if (primitive.equals(Primitives.SEND_PADDLE_POSITION.toString())) {
						int paddleX = Integer.parseInt(in.readLine());
						gamerManagement.getGamer().getPaddle().setX(paddleX); //Modifie en temps réel la raquette du joueur courant
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
