/*
 * The programs are designed for PDC paper
 */
package ChessGUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public final class ChessDBManager {

    private static final String URL = "jdbc:derby://localhost:1527/ChessDB";
    private static final String USER_NAME = "Chess"; //your DB username
    private static final String PASSWORD = "Chess"; //your DB password
    
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
            }
        } catch (Exception e) {
            // Log or throw the exception
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
    String sql = "SELECT name FROM players"; // Assuming 'name' is a column in your 'players' table
    try {
        ResultSet rs = queryDB(sql);
        while (rs.next()) {
            players.add(rs.getString("name"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return players;
}
}
