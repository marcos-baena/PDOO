module Irrgarten
  class Game
    @@MAX_ROUNDS=10

    def initialize(nplayers)
      @current_player_index=Dice.who_starts(nplayers)

      @players = Array.new(nplayers) 

      #Inicializa con players
      for i in 0...nplayers
        @players << Player.new(i.chr, Dice.random_intelligence, Dice.random_strength)
      end
      @current_player=players[@current_player_index]
      @labyrinth.spread_players(@players)

      current_player
      @monsters=Array.new
      @labyrinth=Array.new

      @log = ""
    end

    def finished
      @labyrinth.have_a_winner
    end

    def next_step(preferred_direction)
      #Me darán más información en la práctica 3
    end

    def get_game_state
      Gamestate.new(@labyrinth.to_s, @players.to_s, @monsters.to_s, current_player_index, finished, log)
    end
    
    private

    def configure_labyrinth
      #Aún no sé cómo se hace
    end

    def next_player
      if @current_player_index == ( @players.size -1 )
        @current_player_index = 0
        @current_player = @players[@current_player_index]
      else
        @current_player_index+=1
        @current_player = @players[current_player_index]
      end
    end

    def actual_direction(preferred_direction)
      #Me darán más información en la práctica 3
    end

    def combat(monster)
      #Me darán más información en la práctica 3
    end

    def manage_reward
      #Me darán más información en la práctica 3
    end

    def manage_resurrection
      #Me darán más información en la práctica 3
    end

    def log_player_won
      log+="El jugador #{@current_player.to_s} ha ganado el combate \n"
    end

    def log_monster_won
      log+="El jugador monstruo ha ganado el combate \n"
    end

    def log_resurrected
      log+="El jugador #{@current_player.to_s} ha resucitado \n"
    end

    def log_player_skip_turn
      log+="El jugador #{@current_player.to_s} ha perdido el turno por estar muerto \n"
    end

    def log_player_no_orders
      log+="El jugador #{@current_player.to_s} no ha podido seguir las instrucciones del jugador humano \n"
    end

    def log_no_monster
      log+="El jugador #{@current_player.to_s} se ha movido a una celda vacía o no le ha sido posible moverse \n"
    end

    def log_rounds(rounds, max)
      log+="Se han jugado #{rounds} de #{max} rondas \n"
    end