# encoding:utf-8
require_relative 'Dice'
require_relative 'GameState'
require_relative 'Shield'
require_relative 'Weapon'
require_relative 'GameCharacter'
require_relative 'Orientation'
require_relative 'Directions'


# Se asume que las clases y módulos (Dice, GameState, Shield, Weapon, GameCharacter, Orientation, Directions) ya han sido definidos previamente en la práctica.

class TestP1
  def self.main
    puts "=== Inicio de pruebas de la práctica ==="

    # --- Prueba de los tipos enumerados ---
    puts "\nValores enumerados:"
    puts "Irrgarten::GameCharacter::PLAYER    => #{Irrgarten::GameCharacter::PLAYER}"
    puts "Irrgarten::GameCharacter::MONSTER   => #{Irrgarten::GameCharacter::MONSTER}"
    puts "Irrgarten::Orientation::VERTICAL    => #{Irrgarten::Orientation::VERTICAL}"
    puts "Irrgarten::Orientation::HORIZONTAL  => #{Irrgarten::Orientation::HORIZONTAL}"
    puts "Irrgarten::Directions::LEFT         => #{Irrgarten::Directions::LEFT}"
    puts "Irrgarten::Directions::RIGHT        => #{Irrgarten::Directions::RIGHT}"
    puts "Irrgarten::Directions::UP           => #{Irrgarten::Directions::UP}"
    puts "Irrgarten::Directions::DOWN         => #{Irrgarten::Directions::DOWN}"

    # --- Creación de instancias de las clases de la práctica ---
    puts "\nCreando instancias de clases..."
    dice       = Irrgarten::Dice.new
    game_state = Irrgarten::GameState.new("Laberinto1", ["Jugador1", "Jugador2"], ["Monstruo1"], "Jugador1", nil, [])
    shield     = Irrgarten::Shield.new(5, 3)
    weapon     = Irrgarten::Weapon.new(7, 4)

    # --- Prueba de la clase GameState ---
    puts "\nPrueba de GameState:"
    puts "Labyrinth: #{game_state.labyrinth}"
    puts "Players: #{game_state.players}"
    puts "Monsters: #{game_state.monsters}"
    puts "Current Player: #{game_state.current_player}"
    puts "Winner: #{game_state.winner}"
    puts "Log: #{game_state.log}"

    # --- Prueba de la clase Shield ---
    puts "\nPrueba de Shield:"
    puts "Antes de usar protect: #{shield.to_s}"
    defensa = shield.protect
    puts "Resultado de protect: #{defensa}"
    puts "Después de usar protect: #{shield.to_s}"
    puts "Resultado de discard: #{shield.discard}"

    # --- Prueba de la clase Weapon ---
    puts "\nPrueba de Weapon:"
    puts "Antes de usar attack: #{weapon.to_s}"
    daño = weapon.attack
    puts "Resultado de attack: #{daño}"
    puts "Después de usar attack: #{weapon.to_s}"
    puts "Resultado de discard: #{weapon.discard}"

    # --- Prueba de la clase Dice 100 veces ---
    puts "\nPrueba de métodos de Dice (100 iteraciones cada uno):"
    test_dice_methods(dice)

    puts "\n=== Pruebas completadas ==="
  end

  # Método auxiliar para probar los métodos de Dice 100 veces y mostrar estadísticas básicas.
  def self.test_dice_methods(dice)
    num_trials = 100

    # random_pos(max)
    pos_distribution = Hash.new(0)
    num_trials.times { pos_distribution[dice.random_pos(10)] += 1 }
    puts "\nDistribución de random_pos(10):"
    pos_distribution.sort.each { |valor, veces| puts "Valor #{valor}: #{veces} veces" }

    # who_starts(nplayers)
    start_distribution = Hash.new(0)
    num_trials.times { start_distribution[dice.who_starts(4)] += 1 }
    puts "\nDistribución de who_starts(4):"
    start_distribution.sort.each { |jugador, veces| puts "Jugador #{jugador}: #{veces} veces" }

    # random_intelligence
    intel_values = Array.new(num_trials) { dice.random_intelligence }
    avg_intel = intel_values.sum / num_trials.to_f
    puts "\nValor medio de random_intelligence: #{avg_intel}"

    # random_strength
    strength_values = Array.new(num_trials) { dice.random_strength }
    avg_strength = strength_values.sum / num_trials.to_f
    puts "\nValor medio de random_strength: #{avg_strength}"

    # resurrect_player (cuenta cuántas veces devuelve true)
    resurrect_count = 0
    num_trials.times { resurrect_count += 1 if dice.resurrect_player }
    puts "\nresurrect_player ha devuelto true #{resurrect_count} de #{num_trials} veces"

    # weapons_reward
    weapons_rewards = Hash.new(0)
    num_trials.times { weapons_rewards[dice.weapons_reward] += 1 }
    puts "\nDistribución de weapons_reward:"
    weapons_rewards.sort.each { |reward, veces| puts "Reward #{reward}: #{veces} veces" }

    # shields_reward
    shields_rewards = Hash.new(0)
    num_trials.times { shields_rewards[dice.shields_reward] += 1 }
    puts "\nDistribución de shields_reward:"
    shields_rewards.sort.each { |reward, veces| puts "Reward #{reward}: #{veces} veces" }

    # health_reward
    health_rewards = Hash.new(0)
    num_trials.times { health_rewards[dice.health_reward] += 1 }
    puts "\nDistribución de health_reward:"
    health_rewards.sort.each { |reward, veces| puts "Reward #{reward}: #{veces} veces" }

    # weapon_power
    weapon_powers = Hash.new(0)
    num_trials.times { weapon_powers[dice.weapon_power] += 1 }
    puts "\nDistribución de weapon_power:"
    weapon_powers.sort.each { |power, veces| puts "Power #{power}: #{veces} veces" }

    # shield_power
    shield_powers = Hash.new(0)
    num_trials.times { shield_powers[dice.shield_power] += 1 }
    puts "\nDistribución de shield_power:"
    shield_powers.sort.each { |power, veces| puts "Power #{power}: #{veces} veces" }

    # uses_left
    uses_distribution = Hash.new(0)
    num_trials.times { uses_distribution[dice.uses_left] += 1 }
    puts "\nDistribución de uses_left:"
    uses_distribution.sort.each { |uses, veces| puts "Uses #{uses}: #{veces} veces" }

    # intensity(competence): Usamos competencia = 5
    intensity_values = Array.new(num_trials) { dice.intensity(5) }
    avg_intensity = intensity_values.sum / num_trials.to_f
    puts "\nValor medio de intensity(5): #{avg_intensity}"

    # discard_element(uses_left): Probamos con un valor fijo de uses_left, por ejemplo 3.
    discard_results = { true => 0, false => 0 }
    num_trials.times { discard_results[dice.discard_element(3)] += 1 }
    puts "\nResultados de discard_element(3):"
    discard_results.each { |resultado, veces| puts "#{resultado}: #{veces} veces" }
  end
end

# Al final del fichero se invoca el método main para ejecutar el programa principal.
TestP1.main
