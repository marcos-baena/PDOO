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
      return @generator.rand(max) #[0, max)
    end
    def self.who_starts(nplayers)
      return @generator.rand(nplayers) #[0, nplayers)
    end
    def self.random_intelligence
      return @generator.rand(MAX_INTELLIGENCE) #[0, MAX_INTELLIGENCE)
    end
    def self.random_strength
      return @generator.rand(MAX_STRENGTH) #[0, MAX_STRENGTH)
    end
    def self.resurrect_player
      return @generator.rand < RESURRECT_PROB
    end
    def self.weapons_reward
      return @generator.rand(WEAPONS_REWARD+1) #[0, WEAPONS_REWARD]
    end
    def self.shields_reward
      return @generator.rand(SHIELDS_REWARD+1)  #[0, SHIELDS_REWARD]
    end
    def self.health_reward
      return @generator.rand(HEALTH_REWARD+1) #[0, HEALTH_REWARD]
    end
    def self.weapon_power
      return @generator.rand(MAX_ATTACK)  #[0, MAX_ATTACK)
    end
    def self.shield_power
      return @generator.rand(MAX_SHIELD)  #[0, MAX_SHIELD)
    end
    def self.uses_left
      return @generator.rand(MAX_USES+1) #[0, MAX_USES]
    end
    def self.intensity(competence)
      return @generator.rand(competence) #[0.0, competence)
    end
    def self.discard_element(uses_left)
      if uses_left == MAX_USES
        result=false;
      elsif uses_left == 0
        result=true;
      else
        result= @generator.rand < (MAX_USES-uses_left).to_f/MAX_USES 
      end                                                 
      return result
    end
  end
end
