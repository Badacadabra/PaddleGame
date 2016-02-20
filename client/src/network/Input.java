package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import game.Ball;
import game.GameZone;

/**
 * Classe permettant de gérer les flux entrants de manière plus élégante.
 * Elle agit comme un gestionnaire de réponses venant du serveur.
 * 
 * @author Baptiste Vannesson
 */
public class Input extends Thread {

    /** Représentation du flux d'entrée (pour la réception de données venant du serveur) */
    private BufferedReader in;
    
    /** Zone de jeu */
    private GameZone gameZone;
    
    /**
     * Constructeur de la classe.
     * Celui-ci prend en paramètres un objet InputStream représentant un flux d'entrée,
     * ainsi qu'une balle et la liste de tous les joueurs.
     * À noter que le flux d'entrée est décoré d'un BufferedReader lors de l'initialisation...
     */
    public Input(InputStream in, GameZone gameZone) {
        this.in = new BufferedReader(new InputStreamReader(in)); // Pattern Decorator
        this.gameZone = gameZone;
    }
    
    /**
     * Méthode gérant les interactions effectives avec le serveur, en lecture.
     */ 
    public void read() throws IOException {
        String primitive = in.readLine();
        System.out.println(primitive);
        if (primitive.equals(Primitives.SEND_GAMERS_INFO.getName())) {
            System.out.println("Réception des informations de tous les joueurs");
            /* String[] gamerInfo = in.readLine().split("_");
            String pseudo = gamerInfo[0];
            int score = Integer.parseInt(gamerInfo[1]);
            Paddle paddle = new Paddle(Integer.parseInt(gamerInfo[2]));
            gamers.add(new Gamer(pseudo, score, paddle, Color.CYAN));
            System.out.println("Pseudo : " + pseudo);
            System.out.println("Score : " + gamerInfo[1]);
            System.out.println("Paddle position : " + gamerInfo[2]); */
        } else if (primitive.equals(Primitives.SEND_NEW_BALL.getName())) {
            System.out.println("Réception de la nouvelle balle lancée par le serveur");
            int ballX = Integer.parseInt(in.readLine());
            int ballY = Integer.parseInt(in.readLine());
            System.out.println(gameZone.getGamers());
            /* gameZone.setBall(Ball.getBall(ballX, ballY));
            new Thread(gameZone).start(); */
        } else if(primitive.equals(Primitives.SEND_BALL_COORDS.getName())) { 
            System.out.println("Réception de la position de la balle");
            int ballX = Integer.parseInt(in.readLine());
            int ballY = Integer.parseInt(in.readLine());
            System.out.println("Coordonnées de la balle : (" + ballX + "," + ballY + ")");
            /* gameZone.getBall().setX(ballX);
            gameZone.getBall().setY(ballY); */
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
                    read();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

}
