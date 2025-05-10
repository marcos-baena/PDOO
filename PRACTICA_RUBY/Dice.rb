#encoding: utf-8
module Irrgarten
  class Dice

    @@MAX_USES = 5 
    @@MAX_INTELLIGENCE = 10.0 
    @@MAX_STRENGTH = 10.0 
    @@RESURRECT_PROB = 0.3 
    @@WEAPONS_REWARD = 2 
    @@SHIELDS_REWARD = 3 
    @@HEALTH_REWARD = 5 
    @@MAX_ATTACK = 3 
    @@MAX_SHIELD = 2

    @generator=Random.new

    def self.random_pos(max)
      @generator.rand(max) #[0, max)
    end
    def self.who_starts(nplayers)
      @generator.rand(nplayers) #[0, nplayers)
    end
    def self.random_intelligence
      @generator.rand(MAX_INTELLIGENCE) #[0, MAX_INTELLIGENCE)
    end
    def self.random_strength
      @generator.rand(MAX_STRENGTH) #[0, MAX_STRENGTH)
    end
    def self.resurrect_player
      @generator.rand < RESURRECT_PROB
    end
    def self.weapons_reward
      @generator.rand(WEAPONS_REWARD+1) #[0, WEAPONS_REWARD]
    end
    def self.shields_reward
      @generator.rand(SHIELDS_REWARD+1)  #[0, SHIELDS_REWARD]
    end
    def self.health_reward
      @generator.rand(HEALTH_REWARD+1) #[0, HEALTH_REWARD]
    end
    def self.weapon_power
      @generator.rand(MAX_ATTACK)  #[0, MAX_ATTACK)
    end
    def self.shield_power
      @generator.rand(MAX_SHIELD)  #[0, MAX_SHIELD)
    end
    def self.uses_left
      @generator.rand(MAX_USES+1) #[0, MAX_USES]
    end
    def self.intensity(competence)
      @generator.rand(competence) #[0.0, competence)
    end
    def self.discard_element(uses_left)
      if uses_left == MAX_USES
        result=false;
      elsif uses_left == 0
        result=true;
      else
        result= @generator.rand < (MAX_USES-uses_left).to_f/MAX_USES 
      end                                                 
      result
    end
  end
end
