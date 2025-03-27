#encoding: utf-8
module Irrgarten
  # Represents a shield in the Irrgarten game
  # Manages shield protection and remaining uses
  class Shield
    # Initializes a new shield with specified protection and uses
    # @param p [Integer] protection value of the shield
    # @param u [Integer] number of remaining uses
    def initialize(p, u)
      @protection=p
      @uses=u
    end

    # Calculates defense value from the shield
    # Decreases uses by 1 if shield has remaining uses
    # @return [Integer] protection value if shield has uses, 0 otherwise
    def protect
      defense=0
      if @uses>0
        @uses -= 1
        defense=@protection
      end
      defense
    end

    # Returns string representation of shield state
    # @return [String] formatted shield description showing protection and uses
    def to_s
      "S[#{@protection}, #{@uses}]"
    end

    # Determines if shield should be discarded
    # Uses dice roll to decide based on remaining uses
    # @return [Boolean] true if shield should be discarded
    def discard
      Dice.discard_element(@uses)
    end
  end
end
