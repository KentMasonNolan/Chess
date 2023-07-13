import javax.swing.*;
import java.awt.*;


public class Game extends JFrame {
        private static final int BOARD_SIZE = 8; // Size of the Chess board
        private static final int DEFAULT_WIDTH = 800; // Default width of the JFrame
        private static final int DEFAULT_HEIGHT = 800; // Default height of the JFrame
        private JPanel chessBoardPanel; // Panel to hold the Chess board

        public Game() {
            setTitle("Chess Board GUI");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            // Create the Chess board panel with a GridLayout
            chessBoardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
            add(chessBoardPanel, BorderLayout.CENTER);

            // Create and add the Chess board squares
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    JPanel square = new JPanel();
                    square.setBackground(getSquareColor(row, col));
                    chessBoardPanel.add(square);
                }
            }

            setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT); // Set the default size
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private Color getSquareColor(int row, int col) {
            // Calculate the color of the square based on row and column indexes
            if ((row + col) % 2 == 0) {
                return Color.WHITE;
            } else {
                return Color.GRAY;
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(Game::new);
        }
    }


