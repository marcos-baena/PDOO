#encoding: utf-8
module Irrgarten
  class Player
    @@MAX_WEAPONS=2
    @@MAX_SHIELDS=3
    @@INITIAL_HEALTH=10
    @@HITS2LOSE=3

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

    def resurrect
      @shields.clear
      @weapons.clear
      @health=@@INITIAL_HEALTH
      reset_hits
    end
    
    def row
      @row
    end

    def col
      @col
    end

    def number
      @number
    end

    def set_pos(r,c)
      @row=r
      @col=c
      nil
    end

    def dead
      @health == 0
    end

    def move(d,v)
      #Me darán información en la práctica3
    end

    def attack
      @strength+sum_weapons
    end

    def defend(received_attack)
      manage_hit(received_attack)
    end

    def receive_reward
      #Me darán información en la práctica3
    end

    def to_s
      "P[#{@name}, I: #{@intelligence}, S: #{@strength}, H: #{@health}, Pos: (#{@row}, #{@col})]"
    end

    def receive_weapon
      #Me darán información en la práctica3
    end

    def receive_shield
      #Me darán información en la práctica3
    end
  
    def new_weapon
      Weapon.new(Dice.random_strength, Dice.uses_left)
    end

    def new_shield
      Shield.new(Dice.random_strength, Dice.uses_left)
    end

    def defensive_energy
      @intelligence+sum_shields
    end

    def manage_hit
      #Me darán información en la práctica3
    end

    def reset_hits
      @consecutive_hits=0
    end

    def sum_weapons
      sum=0
      for i in 0..(@weapons.size-1)
        sum+=@weapons[i].attack
      end
      sum
    end

    def sum_shields
      sum=0
      for i in 0..(@shields.size-1)
        sum+=@shields[i].defend
      end
      sum
    end
  end
end





