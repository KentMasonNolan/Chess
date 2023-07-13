import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class Game extends JFrame {
        private static final int BOARD_SIZE = 8; // Size of the Chess board
        private static final int DEFAULT_WIDTH = 800; // Default width of the JFrame
        private static final int DEFAULT_HEIGHT = 800; // Default height of the JFrame
        private JPanel chessBoardPanel; // Panel to hold the Chess board

    public Game() {
        setTitle("Chess Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the Chess board panel with a GridLayout
        chessBoardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        add(chessBoardPanel, BorderLayout.CENTER);

        // Create and add the Chess board squares
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                ChessTile tile = new ChessTile(row * BOARD_SIZE + col);
                JPanel square = createSquarePanel(tile);
                chessBoardPanel.add(square);
            }
        }

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT); // Set the default size
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createSquarePanel(ChessTile tile) {
        JPanel square = new JPanel();
        square.setBackground(getSquareColor(tile));
        int borderWidth = 1;
        Border border = BorderFactory.createLineBorder(Color.BLACK, borderWidth);
        square.setBorder(border);
        // You can customize the square panel here, such as adding labels, images, or other components
        return square;
    }

    private Color getSquareColor(ChessTile tile) {
        int row = tile.tileNumber / BOARD_SIZE;
        int col = tile.tileNumber % BOARD_SIZE;
        if ((row + col) % 2 == 0) {
            return new Color(238, 238, 210);
        } else {
            return new Color(118, 150, 86);
        }
    }


        public static void main(String[] args) {
            SwingUtilities.invokeLater(Game::new);
        }
    }


