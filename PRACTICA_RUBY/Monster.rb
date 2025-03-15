#encoding: utf-8
module Irrgarten
  class Monster
    @@INITIAL_HEALTH=5

    def initialize(n, i, s)
      @name=n
      @intelligence=i
      @strength=s
      @health=@@INITIAL_HEALTH
      @row=-1
      @col=-1
    end
    
    def dead
      health == 0
    end

    def attack
      Dice.intensity(strength)
    end

    def set_pos(r, c)
      @row=r
      @col=c
      nil
    end

    def to_s
      "M[#{@name}, I: #{@intelligence}, S: #{@strength}, H: #{@health}, Pos: (#{@row}, #{@col})]"
    end

    def defend(received_attack)
      #Recibiré información en la práctica3
    end

    private

    def gotWounded
      @health-=1
      nil
    end
  end
end