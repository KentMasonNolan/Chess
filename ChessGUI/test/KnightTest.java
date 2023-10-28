/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import ChessGUI.Knight;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ChessGUI.ChessTile;


/**
 *
 * @author kentn
 */   


import static org.junit.Assert.*;

public class KnightTest {

    private ChessTile[][] chessboard;
    private Knight knight;

    @Before
    public void setUp() {
        // Initialize a chessboard with empty tiles
        chessboard = new ChessTile[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessboard[i][j] = new ChessTile(i, j);
            }
        }

        // Create a knight at 4,4
        knight = new Knight("White", 4, 4);
        chessboard[4][4].setPiece(knight);
    }

    @Test
    public void testValidKnightMove() {
        // Test a valid knight move
        boolean result = knight.isValidMove(4, 4, 6, 5, chessboard);
        assertTrue(result);
    }

    @Test
    public void testInvalidKnightMove() {
        // Test an invalid knight move
        boolean result = knight.isValidMove(4, 4, 6, 6, chessboard);
        assertFalse(result);
    }
}
