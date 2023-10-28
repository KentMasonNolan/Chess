package ChessGUI;

import ChessGUI.ChessDBManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ChessMenu {

    
    //I'm not going to comment the stuff below. You know what is is. They are buttons and stuff.
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


// constructor for the ChessMenu
public ChessMenu() {
    
    // set up the main window with a title
    frame = new JFrame("Chess Game");
    
    // create or ensure our database tables are ready to go
    dbManager.createDatabaseTables();
    
    // setting up our title label for the menu
    titleLabel = new JLabel("Chess Game");
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    titleLabel.setFont(new Font("Serif", Font.BOLD, 24));

    // setting up a panel to hold our buttons with GridBagLayout for flexibility
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 0, 10, 0);

    // initializing the main menu buttons
    JButton playButton = new JButton("Play");
    JButton listPlayers = new JButton("List Players");
    JButton recentGames = new JButton("Recent Games");
    JButton exitButton = new JButton("Exit");
    
    // combo boxes to select players for a game
    playerOneComboBox = new JComboBox<>();
    playerTwoComboBox = new JComboBox<>();
    // populate the player combo boxes with player names
    updatePlayerComboBoxes();
    
    // button to start a game once players are selected
    startGameButton = new JButton("Start Game");

    // adding an action to the start game button
    startGameButton.addActionListener(e -> {
        // only act if the start button is active
        if (startGameButton.isEnabled()) {
            String playerOne = (String) playerOneComboBox.getSelectedItem();
            String playerTwo = (String) playerTwoComboBox.getSelectedItem();
            
            // ensure two different players are selected
            if (!playerOne.equals(playerTwo)) {
                // start a new game with the chosen players
                currentGame = new Game(playerOne, playerTwo);
                
                // mark that there's a game ongoing now
                isGameOngoing = true;
                
                // deactivate the start button to avoid starting another game
                startGameButton.setEnabled(false);
            } else {
                // notify the user they've selected the same player twice
                JOptionPane.showMessageDialog(frame, "Please select two different players!");
            }
        }
    });



    // adding an action to the restart button
restartButton.addActionListener(e -> {
    // check if there's an ongoing game and if it's a valid game object
    if (isGameOngoing && currentGame != null) {
        // close the current game
        currentGame.closeGame();
        
        // flag to indicate the game has ended
        isGameOngoing = false;
        
        // get the players from the combo boxes
        String playerOne = (String) playerOneComboBox.getSelectedItem();
        String playerTwo = (String) playerTwoComboBox.getSelectedItem();
        
        // start a new game with the chosen players
        currentGame = new Game(playerOne, playerTwo);
        
        // flag to indicate the game is on again
        isGameOngoing = true;
    } else {
        // notify the user when there's no game to restart
        JOptionPane.showMessageDialog(frame, "No game is currently ongoing!");
    }
});

// listen for a change in the playerOne combo box
playerOneComboBox.addItemListener(e -> {
    // check if a new item was selected
    if (e.getStateChange() == ItemEvent.SELECTED) {
        // activate the start button only if both combo boxes have selections
        if (playerOneComboBox.getSelectedItem() != null && playerTwoComboBox.getSelectedItem() != null) {
            startGameButton.setEnabled(true);
        }
    }
});

// similarly, listen for a change in the playerTwo combo box
playerTwoComboBox.addItemListener(e -> {
    // again, checking if a new item got selected
    if (e.getStateChange() == ItemEvent.SELECTED) {
        // activate the start button only if both combo boxes have selections
        if (playerOneComboBox.getSelectedItem() != null && playerTwoComboBox.getSelectedItem() != null) {
            startGameButton.setEnabled(true);
        }
    }
});
// switch to the player selection panel when the play button is clicked
playButton.addActionListener(e -> {
    cardLayout.show(mainPanel, "playerSelection");
});

// show the list of players when the corresponding button is clicked
listPlayers.addActionListener(e -> {
    updatePlayersList(); // first, update the list
    cardLayout.show(mainPanel, "playersList"); // then show the list
});

// similarly, show the recent games when its button is clicked
recentGames.addActionListener(e -> {
    updateRecentGamesList(); // refresh the list of recent games first
    cardLayout.show(mainPanel, "gamesList"); // then display them
});

// let's handle the game-saving action
saveGameButton.addActionListener(e -> {
    // check if there's an ongoing game and it's a legit game object
    if (isGameOngoing && currentGame != null) {
        try {
            // extracting player one's ID
            String selectedItemPlayerOne = (String) playerOneComboBox.getSelectedItem();
            String[] partsPlayerOne = selectedItemPlayerOne.split("\\|");
            int playerOneId = Integer.parseInt(partsPlayerOne[0].split(":")[1].trim());

            // similarly, getting player two's ID
            String selectedItemPlayerTwo = (String) playerTwoComboBox.getSelectedItem();
            String[] partsPlayerTwo = selectedItemPlayerTwo.split("\\|");
            int playerTwoId = Integer.parseInt(partsPlayerTwo[0].split(":")[1].trim());

            // save the game in the database
            dbManager.saveGame(currentGame, playerOneId, playerTwoId);
            JOptionPane.showMessageDialog(frame, "Game has been saved");
        } catch (Exception ex) {
            // print the error message if something goes south
            System.out.println("Error extracting player IDs: " + ex.getMessage());
            // also, show a user-friendly error dialog
            JOptionPane.showMessageDialog(frame, "Error saving game. Please try again.");
        }
    } else {
        // notify the user if there's no game to save
        JOptionPane.showMessageDialog(frame, "No game is currently ongoing to save!");
    }
});
// attach an action listener to the "Load Game" button
loadGameButton.addActionListener(e -> {
    // fetch all saved games from the database
    List<GameInfo> savedGames = dbManager.fetchSavedGames();

    // if no saved games are found, notify the user
    if (savedGames.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "No saved games found!");
        return;
    }

    // create a list model to populate the JList component
    DefaultListModel<GameInfo> listModel = new DefaultListModel<>();
    for (GameInfo gameInfo : savedGames) {
        listModel.addElement(gameInfo); // add every game to the list model
    }

    // create a JList to display the saved games
    JList<GameInfo> gameList = new JList<>(listModel);
    gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ensure only one game can be selected

    // show a dialog containing the list of saved games and await user's selection
    int result = JOptionPane.showConfirmDialog(frame, new JScrollPane(gameList), 
                                               "Select a Game", JOptionPane.OK_CANCEL_OPTION, 
                                               JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
        // if user confirms a selection
        GameInfo selectedGame = gameList.getSelectedValue(); // get the selected game info
        Game loadedGame = dbManager.loadGameById(selectedGame.getGameId()); // load the complete game details using the selected game's ID

        // if for some reason the loaded game is null, handle this scenario
        if (loadedGame == null) {
            System.out.println("The loaded game is null.");
            return;
        }

        // close the current game (if any) before loading the new game
        currentGame.closeGame();
        // instantiate a new Game object using the loaded game's details
        currentGame = new Game(loadedGame);
        // this seems to be a placeholder; uncomment to initialize and display the game
//        currentGame.initializeAndShow();

        // just a debug message indicating the game would be loaded
        System.out.println("Game would be loaded");
    }
});



        exitButton.addActionListener(e -> System.exit(0));
        recentGames.addActionListener(e -> {
            // code to show recent games goes here
        });

        // adding buttons to the main menu using grid bag constraints
        buttonPanel.add(playButton, gbc);
        buttonPanel.add(listPlayers, gbc);
        buttonPanel.add(recentGames, gbc);
        buttonPanel.add(exitButton, gbc);

        // adding player list and a "back to menu" button
        playersPanel.add(new JScrollPane(playersList), BorderLayout.CENTER);
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "mainMenu"));
        playersPanel.add(backButton, BorderLayout.SOUTH);
        
        // adding recent games list and a "back to menu" button
        gamesPanel.add(new JScrollPane(recentGamesList), BorderLayout.CENTER);
        JButton gamesBackButton = new JButton("Back to Menu");
        gamesBackButton.addActionListener(e -> cardLayout.show(mainPanel, "mainMenu"));
        gamesPanel.add(gamesBackButton, BorderLayout.SOUTH);

        // setting up the player selection (choose opponents)
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

        // adding button to navigate back from player selection
        JButton backFromPlayerSelection = new JButton("Back to Menu");
        backFromPlayerSelection.addActionListener(e -> cardLayout.show(mainPanel, "mainMenu"));
        playerSelectionPanel.add(backFromPlayerSelection);

        // adding all panels to main with their card layout names
        mainPanel.add(playerSelectionPanel, "playerSelection");
        mainPanel.add(buttonPanel, "mainMenu");
        mainPanel.add(gamesPanel, "gamesList");
        mainPanel.add(playersPanel, "playersList");
        mainPanel.add(cardPanel, "ChessBoard");
        
        // displaying the main menu by default
        cardLayout.show(mainPanel, "mainMenu");

        // setting up frame properties
        frame.setLayout(new BorderLayout());
        frame.add(titleLabel, BorderLayout.NORTH);   // title on top
        frame.add(mainPanel, BorderLayout.CENTER);   // main panel in the center
        frame.setSize(400, 450);                     // setting the frame size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit app on close
        frame.setVisible(true);   // showing the frame

    }

    // updates the player list from the database
    private void updatePlayersList() {
        List<String> players = dbManager.fetchPlayersFromDB();
        listModel.clear();
        for (String player : players) {
            listModel.addElement(player);
        }
    }
    
    // updates the list of recent games from the database
    private void updateRecentGamesList() {
        List<String> games = dbManager.fetchRecentGames();
        gamesListModel.clear();
        for (String game : games) {
            gamesListModel.addElement(game);
        }
    }
    
    // updates the combo boxes for selecting players
    private void updatePlayerComboBoxes() {
        List<String> players = dbManager.fetchPlayersFromDB();
        for (String player : players) {
            playerOneComboBox.addItem(player);
            playerTwoComboBox.addItem(player);
        }
    }
    
    // main method to run the chess menu
    public static void main(String[] args) {
        new ChessMenu();
    }
}
