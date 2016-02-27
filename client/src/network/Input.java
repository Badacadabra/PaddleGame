package network;

import game.Ball;
import game.GameZone;
import game.Gamer;
import game.Paddle;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

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
    private int index = 0;
    
    /**
     * Constructeur de la classe.
     * Celui-ci prend en paramètres un objet InputStream représentant un flux d'entrée.
     * À noter que le flux d'entrée est décoré d'un BufferedReader lors de l'initialisation...
     */
    public Input(InputStream in, GameZone gameZone) {
        super(gameZone);
        this.in = new BufferedReader(new InputStreamReader(in)); // Pattern Decorator
    }
    
    /**
     * Méthode gérant les interactions effectives avec le serveur, en lecture.
     */ 
    private void read() throws IOException {
        String message = in.readLine();
        // Le client reçoit du serveur toutes les informations sur tous les joueurs
        if (!message.equals(Primitives.SEND_BALL_COORDS.getName())) {
        	//System.out.println(message);
            //int nbGamers = Integer.parseInt(in.readLine());
    		//System.out.println(nbGamers);
            Color[] colors = {Color.GREEN, Color.CYAN, Color.RED, Color.PINK, Color.ORANGE, Color.BLUE};
            //for (int i = 0; i < nbGamers; i++) {
                // On récupère toutes les informations de chaque joueur
            	//String tab = in.readLine();
                String[] gamerInfo = message.split("_");
                String pseudo = gamerInfo[0];
                int score = Integer.parseInt(gamerInfo[1]);
                if (!pseudo.equals(gameZone.getGamer().getPseudo())) {
                    Paddle paddle = new Paddle(Integer.parseInt(gamerInfo[2]));
                    Gamer opponent = new Gamer(pseudo, score, paddle, colors[index]);
                    //System.out.println(opponent);
                    // On ajoute l'adversaire au jeu
                    if (!gameZone.getOpponents().containsKey(pseudo)) {
                        gameZone.addOpponent(opponent);
                        gameZone.getScoresModel().addElement(opponent);
                    } else { // On met à jour l'adversaire               
                        gameZone.updateOpponent(opponent);
                        gameZone.getScoresModel().set(index, opponent);
                    }
                } else {
                	gameZone.getGamer().setScore(score);
                	gameZone.getScoresModel().set(index,gameZone.getGamer());
                }
                //On envoie les données en console pour vérification
                /*System.out.println("Pseudo : " + pseudo);
                System.out.println("Score : " + gamerInfo[1]);
                System.out.println("Paddle position : " + gamerInfo[2]);*/
                index++;
            }
        // Le client reçoit du serveur les nouvelles coordonnées de la balle
        //}
        if(message.equals(Primitives.SEND_BALL_COORDS.getName())) { 
        	//System.out.println(message);
        	// boolean isBlocked = false;
    		int ballX = Integer.parseInt(in.readLine());
    		int ballY = Integer.parseInt(in.readLine());
    		gameZone.getBall().setX(ballX);
    		gameZone.getBall().setY(ballY);
    		gameZone.moveBall();
    		index = 0;
            /* if (ballY > Paddle.Y + Ball.BALL_DIAMETER) {
            	int answer = JOptionPane.showConfirmDialog(null, "Voulez-vous continuer ?", "Balle perdue !", JOptionPane.YES_NO_OPTION);
            	if (answer == JOptionPane.YES_OPTION) {
                    isBlocked = true;
                }
                if (answer == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
                while (in.readLine() != null && !isBlocked) {
            		// test
            	}
            } */
            //System.out.println("Coordonnées de la balle : (" + ballX + "," + ballY + ")");
        }
    }
    
    /**
     * Méthode gérant la lecture dans l'entrée standard
     */
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
