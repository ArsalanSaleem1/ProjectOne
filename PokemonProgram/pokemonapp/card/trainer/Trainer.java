package pokemonapp.card.trainer;

import pokemonapp.card.Card;
import pokemonapp.card.pokemon.Pokemon;

/**
 * Trainer cards have effects, like healing, drawing,
 * or searching for specific cards.
 */
public abstract class Trainer extends Card {
    private String name;

    public Trainer(String name) {
        super(name);
    }

    public abstract void apply(Pokemon pokemon);

    public boolean isTrainer() {
        return true;
    }
}
