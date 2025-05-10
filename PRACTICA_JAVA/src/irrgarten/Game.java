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
public class Game {
    private static final int MAX_ROUNDS=10;
    
    private int currentPlayerIndex;
    private String log;
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    private Player currentPlayer;
    private Labyrinth labyrinth;
    /*
   
    HACER TODOS LOS MÉTODOS DE ESTA CLASEÉ

    */
    public Game(int nplayers){
        log="";
        
        //Inicializo players
        players = new ArrayList<Player>();
        
        //Creo los players y los meto en el contenedor players
        for(int i=0; i<nplayers; ++i)
            players.add(new Player((char) i, Dice.randomIntelligence(), Dice.randomStrength()));
        
        labyrinth= new Labyrinth(4,4,1,1);
        
        //Esparcir a los jugadores por el tablero
        labyrinth.spreadPlayers(players.toArray(Player[]::new));
        
        //Elijo quién empieza la partida
        currentPlayerIndex=Dice.whoStarts(nplayers);
        
        //Fijo el jugador actual
        currentPlayer=players.get(currentPlayerIndex);
        
        //Inicializo monsters
        monsters = new ArrayList<Monster>();
        
        //Instancio un laberinto para poder inicializar el atributo labyrinth??
        
         //Le pongo esos parametros al constructor un poco por la cara 
    }
    
    public boolean finished(){
        return labyrinth.haveAWinner();
    }
    
    public boolean nextStep(Directions preferredDirection){
        throw new UnsupportedOperationException();
    }
    
    public GameState getGameState(){
        
        //CAMBIAR
        GameState state=new GameState(labyrinth.toString(), players.toString(), 
                                      monsters.toString(), currentPlayerIndex,
                                      finished(), log);
        return state;
    }
    
    private void configureLabyrinth(){
        for(int i=0; i<monsters.size(); ++i){
//            labyrinth.addMonster(monsters.get(i).setPos(Dice.randomPos(labyrinth.), i), monsters.get(i)); Tengo que hacerlo pero no se como
        }
    }
    
    private void nextPlayer(){
        //Pasar al siguiente jugador
            currentPlayerIndex= (currentPlayerIndex + 1)% players.size();
            currentPlayer=players.get(currentPlayerIndex);
    }
    
    private Directions actualDirection(Directions preferredDirection){
        throw new UnsupportedOperationException();
    }
    
    private GameCharacter combat(Monster monster){
        throw new UnsupportedOperationException();
    }
    
    private void manageReward(GameCharacter winner){
        throw new UnsupportedOperationException();
    }
    
    private void manageResurrection(){
        throw new UnsupportedOperationException();
    }
    
    private void logPlayerWon(){
        log+="El jugador "+currentPlayer+" ha ganado el combate.\n";
    }
    
    private void logMonsterWon(){
        log+="El monstruo ha ganado el combate.\n";
    }
    
    private void logResurrected(){
        log+="El jugador "+currentPlayer+" ha resucitado.\n";
    }
    
    private void logPlayerSkipTurn(){
        log+="El jugador "+currentPlayer+" ha perdido el turno por estar muerto.\n";
    }
    
    private void logPlayerNoOrders(){
        log+="El jugador "+currentPlayer+" no ha podido seguir las instrucciones del jugador humano.\n";
    }
    
    private void logNoMonster(){
        log+="El jugador "+currentPlayer+" se ha movido a una celda vacía o no le ha sido posible moverse.\n";
    }
    
    private void logRounds(int rounds, int max){
        log+="Se han jugado "+Integer.toString(rounds)+" de "+Integer.toString(max)+" rondas.\n";
    }
}

