#encoding: utf-8
module Irrgarten
  # Provides random number generation and probability calculations for game mechanics
  class Dice
    # Maximum number of uses for weapons and shields
    @@MAX_USES = 5 
    # Maximum intelligence value for characters
    @@MAX_INTELLIGENCE = 10.0 
    # Maximum strength value for characters
    @@MAX_STRENGTH = 10.0 
    # Probability of player resurrection
    @@RESURRECT_PROB = 0.3 
    # Maximum number of weapons as reward
    @@WEAPONS_REWARD = 2 
    # Maximum number of shields as reward
    @@SHIELDS_REWARD = 3 
    # Maximum health points as reward
    @@HEALTH_REWARD = 5 
    # Maximum attack power for weapons
    @@MAX_ATTACK = 3 
    # Maximum shield power for shields
    @@MAX_SHIELD = 2

    # Random number generator instance
    @generator=Random.new

    # Generates a random position within given maximum
    # @param max [Integer] maximum position value
    # @return [Integer] random position between 0 and max-1
    def self.random_pos(max)
      @generator.rand(max) #[0, max)
    end
    # Determines which player starts the game
    # @param nplayers [Integer] number of players
    # @return [Integer] random player index between 0 and nplayers-1
    def self.who_starts(nplayers)
      @generator.rand(nplayers) #[0, nplayers)
    end
    # Generates random intelligence value
    # @return [Float] random value between 0 and MAX_INTELLIGENCE
    def self.random_intelligence
      @generator.rand(@@MAX_INTELLIGENCE) #[0, MAX_INTELLIGENCE)
    end
    # Generates random strength value
    # @return [Float] random value between 0 and MAX_STRENGTH
    def self.random_strength
      @generator.rand(@@MAX_STRENGTH) #[0, MAX_STRENGTH)
    end
    # Determines if a player should be resurrected
    # @return [Boolean] true if player should be resurrected (based on RESURRECT_PROB)
    def self.resurrect_player
      @generator.rand < @@RESURRECT_PROB
    end
    # Determines number of weapons as reward
    # @return [Integer] random number between 0 and WEAPONS_REWARD
    def self.weapons_reward
      @generator.rand(@@WEAPONS_REWARD+1) #[0, WEAPONS_REWARD]
    end
    # Determines number of shields as reward
    # @return [Integer] random number between 0 and SHIELDS_REWARD
    def self.shields_reward
      @generator.rand(@@SHIELDS_REWARD+1)  #[0, SHIELDS_REWARD]
    end
    # Determines health points as reward
    # @return [Integer] random number between 0 and HEALTH_REWARD
    def self.health_reward
      @generator.rand(@@HEALTH_REWARD+1) #[0, HEALTH_REWARD]
    end
    # Determines weapon attack power
    # @return [Integer] random number between 0 and MAX_ATTACK-1
    def self.weapon_power
      @generator.rand(@@MAX_ATTACK)  #[0, MAX_ATTACK)
    end
    # Determines shield defense power
    # @return [Integer] random number between 0 and MAX_SHIELD-1
    def self.shield_power
      @generator.rand(@@MAX_SHIELD)  #[0, MAX_SHIELD)
    end
    # Determines remaining uses for weapons/shields
    # @return [Integer] random number between 0 and MAX_USES
    def self.uses_left
      @generator.rand(@@MAX_USES+1) #[0, MAX_USES]
    end
    # Determines intensity of an action based on competence
    # @param competence [Float] maximum possible intensity
    # @return [Float] random intensity between 0 and competence
    def self.intensity(competence)
      @generator.rand(competence) #[0.0, competence)
    end
    # Determines if a weapon/shield should be discarded
    # @param uses_left [Integer] remaining uses of the element
    # @return [Boolean] true if element should be discarded
    def self.discard_element(uses_left)
      if uses_left == @@MAX_USES
        result=false;
      elsif uses_left == 0
        result=true;
      else
        result= @generator.rand < (@@MAX_USES-uses_left).to_f/@@MAX_USES 
      end                                                 
      result
    end
  end
end
