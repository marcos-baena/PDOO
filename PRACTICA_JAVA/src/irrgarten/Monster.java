/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author marcosbslinux
 */
public class Monster {

    private static final int INITIAL_HEALTH = 5;

    private String name;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;

    public Monster(String name, float intelligence, float strength) {
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;

        //Esto no lo pide, lo hago??
//        row = -1;
//        col = -1;
    }

    public boolean dead() {
        return health <= 0;
    }

    public float attack() {
        return Dice.intensity(strength);
    }

    public boolean defend(float receivedAttack) {
        throw new UnsupportedOperationException();
    }

    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
//        return "Estado actual del monstruo " + name + ": \n\tInteligencia: " + Float.toString(intelligence)
//                + "\n\tFuerza: " + Float.toString(strength) + "\n\tSalud: " + Float.toString(health)
//                + "\n\tPosición:\n\t\tFila: " + Integer.toString(row) + "\n\t\tColumna: " + Integer.toString(col);
          return "M["+name+", I: "+ Float.toString(intelligence) +", S: "+ Float.toString(strength) + "H: "+ 
                  Float.toString(health) + "Pos: ("+Integer.toString(row)+", "+Integer.toString(col)+")]";

    }

    private void gotWounded() {
        --health;
    }
}
