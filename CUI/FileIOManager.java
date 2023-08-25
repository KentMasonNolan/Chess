package CUI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileIOManager {

    public List<String> getSavedGamesList(String metadataFilePath) throws IOException {
        createMetadataFileIfNotExists(metadataFilePath); // Create the file if it doesn't exist
        List<String> savedGames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(metadataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                savedGames.add(line);
            }
        }
        return savedGames;
    }

    public void writeSavedGameMetadata(String metadataFilePath, String gameInfo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(metadataFilePath, true))) {
            writer.write(gameInfo);
            writer.newLine();
        }
    }

    private void createMetadataFileIfNotExists(String metadataFilePath) throws IOException {
        File metadataFile = new File(metadataFilePath);
        if (!metadataFile.exists()) {
            metadataFile.createNewFile();
        }
    }

    public void writeUserList(String userListFilePath, List<String> userLines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userListFilePath))) {
            for (String userLine : userLines) {
                writer.write(userLine);
                writer.newLine();
            }
        }
    }

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

    public void writeMoveHistory(String moveHistoryFilePath, List<Game.MoveInfo> moveHistoryLines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(moveHistoryFilePath))) {
            for (Game.MoveInfo moveHistoryLine : moveHistoryLines) {
                writer.write(moveHistoryLine.toString());
                writer.newLine();
            }
        }
    }

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



