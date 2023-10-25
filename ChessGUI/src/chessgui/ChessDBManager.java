/*
 * The programs are designed for PDC paper
 */
package chessgui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public final class ChessDBManager {

    private static final String URL = "jdbc:derby://localhost:1527/ChessDB";
    private static final String USER_NAME = "Chess"; //your DB username
    private static final String PASSWORD = "Chess"; //your DB password
    Statement statement = null;
        
    Connection conn;

    public ChessDBManager() {
        establishConnection();
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void establishConnection()  {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

            if (conn != null) {
                System.out.println("Connected to the database");
            } else {
                System.err.println("Failed to connect to the database");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: JDBC driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error: Failed to establish a connection to the database");
            e.printStackTrace();
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                // Log or throw the exception
                ex.printStackTrace();
            }
        }
    }

    public ResultSet queryDB(String sql) {
        try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
            return statement.executeQuery();
        } catch (SQLException ex) {
            // Log or throw the exception
            ex.printStackTrace();
            return null;
        }
    }

    public void updateDB(String sql) {
        try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            // Log or throw the exception
            ex.printStackTrace();
        }
    }
    
public List<String> fetchPlayersFromDB() {
    List<String> players = new ArrayList<>();
    String sql = "SELECT name, rating, joined_date FROM players"; // Assuming 'name', 'elo', and 'join_date' are columns in your 'players' table
    try (PreparedStatement statement = this.conn.prepareStatement(sql);
         ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
            String playerInfo = String.format("Name: %s | Elo: %d | Join Date: %s",
                                              rs.getString("name"),
                                              rs.getInt("rating"),
                                              rs.getDate("joined_date").toString());  // Assuming join_date is stored as DATE
            players.add(playerInfo);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return players;
}

public List<String> fetchRecentGames() {
    List<String> recentGamesList = new ArrayList<>();

    try {
        Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        Statement statement = connection.createStatement();
        String query = "SELECT G.GAME_ID, P1.NAME as PLAYER1_NAME, P2.NAME as PLAYER2_NAME, G.START_TIME, G.END_TIME " +
                       "FROM chess.games G " +
                       "JOIN chess.players P1 ON G.PLAYER1_ID = P1.PLAYER_ID " +
                       "JOIN chess.players P2 ON G.PLAYER2_ID = P2.PLAYER_ID";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String gameInfo = "Game ID: " + resultSet.getInt("GAME_ID") + 
                              " | Player 1: " + resultSet.getString("PLAYER1_NAME") +
                              " | Player 2: " + resultSet.getString("PLAYER2_NAME") +
                              " | Start Time: " + resultSet.getTimestamp("START_TIME") +
                              " | End Time: " + resultSet.getTimestamp("END_TIME");
            recentGamesList.add(gameInfo);
        }
        
        resultSet.close();
        statement.close();
        connection.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return recentGamesList;
}



}
