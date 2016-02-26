package game;

import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import network.IO;
import network.Input;
import network.Output;

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
    
    /** Canevas principal */
    private Container screen;
    
    /** Zone affichant les scores (de type JList) */
    private ScoresZone scoresZone;
    
    /** Zone affichant le jeu proprement dit, avec balle et raquettes */
    private GameZone gameZone;
    
    /** Socket de communication avec le serveur */
    private Socket socket;
    
    /** Objet encapsulant les requêtes entrantes (in) */
    private IO input;
    
    /** Objet encapsulant les requêtes sortantes (out) */
    private IO output;

    public ClientGUI() {
        
        // On initialise avant tout un nouveau joueur avec tous les paramètres par défaut
        int center = (GameZone.BALL_AREA_WIDTH / 2) - (Paddle.WIDTH / 2);
        Paddle paddle = new Paddle();
        paddle.setX(center);
        Gamer gamer = new Gamer();
        gamer.setPaddle(paddle);
        Ball ball = Ball.getBall();
        
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
        
        // Création de l'interface graphique en Swing
        screen = this.getContentPane();
        
        DefaultListModel<Gamer> scoresModel = new DefaultListModel<>();
        scoresModel.addElement(gamer);
        ScoresList<Gamer> scoresList = new ScoresList<Gamer>(scoresModel);
        scoresZone = new ScoresZone(scoresList);
        
        gameZone = new GameZone(ball, gamer, scoresModel);
        //new Thread(gameZone).start();
        
        screen.setLayout(new BoxLayout(screen, BoxLayout.X_AXIS)); // Pattern Strategy
        screen.add(scoresZone);
        screen.add(gameZone);
        
        // Lancement du Thread de connexion au serveur
        new Thread(this).start();
        
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
     * Méthode exécutée si l'utilisateur dépasse le stade de la pop-up.
     * Ici le client se connecte au serveur via sa socket.
     * Pour des raisons de clarté du code, les traitements sont délégués à des classes gérant les entrées/sorties.
     * Ces classes sont elles-mêmes des Threads pouvant être lancés.
     */
    @Override
    public void run() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), GAME_PORT);
            input = new Input(socket.getInputStream(), gameZone);
            output = new Output(socket.getOutputStream(), gameZone);
            input.start();
            output.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
