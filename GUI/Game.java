package GUI;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class Game extends JFrame {
        private static final int BOARD_SIZE = 8; // Size of the Chess board
        private static final int DEFAULT_WIDTH = 800; // Default width of the JFrame
        private static final int DEFAULT_HEIGHT = 800; // Default height of the JFrame
        private JPanel chessBoardPanel; // Panel to hold the Chess board

        ChessTile[][] chessboard = createEmptyChessboard();

    private ChessTile[][] createEmptyChessboard() {
        ChessTile[][] chessboard = new ChessTile[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                chessboard[row][col] = new ChessTile(row, col);
            }
        }
        return chessboard;
    }

    private void setupInitialPieces(ChessTile[][] chessboard, GameState gameState) {
        // Set white pieces
        Rook whiteRook1 = new Rook("white", 0, 0);
        Knight whiteKnight1 = new Knight("white", 0, 1);
        Bishop whiteBishop1 = new Bishop("white", 0, 2);
        Queen whiteQueen = new Queen("white", 0, 3);
        King whiteKing = new King("white", 0, 4, gameState);
        Bishop whiteBishop2 = new Bishop("white", 0, 5);
        Knight whiteKnight2 = new Knight("white", 0, 6);
        Rook whiteRook2 = new Rook("white", 0, 7);

        // Add white pieces to the gameState's whitePieces list
        gameState.getWhitePieces().add(whiteRook1);
        gameState.getWhitePieces().add(whiteKnight1);
        gameState.getWhitePieces().add(whiteBishop1);
        gameState.getWhitePieces().add(whiteQueen);
        gameState.getWhitePieces().add(whiteKing);
        gameState.getWhitePieces().add(whiteBishop2);
        gameState.getWhitePieces().add(whiteKnight2);
        gameState.getWhitePieces().add(whiteRook2);

        // Set white pawns in the second row
        for (int col = 0; col < BOARD_SIZE; col++) {
            Pawn whitePawn = new Pawn("white", 1, col);
            gameState.getWhitePieces().add(whitePawn);
        }

        // Set black pieces
        Rook blackRook1 = new Rook("black", 7, 0);
        Knight blackKnight1 = new Knight("black", 7, 1);
        Bishop blackBishop1 = new Bishop("black", 7, 2);
        Queen blackQueen = new Queen("black", 7, 3);
        King blackKing = new King("black", 7, 4, gameState);
        Bishop blackBishop2 = new Bishop("black", 7, 5);
        Knight blackKnight2 = new Knight("black", 7, 6);
        Rook blackRook2 = new Rook("black", 7, 7);

        // Add black pieces to the gameState's blackPieces list
        gameState.getBlackPieces().add(blackRook1);
        gameState.getBlackPieces().add(blackKnight1);
        gameState.getBlackPieces().add(blackBishop1);
        gameState.getBlackPieces().add(blackQueen);
        gameState.getBlackPieces().add(blackKing);
        gameState.getBlackPieces().add(blackBishop2);
        gameState.getBlackPieces().add(blackKnight2);
        gameState.getBlackPieces().add(blackRook2);

        // Set black pawns in the second-to-last row
        for (int col = 0; col < BOARD_SIZE; col++) {
            Pawn blackPawn = new Pawn("black", 6, col);
            gameState.getBlackPieces().add(blackPawn);
        }


        // Set pieces on the board
        for (Piece whitePiece : gameState.getWhitePieces()) {
            chessboard[whitePiece.getPieceRow()][whitePiece.getPieceCol()].setPiece(whitePiece);
        }

        for (Piece blackPiece : gameState.getBlackPieces()) {
            chessboard[blackPiece.getPieceRow()][blackPiece.getPieceCol()].setPiece(blackPiece);
        }
    }

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
                GUI.ChessTile tile = chessboard[row][col];
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
        return square;
    }

    private Color getSquareColor(ChessTile tile) {
        int row = tile.getRow();
        int col = tile.getCol();
        if ((row + col) % 2 == 0) {
            return new Color(238, 238, 210); // Light color
        } else {
            return new Color(118, 150, 86); // Dark color
        }
    }



        public static void main(String[] args) {
            SwingUtilities.invokeLater(Game::new);
        }
    }


