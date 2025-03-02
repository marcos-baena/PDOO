/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author marcosbslinux
 */
public class Shield {
    private float protection;
    private int uses;
    
    //Constructor
    
    public Shield(float p, int u){
        protection=p;
        uses=u;
    }
    
    public float protect(){
        float defense=0;
        
        if(uses>0){
            --uses;
            defense=protection;
        }
        return defense;
    }
    @Override
    public String toString(){
        return "S[" + Float.toString(protection) + ", " + Integer.toString(uses) + "]";
    }
    public boolean discard(){
        return Dice.discardElement(uses);
    }
}
