/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author marcosbslinux
 */
public class Labyrinth {

    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 0;

    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;

    private Monster[][] monsterSquare;
    private Player[][] playerSquare;
    private char[][] labyrinthSquare;

    /*
    MÉTODOS
     */
    
    /*
    
    HACER EL CONSTRUCTOR |
                         V
     */
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol) {

    }

    public void spreadPlayers(Player[] players) {
        throw new UnsupportedOperationException();
    }

    public boolean haveAWinner() {
        return playerSquare[exitRow][exitCol] != null;
    }

    public String toString() {

        String tablero = "";
        //Corregir porque aquí lo primero que hace es imprimirme un salto de línea, tengo que saltarme la comprobación en la primera filaó

        for (int i = 0; i < nRows; ++i) {
            for (int j = 0; i < nCols; ++j) {
                if (j == COL) {
                    tablero += "\n" + labyrinthSquare[i][j];
                } else {
                    tablero += labyrinthSquare[i][j];
                }
            }
        }
        return tablero;
    }

    public void addMonster(int row, int col, Monster monster) {
        if (posOK(row, col) && emptyPos(row, col)) {
            labyrinthSquare[row][col] = MONSTER_CHAR;
            monsterSquare[row][col] = monster;
        }

    }

    public Monster putPlayer(Directions direction, Player player) {
        throw new UnsupportedOperationException();
    }

    public void addBlock(Orientation orientation, int startRow, int startCol, int length) {
        throw new UnsupportedOperationException();
    }

    public Directions[] validMoves(int row, int col) {
        throw new UnsupportedOperationException();
    }

    private boolean posOK(int row, int col) {
        return (0 <= row && row <= nRows) && (0 <= col && col <= nCols);
    }

    private boolean emptyPos(int row, int col) {
        return labyrinthSquare[row][col] == EMPTY_CHAR;
    }

    private boolean monsterPos(int row, int col) {
        return labyrinthSquare[row][col] == MONSTER_CHAR;
    }

    private boolean exitPos(int row, int col) {
        return labyrinthSquare[row][col] == EXIT_CHAR;
    }

    private boolean combatPos(int row, int col) {
        return labyrinthSquare[row][col] == COMBAT_CHAR;
    }

    private boolean canStepOn(int row, int col) {
        char tile = labyrinthSquare[row][col];
        
        if (posOK(row, col)) {
            return (tile == EMPTY_CHAR || tile == MONSTER_CHAR || tile == EXIT_CHAR);
        } else {
            return false;
        }
    }

    private void updateOldPos(int row, int col) {
        if (posOK(row, col)) {
            if (labyrinthSquare[row][col] == COMBAT_CHAR) {
                labyrinthSquare[row][col] = MONSTER_CHAR;
            } else {
                labyrinthSquare[row][col] = EMPTY_CHAR;
            }
        }
    }

    private int[] dir2Pos(int row, int col, Directions direction) {
        int[] pos = {row, col};

        switch (direction) {
            case Directions.DOWN ->
                pos[0]--;
            case Directions.UP ->
                pos[0]++;
            case Directions.LEFT ->
                pos[1]--;
            case Directions.RIGHT ->
                pos[1]++;
        }
        return pos;
    }

    private int[] randomEmptyPos() {
        int[] randomPos = new int[2];

        do {
            randomPos[0] = Dice.randomPos(nRows);
            randomPos[1] = Dice.randomPos(nCols);
        } while (labyrinthSquare[randomPos[0]][randomPos[1]] != EMPTY_CHAR);

        return randomPos;
    }

    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player) {
        throw new UnsupportedOperationException();
    }

}
