package CUI;

public class GameState {
    private ChessTile[][] chessboard;
    private String currentPlayer;
    private boolean isCheck;
    protected boolean isCheckmate;


    public boolean isCheckmate() {
        return isCheckmate;
    }

    public void setCheckmate(boolean checkmate) {
        isCheckmate = checkmate;
    }

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


    protected boolean isKingInCheck(String kingColor) {
        //check if king is in check
        return false;
    }


}

