/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;

/**
 *
 * @author marcosbslinux
 */
public class Player {

    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;

    private String name;
    private char number;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits = 0;
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;

    public Player(char number, float intelligence, float strength) {
        weapons = new ArrayList<Weapon>();//Debería inicializarlos aquí o en la lista de atributos??
        shields= new ArrayList<Shield>();
        
        this.number = number;
        this.intelligence = intelligence;
        this.strength = strength;
        this.name = "Player #" + Character.toString(number);
        this.health = INITIAL_HEALTH;
        //inicializo row y col a -1?? Preguntar
        row=-1;
        col=-1;
    }

    public void resurrect() {
        weapons.clear();
        shields.clear();
        health = INITIAL_HEALTH;
        resetHits();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getNumber() {
        return number;
    }

    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean dead() {
        return health <= 0;
    }

    public Directions move(Directions direction, Directions[] validMoves) {
        throw new UnsupportedOperationException();
    }

    public float attack() {
        return strength + sumWeapons();
    }

    public boolean defend(float receivedAttack) {
        //Delegates its funcionality to manageHit() from this class. 
        //Pongo los comentarios en ingles para hacerme el chulo
        return manageHit(receivedAttack);
    }

    public void receiveReward() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
//        return "Estado actual del jugador " + name + ": \n\tInteligencia: " + Float.toString(intelligence)
//                + "\n\tFuerza: " + Float.toString(strength) + "\n\tSalud: " + Float.toString(health)
//                + "\n\tPosición:\n\t\tFila: " + Integer.toString(row) + "\n\t\tColumna: " + Integer.toString(col)
//                + "\n Golpes consecutivos: " + Integer.toString(consecutiveHits);
          return "P["+name+", I: "+ Float.toString(intelligence) +", S: "+ Float.toString(strength) + "H: "+ 
                  Float.toString(health) + "Pos: ("+Integer.toString(row)+", "+Integer.toString(col)+")]";
    }

    private void receiveWeapon(Weapon w) {
        throw new UnsupportedOperationException();
    }

    private void receiveShield(Shield s) {
        throw new UnsupportedOperationException();
    }

    private Weapon newWeapon() {
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }

    private Shield newShield() {
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }

    private float sumWeapons() {
        float sum = 0;
        for (Weapon element : weapons) {
            sum += element.attack();
        }
        return sum;
    }

    private float sumShields() {
        float sum = 0;
        for (Shield element : shields) {
            sum += element.protect();
        }
        return sum;
    }

    private float defensiveEnergy() {
        return intelligence + sumShields();
    }

    private boolean manageHit(float receivedAttack) {
        throw new UnsupportedOperationException();
    }

    private void resetHits() {
        consecutiveHits = 0; //Cómo ponerlo sin número mágico??
    }

    private void gotWounded() {
        --health;
    }

    private void incConsecutiveHits() {
        ++consecutiveHits;
    }
}
