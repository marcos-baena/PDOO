#encoding: utf-8
module Irrgarten
  class Weapon
    def initialize(p, u)
      @power=p
      @uses=u
    end

    def attack
      damage=0
      if @uses>0
        @uses -= 1
        damage=@power
      end
      return damage
    end

    def to_s
      "W[#{@power}, #{@uses}]"
    end

    def discard
      Dice.discard_element(@uses)
    end
  end
end
