package pokemonapp.card;

/**
 * Cards represent a card used for attacking.
 */
public abstract class Card {
    private String name;

    public Card(String name) {
        this.name = name;
    }

    public boolean isPokemon() {
        return false;
    }

    public boolean isCandy() {
        return false;
    }

    public boolean isTrainer() {
        return false;
    }

    public boolean isEnergy() {
        return false;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "( " +
                this.getName() +
                " )";
    }

}
