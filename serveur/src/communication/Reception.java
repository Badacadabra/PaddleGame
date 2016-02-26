package communication;

import game.GamerManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
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
		in = gamerManagement.getIn();
		this.start();
	}
	@Override
	/**
	 * Méthode génrant la reception des données dans l'entrée standard des clients
	 */
	public void run() {
		read();
	}
	/**
	 * Méthde effectuant une lecture permanente des données 
	 * depuis l'entrée standard des clients
	 */
	private void read() {
		try {
			boolean canRead = true;
			ArrayList<String> listPrimitve = new ArrayList<>();
			listPrimitve.add(Primitives.SEND_PSEUDO);
			listPrimitve.add(Primitives.SEND_PADDLE_POSITION);
			primitive = in.readLine();
			if (primitive.equals(Primitives.SEND_PSEUDO)) { 
				pseudo  = in.readLine();
				gamerManagement.getGamer().setPseudo(pseudo); //associe le pseudo au joueur courant
			}
			while(canRead) {
				primitive = in.readLine();
				if (primitive!=null) {
					if (primitive.equals(Primitives.SEND_PADDLE_POSITION)) {
						int paddleX = Integer.parseInt(in.readLine());
						gamerManagement.getGamer().getPaddle().setX(paddleX); //On associe le pseudo au joueur courant
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
