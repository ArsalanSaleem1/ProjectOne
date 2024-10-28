package pokemonapp.player;

import pokemonapp.card.Card;
import pokemonapp.card.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class Hand extends CardContainer {


    /**
     * Check if this hand has a pokemon.
     *
     * @return true if has a pokemon, false otherwise.
     */
    public boolean hasPokemon() {
        return getPokemon() != null;
    }

    public Pokemon getPokemon() {
        for (Card card : cards) {
            if (card.isPokemon()) {
                return (Pokemon) card;
            }
        }

        return null;
    }

    public List<Card> getNonPokemonAndNonCandyCards() {
        List<Card> result = new ArrayList<>();

        for (Card card : cards) {
            if (!card.isPokemon() && !card.isCandy()) {
                result.add(card);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        String s = "{\n";

        for (int i = 0; i < this.cards.size(); i++) {
            if (i > 0) {
                s += "\n";
            }

            s += (i + 1) + ". " + this.cards.get(i);
        }

        return s + "\n}";
    }
}
