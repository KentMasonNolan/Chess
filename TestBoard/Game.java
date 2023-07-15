package TestBoard;

public class Game {
    private static final int BOARD_SIZE = 8;

    public static void main(String[] args) {
        // Create a new instance of the Game class and start the game
        Game game = new Game();
        game.start();
    }

    private void start() {
        // Initialize the chessboard and pieces
        ChessTile[][] chessboard = createEmptyChessboard();
        setupInitialPieces(chessboard);

        // Draw the chessboard
        drawChessboard(chessboard);
    }

    private ChessTile[][] createEmptyChessboard() {
        ChessTile[][] chessboard = new ChessTile[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                chessboard[row][col] = new ChessTile(row, col);
            }
        }
        return chessboard;
    }

    private void setupInitialPieces(ChessTile[][] chessboard) {
        // Set up the initial pieces on the chessboard
        // Implement your logic here to place the pieces in their starting positions
    }

    private void drawChessboard(ChessTile[][] chessboard) {
        System.out.println("   a b c d e f g h"); // Column labels
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print((row + 1) + " "); // Row label
            for (int col = 0; col < BOARD_SIZE; col++) {
                ChessTile tile = chessboard[row][col];
                if (tile.isTileFilled()) {
                    // Print the piece symbol based on the piece type
                    System.out.print(getPieceSymbol(tile.getPiece()) + " ");
                } else {
                    System.out.print("- "); // Empty tile symbol
                }
            }
            System.out.println();
        }
    }

    private String getPieceSymbol(Piece piece) {
        // Map the Piece object to the corresponding ASCII character or symbol
        // Implement your logic here to determine the appropriate symbol for each piece
        // Example mapping: pawn = "P", rook = "R", knight = "N", bishop = "B", etc.
        return piece.getType().substring(0, 1).toUpperCase(); // Return the first letter as the symbol
    }
}

