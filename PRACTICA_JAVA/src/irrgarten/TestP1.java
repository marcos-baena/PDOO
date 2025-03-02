package irrgarten;

import java.util.Arrays;

public class TestP1 {

    public static void main(String[] args) {
        // 1. Imprimir valores enumerados
        System.out.println("=== Inicio de pruebas de la práctica ===");

        System.out.println("\nValores enumerados:");
        System.out.println("Irrgarten.GameCharacter.PLAYER    => " + GameCharacter.PLAYER);
        System.out.println("Irrgarten.GameCharacter.MONSTER   => " + GameCharacter.MONSTER);
        System.out.println("Irrgarten.Orientation.VERTICAL    => " + Orientation.VERTICAL);
        System.out.println("Irrgarten.Orientation.HORIZONTAL  => " + Orientation.HORIZONTAL);
        System.out.println("Irrgarten.Directions.LEFT         => " + Directions.LEFT);
        System.out.println("Irrgarten.Directions.RIGHT        => " + Directions.RIGHT);
        System.out.println("Irrgarten.Directions.UP           => " + Directions.UP);
        System.out.println("Irrgarten.Directions.DOWN         => " + Directions.DOWN);

        // 2. Crear instancias de las clases
        System.out.println("\nProbar las instancias de las clases.");

        // Prueba de GameState
        System.out.println("\nClase GameState:");
        GameState gameState = new GameState("Laberinto1", "Jugador1, Jugador2", "Monstruo1", 0, false, "");
        System.out.println("Labyrinth: " + gameState.getLabyrinth());
        System.out.println("Players: " + gameState.getPlayers());
        System.out.println("Monsters: " + gameState.getMonsters());
        System.out.println("Current Player: " + gameState.getCurrentPlayer());
        System.out.println("Winner: " + gameState.getWinner());
        System.out.println("Log: " + gameState.getLog());

        // Prueba de Shield
        System.out.println("\nPrueba de Shield:");
        Shield shield = new Shield(5.0f, 3);
        System.out.println("Antes de usar protect: " + shield);
        float protection = shield.protect();
        System.out.println("Resultado de protect: " + protection);
        System.out.println("Después de usar protect: " + shield);
        boolean discardShield = shield.discard();
        System.out.println("Resultado de discard: " + discardShield);

        // Prueba de Weapon
        System.out.println("\nPrueba de Weapon:");
        Weapon weapon = new Weapon(7.0f, 4);
        System.out.println("Antes de usar attack: " + weapon);
        float damage = weapon.attack();
        System.out.println("Resultado de attack: " + damage);
        System.out.println("Después de usar attack: " + weapon);
        boolean discardWeapon = weapon.discard();
        System.out.println("Resultado de discard: " + discardWeapon);

        // 3. Prueba de métodos de Dice (100 iteraciones cada uno)
        Dice dice = new Dice();
        System.out.println("\nPrueba de métodos de Dice (100 iteraciones cada uno):");

        // randomPos(10)
        System.out.println("\nDistribución de random_pos(10):");
        int[] randomPosCount = new int[10];
        for (int i = 0; i < 100; i++) {
            randomPosCount[dice.randomPos(10)]++;
        }
        for (int i = 0; i < randomPosCount.length; i++) {
            System.out.println("Valor " + i + ": " + randomPosCount[i] + " veces");
        }

        // whoStarts(4)
        System.out.println("\nDistribución de who_starts(4):");
        int[] whoStartsCount = new int[4];
        for (int i = 0; i < 100; i++) {
            whoStartsCount[dice.whoStarts(4)]++;
        }
        for (int i = 0; i < whoStartsCount.length; i++) {
            System.out.println("Jugador " + i + ": " + whoStartsCount[i] + " veces");
        }

        // randomIntelligence()
        System.out.println("\nValor medio de random_intelligence: " + average(dice, 100, "randomIntelligence"));

        // randomStrength()
        System.out.println("Valor medio de random_strength: " + average(dice, 100, "randomStrength"));

        // resurrectPlayer
        System.out.println("resurrect_player ha devuelto true " + countTrue(dice, 100) + " de 100 veces");

        // weaponsReward()
        System.out.println("\nDistribución de weapons_reward:");
        int[] weaponsRewardCount = new int[3];
        for (int i = 0; i < 100; i++) {
            weaponsRewardCount[dice.weaponsReward()]++;
        }
        for (int i = 0; i < weaponsRewardCount.length; i++) {
            System.out.println("Reward " + i + ": " + weaponsRewardCount[i] + " veces");
        }

        // shieldsReward()
        System.out.println("\nDistribución de shields_reward:");
        int[] shieldsRewardCount = new int[4];
        for (int i = 0; i < 100; i++) {
            shieldsRewardCount[dice.shieldsReward()]++;
        }
        for (int i = 0; i < shieldsRewardCount.length; i++) {
            System.out.println("Reward " + i + ": " + shieldsRewardCount[i] + " veces");
        }

        // healthReward()
        System.out.println("\nDistribución de health_reward:");
        int[] healthRewardCount = new int[6];
        for (int i = 0; i < 100; i++) {
            healthRewardCount[dice.healthReward()]++;
        }
        for (int i = 0; i < healthRewardCount.length; i++) {
            System.out.println("Reward " + i + ": " + healthRewardCount[i] + " veces");
        }

        // weaponPower()
        System.out.println("\nDistribución de weapon_power:");
        int[] weaponPowerCount = new int[3];
        for (int i = 0; i < 100; i++) {
            weaponPowerCount[(int) dice.weaponPower()]++;
        }
        for (int i = 0; i < weaponPowerCount.length; i++) {
            System.out.println("Power " + i + ": " + weaponPowerCount[i] + " veces");
        }

        // shieldPower()
        System.out.println("\nDistribución de shield_power:");
        int[] shieldPowerCount = new int[2];
        for (int i = 0; i < 100; i++) {
            shieldPowerCount[(int) dice.shieldPower()]++;
        }
        for (int i = 0; i < shieldPowerCount.length; i++) {
            System.out.println("Power " + i + ": " + shieldPowerCount[i] + " veces");
        }

        // usesLeft()
        System.out.println("\nDistribución de uses_left:");
        int[] usesLeftCount = new int[6];
        for (int i = 0; i < 100; i++) {
            usesLeftCount[dice.usesLeft()]++;
        }
        for (int i = 0; i < usesLeftCount.length; i++) {
            System.out.println("Uses " + i + ": " + usesLeftCount[i] + " veces");
        }

        // intensity()
        System.out.println("\nValor medio de intensity(5): " + averageIntensity(dice, 100, 5));

        // discardElement(3)
        System.out.println("\nResultados de discard_element(3):");
        int trueCount = 0, falseCount = 0;
        for (int i = 0; i < 100; i++) {
            if (dice.discardElement(3)) {
                trueCount++;
            } else {
                falseCount++;
            }
        }
        System.out.println("true: " + trueCount + " veces");
        System.out.println("false: " + falseCount + " veces");

        System.out.println("\n=== Pruebas completadas ===");
    }

    // Método corregido para calcular el valor medio
    private static double average(Dice dice, int iterations, String methodName) {
        double sum = 0;
        for (int i = 0; i < iterations; i++) {
            try {
                Float value = (Float) Dice.class.getMethod(methodName).invoke(dice);
                sum += value.doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sum / iterations;
    }

    private static double averageIntensity(Dice dice, int iterations, float competence) {
        double sum = 0;
        for (int i = 0; i < iterations; i++) {
            sum += dice.intensity(competence);
        }
        return sum / iterations;
    }

    private static int countTrue(Dice dice, int iterations) {
        int count = 0;
        for (int i = 0; i < iterations; i++) {
            if (dice.resurrectPlayer()) {
                count++;
            }
        }
        return count;
    }
}
