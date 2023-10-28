/*
 * The programs are designed for PDC paper
 */
package ChessGUI;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.DatabaseMetaData;


public final class ChessDBManager {

    private static final String URL = "jdbc:derby:ChessDB;create=true";
//    private static final String USER_NAME = "Chess"; //your DB username
//    private static final String PASSWORD = "Chess"; //your DB password
    Statement statement = null;
        
    Connection conn;

    public ChessDBManager() {
        establishConnection();
    }

    public Connection getConnection() {
        return this.conn;
    }
    
        public void establishConnection() {
        try {
            // Load the EmbeddedDriver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            conn = DriverManager.getConnection(URL);

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
                ex.printStackTrace();
            }
        }

        // Properly shut down the database
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            // Expect an exception upon successful shutdown
            if (!((e.getErrorCode() == 50000) && ("XJ015".equals(e.getSQLState())))) {
                System.err.println("Error: Failed to shut down the database");
                e.printStackTrace();
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
    
    // Selecting player_ID as well
    String sql = "SELECT player_ID, name, rating, joined_date FROM players"; 
    try (PreparedStatement statement = this.conn.prepareStatement(sql);
         ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
            // Including player_ID in the formatted string
            String playerInfo = String.format("ID: %d | Name: %s | Elo: %d | Join Date: %s",
                                              rs.getInt("player_ID"),
                                              rs.getString("name"),
                                              rs.getInt("rating"),
                                              rs.getDate("joined_date").toString());
            players.add(playerInfo);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return players;
}


public List<String> fetchRecentGames() {
    List<String> recentGamesList = new ArrayList<>();

    try (Statement statement = conn.createStatement()) {
        String query = "SELECT G.GAME_ID, P1.NAME as PLAYER1_NAME, P2.NAME as PLAYER2_NAME, G.START_TIME, G.END_TIME " +
                       "FROM GAMES G " +
                       "JOIN PLAYERS P1 ON G.PLAYER1_ID = P1.PLAYER_ID " +
                       "JOIN PLAYERS P2 ON G.PLAYER2_ID = P2.PLAYER_ID";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String gameInfo = "Game ID: " + resultSet.getInt("GAME_ID") + 
                              " | Player 1: " + resultSet.getString("PLAYER1_NAME") +
                              " | Player 2: " + resultSet.getString("PLAYER2_NAME") +
                              " | Start Time: " + resultSet.getTimestamp("START_TIME") +
                              " | End Time: " + resultSet.getTimestamp("END_TIME");
            recentGamesList.add(gameInfo);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return recentGamesList;
}

public List<GameInfo> fetchSavedGames() {
    List<GameInfo> savedGamesList = new ArrayList<>();

    // The SQL query to fetch the saved games
    String query = "SELECT S.SAVE_ID, P1.NAME as PLAYER1_NAME, P2.NAME as PLAYER2_NAME, S.SAVE_TIMESTAMP " +
                   "FROM SAVED_GAMES S " +
                   "JOIN PLAYERS P1 ON S.PLAYER1_ID = P1.PLAYER_ID " +
                   "JOIN PLAYERS P2 ON S.PLAYER2_ID = P2.PLAYER_ID";
    
    try (Statement statement = conn.createStatement(); 
         ResultSet resultSet = statement.executeQuery(query)) {

        while (resultSet.next()) {
            GameInfo game = new GameInfo();
            game.setGameId(resultSet.getInt("SAVE_ID"));
            game.setPlayerOneName(resultSet.getString("PLAYER1_NAME"));
            game.setPlayerTwoName(resultSet.getString("PLAYER2_NAME"));
            game.setStartTime(resultSet.getTimestamp("SAVE_TIMESTAMP"));
            // Since SAVED_GAMES might not have an end time, we'll skip setting that for now
            savedGamesList.add(game);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    System.out.println(savedGamesList);
    return savedGamesList;
}






public void createDatabaseTables(){
    createPlayersTable();
    createGamesTable();
    createSavedGamesTable();
    insertDummyData();
}

public boolean isPlayersTableEmpty() {
    String sql = "SELECT COUNT(*) FROM PLAYERS";
    try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count == 0;
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return false;
}

public void insertDummyData() {
    if (isPlayersTableEmpty()) {
        insertDummyPlayers();
        insertDummyGames();
    }
}


public void clearPlayersTable() {
    String sql = "DELETE FROM PLAYERS";
    try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
        statement.executeUpdate();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void clearGamesTable() {
    String sql = "DELETE FROM GAMES";
    try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
        statement.executeUpdate();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void clearDatabase() {
    clearGamesTable();  // Clear GAMES first due to foreign key constraints
    clearPlayersTable();
}

public int getPlayerIdByName(String playerName) {
    String sql = "SELECT player_ID FROM players WHERE name = ?";
    try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
        statement.setString(1, playerName);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return rs.getInt("player_ID");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return -1; // return -1 or throw an exception if the player is not found
}



public void createPlayersTable() {
    if (!tableExists("PLAYERS")) {
        String sql = "CREATE TABLE PLAYERS (" +
                     "PLAYER_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                     "NAME VARCHAR(255) NOT NULL," +
                     "RATING INT," +
                     "JOINED_DATE DATE" +
                     ")";
        executeUpdate(sql);
    } else {
        System.out.println("PLAYERS table already exists.");
    }
}

public void createGamesTable() {
    if (!tableExists("GAMES")) {
        String sql = "CREATE TABLE GAMES (" +
                     "GAME_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                     "PLAYER1_ID INT REFERENCES PLAYERS(PLAYER_ID)," +
                     "PLAYER2_ID INT REFERENCES PLAYERS(PLAYER_ID)," +
                     "START_TIME TIMESTAMP," +
                     "END_TIME TIMESTAMP" +
                     ")";
        executeUpdate(sql);
    } else {
        System.out.println("GAMES table already exists.");
    }
}

public void createSavedGamesTable() {
    if (!tableExists("SAVED_GAMES")) {
        String sql = "CREATE TABLE SAVED_GAMES (" +
                     "SAVE_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                     "PLAYER1_ID INT REFERENCES PLAYERS(PLAYER_ID)," +
                     "PLAYER2_ID INT REFERENCES PLAYERS(PLAYER_ID)," +
                     "SAVE_TIMESTAMP TIMESTAMP," +
                     "GAME_STATE BLOB" +   // This column will store the serialized game object
                     ")";
        executeUpdate(sql);
    } else {
        System.out.println("SAVED_GAMES table already exists.");
    }
}


private boolean tableExists(String tableName) {
    boolean exists = false;
    try {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName.toUpperCase(), null);
        if (resultSet.next()) {
            exists = true;
        }
        resultSet.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return exists;
}

private void executeUpdate(String sql) {
    try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
        statement.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}



public void insertDummyPlayers() {
    String sql = "INSERT INTO PLAYERS (NAME, RATING, JOINED_DATE) VALUES (?, ?, ?)";
    try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
        // First player
        statement.setString(1, "John Doe");
        statement.setInt(2, 1500);
        statement.setDate(3, java.sql.Date.valueOf("2021-01-01"));
        statement.executeUpdate();

        // Second player
        statement.setString(1, "Jane Smith");
        statement.setInt(2, 1525);
        statement.setDate(3, java.sql.Date.valueOf("2021-02-01"));
        statement.executeUpdate();
        
        // Add more players as needed
        
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void insertDummyGames() {
    int johnDoeId = getPlayerIdByName("John Doe");
    int janeSmithId = getPlayerIdByName("Jane Smith");

    if (johnDoeId == -1 || janeSmithId == -1) {
        System.out.println("Could not find player IDs.");
        return;
    }

    String sql = "INSERT INTO GAMES (PLAYER1_ID, PLAYER2_ID, START_TIME, END_TIME) VALUES (?, ?, ?, ?)";
    try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
        statement.setInt(1, johnDoeId);
        statement.setInt(2, janeSmithId);
        statement.setTimestamp(3, java.sql.Timestamp.valueOf("2021-03-01 10:00:00"));
        statement.setTimestamp(4, java.sql.Timestamp.valueOf("2021-03-01 10:30:00"));
        statement.executeUpdate();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void saveGame(Game currentGame, int player1Id, int player2Id) {
    String sql = "INSERT INTO SAVED_GAMES (PLAYER1_ID, PLAYER2_ID, SAVE_TIMESTAMP, GAME_STATE) VALUES (?, ?, ?, ?)";
    
    try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
        
        // Serialize the current game state to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(currentGame);
        byte[] gameAsBytes = bos.toByteArray();

        // Debug: Print byte array length
        System.out.println("Serialized game byte length: " + gameAsBytes.length);

        // Set parameters for the prepared statement
        statement.setInt(1, player1Id);
        statement.setInt(2, player2Id);
        statement.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
        statement.setBytes(4, gameAsBytes);

        // Check number of rows affected
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            System.out.println("Warning: No rows were inserted.");
        } else {
            System.out.println("Game has been saved. Rows affected: " + rowsAffected);
        }
        
        // Debug: Print last error (if any)
        System.out.println("Last database error (if any): " + statement.getWarnings());
        
        // Optional: Commit changes (in case auto-commit is off)
        conn.commit();
        
    } catch (SQLException | IOException ex) {
        System.out.println("Error while saving game: " + ex.getMessage());
    }
}


public void printSavedGames() {
    String query = "SELECT * FROM SAVED_GAMES";

    try (Statement statement = conn.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

        while (resultSet.next()) {
            int saveId = resultSet.getInt("SAVE_ID");
            int player1Id = resultSet.getInt("PLAYER1_ID");
            int player2Id = resultSet.getInt("PLAYER2_ID");
            Timestamp saveTimestamp = resultSet.getTimestamp("SAVE_TIMESTAMP");

            // If you want to print the actual game state as well, you can deserialize it here,
            // but for simplicity, we're only printing basic info.
            
            System.out.println("Save ID: " + saveId);
            System.out.println("Player 1 ID: " + player1Id);
            System.out.println("Player 2 ID: " + player2Id);
            System.out.println("Save Timestamp: " + saveTimestamp);
            System.out.println("-----------------------------");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


public Game loadGameById(int SAVE_ID) {
    Game loadedGame = null;
    printSavedGames(); //just for testing 
    String sql = "SELECT GAME_STATE FROM SAVED_GAMES WHERE SAVE_ID = ?";

    try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
        statement.setInt(1, SAVE_ID); // Set the SAVE_ID in the prepared statement

        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                byte[] gameAsBytes = rs.getBytes("GAME_STATE");

                if (gameAsBytes != null) {
                    // Deserialize byte array to Game object
                    try (ByteArrayInputStream bis = new ByteArrayInputStream(gameAsBytes);
                         ObjectInputStream ois = new ObjectInputStream(bis)) {
                        loadedGame = (Game) ois.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error during deserialization: " + e.getMessage());
                    }
                } else {
                    System.out.println("No game data found for SAVE_ID: " + SAVE_ID);
                }
            } else {
                System.out.println("No game found for SAVE_ID: " + SAVE_ID);
            }
        }
    } catch (SQLException ex) {
        System.out.println("SQL error while fetching game: " + ex.getMessage());
    }

    if (loadedGame == null) {
        System.out.println("The loaded game is null for SAVE_ID: " + SAVE_ID);
    }

    return loadedGame;
}










}
