package game;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import communication.Emission;
import communication.Reception;

/**
 * Classe gérant l'interface graphique du jeu.
 * Le top-level container est bien sûr une fenêtre.
 * À noter que cette classe implémente l'interface Runnable pour plus de flexibilité.
 * Il sera ainsi possible de lancer un thread en passant une instance de cette classe au constructeur de Thread.
 * 
 * @author Baptiste Vannesson
 */
public class ClientGUI extends JFrame implements Runnable {

    /** Constante déterminant la largeur de la fenêtre */
    public static final int WIDTH = 800;
    
    /** Constante déterminant la hauteur de la fenêtre */
    public static final int HEIGHT = 600;
    
    /** Constante déterminant le port de connexion au serveur */
    public static final int PORT = 1337;
    
    /** Socket de communication avec le serveur */
    private Socket socket;
    
    /** Canevas principal */
    private Container screen;
    
    /** Zone affichant les scores (de type JList) */
    private ScoresZone scoresZone;
    
    /** Zone affichant le jeu proprement dit, avec balle et raquettes */
    private GameZone gameZone;
    
    /** Objet représentant le joueur courant */
    private Gamer gamer;
    
    /** Liste contenant tous les joueurs */
    private List<Gamer> gamers;
    
    /** Objet encapsulant les requêtes sortantes (out) */
    private Emission emission;
    
    /** Objet encapsulant les requêtes entrantes (in) */
    private Reception reception;

    public ClientGUI() {
        // TEST
        Ball ball = Ball.getBall(0, 0);
        
        List<Paddle> paddles = new ArrayList<>();
        Paddle paddle1 = new Paddle();
        Paddle paddle2 = new Paddle(70, Color.CYAN);
        Paddle paddle3 = new Paddle(500, Color.GREEN);
        paddles.add(paddle1);
        paddles.add(paddle2);
        paddles.add(paddle3);
        
        gamers = new ArrayList<>();
        gamers.add(new Gamer("Toto", 0, paddle1));
        gamers.add(new Gamer("Tata", 50, paddle2));
        gamers.add(new Gamer("Titi", 50, paddle3));
        
        // Intégration des composants Swing        
        screen = this.getContentPane();
        scoresZone = new ScoresZone(gamers.toArray());
        gameZone = new GameZone(ball, paddles);
        
        JScrollPane scrollPane = new JScrollPane(scoresZone);
        
        screen.setLayout(new BoxLayout(screen, BoxLayout.X_AXIS)); // Pattern Strategy pour le choix du layout...
        screen.add(scoresZone);
        screen.add(gameZone);
        
        // Configuration de la fenêtre
        setTitle("Paddle Game (par Macky Dieng & Baptiste Vannesson)");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        
        // Affichage de la pop-up initiale pour demander le pseudo de l'utilisateur
        String response = JOptionPane.showInputDialog(this, "Entrez votre pseudo :", "Bienvenue !", JOptionPane.PLAIN_MESSAGE);
        if (response == null) { // Si l'utilisateur clique sur « Annuler »...
            System.exit(0);
        } else { // Sinon...
            if (response.length() >= 3) { // Si le pseudo contient au moins 3 caractères, on accepte le joueur
                System.out.println("OK : " + response);
            } else { // On dit à l'utilisateur que son pseudo n'est pas bon
                JOptionPane.showMessageDialog(this, "Votre pseudo doit contenir au moins 3 caractères", "Mauvais pseudo !", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        
        // Lancement du Thread de connexion au serveur
        new Thread(this);
    }
    
    /**
     * Méthode exécutée lors du lancement d'un thread à partir d'une instance de cette classe.
     * Ici le client se connecte au serveur via sa socket.
     */
    @Override
    public void run() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), PORT);
            emission = new Emission(new PrintWriter(socket.getOutputStream()), gamer); // Pattern Decorator
            reception = new Reception(new BufferedReader(new InputStreamReader(socket.getInputStream())), gamers); // Pattern Decorator
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
