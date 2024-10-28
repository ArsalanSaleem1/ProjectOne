package pokemonapp.card.trainer;

import pokemonapp.card.pokemon.Pokemon;

public class TastyCandy extends Trainer {
    public TastyCandy() {
        super("TastyCandy");
    }

    public boolean isCandy() {
        return true;
    }

    @Override
    public void apply(Pokemon pokemon) {

    }
}
