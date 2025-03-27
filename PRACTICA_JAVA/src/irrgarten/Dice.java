package irrgarten;

import java.util.Random;

/**
 * Utility class for generating random values used throughout the game.
 * Provides methods for random positions, starting player, attributes, rewards,
 * and element discarding probabilities.
 * 
 * @author marcosbslinux
 */
public class Dice {

    /** Maximum number of uses for weapons and shields */
    private static final int MAX_USES = 5;
    
    /** Maximum intelligence value for players and monsters */
    private static final float MAX_INTELLIGENCE = 10.0f;
    
    /** Maximum strength value for players and monsters */
    private static final float MAX_STRENGTH = 10.0f;
    
    /** Probability of a player being resurrected each turn */
    private static final float RESURRECT_PROB = 0.3f;
    
    /** Maximum number of weapons received when winning a combat */
    private static final int WEAPONS_REWARD = 2;
    
    /** Maximum number of shields received when winning a combat */
    private static final int SHIELDS_REWARD = 3;
    
    /** Maximum health units received when winning a combat */
    private static final int HEALTH_REWARD = 5;
    
    /** Maximum attack power for weapons */
    private static final int MAX_ATTACK = 3;
    
    /** Maximum shield power for shields */
    private static final int MAX_SHIELD = 2;

    /** Random number generator instance */
    private static Random generator = new Random();

    /**
     * Generates a random position within a given range
     * @param max The upper bound (exclusive) for the random position
     * @return A random integer between 0 (inclusive) and max (exclusive)
     */
    public static int randomPos(int max) {
        return generator.nextInt(max);
    }

    /**
     * Randomly selects which player starts the game
     * @param nplayers Number of players in the game
     * @return A random integer between 0 (inclusive) and nplayers (exclusive)
     */
    public static int whoStarts(int nplayers) {
        return generator.nextInt(nplayers);
    }

    /**
     * Generates a random intelligence value
     * @return A random float between 0.0 (inclusive) and MAX_INTELLIGENCE (exclusive)
     */
    public static float randomIntelligence() {
        return generator.nextFloat(MAX_INTELLIGENCE);
    }

    /**
     * Generates a random strength value
     * @return A random float between 0.0 (inclusive) and MAX_STRENGTH (exclusive)
     */
    public static float randomStrength() {
        return generator.nextFloat(MAX_STRENGTH);
    }

    /**
     * Determines if a player should be resurrected based on resurrection probability
     * @return true if player should be resurrected, false otherwise
     */
    public static boolean resurrectPlayer() {
        return generator.nextFloat() < RESURRECT_PROB;
    }

    /**
     * Generates a random number of weapons as a combat reward
     * @return A random integer between 0 and WEAPONS_REWARD (inclusive)
     */
    public static int weaponsReward() {
        return generator.nextInt(WEAPONS_REWARD + 1);
    }

    /**
     * Generates a random number of shields as a combat reward
     * @return A random integer between 0 and SHIELDS_REWARD (inclusive)
     */
    public static int shieldsReward() {
        return generator.nextInt(SHIELDS_REWARD + 1);
    }

    /**
     * Generates a random number of health units as a combat reward
     * @return A random integer between 0 and HEALTH_REWARD (inclusive)
     */
    public static int healthReward() {
        return generator.nextInt(HEALTH_REWARD + 1);
    }

    /**
     * Generates a random weapon power value
     * @return A random float between 0.0 (inclusive) and MAX_ATTACK (exclusive)
     */
    public static float weaponPower() {
        return generator.nextFloat(MAX_ATTACK);
    }

    /**
     * Generates a random shield power value
     * @return A random float between 0.0 (inclusive) and MAX_SHIELD (exclusive)
     */
    public static float shieldPower() {
        return generator.nextFloat(MAX_SHIELD);
    }

    /**
     * Generates a random number of uses left for a weapon or shield
     * @return A random integer between 0 and MAX_USES (inclusive)
     */
    public static int usesLeft() {
        return generator.nextInt(MAX_USES + 1);
    }

    /**
     * Generates a random intensity value based on a competence parameter
     * @param competence The maximum possible intensity value
     * @return A random float between 0.0 (inclusive) and competence (exclusive)
     */
    public static float intensity(float competence) {
        return generator.nextFloat(competence);
    }

    /**
     * Determines if a weapon or shield should be discarded based on its remaining uses
     * @param usesLeft Number of remaining uses of the element
     * @return true if the element should be discarded, false otherwise
     */
    public static boolean discardElement(int usesLeft) {
        boolean result;
        if (usesLeft == 0) {
            result = true;
        } else if (usesLeft >= MAX_USES) {
            result = false;
        } else {
            result = generator.nextFloat() < (float) (MAX_USES - usesLeft) / (float) MAX_USES;//El método nextFloat() nunca va a producir el número 1.0, tengo que ver como arreglarlo.
        }
        return result;
    }
}
