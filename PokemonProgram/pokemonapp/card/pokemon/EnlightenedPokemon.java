package pokemonapp.card.pokemon;

import pokemonapp.card.pokemon.basic.BasicPokemon;

/**
 * A leveled-up pokemon, becomes such after consuming candy.
 */
public class EnlightenedPokemon extends BasicPokemon {
    public EnlightenedPokemon() {
        super("EnlightenedPokemon");
        this.maxHp = 1_000_000;
        this.hp = this.maxHp;
    }

    @Override
    public boolean isBasic() {
        return false;
    }

    @Override
    public boolean isEnlightened() {
        return true;
    }
}
