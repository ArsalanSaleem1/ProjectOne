package pokemonapp.card.trainer;

import pokemonapp.card.pokemon.Pokemon;

public class AttackTrainer extends Trainer {
    public AttackTrainer() {
        super("AttackTrainer");
    }

    /**
     * Increases a pokemon's attack.
     *
     * @param pokemon a pokemon.
     */
    @Override
    public void apply(Pokemon pokemon) {
        pokemon.setAttack(pokemon.getAttack() + 20);
    }
}
