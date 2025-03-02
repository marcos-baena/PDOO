/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.Random;

/**
 *
 * @author marcosbslinux
 */
public class Dice {

    private static final int MAX_USES = 5; //(número máximo de usos de armas y escudos)
    private static final float MAX_INTELLIGENCE = 10.0f; //(valor máximo para la inteligencia de jugadores y monstruos)
    private static final float MAX_STRENGTH = 10.0f; //(valor máximo para la fuerza de jugadores y monstruos)
    private static final float RESURRECT_PROB = 0.3f; //(probabilidad de que un jugador sea resucitado en cada turno)
    private static final int WEAPONS_REWARD = 2; //(numero máximo de armas recibidas al ganar un combate)
    private static final int SHIELDS_REWARD = 3; //(numero máximo de escudos recibidos al ganar un combate)
    private static final int HEALTH_REWARD = 5; //(numero máximo de unidades de salud recibidas al ganar un combate)
    private static final int MAX_ATTACK = 3; //(máxima potencia de las armas)
    private static final int MAX_SHIELD = 2; //(máxima potencia de los escudos)

    private static Random generator = new Random();

    public static int randomPos(int max) {
        return generator.nextInt(max);
    }

    public static int whoStarts(int nplayers) {
        return generator.nextInt(nplayers);
    }

    public static float randomIntelligence() {
        return generator.nextFloat(MAX_INTELLIGENCE);
    }

    public static float randomStrength() {
        return generator.nextFloat(MAX_STRENGTH);
    }

    public static boolean resurrectPlayer() {
        return generator.nextFloat() < RESURRECT_PROB;
    }

    public static int weaponsReward() {
        return generator.nextInt(WEAPONS_REWARD + 1);
    }

    public static int shieldsReward() {
        return generator.nextInt(SHIELDS_REWARD + 1);
    }

    public static int healthReward() {
        return generator.nextInt(HEALTH_REWARD + 1);
    }

    public static float weaponPower() {
        return generator.nextFloat(MAX_ATTACK);
    }

    public static float shieldPower() {
        return generator.nextFloat(MAX_SHIELD);
    }

    public static int usesLeft() {
        return generator.nextInt(MAX_USES + 1);
    }

    public static float intensity(float competence) {
        return generator.nextFloat(competence);
    }

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
