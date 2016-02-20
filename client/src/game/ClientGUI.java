package game;

import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import network.Input;
import network.Output;
import network.Primitives;

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
    public static final int GUI_WIDTH = 800;
    
    /** Constante déterminant la hauteur de la fenêtre */
    public static final int GUI_HEIGHT = 600;
    
    /** Constante déterminant le port de connexion au serveur */
    public static final int GAME_PORT = 1337;
    
    /** Constante déterminant le score par défaut d'un nouveau joueur */
    public static final int GAMER_DEFAULT_SCORE = 0;
    
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
    
    /** Objet représentant la balle */
    private Ball ball;
    
    /** Liste contenant tous les joueurs */
    private List<Gamer> gamers;
    
    /** Objet encapsulant les requêtes entrantes (in) */
    private Input input;
    
    /** Objet encapsulant les requêtes sortantes (out) */
    private Output output;

    public ClientGUI() {
        
        // On initialise avant tout un nouveau joueur avec tous les paramètres par défaut
        int initialPaddlePosition = (GameZone.BALL_AREA_WIDTH / 2) - (Paddle.WIDTH / 2);
        Paddle paddle = new Paddle(initialPaddlePosition);
        gamer = new Gamer("", GAMER_DEFAULT_SCORE, paddle, Color.WHITE);
        gamers = new ArrayList<>();
        
        // Affichage de la pop-up initiale pour demander le pseudo de l'utilisateur
        String response = JOptionPane.showInputDialog(this, "Entrez votre pseudo :", "Bienvenue sur Paddle Game !", JOptionPane.PLAIN_MESSAGE);
        if (response == null) { // Si l'utilisateur clique sur « Annuler »...
            System.exit(0);
        } else { // Sinon...
            if (response.length() >= 3) { // Si le pseudo contient au moins 3 caractères, on accepte le joueur
                System.out.println("Utilisateur OK : " + response);
                gamer.setPseudo(response);
            } else { // On dit à l'utilisateur que son pseudo n'est pas bon et on met fin au jeu
                JOptionPane.showMessageDialog(this, "Votre pseudo doit contenir au moins 3 caractères", "Mauvais pseudo !", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        
        // Lancement du Thread de connexion au serveur
        new Thread(this).start();
        
        // TEST
        ball = Ball.getBall(0, 0);
        
        Paddle opponentPaddle1 = new Paddle(70);
        Paddle opponentPaddle2 = new Paddle(500);
        
        gamers.add(gamer);
        gamers.add(new Gamer("Macky", 50, opponentPaddle1, Color.GREEN));
        gamers.add(new Gamer("Yann", 50, opponentPaddle2, Color.YELLOW));
        
        // Création de l'interface graphique en Swing
        screen = this.getContentPane();
        
        DefaultListModel<Gamer> model = new DefaultListModel<>();
        model.addElement(gamer);
        /* for (Gamer gamer : gamers) {
            model.addElement(gamer);
        } */
        ScoresList scoresList = new ScoresList(model);
        
        scoresZone = new ScoresZone(scoresList);
        gameZone = new GameZone(ball, gamers, model);
        new Thread(gameZone).start();
        
        screen.setLayout(new BoxLayout(screen, BoxLayout.X_AXIS)); // Pattern Strategy
        screen.add(scoresZone);
        screen.add(gameZone);
        
        // Configuration de la fenêtre
        setTitle("Paddle Game (par Macky Dieng & Baptiste Vannesson)");
        setSize(GUI_WIDTH, GUI_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        
    }
    
    /**
     * Méthode exécutée lors du lancement d'un thread à partir d'une instance de cette classe.
     * Ici le client se connecte au serveur via sa socket.
     */
    @Override
    public void run() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), GAME_PORT);
            input = new Input(socket.getInputStream(), gameZone);
            output = new Output(socket.getOutputStream(), gamer);
            input.start();
            output.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
