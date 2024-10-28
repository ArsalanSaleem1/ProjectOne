package pokemonapp.card.trainer;

import pokemonapp.card.pokemon.Pokemon;

/**
 * Heals the pokemon.
 */
public class HealTrainer extends Trainer {
    public HealTrainer() {
        super("HealTrainer");
    }

    /**
     * Restores the HP.
     *
     * @param pokemon the pokemon.
     */
    @Override
    public void apply(Pokemon pokemon) {
        pokemon.setHp(pokemon.getMaxHp());
    }
}
