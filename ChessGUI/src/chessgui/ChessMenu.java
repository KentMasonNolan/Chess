package ChessGUI;

import ChessGUI.ChessDBManager;
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
    
    private JButton restartButton = new JButton("Restart Game");
    
    JButton saveGameButton = new JButton("Save Game");
    
    private JComboBox<String> playerOneComboBox;
    private JComboBox<String> playerTwoComboBox;
    private JButton startGameButton;
    JButton loadGameButton = new JButton("Load Game");

    
    private Game currentGame;

    ChessDBManager dbManager = new ChessDBManager();
    
    private boolean isGameOngoing = false;


    public ChessMenu() {
        frame = new JFrame("Chess Game");
        
        dbManager.createDatabaseTables();
        
        
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
        
        playerOneComboBox = new JComboBox<>();
        playerTwoComboBox = new JComboBox<>();
        updatePlayerComboBoxes();
        
        startGameButton = new JButton("Start Game");

startGameButton.addActionListener(e -> {
    if (startGameButton.isEnabled()) {
        String playerOne = (String) playerOneComboBox.getSelectedItem();
        String playerTwo = (String) playerTwoComboBox.getSelectedItem();
        
        if (!playerOne.equals(playerTwo)) {
            // Initialize game with the selected players
            currentGame = new Game(playerOne, playerTwo);
            
            // Set the game ongoing flag
            isGameOngoing = true;
            
            // Disable the start button since a game has started
            startGameButton.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select two different players!");
        }
    }
});

        
    restartButton.addActionListener(e -> {
        if (isGameOngoing && currentGame != null) {
            currentGame.closeGame();
            isGameOngoing = false;
            String playerOne = (String) playerOneComboBox.getSelectedItem();
            String playerTwo = (String) playerTwoComboBox.getSelectedItem();
            currentGame = new Game(playerOne, playerTwo);
            isGameOngoing = true;
        } else {
            JOptionPane.showMessageDialog(frame, "No game is currently ongoing!");
        }
    });
        


        playerOneComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (playerOneComboBox.getSelectedItem() != null && playerTwoComboBox.getSelectedItem() != null) {
                    startGameButton.setEnabled(true);
                }
            }
        });

        playerTwoComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (playerOneComboBox.getSelectedItem() != null && playerTwoComboBox.getSelectedItem() != null) {
                    startGameButton.setEnabled(true);
                }
            }
        });

        playButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "playerSelection");
        });


        listPlayers.addActionListener(e -> {
            updatePlayersList();
            cardLayout.show(mainPanel, "playersList");
        });
        
        recentGames.addActionListener(e -> {
            updateRecentGamesList();
            cardLayout.show(mainPanel, "gamesList");
        });
        
saveGameButton.addActionListener(e -> {
    if (isGameOngoing && currentGame != null) {
        try {
            String selectedItemPlayerOne = (String) playerOneComboBox.getSelectedItem();
            String[] partsPlayerOne = selectedItemPlayerOne.split("\\|");
            int playerOneId = Integer.parseInt(partsPlayerOne[0].split(":")[1].trim());
            
            String selectedItemPlayerTwo = (String) playerTwoComboBox.getSelectedItem();
            String[] partsPlayerTwo = selectedItemPlayerTwo.split("\\|");
            int playerTwoId = Integer.parseInt(partsPlayerTwo[0].split(":")[1].trim());

            dbManager.saveGame(currentGame, playerOneId, playerTwoId);
            JOptionPane.showMessageDialog(frame, "Game has been saved");
        } catch (Exception ex) {
            System.out.println("Error extracting player IDs: " + ex.getMessage());
            JOptionPane.showMessageDialog(frame, "Error saving game. Please try again.");
        }
    } else {
        JOptionPane.showMessageDialog(frame, "No game is currently ongoing to save!");
    }
});

loadGameButton.addActionListener(e -> {
    List<GameInfo> savedGames = dbManager.fetchSavedGames();
    
    if (savedGames.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "No saved games found!");
        return;
    }

    DefaultListModel<GameInfo> listModel = new DefaultListModel<>();
    for (GameInfo gameInfo : savedGames) {
        listModel.addElement(gameInfo);
    }

    JList<GameInfo> gameList = new JList<>(listModel);
    gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    int result = JOptionPane.showConfirmDialog(frame, new JScrollPane(gameList), 
                                               "Select a Game", JOptionPane.OK_CANCEL_OPTION, 
                                               JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
        GameInfo selectedGame = gameList.getSelectedValue();
        Game loadedGame = dbManager.loadGameById(selectedGame.getGameId());
       if (loadedGame == null) {
           System.out.println("The loaded game is null.");
           return;
       }

       currentGame.closeGame();
       currentGame = new Game(loadedGame);
//        currentGame.initializeAndShow();
       
        System.out.println("Game would be loaded");

    }
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
        
        JPanel playerSelectionPanel = new JPanel();
        playerSelectionPanel.setLayout(new BoxLayout(playerSelectionPanel, BoxLayout.Y_AXIS));
        playerSelectionPanel.add(new JLabel("Select Player 1:"));
        playerSelectionPanel.add(playerOneComboBox);
        playerSelectionPanel.add(new JLabel("Select Player 2:"));
        playerSelectionPanel.add(playerTwoComboBox);
        playerSelectionPanel.add(startGameButton);
        playerSelectionPanel.add(restartButton, gbc);
        playerSelectionPanel.add(saveGameButton);
        playerSelectionPanel.add(loadGameButton);
        JButton backFromPlayerSelection = new JButton("Back to Menu");
        backFromPlayerSelection.addActionListener(e -> cardLayout.show(mainPanel, "mainMenu"));
        playerSelectionPanel.add(backFromPlayerSelection);

        mainPanel.add(playerSelectionPanel, "playerSelection");
        mainPanel.add(buttonPanel, "mainMenu");
        mainPanel.add(gamesPanel, "gamesList");
        mainPanel.add(playersPanel, "playersList");
        mainPanel.add(cardPanel, "ChessBoard");
        
        cardLayout.show(mainPanel, "mainMenu");


        frame.setLayout(new BorderLayout());
        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setSize(400, 450);
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
    
    private void updatePlayerComboBoxes() {
    List<String> players = dbManager.fetchPlayersFromDB();
    for (String player : players) {
        playerOneComboBox.addItem(player);
        playerTwoComboBox.addItem(player);
    }
}
    
    


    public static void main(String[] args) {
        new ChessMenu();
    }
}
