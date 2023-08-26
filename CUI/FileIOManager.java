package CUI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileIOManager {

    // retrieves the list of saved games from the specified metadata file
    // if the file does not exist, it creates an empty one
    public List<String> getSavedGamesList(String metadataFilePath) throws IOException {
        // create the metadata file if it doesn't exist
        createMetadataFileIfNotExists(metadataFilePath);

        List<String> savedGames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(metadataFilePath))) {
            String line;
            // read each line from the metadata file and add it to the list of saved games
            while ((line = reader.readLine()) != null) {
                savedGames.add(line);
            }
        }
        return savedGames;
    }

    // writes game information to the specified metadata file
    // the new information is appended to the existing content
    public void writeSavedGameMetadata(String metadataFilePath, String gameInfo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(metadataFilePath, true))) {
            // write the game information to the metadata file and add a new line
            writer.write(gameInfo);
            writer.newLine();
        }
    }

    // creates the metadata file if it does not exist
    private void createMetadataFileIfNotExists(String metadataFilePath) throws IOException {
        File metadataFile = new File(metadataFilePath);
        // if the metadata file does not exist, create a new empty file
        if (!metadataFile.exists()) {
            metadataFile.createNewFile();
        }
    }

    // writes the provided list of user lines to the specified user list file
    public void writeUserList(String userListFilePath, List<String> userLines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userListFilePath))) {
            for (String userLine : userLines) {
                writer.write(userLine);
                writer.newLine();
            }
        }
    }

    // reads and returns the list of user lines from the specified user list file
    public List<String> readUserList(String userListFilePath) throws IOException {
        List<String> userLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(userListFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                userLines.add(line);
            }
        }
        return userLines;
    }

    // writes the provided list of move history lines to the specified move history file
    public void writeMoveHistory(String moveHistoryFilePath, List<MoveInfo> moveHistoryLines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(moveHistoryFilePath))) {
            for (MoveInfo moveHistoryLine : moveHistoryLines) {
                writer.write(moveHistoryLine.toString());
                writer.newLine();
            }
        }
    }

    // reads and returns the list of move history lines from the specified move history file
    public List<String> readMoveHistory(String moveHistoryFilePath) throws IOException {
        List<String> moveHistoryLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(moveHistoryFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                moveHistoryLines.add(line);
            }
        }
        return moveHistoryLines;
    }
}



