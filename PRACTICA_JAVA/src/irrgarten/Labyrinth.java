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
    private static final int COL = 1;

    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;

    private Monster[][] monsters;//CAMBIAR
    private Player[][] players;//ESTOS
    private char[][] labyrinth;//NOMBRES

    /*
    MÉTODOS
     */
 /*
    
    HACER EL CONSTRUCTOR |
                         V
     */
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol) {
        monsters=new Monster[nRows][nCols];
        players=new Player[nRows][nCols];
        labyrinth=new char[nRows][nCols];

    }

    public void spreadPlayers(Player[] players) {
        throw new UnsupportedOperationException();
    }

    public boolean haveAWinner() {
        return players[exitRow][exitCol] != null;
    }

    public String toString() {

        StringBuilder tablero = new StringBuilder();
        for (int i = 0; i < nRows; ++i) {
            for (int j = 0; j < nCols; ++j) {
                tablero.append(labyrinth[i][j]);
            }
            tablero.append("\n");
        }
        return tablero.toString();
    }

    public void addMonster(int row, int col, Monster monster) {
        if (posOK(row, col) && emptyPos(row, col)) {
            labyrinth[row][col] = MONSTER_CHAR;
            monster.setPos(row, col);
            monsters[row][col] = monster;
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
        return (0 <= row && row < nRows) && (0 <= col && col < nCols);
    }

    private boolean emptyPos(int row, int col) {
        return labyrinth[row][col] == EMPTY_CHAR;
    }

    private boolean monsterPos(int row, int col) {
        return labyrinth[row][col] == MONSTER_CHAR;
    }

    private boolean exitPos(int row, int col) {
        return labyrinth[row][col] == EXIT_CHAR;
    }

    private boolean combatPos(int row, int col) {
        return labyrinth[row][col] == COMBAT_CHAR;
    }

    private boolean canStepOn(int row, int col) {
        return posOK(row, col) && (emptyPos(row, col) || monsterPos(row, col) || exitPos(row, col));
    }

    private void updateOldPos(int row, int col) {
        if (posOK(row, col)) {
            if (labyrinth[row][col] == COMBAT_CHAR) {
                labyrinth[row][col] = MONSTER_CHAR;
            } else {
                labyrinth[row][col] = EMPTY_CHAR;
            }
        }
    }

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

    private int[] randomEmptyPos() {
        int[] randomPos = new int[2];

        do {
            randomPos[ROW] = Dice.randomPos(nRows);
            randomPos[COL] = Dice.randomPos(nCols);
        } while (labyrinth[randomPos[0]][randomPos[1]] != EMPTY_CHAR);

        return randomPos;
    }

    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player) {
        throw new UnsupportedOperationException();
    }

}
