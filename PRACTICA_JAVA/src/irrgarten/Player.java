package irrgarten;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a player in the Irrgarten game.
 * Players have attributes like intelligence, strength and health,
 * and can collect weapons and shields to improve their abilities.
 * 
 * @author marcosbslinux
 * @version 1.0
 */
public class Player {

    /** Maximum number of weapons a player can carry */
    private static final int MAX_WEAPONS = 2;
    
    /** Maximum number of shields a player can carry */
    private static final int MAX_SHIELDS = 3;
    
    /** Initial health value for new players */
    private static final int INITIAL_HEALTH = 10;
    
    /** Number of consecutive hits before player loses */
    private static final int HITS2LOSE = 3;

    /** Player's name */
    private String name;
    
    /** Player's number/identifier */
    private char number;
    
    /** Player's intelligence level used for defense */
    private float intelligence;
    
    /** Player's strength level used for attack */
    private float strength;
    
    /** Player's current health */
    private float health;
    
    /** Player's current row position in labyrinth */
    private int row;
    
    /** Player's current column position in labyrinth */
    private int col;
    
    /** Number of consecutive hits received */
    private int consecutiveHits = 0;
    
    /** List of weapons player is carrying */
    private ArrayList<Weapon> weapons;
    
    /** List of shields player is carrying */
    private ArrayList<Shield> shields;

    /**
     * Constructs a new Player with specified attributes
     * @param number Player's number/identifier
     * @param intelligence Player's intelligence level
     * @param strength Player's strength level
     */
    public Player(char number, float intelligence, float strength) {
        weapons = new ArrayList<Weapon>();//Debería inicializarlos aquí o en la lista de atributos??
        shields = new ArrayList<Shield>();

        this.number = number;
        this.intelligence = intelligence;
        this.strength = strength;
        this.name = "Player #" + Character.toString(number);
        this.health = INITIAL_HEALTH;
        //inicializo row y col a -1?? Preguntar
        row = -1;
        col = -1;
    }

    /**
     * Resets player to initial state
     */
    public void resurrect() {
        weapons.clear();
        shields.clear();
        health = INITIAL_HEALTH;
        resetHits();
    }

    /**
     * Gets player's current row position
     * @return Current row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets player's current column position
     * @return Current column
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets player's number/identifier
     * @return Player number
     */
    public char getNumber() {
        return number;
    }

    /**
     * Sets player's position in labyrinth
     * @param row Row position
     * @param col Column position
     */
    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Checks if player is dead (health <= 0)
     * @return true if player is dead, false otherwise
     */
    public boolean dead() {
        return health <= 0;
    }

    /**
     * Determines player's movement direction
     * @param direction Desired direction
     * @param validMoves Array of valid directions
     * @return Selected movement direction
     */
    public Directions move(Directions direction, ArrayList<Directions> validMoves) {
        int size = validMoves.size();
        boolean contained = validMoves.contains(direction);
        Directions firstElement;

        if (size > 0 && (!contained)) {
            firstElement = validMoves.get(0);
        } else {
            firstElement = direction;
        }

        return firstElement;
    }

    /**
     * Calculates attack strength including weapon bonuses
     * @return Total attack strength
     */
    public float attack() {
        return strength + sumWeapons();
    }

    /**
     * Defends against an attack
     * @param receivedAttack Attack strength to defend against
     * @return true if player loses from attack, false otherwise
     */
    public boolean defend(float receivedAttack) {
        //Delegates its funcionality to manageHit() from this class. 
        //Pongo los comentarios en ingles para hacerme el chulo
        return manageHit(receivedAttack);
    }

    /**
     * Gives player random rewards including weapons, shields and health
     */
    public void receiveReward() {
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();
        Weapon wnew;
        Shield snew;

        for (int i = 0; i < wReward; ++i) {
            wnew = newWeapon();
            receiveWeapon(wnew);
        }
        for (int i = 0; i < sReward; ++i) {
            snew = newShield();
            receiveShield(snew);
        }
        health += Dice.healthReward();
    }

    /**
     * Generates string representation of player's state
     * @return String showing player's attributes and position
     */
    public String toString() {
        return "P[" + name + ", I:" + Float.toString(intelligence) + ", S: " + Float.toString(strength) + ", H:"
                + Float.toString(health) + ", Pos:(" + Integer.toString(row) + ", " + Integer.toString(col) + ")]";
    }

    /**
     * Adds a weapon to player's inventory if space available
     * @param w Weapon to add
     */
    private void receiveWeapon(Weapon w) {
        for (int i = 0; i < weapons.size(); ++i) { //Esto es un poco raro que no me de error, lo mismo en receiveShields
            if (weapons.get(i).discard()) {
                weapons.remove(i);
            }
        }

        if (weapons.size() < MAX_WEAPONS) {
            weapons.add(w);
        }
    }

    /**
     * Adds a shield to player's inventory if space available
     * @param s Shield to add
     */
    private void receiveShield(Shield s) {
        for (int i = 0; i < shields.size(); ++i) {
            if (shields.get(i).discard()) {
                shields.remove(i);
            }
        }

        if (shields.size() < MAX_SHIELDS) {
            shields.add(s);
        }
    }

    /**
     * Creates a new random weapon
     * @return New Weapon instance
     */
    private Weapon newWeapon() {
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }

    /**
     * Creates a new random shield
     * @return New Shield instance
     */
    private Shield newShield() {
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }

    /**
     * Calculates total attack bonus from all weapons
     * @return Sum of weapon attack values
     */
    private float sumWeapons() {
        float sum = 0;
        for (Weapon element : weapons) {
            sum += element.attack();
        }
        return sum;
    }

    /**
     * Calculates total defense bonus from all shields
     * @return Sum of shield protection values
     */
    private float sumShields() {
        float sum = 0;
        for (Shield element : shields) {
            sum += element.protect();
        }
        return sum;
    }

    /**
     * Calculates total defensive energy (intelligence + shields)
     * @return Total defensive energy
     */
    private float defensiveEnergy() {
        return intelligence + sumShields();
    }

    /**
     * Manages hit received from attack
     * @param receivedAttack Attack strength received
     * @return true if player loses from hit, false otherwise
     */
    private boolean manageHit(float receivedAttack) {
        float defense = defensiveEnergy();
        boolean lose;

        if (defense < receivedAttack) {
            gotWounded();
            incConsecutiveHits();
        } else {
            resetHits();
        }

        if (consecutiveHits == HITS2LOSE || (dead())) {
            resetHits();
            lose = true;
        } else {
            lose = false;
        }
        return lose;

    }

    /**
     * Resets consecutive hits counter
     */
    private void resetHits() {
        consecutiveHits = 0; //Cómo ponerlo sin número mágico??
    }

    /**
     * Reduces player's health by 1 when wounded
     */
    private void gotWounded() {
        --health;
    }

    /**
     * Increments consecutive hits counter
     */
    private void incConsecutiveHits() {
        ++consecutiveHits;
    }
}
