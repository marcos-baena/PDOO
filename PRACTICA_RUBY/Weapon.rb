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
      return "W["+@power.to_s+", "+@uses.to_s+"]"
    end

    def discard
      return Dice.discard_element(@uses)
    end
  end
end
