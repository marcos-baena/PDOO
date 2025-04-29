#encoding: utf-8

module Irrgarten
  # Represents the complete state of the Irrgarten game
  # Contains all game elements and current state information
  class GameState
    # Initializes a new game state with all game elements
    # @param l [Labyrinth] the game's labyrinth/map
    # @param p [Array<Player>] array of all players
    # @param m [Array<Monster>] array of all monsters
    # @param c [Integer] index of current player
    # @param w [Boolean] true if there is a winner
    # @param lo [String] game log/messages
    def initialize(l, p, m, c, w, lo)
      @labyrinth=l 
      @players =p
      @monsters=m
      @current_player= c
      @winner=w
      @log=lo
    end
    # @!attribute [r] labyrinth
    #   @return [Labyrinth] the game's labyrinth/map
    attr_reader :labyrinth
    
    # @!attribute [r] players
    #   @return [Array<Player>] array of all players
    attr_reader :players
    
    # @!attribute [r] monsters
    #   @return [Array<Monster>] array of all monsters
    attr_reader :monsters
    
    # @!attribute [r] current_player
    #   @return [Integer] index of current player
    attr_reader :current_player
    
    # @!attribute [r] winner
    #   @return [Boolean] true if there is a winner
    attr_reader :winner
    
    # @!attribute [r] log
    #   @return [String] game log/messages
    attr_reader :log

  end
end
