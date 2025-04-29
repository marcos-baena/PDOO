#encoding:utf-8
module Irrgarten
  # Represents the game's labyrinth/map
  # Manages player and monster positions, walls, and game state
  class Labyrinth
    # Character representations for different map elements
    @@BLOCK_CHAR='X'    # Wall/block character
    @@EMPTY_CHAR='-'    # Empty space character
    @@MONSTER_CHAR='M'  # Monster character
    @@COMBAT_CHAR='C'   # Combat position character
    @@EXIT_CHAR='E'     # Exit character
    
    # Array indices for row/col positions
    @@ROW=0
    @@COL=1

    # Initializes a new labyrinth with specified dimensions and exit position
    # @param n_r [Integer] number of rows
    # @param n_c [Integer] number of columns
    # @param e_r [Integer] exit row position
    # @param e_c [Integer] exit column position
    def initialize(n_r, n_c, e_r, e_c)
      @n_rows=n_r
      @n_cols=n_c
      
      @monsters = Array.new(@n_rows) {Array.new(@n_cols)}
      @players = Array.new(@n_rows) {Array.new(@n_cols)}
      @labyrinth= Array.new(@n_rows) {Array.new(@n_cols, @@EMPTY_CHAR)}
      
      @exit_row=e_r
      @exit_col=e_c
      @labyrinth[@exit_row][@exit_col] = @@EXIT_CHAR
    end

    # Spreads players across the labyrinth (implementation pending)
    # @param players [Array<Player>] array of players to place
    def spread_players(players)
      pos = Array.new
      p

      for i in 0...players.size
        pos = random_empty_pos
        p = players[i]
        put_player_2D(-1,-1, pos[@@ROW], pos[@@COL], p)
      end
    end

    # Checks if there is a winner at the exit position
    # @return [Boolean] true if a player is at the exit
    def have_a_winner
      @players[@exit_row][@exit_col] != nil
    end

    # Returns string representation of the labyrinth
    # @return [String] formatted labyrinth map
    def to_s
      tablero=String.new
      for i in 0...@n_rows
        for j in 0...@n_cols
          tablero << @labyrinth[i][j] << " "
        end
          tablero << "\n"
      end
      tablero
    end

    # Adds a monster to the labyrinth at specified position
    # @param row [Integer] row position
    # @param col [Integer] column position
    # @param monster [Monster] monster to add
    # @return [nil]
    def add_monster(row, col, monster)
      if pos_OK(row, col) && empty_pos(row, col)
        @labyrinth[row][col] = @@MONSTER_CHAR
        monster.set_pos(row, col)
        @monsters[row][col] = monster
      end
      nil
    end

    # Moves a player in specified direction
    # @param direction [Symbol] direction to move (from Directions module)
    # @param player [Player] player to move
    # @return [Monster, nil] monster if combat occurs, nil otherwise
    def put_player(direction, player)
      old_row = player.row
      old_col  = player.col
      new_pos = Array.new

      new_pos = dir_2_pos(old_row, old_col, direction)

      monster = put_player_2D(old_row, old_col, new_pos[@@ROW], new_pos[@@COL], player)

      monster
    end#Me darán más información en la práctica 3

    # Adds a block/wall to the labyrinth
    # @param orientation [Symbol] block orientation (from Orientation module)
    # @param start_row [Integer] starting row position
    # @param start_col [Integer] starting column position
    # @param length [Integer] length of the block
    # @return [nil]
    def add_block(orientation, start_row, start_col, length)
      if orientation == Orientation::VERTICAL
        inc_row = 1
        inc_col = 0
      else
        inc_row = 0
        inc_col = 1
      end

      row = start_row
      col = start_col
      
      while pos_OK(row, col) && empty_pos(row, col) && length > 0
        @labyrinth[row][col] = @@BLOCK_CHAR
        length -= 1
        row += inc_row
        col += inc_col
      end
    end

    # Gets valid moves from current position
    # @param row [Integer] current row position
    # @param col [Integer] current column position
    # @return [Array<Symbol>] array of valid directions (from Directions module)
    def valid_moves(row, col)
      output = []
      if can_step_on(row + 1, col)
        output << Directions::DOWN
      end
      if can_step_on(row - 1, col)
        output << Directions::UP
      end
      if can_step_on(row, col + 1)
        output << Directions::RIGHT
      end
      if can_step_on(row, col - 1)
        output << Directions::LEFT
      end
      output
    end
    
    private
    
    # Checks if position is within labyrinth bounds
    # @param row [Integer] row position
    # @param col [Integer] column position
    # @return [Boolean] true if position is valid
    def pos_OK(row, col)
      (0 <= row && row < @n_rows) && (0 <= col && col < @n_cols)
    end

    # Checks if position is empty
    # @param row [Integer] row position
    # @param col [Integer] column position
    # @return [Boolean] true if position is empty
    def empty_pos(row,col)
      @labyrinth[row][col] == @@EMPTY_CHAR
    end

    # Checks if position contains a monster
    # @param row [Integer] row position
    # @param col [Integer] column position
    # @return [Boolean] true if position has a monster
    def monster_pos(row,col)
      @labyrinth[row][col] == @@MONSTER_CHAR
    end
    
    # Checks if position is the exit
    # @param row [Integer] row position
    # @param col [Integer] column position
    # @return [Boolean] true if position is the exit
    def exit_pos(row,col)
      @labyrinth[row][col] == @@EXIT_CHAR
    end

    # Checks if position is a combat position
    # @param row [Integer] row position
    # @param col [Integer] column position
    # @return [Boolean] true if position is a combat position
    def combat_pos(row,col)
      @labyrinth[row][col] == @@COMBAT_CHAR
    end

    # Checks if position can be stepped on
    # @param row [Integer] row position
    # @param col [Integer] column position
    # @return [Boolean] true if position is valid and accessible
    def can_step_on(row,col)
      pos_OK(row,col) && (empty_pos(row,col) || monster_pos(row,col) || exit_pos(row,col))
    end

    # Updates old position after movement
    # @param row [Integer] row position
    # @param col [Integer] column position
    # @return [nil]
    def update_old_pos(row,col)
      if pos_OK(row, col)
        if @labyrinth[row][col] == @@COMBAT_CHAR 
          @labyrinth[row][col] = @@MONSTER_CHAR;
        else 
          @labyrinth[row][col] = @@EMPTY_CHAR;
        end
      end
      nil
    end

    # Converts direction to position change
    # @param row [Integer] current row position
    # @param col [Integer] current column position
    # @param direction [Symbol] direction to move (from Directions module)
    # @return [Array<Integer>] new [row, col] position
    def dir_2_pos(row,col,direction)
      pos = Array.new(2)
      pos[@@ROW]=row
      pos[@@COL]=col

        case direction 
        when Directions::DOWN 
          pos[@@ROW]+=1
        when Directions::UP 
          pos[@@ROW]-=1
        when Directions::LEFT 
          pos[@@COL]-=1
        when Directions::RIGHT 
          pos[@@COL]+=1
        end
      pos
    end

    # Finds a random empty position
    # @return [Array<Integer>] [row, col] of empty position
    def random_empty_pos
      random_pos = Array.new(2)

      random_pos[@@ROW] = Dice.random_pos(@n_rows)
      random_pos[@@COL] = Dice.random_pos(@n_cols)

      while !empty_pos(random_pos[@@ROW],random_pos[@@COL])
        random_pos[@@ROW] = Dice.random_pos(@n_rows)
        random_pos[@@COL] = Dice.random_pos(@n_cols)
      end
      random_pos
    end

    # Internal method to place player in 2D space
    # @param old_row [Integer] previous row position
    # @param old_col [Integer] previous column position
    # @param row [Integer] new row position
    # @param col [Integer] new column position
    # @param player [Player] player to move
    # @return [Monster, nil] monster if combat occurs, nil otherwise
    def put_player_2D(old_row, old_col, row, col, player)
      output = nil
      
      if can_step_on(row, col)
        if pos_OK(old_row, old_col)
          p = @players[old_row][old_col]
          if p == player
            update_old_pos(old_row, old_col)
            @players[old_row][old_col] = nil
          end
        end

        monster_pos = monster_pos(row, col)

        if monster_pos
          @labyrinth[row][col] = @@COMBAT_CHAR
          output = @monsters[row][col]
        else
          number = player.number
          @labyrinth[row][col] = number
        end

        @players[row][col] = player
        
        player.set_pos(row, col)
      end
      output
    end
  end
end
