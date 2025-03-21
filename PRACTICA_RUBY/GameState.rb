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
    attr_reader :labyrinth
    attr_reader :players
    attr_reader :monsters
    attr_reader :current_player
    attr_reader :winner
    attr_reader :log

  end
end