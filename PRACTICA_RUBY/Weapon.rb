#encoding: utf-8
module Irrgarten
  # Represents a weapon in the Irrgarten game
  # Manages weapon power and remaining uses
  class Weapon
    # Initializes a new weapon with specified power and uses
    # @param p [Integer] attack power of the weapon
    # @param u [Integer] number of remaining uses
    def initialize(p, u)
      @power=p
      @uses=u
    end

    # Calculates attack damage from the weapon
    # Decreases uses by 1 if weapon has remaining uses
    # @return [Integer] attack power if weapon has uses, 0 otherwise
    def attack
      damage=0
      if @uses>0
        @uses -= 1
        damage=@power
      end
      damage
    end

    # Returns string representation of weapon state
    # @return [String] formatted weapon description showing power and uses
    def to_s
      "W[#{@power}, #{@uses}]"
    end

    # Determines if weapon should be discarded
    # Uses dice roll to decide based on remaining uses
    # @return [Boolean] true if weapon should be discarded
    def discard
      Dice.discard_element(@uses)
    end
  end
end
