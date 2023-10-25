package ChessGUI;

import chessgui.ChessDBManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ChessMenu {

    private JFrame frame;
    private JPanel buttonPanel;
    private JLabel titleLabel;
    private JPanel playersPanel = new JPanel(new BorderLayout());
    private JPanel gamesPanel = new JPanel(new BorderLayout());
    
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);
    private JPanel mainPanel = new JPanel(cardLayout);
    
    private DefaultListModel<String> gamesListModel = new DefaultListModel<>();
    private JList<String> recentGamesList = new JList<>(gamesListModel);  


    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> playersList = new JList<>(listModel);  



    ChessDBManager dbManager = new ChessDBManager();

    public ChessMenu() {
        frame = new JFrame("Chess Game");

        // Main title label
        titleLabel = new JLabel("Chess Game");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JButton playButton = new JButton("Play");
        JButton listPlayers = new JButton("List Players");
        JButton recentGames = new JButton("Recent Games");
        JButton exitButton = new JButton("Exit");

        playButton.addActionListener(e -> {
            Game game = new Game();
        });

        listPlayers.addActionListener(e -> {
            updatePlayersList();
            cardLayout.show(mainPanel, "playersList");
        });
        
        recentGames.addActionListener(e -> {
            updateRecentGamesList();
            cardLayout.show(mainPanel, "gamesList");
        });


        exitButton.addActionListener(e -> System.exit(0));
        recentGames.addActionListener(e -> {
            // Code for showing recent games
        });

        buttonPanel.add(playButton, gbc);
        buttonPanel.add(listPlayers, gbc);
        buttonPanel.add(recentGames, gbc);
        buttonPanel.add(exitButton, gbc);

        playersPanel.add(new JScrollPane(playersList), BorderLayout.CENTER);
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "mainMenu"));
        playersPanel.add(backButton, BorderLayout.SOUTH);
        
        gamesPanel.add(new JScrollPane(recentGamesList), BorderLayout.CENTER);
        JButton gamesBackButton = new JButton("Back to Menu");
        gamesBackButton.addActionListener(e -> cardLayout.show(mainPanel, "mainMenu"));
        gamesPanel.add(gamesBackButton, BorderLayout.SOUTH);

        mainPanel.add(buttonPanel, "mainMenu");
        mainPanel.add(gamesPanel, "gamesList");
        mainPanel.add(playersPanel, "playersList");
        mainPanel.add(cardPanel, "ChessBoard");

        frame.setLayout(new BorderLayout());
        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setSize(300, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void updatePlayersList() {
        List<String> players = dbManager.fetchPlayersFromDB();
        listModel.clear();
        for (String player : players) {
            listModel.addElement(player);
        }
    }
    
    private void updateRecentGamesList() {
    List<String> games = dbManager.fetchRecentGames();
    gamesListModel.clear();
    for (String game : games) {
        gamesListModel.addElement(game);
    }
}
    
    


    public static void main(String[] args) {
        new ChessMenu();
    }
}
