package network;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import game.GameZone;
import game.Gamer;
import game.Paddle;


/**
 * Classe permettant de gérer les flux entrants de manière plus élégante.
 * Elle agit comme un gestionnaire de réponses venant du serveur.
 * 
 * @author Baptiste Vannesson
 */
public class Input extends IO {

    /** Représentation du flux d'entrée (pour la réception de données venant du serveur) */
    private BufferedReader in;
    
    /** L'index de mise à jour de la liste des joueurs */
    private static int index = 0;
    
    /**
     * Constructeur de la classe.
     * Celui-ci prend en paramètres un objet InputStream représentant un flux d'entrée.
     * À noter que le flux d'entrée est décoré d'un BufferedReader lors de l'initialisation...
     * 
     * @param in Flux d'entrée
     * @param gameZone Zone de jeu
     */
    public Input(InputStream in, GameZone gameZone) {
        super(gameZone);
        this.in = new BufferedReader(new InputStreamReader(in)); // Pattern Decorator
    }
    
    /**
     * Méthode gérant les interactions effectives avec le serveur, en lecture.
     */ 
    private void read() throws IOException {
    	// Réception du message venant du serveur
        String message = in.readLine();
        // Si le client reçoit les nouvelles coordonnées de la balle...
        if(message.equals(Primitives.SEND_BALL_COORDS.toString())) { 
    		int ballX = Integer.parseInt(in.readLine());
    		int ballY = Integer.parseInt(in.readLine());
    		gameZone.getBall().setX(ballX);
    		gameZone.getBall().setY(ballY);
    		gameZone.repaint();
    		index = 0;
        } else { // Sinon le client reçoit les informations sur les joueurs...
        	// Attention, ce tableau fini de couleurs va poser problème au-delà de 7 joueurs !
            Color[] colors = {Color.GREEN, Color.RED, Color.CYAN, Color.PINK, Color.ORANGE, Color.BLUE, Color.YELLOW};
            String[] gamerInfo = message.split("_");
            String pseudo = gamerInfo[0];
            int score = Integer.parseInt(gamerInfo[1]);
            int paddlePosition = Integer.parseInt(gamerInfo[2]);
            // On fait la distinction entre le joueur courant et ses adversaires
            if (!pseudo.equals(gameZone.getGamer().getPseudo()) && pseudo != null) {
                // On crée et on ajoute l'adversaire au jeu si ce joueur n'existe pas
                if (!gameZone.getOpponents().containsKey(pseudo)) {
                	Paddle paddle = new Paddle(paddlePosition);
                    Gamer opponent = new Gamer(pseudo, score, paddle, colors[index]);
                    gameZone.addOpponent(opponent);
                    gameZone.getScoresModel().addElement(opponent);
                } else { // On met à jour l'adversaire s'il existe déjà
                	Gamer opponent = gameZone.getOpponents().get(pseudo);
                    opponent.getPaddle().setX(paddlePosition);
                    opponent.setScore(score);
                    gameZone.getScoresModel().set(index, opponent);
                }
            } else {
            	gameZone.getGamer().setScore(score);
            	gameZone.getScoresModel().set(index, gameZone.getGamer());
            }
            index++;
        }
    }
    
    /**
     * Méthode du thread gérant la lecture dans l'entrée standard
     */
    @Override
    public void run() {
        while(true) {
            try {
                if (in.ready()) {
                    // Le client lit en continu les informations provenant du serveur
                    read();
                }
            } catch(IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

}
