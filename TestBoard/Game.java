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
        // For testing - set up the pawns on the second row of the chessboard
        String[] colors = {"black", "white"};
        for (String color : colors) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                chessboard[1][col].setPiece(new Pawn(color, 1, col));
                chessboard[6][col].setPiece(new Pawn(color, 6, col)); // Filling the second-to-last row as well
            }
        }
    }

    private void drawChessboard(ChessTile[][] chessboard) {
        System.out.println("  a b c d e f g h"); // Column labels
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
        // not sure if I need this
        return piece.getType().substring(0, 1).toUpperCase();
    }

    private void clearBlackCaptureflag(){
        //TODO loop through all tiles and set flag to false
    }
    private void clearWhiteCaptureflag(){

        //TODO loop through all tiles and set flag to false
    }

    private void setBlackCaptureflag(){
        //TODO loop through all tiles if they have a piece, check where they can capture and set flag
    }
    private void setWhiteCaptureflag(){

        //TODO loop through all tiles if they have a piece, check where they can capture and set flag
    }

}

