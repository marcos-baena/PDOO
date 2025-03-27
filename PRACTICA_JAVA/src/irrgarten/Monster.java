package irrgarten;

/**
 * Represents a monster in the Irrgarten game.
 * Monsters have attributes like name, intelligence, strength and health.
 * They can attack players and defend themselves.
 * 
 * @author marcosbslinux
 * @version 1.0
 */
public class Monster {

    /** Initial health value for new monsters */
    private static final int INITIAL_HEALTH = 5;

    /** Monster's name */
    private String name;
    
    /** Monster's intelligence level used for defense */
    private float intelligence;
    
    /** Monster's strength level used for attack */
    private float strength;
    
    /** Monster's current health */
    private float health;
    
    /** Monster's current row position in labyrinth */
    private int row;
    
    /** Monster's current column position in labyrinth */
    private int col;

    /**
     * Constructs a new Monster with specified attributes
     * @param name Monster's name
     * @param intelligence Monster's intelligence level
     * @param strength Monster's strength level
     */
    public Monster(String name, float intelligence, float strength) {
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;

        //Esto no lo pide, lo hago??
        row = -1;
        col = -1;
    }

    /**
     * Checks if monster is dead (health <= 0)
     * @return true if monster is dead, false otherwise
     */
    public boolean dead() {
        return health <= 0;
    }

    /**
     * Calculates attack strength using dice roll
     * @return Attack strength value
     */
    public float attack() {
        return Dice.intensity(strength);
    }

    /**
     * Defends against an attack
     * @param receivedAttack Attack strength to defend against
     * @return true if monster dies from attack, false otherwise
     */
    public boolean defend(float receivedAttack) {
        boolean isDead = dead();

        if (!isDead) {
            float defensiveEnergy = Dice.intensity(intelligence);

            if (defensiveEnergy < receivedAttack) {
                gotWounded();
                isDead = dead();
            }
        }
        return isDead;
    }

    /**
     * Sets monster's position in labyrinth
     * @param row Row position
     * @param col Column position
     */
    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Generates string representation of monster's state
     * @return String showing monster's attributes and position
     */
    @Override
    public String toString() {
//        return "Estado actual del monstruo " + name + ": \n\tInteligencia: " + Float.toString(intelligence)
//                + "\n\tFuerza: " + Float.toString(strength) + "\n\tSalud: " + Float.toString(health)
//                + "\n\tPosición:\n\t\tFila: " + Integer.toString(row) + "\n\t\tColumna: " + Integer.toString(col);
        return "M[" + name + ", I: " + Float.toString(intelligence) + ", S: " + Float.toString(strength) + "H: "
                + Float.toString(health) + "Pos: (" + Integer.toString(row) + ", " + Integer.toString(col) + ")]";

    }

    /**
     * Reduces monster's health by 1 when wounded
     */
    private void gotWounded() {
        --health;
    }
}
