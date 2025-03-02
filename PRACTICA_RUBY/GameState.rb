#encoding: utf-8

module Irrgarten
  class GameState
    def initialize(l, p, m, c, w, lo)
      @labyrinth=l 
      @players =p
      @monsters=m
      @current_player= c
      @winner=w
      @log=lo
    end
    def labyrinth
      return @labyrinth
    end
    def players
      return @players
    end
    def monsters
      return @monsters
    end
    def current_player
      return @current_player
    end
    def  winner
      return @winner
    end
    def log
      return @log
    end
  end
end