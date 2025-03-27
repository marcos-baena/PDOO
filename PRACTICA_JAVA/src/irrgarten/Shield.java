package irrgarten;

/**
 * Represents a shield that provides protection in the Irrgarten game.
 * Shields have a protection value and a number of uses before they are discarded.
 * 
 * @author marcosbslinux
 * @version 1.0
 */
public class Shield {
    /** Protection value of the shield */
    private float protection;
    
    /** Number of remaining uses */
    private int uses;
    
    /**
     * Constructs a new Shield with specified protection and uses
     * @param p Protection value
     * @param u Number of uses
     */
    public Shield(float p, int u){
        protection=p;
        uses=u;
    }
    
    /**
     * Uses the shield to provide protection
     * @return Protection value if shield has uses remaining, 0 otherwise
     */
    public float protect(){
        float defense=0;
        
        if(uses>0){
            --uses;
            defense=protection;
        }
        return defense;
    }
    /**
     * Generates string representation of shield's state
     * @return String showing protection and remaining uses
     */
    @Override
    public String toString(){
        return "S[" + Float.toString(protection) + ", " + Integer.toString(uses) + "]";
    }
    /**
     * Determines if shield should be discarded based on remaining uses
     * @return true if shield should be discarded, false otherwise
     */
    public boolean discard(){
        return Dice.discardElement(uses);
    }
}
