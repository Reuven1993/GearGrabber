package my_game;

import DB.ExcelDB;
import DB.ExcelTable;
import base.Game;
import java.io.File;

/**
 * Manages the player scoreboard using Excel as a database.
 */
public class ScoreBoard {
    private static final String TABLE_NAME = "PlayerScores";
    private ExcelTable scoreTable;
    private static final String[] HEADINGS = {"ID", "PlayerName", "Score", "Difficulty", "Date", "GearsCollected", "TimeRemaining"};
    
    public ScoreBoard() {
        ExcelDB db = Game.excelDB();
        if (db == null) {
            throw new IllegalStateException("ExcelDB instance from Game.excelDB() is null. Cannot initialize ScoreBoard.");
        }
        
        // Ensure the db_tables directory exists
        File dbDir = new File("db_tables");
        if (!dbDir.exists()) {
            boolean dirCreated = dbDir.mkdir();
            if (!dirCreated) {
                throw new IllegalStateException("Failed to create db_tables directory.");
            }
            System.out.println("Created db_tables directory.");
        }

        try {
            // Try to get the table from the DB
            scoreTable = db.getTable(TABLE_NAME);
            
            if (scoreTable == null) {
                File scoreFile = new File("db_tables\\" + TABLE_NAME + ".xlsx");
                if (scoreFile.exists()) {
                    // Load existing table (assumed to have 7-column format)
                    scoreTable = db.createTableFromExcel(TABLE_NAME);
                    System.out.println("Loaded existing score table from file");
                } else {
                    // Create a new table with the 7-column header
                    scoreTable = db.createNewTable(TABLE_NAME, HEADINGS);
                    System.out.println("Created new score table with " + HEADINGS.length + " columns");
                }
            } else {
                System.out.println("Using existing score table from DB");
            }
        } catch (Exception e) {
            System.err.println("Error initializing score table: " + e.getMessage());
            e.printStackTrace();
            throw new IllegalStateException("Failed to initialize score table: " + e.getMessage(), e);
        }
        
        if (scoreTable == null) {
            throw new IllegalStateException("Score table is still null after initialization.");
        }
    }
    
    public void addScore(String playerName, int score, String difficulty, int gearsCollected, long timeRemaining) {
        try {
            // Format date
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(new java.util.Date());
            
            // Generate a unique ID
            String uniqueId = "score_" + System.currentTimeMillis();
            
            // Create row data with 7 columns
            String[] row = {
                uniqueId,               // ID
                playerName,             // PlayerName
                String.valueOf(score),  // Score
                difficulty,             // Difficulty
                date,                   // Date
                String.valueOf(gearsCollected), // GearsCollected
                String.valueOf(timeRemaining)   // TimeRemaining
            };
            
            System.out.println("Adding score with ID: " + uniqueId);
            
            // Insert the row
            try {
                scoreTable.insertRow(row);
                System.out.println("Score added successfully");
            } catch (Exception e) {
                System.err.println("Error inserting row: " + e.getMessage());
                if (e.getMessage() != null && e.getMessage().contains("Primary key already exist")) {
                    uniqueId = "score_" + System.currentTimeMillis() + "_" + Math.random();
                    row[0] = uniqueId;
                    scoreTable.insertRow(row);
                    System.out.println("Score added with alternative ID: " + uniqueId);
                } else {
                    throw e;
                }
            }
            
            // Commit changes to save to file
            System.out.println("Committing changes...");
            Game.excelDB().commit();
            System.out.println("Changes committed");
            
        } catch (Exception e) {
            System.err.println("Error adding score: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public String[][] getTopScores(int limit) {
        try {
            // Sort the table by the Score column (index 2) in descending order
            scoreTable.sortTable(2, true);
            
            // Get the table data
            String[][] allScores = scoreTable.getTableAsMatrix();
            
            // Handle empty table
            if (allScores == null || allScores.length == 0) {
                System.out.println("No scores available");
                return new String[0][0];
            }
            
            System.out.println("Retrieved " + allScores.length + " scores from table");
            
            // Return top N scores
            int resultSize = Math.min(limit, allScores.length);
            String[][] topScores = new String[resultSize][allScores[0].length];
            
            for (int i = 0; i < resultSize; i++) {
                topScores[i] = allScores[i];
            }
            
            return topScores;
        } catch (Exception e) {
            System.err.println("Error getting top scores: " + e.getMessage());
            e.printStackTrace();
            return new String[0][0];
        }
    }
}