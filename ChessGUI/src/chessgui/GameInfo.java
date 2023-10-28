/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessGUI;

import java.sql.Timestamp;

/**
 *
 * @author kentn
 */
public class GameInfo {
    private int gameId;
    private String playerOneName;
    private String playerTwoName;
    private Timestamp startTime;
    private Timestamp endTime;

    public GameInfo() {
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getPlayerOneName() {
        return playerOneName;
    }

    public void setPlayerOneName(String playerOneName) {
        this.playerOneName = playerOneName;
    }

    public String getPlayerTwoName() {
        return playerTwoName;
    }

    public void setPlayerTwoName(String playerTwoName) {
        this.playerTwoName = playerTwoName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }


    @Override
    public String toString() {
        return "Game ID: " + gameId + 
               " | Player 1: " + playerOneName +
               " | Player 2: " + playerTwoName +
               " | Start Time: " + startTime +
               " | End Time: " + endTime;
    }
}
