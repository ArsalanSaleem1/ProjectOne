package pokemonapp.player;

import pokemonapp.card.Card;

import java.util.List;

/**
 * Contains the prize cards - all cards, except
 * for the pokemons and candy.
 */
public class Prize extends CardContainer {
    public Prize(List<Card> cards) {
        super(cards);
    }

    @Override
    public String toString() {
        String s = "\n>>>> Prize <<<<\n";

        for (int i = 0; i < cards.size(); i++) {
            if (i > 0) {
                s += "\n";
            }

            s += "    " + (i + 1) + ". " + cards.get(i);
        }

        return s + "\n<<<<<<<<<<<<<<<<<";
    }
}
