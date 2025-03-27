module Irrgarten
  # Main game controller class that manages game state and flow
  class Game
    # Maximum number of combat rounds
    @@MAX_ROUNDS=10

    # Initializes a new game with specified number of players
    # @param nplayers [Integer] number of players in the game
    def initialize(nplayers)
      @current_player_index=Dice.who_starts(nplayers)

      @players = Array.new(nplayers) 

      #Inicializa con players
      for i in 0...nplayers
        @players << Player.new(i.chr, Dice.random_intelligence, Dice.random_strength)
      end
      @current_player=players[@current_player_index]
      @labyrinth=Labyrinth.new
      @labyrinth.spread_players(@players)

      current_player
      @monsters=Array.new
      @log = ""
    end

    # Checks if game has finished (a player reached the exit)
    # @return [Boolean] true if game is finished
    def finished
      @labyrinth.have_a_winner
    end

    # Advances game state by one step
    # @param preferred_direction [Symbol] player's desired movement direction
    # @return [Boolean] true if game has finished after this step
    def next_step(preferred_direction)
      @log = ""
      dead = @current_player.dead

      if !dead
        direction = actual_direction(preferred_direction)

        if direction != preferred_direction
          log_player_no_orders
        end

        monster = @labyrinth.put_player(direction, @current_player)

        if monster == nil
          log_no_monster
        else
            winner = combat(monster)
            manage_reward(winner)
        end
      else
        manage_resurrection
      end

      end_game = finished
      
      if !end_game
        next_player
      end
      end_game
    end

    # Gets current game state
    # @return [GameState] object containing game state information
    def get_game_state
      players_s = String.new
      monsters_s = String.new

      for i in 0...@players.size
        players_s << @players[i].to_s
        players_s << ", "
      end

      for i in 0...@monsters.size
        monsters_s << @monsters[i].to_s
        monsters_s << ", "
      end 
      Gamestate.new(@labyrinth.to_s, players_s, monsters_s, current_player_index, finished, log)
    end
    
    private

    # Configures labyrinth (implementation pending)
    def configure_labyrinth
      #Aún no sé cómo se hace
    end

    # Advances to next player in turn order
    # @return [nil]
    def next_player
      @current_player_index = (@current_player_index + 1) % @players.size
      @current_player = @players[@current_player_index]
    end

    # Determines actual movement direction considering valid moves
    # @param preferred_direction [Symbol] player's desired direction
    # @return [Symbol] actual movement direction
    def actual_direction(preferred_direction)
      current_row = @current_player.row
      current_col = @current_player.col
      
      valid_moves = @labyrinth.valid_moves(current_row, current_col)

      output = @current_player.move(preferred_direction, valid_moves)

      output
    end

    # Handles combat between player and monster
    # @param monster [Monster] monster to fight
    # @return [Symbol] winner of combat (GameCharacter::PLAYER or GameCharacter::MONSTER)
    def combat(monster)      
      rounds = 0
      winner = GameCharacter::PLAYER
      player_attack = @curent_player.attack
      lose = monster.defend(player_attack)

      while !lose && rounds < @@MAX_ROUNDS
        winner = GameCharacter::MONSTER
        rounds+=1

        monster_attack = monster.attack
        lose = @current_player.defend(monster_attack)

        if !lose
          player_attack = @current_player.attack
          winner = GameCharacter::PLAYER
          lose = monster.defend(player_attack)
        end
      end
      log_rounds(rounds, @@MAX_ROUNDS)
      winner
    end

    # Manages rewards after combat
    # @param winner [Symbol] winner of combat
    # @return [nil]
    def manage_reward(winner)
      if winner == GameCharacter::PLAYER
        @current_player.receive_reward
        log_player_won
      else
        log_monster_won
      end
      nil
    end

    # Handles player resurrection logic
    # @return [nil]
    def manage_resurrection
      resurrect = Dice.resurrect_player
      
      if resurrect
        @current_player.resurrect
      else
        log_player_skip_turn
      end
      nil
    end

    # Logs player victory message
    # @return [nil]
    def log_player_won
      @log+="El jugador #{@current_player.to_s} ha ganado el combate \n"
    end

    # Logs monster victory message
    # @return [nil]
    def log_monster_won
      @log+="El jugador monstruo ha ganado el combate \n"
    end

    # Logs player resurrection message
    # @return [nil]
    def log_resurrected
      @log+="El jugador #{@current_player.to_s} ha resucitado \n"
    end

    # Logs player skip turn message
    # @return [nil]
    def log_player_skip_turn
      @log+="El jugador #{@current_player.to_s} ha perdido el turno por estar muerto \n"
    end

    # Logs player unable to follow orders message
    # @return [nil]
    def log_player_no_orders
      @log+="El jugador #{@current_player.to_s} no ha podido seguir las instrucciones del jugador humano \n"
    end

    # Logs no monster encounter message
    # @return [nil]
    def log_no_monster
      @log+="El jugador #{@current_player.to_s} se ha movido a una celda vacía o no le ha sido posible moverse \n"
    end

    # Logs combat rounds information
    # @param rounds [Integer] number of rounds fought
    # @param max [Integer] maximum possible rounds
    # @return [nil]
    def log_rounds(rounds, max)
      @log+="Se han jugado #{rounds} de #{max} rondas \n"
    end
  end
end
