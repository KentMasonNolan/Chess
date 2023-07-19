package CUI;

public class GameState {
    private ChessTile[][] chessboard;
    private String currentPlayer;
    private boolean isCheck;
    private boolean isCheckmate;

    public void switchPlayer() {
        if (currentPlayer.equals("white")) {
            currentPlayer = "black";
        } else {
            currentPlayer = "white";
        }
    }

    public GameState() {
        // Initialize the chessboard and other fields as needed
    }


    public boolean isKingInCheck(String kingColor) {
        //check if king is in check
        return false;
    }


}

