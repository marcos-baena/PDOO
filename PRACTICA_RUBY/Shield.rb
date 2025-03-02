#encoding: utf-8
module Irrgarten

  class Shield
    def initialize(p, u)
      @protection=p
      @uses=u
    end

    def protect
      defense=0
      if @uses>0
        @uses -= 1
        defense=@protection
      end
      return defense
    end

    def to_s
      return "S["+@protection.to_s+", "+@uses.to_s+"]"
    end

    def discard
      return Dice.discard_element(@uses)
    end
  end
end
