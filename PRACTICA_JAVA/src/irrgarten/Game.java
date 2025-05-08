package irrgarten;

import java.util.ArrayList;

/**
 * Main game controller class that manages the game state and flow.
 * Handles player turns, combat, movement, and game state updates.
 * 
 * @author marcosbslinux
 */
public class Game {

    /** Maximum number of rounds allowed in a combat */
    private static final int MAX_ROUNDS = 10;

    /** Index of the current player in the players list */
    private int currentPlayerIndex;
    
    /** Game log containing messages about game events */
    private String log;
    
    /** List of players in the game */
    private ArrayList<Player> players;
    
    /** List of monsters in the game */
    private ArrayList<Monster> monsters;
    
    /** Reference to the current player */
    private Player currentPlayer;
    
    /** Game labyrinth containing players and monsters */
    private Labyrinth labyrinth;

    /**
     * Initializes a new game with the specified number of players
     * @param nplayers Number of players in the game
     */
    public Game(int nplayers) {
        log = "";

        //Inicializo players
        players = new ArrayList<Player>();

        //Creo los players y los meto en el contenedor players
        for (int i = 0; i < nplayers; ++i) {
            char c = (char) ('0' + i);
            players.add(new Player(c, Dice.randomIntelligence(), Dice.randomStrength()));
        }
        
        labyrinth = new Labyrinth(10, 10, 3, 3);


        //Esparcir a los jugadores por el tablero
        labyrinth.spreadPlayers(players);

        //Elijo quién empieza la partida
        currentPlayerIndex = Dice.whoStarts(nplayers);

        //Fijo el jugador actual
        currentPlayer = players.get(currentPlayerIndex);

        //Inicializo monsters
        monsters = new ArrayList<Monster>();
        
        configureLabyrinth();

    }

    /**
     * Checks if the game has ended (a player has won)
     * @return true if the game has ended, false otherwise
     */
    public boolean finished() {
        return labyrinth.haveAWinner();
    }

    /**
     * Advances the game by one step, handling player movement and combat
     * @param preferredDirection The player's desired movement direction
     * @return true if the game has ended after this step, false otherwise
     */
    public boolean nextStep(Directions preferredDirection) {
        log = "";
        boolean dead = currentPlayer.dead();
        boolean endGame;
        Directions direction;
        Monster monster;
        GameCharacter winner;

        if (!dead) {
            direction = actualDirection(preferredDirection);

            if (direction != preferredDirection) {
                logPlayerNoOrders();
            }

            monster = labyrinth.putPlayer(direction, currentPlayer);

            if (monster == null) {
                logNoMonster();
            } else {
                winner = combat(monster);
                manageReward(winner);
            }
        } else {
            manageResurrection();
        }

        endGame = finished();

        if (!endGame) {
            nextPlayer();
        }

        return endGame;
    }

    /**
     * Gets the current state of the game
     * @return GameState object containing all relevant game information
     */
    public GameState getGameState() {
        StringBuilder players_toS = new StringBuilder();
        for(int i=0; i<players.size(); ++i)
        {
            players_toS.append(players.get(i).toString());
            players_toS.append("\n");
        }
        
        StringBuilder monsters_toS = new StringBuilder();
        for(int i=0; i<monsters.size(); ++i)
        {
            monsters_toS.append(monsters.get(i).toString());
            monsters_toS.append(", ");
        }
        //CAMBIAR
        GameState state = new GameState(labyrinth.toString(), players_toS.toString(),
                monsters_toS.toString(), currentPlayerIndex,
                finished(), log);
        return state;
    }

    /**
     * Configures the labyrinth by placing monsters in their positions
     */
    private void configureLabyrinth() {
        // Set labyrinth dimensions and number of monsters
         final int rows = 10;
         final int cols = 10;

         // Add outer walls to the labyrinth
         labyrinth.addBlock(Orientation.HORIZONTAL, 0, 0, cols);
         labyrinth.addBlock(Orientation.HORIZONTAL, rows-1, 0, cols);
         labyrinth.addBlock(Orientation.VERTICAL, 1, 0, rows);
         labyrinth.addBlock(Orientation.VERTICAL, 1, cols-1, rows);

         // Create and add monsters to the labyrinth
         Monster monster = new Monster("1", 200, 200);
         labyrinth.addMonster(2, 6, monster);

         Monster monster2 = new Monster("1", Dice.randomIntelligence(), Dice.randomStrength());
         labyrinth.addMonster(7, 4, monster2);
         
         monsters.add(monster);
         monsters.add(monster2);
        
    }

    /**
     * Advances to the next player's turn
     */
    private void nextPlayer() {
        //Pasar al siguiente jugador
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);
    }

    /**
     * Determines the actual movement direction based on player input and valid moves
     * @param preferredDirection The player's desired movement direction
     * @return The actual direction the player will move
     */
    private Directions actualDirection(Directions preferredDirection) {
        int currentRow = currentPlayer.getRow();
        int currentCol = currentPlayer.getCol();

        ArrayList<Directions> validMoves = labyrinth.validMoves(currentRow, currentCol);

        Directions output = currentPlayer.move(preferredDirection, validMoves);

        return output;
    }

    /**
     * Handles combat between the current player and a monster
     * @param monster The monster to fight against
     * @return The winner of the combat (PLAYER or MONSTER)
     */
    private GameCharacter combat(Monster monster) {
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        float playerAttack = currentPlayer.attack();
        float monsterAttack;
        boolean lose = monster.defend(playerAttack);

        while (!lose && rounds < MAX_ROUNDS) {
            winner = GameCharacter.MONSTER;
            rounds++;

            monsterAttack = monster.attack();
            lose = currentPlayer.defend(monsterAttack);

            if (!lose) {
                playerAttack = currentPlayer.attack();
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack);
            }
        }
        logRounds(rounds, MAX_ROUNDS);

        return winner;
    }

    /**
     * Manages rewards after combat based on the winner
     * @param winner The winner of the combat (PLAYER or MONSTER)
     */
    private void manageReward(GameCharacter winner) {
        if (winner == GameCharacter.PLAYER) {
            currentPlayer.receiveReward();
            logPlayerWon();
        } else {
            logMonsterWon();
        }
    }

    /**
     * Handles player resurrection logic
     */
    private void manageResurrection() {
        boolean resurrect = Dice.resurrectPlayer();

        if (resurrect) {
            currentPlayer.resurrect();
            logResurrected();
        } else {
            logPlayerSkipTurn();
        }
    }

    /**
     * Logs a message when the player wins combat
     */
    private void logPlayerWon() {
        log += "El jugador " + currentPlayer + " ha ganado el combate.\n";
    }

    /**
     * Logs a message when the monster wins combat
     */
    private void logMonsterWon() {
        log += "El monstruo ha ganado el combate.\n";
    }

    /**
     * Logs a message when a player is resurrected
     */
    private void logResurrected() {
        log += "El jugador " + currentPlayer + " ha resucitado.\n";
    }

    /**
     * Logs a message when a player skips their turn due to being dead
     */
    private void logPlayerSkipTurn() {
        log += "El jugador " + currentPlayer + " ha perdido el turno por estar muerto.\n";
    }

    /**
     * Logs a message when a player cannot follow movement orders
     */
    private void logPlayerNoOrders() {
        log += "El jugador " + currentPlayer + " no ha podido seguir las instrucciones del jugador humano.\n";
    }

    /**
     * Logs a message when a player moves to an empty cell
     */
    private void logNoMonster() {
        log += "El jugador " + currentPlayer + " se ha movido a una celda vacía o no le ha sido posible moverse.\n";
    }

    /**
     * Logs the number of rounds played in combat
     * @param rounds Number of rounds played
     * @param max Maximum number of rounds allowed
     */
    private void logRounds(int rounds, int max) {
        log += "Se han jugado " + Integer.toString(rounds) + " de " + Integer.toString(max) + " rondas.\n";
    }
}
