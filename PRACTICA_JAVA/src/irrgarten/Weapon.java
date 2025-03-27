package irrgarten;

/**
 * Represents a weapon in the Irrgarten game.
 * Weapons have attack power and a number of uses before they are discarded.
 * 
 * @author marcosbslinux
 * @version 1.0
 */
public class Weapon {
    /** Attack power of the weapon */
    private float power;
    
    /** Number of remaining uses */
    private int uses;

    /**
     * Constructs a new Weapon with specified power and uses
     * @param p Attack power
     * @param u Number of uses
     */
    public Weapon(float p, int u) {
        power = p;
        uses = u;
    }

    /**
     * Uses the weapon to attack
     * @return Attack power if weapon has uses remaining, 0 otherwise
     */
    public float attack() {
        float damage = 0;
        if (uses > 0) {
            --uses;
            damage = power;
        }
        return damage;
    }

    /**
     * Generates string representation of weapon's state
     * @return String showing power and remaining uses
     */
    @Override
    public String toString() {
        return "W[" + Float.toString(power) + ", " + Integer.toString(uses) + "]";
    }

    /**
     * Determines if weapon should be discarded based on remaining uses
     * @return true if weapon should be discarded, false otherwise
     */
    public boolean discard() {
        return Dice.discardElement(uses);
    }
}
