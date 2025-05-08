package irrgarten;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the game's labyrinth structure containing players, monsters, and obstacles.
 * Manages player movement, monster placement, and win conditions.
 * 
 * @author marcosbslinux
 */
public class Labyrinth {

    /** Character representing a block/wall in the labyrinth */
    private static final char BLOCK_CHAR = 'X';
    
    /** Character representing an empty space in the labyrinth */
    private static final char EMPTY_CHAR = '-';
    
    /** Character representing a monster in the labyrinth */
    private static final char MONSTER_CHAR = 'M';
    
    /** Character representing a combat space in the labyrinth */
    private static final char COMBAT_CHAR = 'C';
    
    /** Character representing the exit in the labyrinth */
    private static final char EXIT_CHAR = 'E';
    
    /** Array index for row position */
    private static final int ROW = 0;
    
    /** Array index for column position */
    private static final int COL = 1;

    /** Number of rows in the labyrinth */
    private int nRows;
    
    /** Number of columns in the labyrinth */
    private int nCols;
    
    /** Row position of the exit */
    private int exitRow;
    
    /** Column position of the exit */
    private int exitCol;

    /** 2D array tracking monster positions */
    private Monster[][] monsters;
    
    /** 2D array tracking player positions */
    private Player[][] players;
    
    /** 2D array representing the labyrinth layout */
    private char[][] labyrinth;

    /**
     * Constructs a new labyrinth with specified dimensions and exit position
     * @param nRows Number of rows in the labyrinth
     * @param nCols Number of columns in the labyrinth
     * @param exitRow Row position of the exit
     * @param exitCol Column position of the exit
     */
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol) {
        // Initialize the monsters, players, and labyrinth arrays with the given dimensions
        monsters = new Monster[nRows][nCols];
        players = new Player[nRows][nCols];
        labyrinth = new char[nRows][nCols];
        
        for (int i=0; i<nRows; ++i)
            for (int j=0; j<nCols; ++j)
                labyrinth[i][j] = EMPTY_CHAR;

        // Set the exit column and row positions
        this.exitCol = exitCol;
        this.exitRow = exitRow;
        labyrinth[exitRow][exitCol] = EXIT_CHAR;

        // Set the number of rows and columns in the labyrinth
        this.nRows = nRows;
        this.nCols = nCols;

    }

    /**
     * Randomly distributes players across empty positions in the labyrinth
     * @param players Array of players to place in the labyrinth
     */
    public void spreadPlayers(ArrayList<Player> players) {
        int[] pos;
        Player p;

        for (int i = 0; i < players.size(); ++i) {
            pos = randomEmptyPos();
            p = players.get(i);
            putPlayer2D(-1, -1, pos[ROW], pos[COL], p);
        }
    }

    /**
     * Checks if there is a winner by seeing if a player is at the exit
     * @return true if a player is at the exit, false otherwise
     */
    public boolean haveAWinner() {
        return players[exitRow][exitCol] != null;
    }

    /**
     * Generates a string representation of the labyrinth
     * @return String showing the labyrinth layout with players, monsters and obstacles
     */
    public String toString() {

        StringBuilder tablero = new StringBuilder();
        for (int i = 0; i < nRows; ++i) {
            for (int j = 0; j < nCols; ++j) {
                tablero.append(labyrinth[i][j]);
                tablero.append(" ");
            }
            tablero.append("\n");
        }
        return tablero.toString();
    }

    /**
     * Adds a monster to the labyrinth at specified position if valid
     * @param row Row position to add monster
     * @param col Column position to add monster
     * @param monster Monster to add
     */
    public void addMonster(int row, int col, Monster monster) {
        if (posOK(row, col) && emptyPos(row, col)) {
            labyrinth[row][col] = MONSTER_CHAR;
            monster.setPos(row, col);
            monsters[row][col] = monster;
        }

    }

    /**
     * Moves a player in the specified direction
     * @param direction Direction to move player
     * @param player Player to move
     * @return Monster if player moved into combat, null otherwise
     */
    public Monster putPlayer(Directions direction, Player player) {
        int oldRow = player.getRow();
        int oldCol = player.getCol();
        int[] newPos;

        newPos = dir2Pos(oldRow, oldCol, direction);

        Monster monster = putPlayer2D(oldRow, oldCol, newPos[ROW], newPos[COL], player);

        return monster;
    }

    /**
     * Adds a block/wall to the labyrinth
     * @param orientation Orientation of the block (VERTICAL or HORIZONTAL)
     * @param startRow Starting row position
     * @param startCol Starting column position
     * @param length Number of cells to fill with block
     */
    public void addBlock(Orientation orientation, int startRow, int startCol, int length) {
        int incRow, incCol, row, col;

        if (orientation == Orientation.VERTICAL) {
            incRow = 1;
            incCol = 0;
        } else {
            incRow = 0;
            incCol = 1;
        }

        row = startRow;
        col = startCol;

        while (posOK(row, col) && (emptyPos(row, col) && (length > 0))) {
            labyrinth[row][col] = BLOCK_CHAR;
            length -= 1;
            row += incRow;
            col += incCol;
        }
    }

    /**
     * Gets valid movement directions from current position
     * @param row Current row position
     * @param col Current column position
     * @return Array of valid Directions
     */
    public ArrayList<Directions> validMoves(int row, int col) {
        ArrayList<Directions> output = new ArrayList<Directions>();

        if (canStepOn(row + 1, col)) {
            output.add(Directions.DOWN);
        }
        if (canStepOn(row - 1, col)) {
            output.add(Directions.UP);
        }
        if (canStepOn(row, col + 1)) {
            output.add(Directions.RIGHT);
        }
        if (canStepOn(row, col - 1)) {
            output.add(Directions.LEFT);
        }

        return output;
    }

    /**
     * Checks if position is within labyrinth bounds
     * @param row Row to check
     * @param col Column to check
     * @return true if position is valid, false otherwise
     */
    private boolean posOK(int row, int col) {
        return (0 <= row && row < nRows) && (0 <= col && col < nCols);
    }

    /**
     * Checks if position is empty
     * @param row Row to check
     * @param col Column to check
     * @return true if position is empty, false otherwise
     */
    private boolean emptyPos(int row, int col) {
        return labyrinth[row][col] == EMPTY_CHAR;
    }

    /**
     * Checks if position contains a monster
     * @param row Row to check
     * @param col Column to check
     * @return true if position has monster, false otherwise
     */
    private boolean monsterPos(int row, int col) {
        return labyrinth[row][col] == MONSTER_CHAR;
    }

    /**
     * Checks if position is the exit
     * @param row Row to check
     * @param col Column to check
     * @return true if position is exit, false otherwise
     */
    private boolean exitPos(int row, int col) {
        return labyrinth[row][col] == EXIT_CHAR;
    }

    /**
     * Checks if position is a combat space
     * @param row Row to check
     * @param col Column to check
     * @return true if position is combat space, false otherwise
     */
    private boolean combatPos(int row, int col) {
        return labyrinth[row][col] == COMBAT_CHAR;
    }

    /**
     * Checks if position can be stepped on (empty, monster or exit)
     * @param row Row to check
     * @param col Column to check
     * @return true if position can be stepped on, false otherwise
     */
    private boolean canStepOn(int row, int col) {
        return posOK(row, col) && (emptyPos(row, col) || monsterPos(row, col) || exitPos(row, col));
    }

    /**
     * Updates old position after player moves
     * @param row Row of old position
     * @param col Column of old position
     */
    private void updateOldPos(int row, int col) {
        if (posOK(row, col)) {
            if (labyrinth[row][col] == COMBAT_CHAR) {
                labyrinth[row][col] = MONSTER_CHAR;
            } else {
                labyrinth[row][col] = EMPTY_CHAR;
            }
        }
    }

    /**
     * Converts direction to new position coordinates
     * @param row Current row
     * @param col Current column
     * @param direction Direction to move
     * @return Array with new row and column coordinates
     */
    private int[] dir2Pos(int row, int col, Directions direction) {
        int[] pos = new int[2];

        pos[ROW] = row;
        pos[COL] = col;

        switch (direction) {
            case Directions.DOWN ->
                pos[ROW]++;
            case Directions.UP ->
                pos[ROW]--;
            case Directions.LEFT ->
                pos[COL]--;
            case Directions.RIGHT ->
                pos[COL]++;
        }
        return pos;
    }

    /**
     * Finds a random empty position in the labyrinth
     * @return Array with row and column of empty position
     */
    private int[] randomEmptyPos() {
        int[] randomPos = new int[2];

        do {
            randomPos[ROW] = Dice.randomPos(nRows);
            randomPos[COL] = Dice.randomPos(nCols);
        } while (labyrinth[randomPos[0]][randomPos[1]] != EMPTY_CHAR);

        return randomPos;
    }

    /**
     * Moves player to new position in 2D space
     * @param oldRow Previous row position
     * @param oldCol Previous column position
     * @param row New row position
     * @param col New column position
     * @param player Player to move
     * @return Monster if moved into combat, null otherwise
     */
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player) {
        Monster output = null;
        Player p;
        boolean monsterPos;
        char number;
        if (canStepOn(row, col)) {
            if (posOK(oldRow, oldCol)) {
                p = players[oldRow][oldCol];
                if (p == player) {
                    updateOldPos(oldRow, oldCol);
                    players[oldRow][oldCol] = null;
                }
            }

            monsterPos = monsterPos(row, col);

            if (monsterPos) {
                labyrinth[row][col] = COMBAT_CHAR;
                output = monsters[row][col];
            } else {
                number = player.getNumber();
                labyrinth[row][col] = number;
            }

            players[row][col] = player;

            player.setPos(row, col);
        }
        return output;
    }

}
