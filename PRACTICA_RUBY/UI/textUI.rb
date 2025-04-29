
require 'io/console'
require_relative '../directions'

module UI

  class TextUI

    #https://gist.github.com/acook/4190379
    def read_char
      STDIN.echo = false
      STDIN.raw!
    
      input = STDIN.getc.chr
      if input == "\e" 
        input << STDIN.read_nonblock(3) rescue nil
        input << STDIN.read_nonblock(2) rescue nil
      end
    ensure
      STDIN.echo = true
      STDIN.cooked!
    
      return input
    end

    def next_move
      print "Where? "
      got_input = false
      while (!got_input)
        c = read_char
        case c
          when "\e[A"
            puts "UP ARROW"
            output = Irrgarten::Directions::UP
            got_input = true
          when "\e[B"
            puts "DOWN ARROW"
            output = Irrgarten::Directions::DOWN
            got_input = true
          when "\e[C"
            puts "RIGHT ARROW"
            output = Irrgarten::Directions::RIGHT
            got_input = true
          when "\e[D"
            puts "LEFT ARROW"
            output = Irrgarten::Directions::LEFT
            got_input = true
          when "\u0003"
            puts "CONTROL-C"
            got_input = true
            exit(1)
          else
            #Error
        end
      end
      output
    end

    def show_game(game_state)
      puts "Tablero: \n"
      puts game_state.labyrinth()
      puts "Jugadores: \n"
      puts game_state.players()
      puts "\nMonstruos: \n"
      puts game_state.monsters()
      puts "\nEs el turno del jugador "+game_state.current_player().to_s()
      puts game_state.log()
    end

  end # class   

end # module   


