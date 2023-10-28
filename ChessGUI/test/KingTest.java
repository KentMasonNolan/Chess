import ChessGUI.King;
import ChessGUI.ChessTile;
import ChessGUI.GameState;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KingTest {

    private ChessTile[][] chessboard;
    private King whiteKing;
    private GameState gameState;

    @Before
    public void setUp() {
        chessboard = new ChessTile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                chessboard[row][col] = new ChessTile(row, col);
            }
        }
        gameState = new GameState();
        whiteKing = new King("white", 4, 4, gameState);
        chessboard[4][4].setPiece(whiteKing);
    }

    @Test
    public void testValidKingMove() {
        assertTrue(whiteKing.isValidMove(4, 4, 5, 4, chessboard));
    }

    @Test
    public void testInvalidKingMove() {
        assertFalse(whiteKing.isValidMove(4, 4, 6, 6, chessboard));
    }

    @Test
    public void testFirstMoveStatus() {
        assertTrue(whiteKing.isFirstMove());
        whiteKing.isValidMove(4, 4, 5, 4, chessboard);
        assertFalse(whiteKing.isFirstMove());
    }
}
