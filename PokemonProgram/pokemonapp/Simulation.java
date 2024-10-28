package pokemonapp;

import pokemonapp.game.Game;
import pokemonapp.player.Player;

/**
 * Monte Carlo simulation for the game.
 */
public class Simulation {

    /**
     * Runs the simulation.
     *
     * @param args is not used.
     */
    public static void main(String[] args) {
        simulate(10_000);
        // small number of runs, otherwise due to deck reshuffling would take very long to finish (find pokemon)
        simulateEdgeCase(1);
    }

    /**
     * Simulates a game with decks with only one pokemon in each.
     * @param runs number of runs.
     */
    private static void simulateEdgeCase(int runs) {
        double attempts = 0;

        // how many reshuffles would be needed if a deck has only one pokemon
        for (int i = 0; i < runs; i++) {
            // setup the game with a special deck
            Player first = new Player("John");
            Player second = new Player("Jane");

            first.getDeck().fillJustOnePokemon();
            second.getDeck().fillJustOnePokemon();
            Game game = new Game(first, second);

            game.playAuto();

            attempts += game.getFirstAttemptsToGetPokemon();
        }

        double chance = attempts / runs;
        System.out.println("Reshuffles needed to get a Pokemon if a deck only has one: " + chance);
    }

    /**
     * Simulates the game, calculates and displays the probabilities.
     *
     * @param runs the number of runs.
     */
    private static void simulate(int runs) {
        double aliceWins = 0;
        double aliceAttemptsGetPokemon = 0;
        double enlightenedPokemonCount = 0;

        for (int i = 0; i < runs; i++) {
            Game game = new Game("Alice", "Bob");
            Player winner = game.playAuto();
            System.out.println("Winner: " + winner);

            if (winner.getName().equalsIgnoreCase("Alice")) {
                aliceWins++;
            }

            aliceAttemptsGetPokemon += game.getFirstAttemptsToGetPokemon() == 1 ? 1 : 0;
            enlightenedPokemonCount += game.getEnlightenedPokemonCount() > 0 ? 1 : 0;
        }

        double aliceWinsChance = aliceWins / runs;
        double alicePicksPokemonChance = aliceAttemptsGetPokemon / runs;
        double enlightenedPokemonChance = enlightenedPokemonCount / runs;

        System.out.println("\nMonte Carlo Simulation Results:\n");
        System.out.println("----------------------------------------------");
        System.out.println("Probability of the first player winning: " + aliceWinsChance);
        System.out.println("Probability of picking the first active Pokemon at the first attempt: " + alicePicksPokemonChance);
        System.out.println("Probability of having at least one Enlightened Pokemon: " + enlightenedPokemonChance);

    }
}