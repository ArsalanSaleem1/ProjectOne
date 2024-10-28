package pokemonapp.player;

import pokemonapp.Utils;
import pokemonapp.card.Card;
import pokemonapp.card.energy.LightningEnergy;
import pokemonapp.card.energy.WaterEnergy;
import pokemonapp.card.pokemon.Pokemon;
import pokemonapp.card.pokemon.basic.Bulbasaur;
import pokemonapp.card.pokemon.basic.Charmander;
import pokemonapp.card.pokemon.basic.Squirtle;
import pokemonapp.card.trainer.AttackTrainer;
import pokemonapp.card.trainer.HealTrainer;
import pokemonapp.card.trainer.TastyCandy;

import java.util.ArrayList;

/**
 * Represents a player's deck.
 */
public class Deck extends CardContainer {
    /**
     * A deck has 60 cards.
     */
    private static final int CARDS_IN_DECK = 60;

    /**
     * The number of cards each player draws from the deck.
     */
    private static final int CARDS_DRAWN = 7;
    /**
     * Each card type can be repeated in a deck at most four times.
     */
    private static final int MAX_CARD_NUMBER = 4;

    public Deck() {
        this.fill();
    }

    public void fillJustOnePokemon() {
        this.cards = new ArrayList<>();
        for (int i = 0; i < CARDS_IN_DECK - 1; i++) {
            this.cards.add(new HealTrainer());
        }

        this.cards.add(new Bulbasaur());
    }

    public Card drawCard() {
        int cardToTakeIndex = Utils.generateNumberInRange(0, this.cards.size() - 1);
        Card card = this.cards.get(cardToTakeIndex);
        this.cards.remove(card);
        return card;
    }

    public Hand drawHand() {
        Hand hand = new Hand();

        for (int i = 0; i < CARDS_DRAWN; i++) {
            int cardToTakeIndex = Utils.generateNumberInRange(0, this.cards.size() - 1);
            hand.add(this.cards.get(cardToTakeIndex));
            this.cards.remove(cardToTakeIndex);

        }

        return hand;
    }

    public void returnHand(Hand hand) {
        for (Card card : hand.getCards()) {
            this.add(card);
        }
    }


    /**
     * Fills the deck with cards.
     */
    public void fill() {
        for (int i = 0; i < MAX_CARD_NUMBER; i++) {
            // add pokemons
            add(new Bulbasaur(), new Charmander(), new Squirtle());

            // add trainers
            add(new HealTrainer(), new AttackTrainer());

            // add energy
            add(new WaterEnergy(), new LightningEnergy());

            // add candy
            add(new TastyCandy());
        }

    }

    public boolean hasPokemon() {
        return findPokemon() != null;
    }

    public Pokemon findPokemon() {
        for (Card card : cards) {
            if (card.isPokemon()) {
                return (Pokemon) card;
            }
        }

        return null;
    }

    /**
     * Reshuffles this deck.
     */
    public void shuffle() {
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = Utils.generateNumberInRange(0, i);
            // swap the cards
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

}
