package pokemonapp.player;

import pokemonapp.card.pokemon.Pokemon;

/**
 * A player in the Pokemon game.
 */
public class Player {
    // constants

    /**
     * There are three types of cards: Pokemon,
     * Trainer Cards, Energy Cards
     */
    private static final int NUMBER_OF_CARD_TYPES = 3;

    // attributes

    private String name;

    /**
     * A deck of cards.
     */
    private Deck deck;
    private Hand hand;
    private Pokemon activePokemon;
    private Prize prize;

    private Bench bench;

    public Player(String name) {
        this.name = name;

        // deck is empty
        this.deck = new Deck();
        this.deck.shuffle();

        this.hand = new Hand();

        this.bench = new Bench();
    }

    public boolean allIsGoodWithActivePokemon() {
        return this.activePokemon != null && this.activePokemon.isAlive();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public String getName() {
        return name;
    }

    public Deck getDeck() {
        return deck;
    }

    public Hand getHand() {
        return hand;
    }

    public Pokemon getActivePokemon() {
        return activePokemon;
    }

    public void setActivePokemon(Pokemon activePokemon) {
        this.activePokemon = activePokemon;
    }

    public Prize getPrize() {
        return prize;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public Bench getBench() {
        return bench;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
