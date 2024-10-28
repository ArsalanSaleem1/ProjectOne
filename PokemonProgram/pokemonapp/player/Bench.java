package pokemonapp.player;

import pokemonapp.card.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains pokemons waiting for their turn to fight.
 */
public class Bench {
    private static final int SPOTS = 5;

    private List<Pokemon> cards;

    public Bench() {
        this.cards = new ArrayList<>();
    }

    @Override
    public String toString() {
        String s = "\n^^^^ Bench ^^^^\n";

        for (int i = 0; i < cards.size(); i++) {
            if (i > 0) {
                s += "\n";
            }

            s += "    " + (i + 1) + ". " + cards.get(i);
        }

        return s + "\n^^^^^^^^^^^^^^^^^^";
    }

    public boolean isFull() {
        return this.cards.size() >= SPOTS;
    }

    public void add(Pokemon pokemon) {
        this.cards.add(pokemon);
    }

    public List<Pokemon> getCards() {
        return cards;
    }
}
