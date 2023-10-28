package ChessGUI;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class Game extends JFrame {
        private static final int BOARD_SIZE = 8; // Size of the Chess board
        private static final int DEFAULT_WIDTH = 600; // Default width of the JFrame
        private static final int DEFAULT_HEIGHT = 600; // Default height of the JFrame
        private static final int TILE_SIZE = DEFAULT_WIDTH / BOARD_SIZE; // Default width of the JFrame
        private JPanel chessBoardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE)); // Panel to hold the Chess board
        private GameState gameState = new GameState();


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

    
    public Game(){
        System.out.println("This is the wrong constructor");
    }
    
public void closeGame() {
    remove(chessBoardPanel);
    revalidate();
    repaint();
    this.dispose();
}

    public void initializeAndShow() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Or whatever you have
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setTitle("Chess Game");
        this.add(chessBoardPanel);
        this.setLocationRelativeTo(null); // Center the frame
        this.setVisible(true);
    }


    public Game(Game otherGame){
        this.chessBoardPanel = otherGame.chessBoardPanel;
        this.chessboard = otherGame.chessboard;
        this.gameState = otherGame.gameState;
        updateBoardDisplay();
        initializeAndShow();
    }
    
    
public Game(String playerOne, String playerTwo) {
    setTitle("Chess Board");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    
    System.out.println(playerOne + " asdasd " + playerTwo);

    // Create the Chess board panel with a GridLayout
//    chessBoardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
    add(chessBoardPanel, BorderLayout.CENTER);

    // Initialize pieces on the board
    setupInitialPieces(chessboard, gameState);

    // Create and add the Chess board squares
    for (int row = 0; row < BOARD_SIZE; row++) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            ChessTile tile = chessboard[row][col];
            JPanel square = createSquarePanel(tile);
            chessBoardPanel.add(square);
        }
    }

    setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT); // Set the default size
    setLocationRelativeTo(null);
    setVisible(true);
}

private JPanel createSquarePanel(ChessTile tile) {
    JPanel square = new JPanel(new BorderLayout()); // BorderLayout to center the image label
    square.setBackground(getSquareColor(tile));
    
    // Assign a unique name to the JPanel based on the ChessTile's position.
    square.setName(tile.getRow() + "," + tile.getCol());

    // Check if the tile has a piece
    Piece piece = tile.getPiece();
    if (piece != null) {
        ImageIcon pieceImage = getPieceImage(piece);  // Get the image for the piece
        JLabel pieceLabel = new JLabel(pieceImage);
        square.add(pieceLabel, BorderLayout.CENTER);  // Add the image to the square
    }

    int borderWidth = 1;
    Border border = BorderFactory.createLineBorder(Color.BLACK, borderWidth);
    square.setBorder(border);
    
    square.addMouseListener(new ChessMouseListener());
    square.addMouseMotionListener(new ChessMouseListener());


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
    
        public JPanel getChessBoardPanel() {
        return chessBoardPanel;
    }
        
        public ImageIcon getPieceImage(Piece piece) {
            if (piece == null) {
                return null; // No image for empty tiles.
            }
            String color = piece.getColour().substring(0, 1); 
            String type = piece.getClass().getSimpleName().substring(0, 1).toUpperCase(); 

            String imagePath = "/chessgui/Images/" + color + type + ".png";

        return new ImageIcon(getClass().getResource(imagePath));
    }
        
        private ChessTile sourceTile; // to remember which tile the piece is coming from

private void setupDragAndDrop(JPanel square, ChessTile tile) {
    square.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("mouse pressed");
            sourceTile = tile;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            ChessTile targetTile = getTileAtMousePosition(e.getPoint());
            if (sourceTile != null && targetTile != null) {
                movePiece(sourceTile, targetTile);
                updateBoardDisplay();
            }
            sourceTile = null;  
        }
    });
}

private ChessTile getTileAtMousePosition(Point point) {
    // Convert the mouse position into board coordinates
    int col = point.x / (DEFAULT_WIDTH / BOARD_SIZE);
    int row = point.y / (DEFAULT_HEIGHT / BOARD_SIZE);
    
    // Boundary checks to ensure the calculated row and column are within valid ranges
    if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
        // Optionally, you can return null or throw a custom exception to indicate an invalid tile.
        // For now, we will simply return null.
        return null;
    }
    
    return chessboard[row][col];
}


private void movePiece(ChessTile sourceTile, ChessTile destTile) {
    Piece piece = sourceTile.getPiece();
    int sourceRow = sourceTile.getRow();
    int sourceCol = sourceTile.getCol();
    int destRow = destTile.getRow();
    int destCol = destTile.getCol();

    if (piece != null && piece.isValidMove(sourceRow, sourceCol, destRow, destCol, chessboard)) {
        destTile.setPiece(piece);
        sourceTile.removePiece();
        piece.setPieceRow(destRow);
        piece.setPieceCol(destCol);

        updateBoardDisplay();

    }
}


private void updateBoardDisplay() {
    chessBoardPanel.removeAll();
    for (int row = 0; row < BOARD_SIZE; row++) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            ChessTile tile = chessboard[row][col];
            JPanel square = createSquarePanel(tile);
            setupDragAndDrop(square, tile); // setup drag and drop for this square
            chessBoardPanel.add(square);
        }
    }
    chessBoardPanel.revalidate();
    chessBoardPanel.repaint();
}

private class ChessMouseListener extends MouseAdapter {

    private Piece currentPiece;
    private JLabel currentPieceLabel;
    private int sourceRow;
    private int sourceCol;
    private JPanel sourceSquare;

    @Override
    public void mousePressed(MouseEvent e) {
        sourceSquare = (JPanel) e.getSource();
        String name = sourceSquare.getName();
        String[] parts = name.split(",");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);

        ChessTile tile = chessboard[row][col];
        if (tile.isTileFilled()) {
            currentPiece = tile.getPiece();
            sourceRow = row;
            sourceCol = col;

            // Visually remove the piece from the source square for dragging
            currentPieceLabel = (JLabel) sourceSquare.getComponent(0);
            sourceSquare.remove(currentPieceLabel);
            sourceSquare.revalidate();
            sourceSquare.repaint();

            System.out.println("Piece selected: " + currentPiece.getColour() + " " + currentPiece.getType());
            System.out.println("sourceRow = " + row);
            System.out.println("sourceCol = " + col);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentPiece != null) {
            currentPieceLabel.setLocation(e.getPoint());
            chessBoardPanel.add(currentPieceLabel, 0);
            currentPieceLabel.repaint();
        }
    }

@Override
public void mouseReleased(MouseEvent e) {
    
    if (currentPiece != null && currentPiece.getColour().equals(gameState.currentPlayer)) {
        // Get the mouse's x and y coordinates relative to the chessBoardPanel
        Point releasePoint = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), chessBoardPanel);

        int destRow = releasePoint.y / TILE_SIZE;
        int destCol = releasePoint.x / TILE_SIZE;

        if (currentPiece.isValidMove(sourceRow, sourceCol, destRow, destCol, chessboard)) {
            chessboard[destRow][destCol].setPiece(currentPiece);
            chessboard[sourceRow][sourceCol].removePiece();

            // Remove the piece's JLabel from the source tile
            JPanel sourceSquare = (JPanel) chessBoardPanel.getComponent(sourceRow * BOARD_SIZE + sourceCol);
            sourceSquare.removeAll();

            // Add the piece's JLabel to the destination tile
            JPanel destSquare = (JPanel) chessBoardPanel.getComponent(destRow * BOARD_SIZE + destCol);
            destSquare.add(new JLabel(getPieceImage(currentPiece)));

            updateBoardDisplay();
            gameState.switchPlayer();

            System.out.println("Piece moved to: " + destRow + "," + destCol);
        } else {
            System.out.println("destRow = " + destRow);
            System.out.println("DestCol = " + destCol);
            System.out.println("Invalid move");
        }

        currentPiece = null;
    }
}

}







        public static void main(String[] args) {
            SwingUtilities.invokeLater(Game::new);
        }
    }



