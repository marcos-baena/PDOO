#encoding: utf-8
module Irrgarten
  # Represents a monster in the Irrgarten game
  # Handles monster attributes and combat behavior
  class Monster
    # Initial health value for all monsters
    @@INITIAL_HEALTH=5

    # Initializes a new monster with specified attributes
    # @param n [String] monster's name
    # @param i [Integer] intelligence value
    # @param s [Integer] strength value
    def initialize(n, i, s)
      @name=n
      @intelligence=i
      @strength=s
      @health=@@INITIAL_HEALTH
      @row=-1
      @col=-1
    end
    
    # Checks if monster is dead
    # @return [Boolean] true if health is 0 or less
    def dead
      @health <= 0 
    end

    # Calculates attack strength using dice roll
    # @return [Integer] attack intensity based on strength
    def attack
      Dice.intensity(@strength)
    end

    # Sets monster's position on the labyrinth
    # @param r [Integer] row position
    # @param c [Integer] column position
    # @return [nil]
    def set_pos(r, c)
      @row=r
      @col=c
      nil
    end

    # Returns string representation of monster state
    # @return [String] formatted monster description
    def to_s
      "M[#{@name}, I: #{@intelligence}, S: #{@strength}, H: #{@health}, Pos: (#{@row}, #{@col})]"
    end

    # Handles monster defense against an attack
    # @param received_attack [Integer] attack intensity received
    # @return [Boolean] true if monster dies from the attack
    def defend(received_attack)
      is_dead = dead

      if !is_dead
        defensive_energy = Dice.intensity(@intelligence)

        if defensive_energy < received_attack
          got_wounded
          is_dead = dead
        end
      end
      is_dead
    end

    private

    # Reduces monster's health when wounded
    # @return [nil]
    def got_wounded
      @health-=1
      nil
    end
  end
end
