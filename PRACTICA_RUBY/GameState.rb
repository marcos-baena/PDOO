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
      @labyrinth
    end
    def players
      @players
    end
    def monsters
      @monsters
    end
    def current_player
      @current_player
    end
    def  winner
      @winner
    end
    def log
      @log
    end
  end
end