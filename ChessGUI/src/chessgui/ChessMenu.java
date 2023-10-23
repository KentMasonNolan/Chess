/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class ChessMenu {

    private JFrame frame;
    private JPanel buttonPanel;
    private JLabel titleLabel;
    private ChessDBManager dbManager;
    Connection conn;
    Statement statement;

    public ChessMenu() {
        
        dbManager = new ChessDBManager();
       
        frame = new JFrame("Chess Game");
        
        // Main title label
        titleLabel = new JLabel("Chess Game");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));

        buttonPanel = new JPanel();
        
        // Use GridBagLayout for more flexible positioning
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JButton playButton = new JButton("Play");
        JButton listPlayers = new JButton("List Players");
        JButton recentGames = new JButton("Recent Games");
        JButton exitButton = new JButton("Exit");
        
        // Add action listeners to the buttons
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to start the game
            }
        });
        
listPlayers.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        showPlayersList();
    }
});

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        recentGames.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        

        // Add buttons to the panel using GridBagLayout constraints
        buttonPanel.add(playButton, gbc);
        buttonPanel.add(listPlayers, gbc);
        buttonPanel.add(recentGames, gbc);
        buttonPanel.add(exitButton, gbc);

        frame.setLayout(new BorderLayout());
        frame.add(titleLabel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(300, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    private void showPlayersList() {
    List<String> players = dbManager.fetchPlayersFromDB();

    // Create a new JFrame to display the players
    JFrame playersFrame = new JFrame("Players List");
    JTextArea textArea = new JTextArea();
    textArea.setEditable(false); // So users can't modify the content

    for (String player : players) {
        textArea.append(player + "\n");
    }

    playersFrame.add(new JScrollPane(textArea));
    playersFrame.setSize(300, 200);
    playersFrame.setVisible(true);
}


    public static void main(String[] args) {
        new ChessMenu();
    }
}
