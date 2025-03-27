package irrgarten;

/**
 * Represents the complete state of the game at a given moment.
 * Contains all necessary information to display and manage the game state.
 * 
 * @author marcosbslinux
 */
public class GameState {

    /** String representation of the labyrinth */
    private String labyrinth;
    
    /** String representation of all players */
    private String players;
    
    /** String representation of all monsters */
    private String monsters;
    
    /** Index of the current player */
    private int currentPlayer;
    
    /** Indicates if there is a winner */
    private boolean winner;
    
    /** Game log containing messages about game events */
    private String log;

    /**
     * Constructs a new GameState with the specified parameters
     * @param l String representation of the labyrinth
     * @param p String representation of all players
     * @param m String representation of all monsters
     * @param c Index of the current player
     * @param w Indicates if there is a winner
     * @param lo Game log containing messages about game events
     */
    public GameState(String l, String p, String m, int c, boolean w, String lo) {
        labyrinth = l;
        players = p;
        monsters = m;
        currentPlayer = c;
        winner = w;
        log = lo;
    }

    /**
     * Gets the string representation of the labyrinth
     * @return Labyrinth string
     */
    public String getLabyrinth() {
        return labyrinth;
    }

    /**
     * Gets the string representation of all players
     * @return Players string
     */
    public String getPlayers() {
        return players;
    }

    /**
     * Gets the string representation of all monsters
     * @return Monsters string
     */
    public String getMonsters() {
        return monsters;
    }

    /**
     * Gets the index of the current player
     * @return Current player index
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Checks if there is a winner
     * @return true if there is a winner, false otherwise
     */
    public boolean getWinner() {
        return winner;
    }

    /**
     * Gets the game log containing messages about game events
     * @return Game log string
     */
    public String getLog() {
        return log;
    }
}
