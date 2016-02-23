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
        String primitive = in.readLine();
        System.out.println(primitive);
        // Le client reçoit du serveur toutes les informations sur tous les joueurs
        if (primitive.equals(Primitives.SEND_GAMERS_INFO.getName())) {
            System.out.println("Réception des informations de tous les joueurs");
            int nbGamers = Integer.parseInt(in.readLine());
            Color[] colors = {Color.GREEN, Color.CYAN, Color.RED, Color.PINK, Color.ORANGE, Color.BLUE};
            for (int i = 0; i < nbGamers; i++) {
                // On récupère toutes les informations de chaque joueur
                String[] gamerInfo = in.readLine().split("_");
                String pseudo = gamerInfo[0];
                if (!pseudo.equals(gameZone.getGamer().getPseudo())) {
                    int score = Integer.parseInt(gamerInfo[1]);
                    Paddle paddle = new Paddle(Integer.parseInt(gamerInfo[2]));
                    Gamer opponent = new Gamer(pseudo, score, paddle, colors[i]);
                    // On ajoute l'adversaire au jeu
                    if (!gameZone.getOpponents().containsKey(pseudo)) {
                        gameZone.addOpponent(opponent);
                        gameZone.getScoresModel().addElement(opponent);
                    } else { // On met à jour l'adversaire               
                        gameZone.updateOpponent(opponent);
                        gameZone.getScoresModel().set(i, opponent);
                    }
                    // On envoie les données en console pour vérification
                    System.out.println("Index : " + i);
                    System.out.println("Pseudo : " + pseudo);
                    System.out.println("Score : " + gamerInfo[1]);
                    System.out.println("Paddle position : " + gamerInfo[2]);
                }
            }
        // Le client reçoit du serveur les nouvelles coordonnées de la balle
        } else if(primitive.equals(Primitives.SEND_BALL_COORDS.getName())) { 
            System.out.println("Réception de la position de la balle");
            int ballX = Integer.parseInt(in.readLine());
            int ballY = Integer.parseInt(in.readLine());
            gameZone.getBall().setX(ballX);
            gameZone.getBall().setY(ballY);
            System.out.println("Coordonnées de la balle : (" + ballX + "," + ballY + ")");
        } else { 
            System.out.println("Primitive inconnue");
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
            }
        }
    }

}
