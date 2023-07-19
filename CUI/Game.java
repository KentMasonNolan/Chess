package CUI;

public class Game {
    private static final int BOARD_SIZE = 8;
    ChessTile[][] chessboard = createEmptyChessboard();
    private String currentPlayerColor;


    public static void main(String[] args) {
        // Create a new instance of the GUI.Game class and start the game
        Game game = new Game();
        game.start();
    }

    private void start() {
        // Initialize the chessboard and pieces

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
            int kingRow;
            if (color.equals("black")) {
                kingRow = 0;
            } else {
                kingRow = BOARD_SIZE - 1;
            }
            chessboard[kingRow][4].setPiece(new King(color, kingRow, 4));
        }
    }

    private void drawChessboard(ChessTile[][] chessboard) {
        System.out.println("  a b c d e f g h"); // Column labels
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print((8 - row) + " "); // Row label
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

    private void handleUserInput(String input) {

        //expected input to be something like C4 C5

        String[] squares = input.split(" ");
        if (squares.length != 2) {
            System.out.println("Invalid input. Please enter two squares separated by a space.");
            return;
        }

        String sourceSquare = squares[0].toUpperCase();
        String destSquare = squares[1].toUpperCase();

        // TODO convert C4 into [2][3], etc
        int sourceRow;
        int sourceCol;
        int destRow;
        int destCol;

        //TODO add movePiece
        movePiece(sourceRow, sourceCol, destRow, destCol);
    }

    private void movePiece(int sourceRow, int sourceCol, int destRow, int destCol) {
        // TODO: Implement the movement logic

        Piece piece = chessboard[sourceRow][sourceCol].getPiece();

        if (piece != null && piece.getColor().equals(currentPlayerColor)) {
            if (piece.isValidMove(sourceRow, sourceCol, destRow, destCol, chessboard)) {
                chessboard[sourceRow][sourceCol].removePiece();
                chessboard[destRow][destCol].setPiece(piece);

                if (piece instanceof Pawn) {
                    ((Pawn) piece).setFirstMove(false);
                }

                // TODO: check for captures, check/checkmate, and switch player turns

            } else {
                System.out.println("Invalid move. Please try again.");
            }
        } else {
            System.out.println("No piece found or it's not your turn. Please try again.");
        }
    }



    private String getPieceSymbol(Piece piece) {
        // not sure if I need this
        return piece.getType().substring(0, 1).toUpperCase();
    }

    private void clearBlackCaptureflag(ChessTile[][] chessboard){
        //TODO loop through all tiles and set flag to false
    }
    private void clearWhiteCaptureflag(ChessTile[][] chessboard){

        //TODO loop through all tiles and set flag to false
    }

    private void setBlackCaptureflag(ChessTile[][] chessboard){
        //TODO loop through all tiles if they have a piece, check where they can capture and set flag
    }
    private void setWhiteCaptureflag(ChessTile[][] chessboard){

        //TODO loop through all tiles if they have a piece, check where they can capture and set flag
    }

}

