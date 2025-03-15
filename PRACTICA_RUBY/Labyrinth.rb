#encoding:utf-8
module Irrgarten
  class Labyrinth
    @@BLOCK_CHAR='X'
    @@EMPTY_CHAR='-'
    @@MONSTER_CHAR='M'
    @@COMBAT_CHAR='C'
    @@EXIT_CHAR='E'
    @@ROW=0
    @@COL=1

    def initialize(n_r, n_c, e_r, e_c)
      @n_rows=n_r
      @n_cols=n_c
      
      @monsters = Array.new(@n_rows) {Array.new(@n_cols)}
      @players = Array.new(@n_rows) {Array.new(@n_cols)}
      @labyrinth= Array.new(@n_rows) {Array.new(@n_cols)}
      
      @exit_row=e_r
      @exit_col=e_c
    end

    def spread_players(players)
      #Recibiré información en la práctica3
    end

    def have_a_winner
      @players[@exit_row][@exit_col] != nil
    end

    def to_s
      tablero=""
      for i in 0..(@n_rows-1)
        for j in 0..(@n_cols-1)
          tablero+="#{@labyrinth[i][j]}"
        end
          tablero+="\n"
      end
      tablero
    end

    def add_monster(row, col, monster)
      if pos_OK(row, col) && empty_pos(row, col)
        @labyrinth[row][col] = @@MONSTER_CHAR
        @monsters[row][col] = monster
      end
      nil
    end

    def put_player
      #Me darán información en la práctica3
    end

    def add_block
      #Me darán información en la práctica3
    end

    def valid_moves
      #Me darán información en la práctica3
    end
    
    private
    
    def pos_OK(row, col)
      (0 <= row && row < @n_rows) && (0 <= col && col < @n_cols)
    end

    def empty_pos(row,col)
      @labyrinth[row][col] == @@EMPTY_CHAR
    end

    def monster_pos(row,col)
      @labyrinth[row][col] == @@MONSTER_CHAR
    end
    
    def exit_pos(row,col)
      @labyrinth[row][col] == @@EXIT_CHAR
    end

    def combat_pos(row,col)
      @labyrinth[row][col] == @@COMBAT_CHAR
    end

    def can_step_on(row,col)
      pos_OK(row,col) && (empty_pos(row,col) || monster_pos(row,col) || exit_pos(row,col))
    end

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

    def dir_2_pos(row,col,direction)
      pos = Array.new(2)
      pos[@@ROW]=row
      pos[@@COL]=col

        case direction 
        when Directions::DOWN 
          pos[@@ROW]-=1
        when Directions::UP 
          pos[@@ROW]+=1
        when Directions::LEFT 
          pos[@@COL]-=1
        when Directions::RIGHT 
          pos[@@COL]+=1
        end
      pos
    end

    def random_empty_pos
      random_pos = Array.new(2)

      random_pos[@@ROW] = Dice.random_pos(@n_rows)
      random_pos[@@COL] = Dice.random_pos(@n_cols)

      while !empty_pos(randomPos[ROW],randomPos[COL])
        random_pos[@@ROW] = Dice.random_pos(@n_rows)
        random_pos[@@COL] = Dice.random_pos(@n_cols)
      end
      random_pos
    end

    def put_player_2D(old_row, old_col, row, col, player)
      #Me darán información en la práctica3
    end
  end
end 

