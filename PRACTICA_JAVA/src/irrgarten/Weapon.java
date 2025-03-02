/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author marcosbslinux
 */
public class Weapon {

    private float power;
    private int uses;

    //Constructor
    public Weapon(float p, int u) {
        power = p;
        uses = u;
    }

    //Método attack
    public float attack() {
        float damage = 0;
        if (uses > 0) {
            --uses;
            damage = power;
        }
        return damage;
    }

    @Override
    public String toString() {
        return "W[" + Float.toString(power) + ", " + Integer.toString(uses) + "]";
    }

    public boolean discard() {
        return Dice.discardElement(uses);
    }
}
