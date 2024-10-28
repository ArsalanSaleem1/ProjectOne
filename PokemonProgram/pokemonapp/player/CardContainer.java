package pokemonapp.player;

import pokemonapp.card.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class CardContainer {
    protected List<Card> cards;

    public CardContainer() {
        this.cards = new ArrayList<>();
    }

    public CardContainer(Card... cards) {
        this();
        this.add(cards);
    }

    public CardContainer(List<Card> cards) {
        this();
        this.add(cards);
    }


    public boolean isEmpty() {
        return this.cards.isEmpty();
    }


    public void add(Card... array) {
        for (Card c : array) {
            this.cards.add(c);
        }
    }

    public void add(List<Card> list) {
        for (Card c : list) {
            this.cards.add(c);
        }
    }

    public List<Card> getCards() {
        return cards;
    }
}
