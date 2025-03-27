#encoding: utf-8
module Irrgarten
  # Represents a player character in the Irrgarten game
  # Manages player state including position, health, weapons, shields and combat stats
  class Player
    # Maximum number of weapons a player can carry
    @@MAX_WEAPONS=2
    # Maximum number of shields a player can carry  
    @@MAX_SHIELDS=3
    # Initial health points for a new player
    @@INITIAL_HEALTH=10
    # Number of consecutive hits before losing
    @@HITS2LOSE=3

    # Initializes a new player with number, intelligence and strength
    # @param n [Integer] player number (unique identifier)
    # @param i [Integer] intelligence value (affects defense)
    # @param s [Integer] strength value (affects attack)
    def initialize(n,i,s)
      @number=n
      @intelligence=i
      @strength=s
      @health=@@INITIAL_HEALTH
      @row=-1
      @col=-1
      @name="Player ##{@number}"
      @shields=Array.new
      @weapons=Array.new
      @consecutive_hits=0
    end

    # Resets player to initial state
    # Clears all weapons and shields, resets health to initial value
    # @return [void]
    def resurrect
      @shields.clear
      @weapons.clear
      @health=@@INITIAL_HEALTH
      reset_hits
    end
    
    # @!attribute [r] row
    #   @return [Integer] current row position on game board
    attr_reader :row
    
    # @!attribute [r] col  
    #   @return [Integer] current column position on game board
    attr_reader :col
    
    # @!attribute [r] number
    #   @return [Integer] player's unique identifier
    attr_reader :number

    # Sets player's position on the game board
    # @param r [Integer] new row position
    # @param c [Integer] new column position
    # @return [void]
    def set_pos(r,c)
      @row=r
      @col=c
      nil
    end

    # Checks if player is dead
    # @return [Boolean] true if health <= 0
    def dead
      @health <= 0
    end

    # Moves player in specified direction if valid
    # If direction is invalid, moves in first valid direction
    # @param direction [Symbol] desired movement direction
    # @param valid_moves [Array<Symbol>] array of valid movement directions
    # @return [Symbol] actual movement direction
    def move(direction,valid_moves)
      size = valid_moves.size
      contained = valid_moves.include?(direction)
      
      if size > 0 && !contained
          first_element = valid_moves[0]
      else
          first_element = direction
      end
      first_element
    end

    # Calculates total attack power
    # Sum of base strength and all weapon attacks
    # @return [Integer] total attack value
    def attack
      @strength+sum_weapons
    end

    # Defends against an attack
    # Compares attack power to defensive energy
    # Manages hits and determines if player loses
    # @param received_attack [Integer] attack power to defend against
    # @return [Boolean] true if player loses the combat
    def defend(received_attack)
      manage_hit(received_attack)
    end

    # Gives random rewards to player
    # Awards weapons, shields and health based on dice rolls
    # @return [void]
    def receive_reward
      w_reward = Dice.weapons_reward
      s_reward = Dice.shields_reward
      
      for i in 0...w_reward
        wnew = new_weapon
        receive_weapon(wnew)
      end
      for i in 0...s_reward
        snew = new_shield
        receive_shield(snew)
      end
      @health += Dice.health_reward
      nil
    end

    # Returns string representation of player state
    # Includes name, stats, health and position
    # @return [String] formatted player description
    def to_s
      "P[#{@name}, I: #{@intelligence}, S: #{@strength}, H: #{@health}, Pos: (#{@row}, #{@col})]"
    end
   
    private 
    
    # Receives a new weapon, discarding old ones if necessary
    # Maintains maximum weapon limit
    # @param w [Weapon] weapon to receive
    # @return [void]
    def receive_weapon(w)
      for i in 0...@weapons.size
        if @weapons[i].discard
          @weapons.delete_at(i)
        end
      end

      if @weapons.size < @@MAX_WEAPONS
        @weapons << w
      end
      nil
    end

    # Receives a new shield, discarding old ones if necessary
    # Maintains maximum shield limit
    # @param s [Shield] shield to receive
    # @return [void]
    def receive_shield(s)
      for i in 0...@shields.size
        if @shields[i].discard
          @shields.delete_at(i)
        end
      end
      
      if weapons.size < @@MAX_SHIELDS
        @shields << s
      end
      nil
    end
  
    # Creates a new random weapon
    # Uses dice to determine power and uses
    # @return [Weapon] new weapon instance
    def new_weapon
      Weapon.new(Dice.weapon_power, Dice.uses_left)
    end

    # Creates a new random shield
    # Uses dice to determine power and uses
    # @return [Shield] new shield instance
    def new_shield
      Shield.new(Dice.shield_power, Dice.uses_left)
    end

    # Calculates total defensive energy
    # Sum of base intelligence and all shield defenses
    # @return [Integer] total defense value
    def defensive_energy
      @intelligence+sum_shields
    end

    # Manages hit received by player
    # Compares attack to defense, updates health and hit counter
    # @param received_attack [Integer] attack power received
    # @return [Boolean] true if player loses due to hit
    def manage_hit(received_attack)
      defense = defensive_energy

      if defense < received_attack
        got_wounded
        inc_consecutive_hits
      else 
        reset_hits
      end

      if consecutive_hits == @@HITS2LOSE || dead
        reset_hits
        lose = true
      else
        lose = false
      end
      lose
    end

    # Resets consecutive hits counter to 0
    # @return [void]
    def reset_hits
      @consecutive_hits=0
      nil
    end

    # Reduces player's health by 1 when wounded
    # @return [void]
    def got_wounded
      @health -=1
      nil
    end

    # Increments consecutive hits counter by 1
    # @return [void]
    def inc_consecutive_hits
      @consecutive_hits += 1
    end

    # Calculates total attack power from all weapons
    # @return [Integer] sum of weapon attacks
    def sum_weapons
      @weapons.sum(&:attack)
    end

    # Calculates total defense power from all shields
    # @return [Integer] sum of shield defenses
    def sum_shields
      @shields.sum(&:defend)
    end
  end
end
