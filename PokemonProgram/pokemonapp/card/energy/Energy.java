package pokemonapp.card.energy;

import pokemonapp.Utils;
import pokemonapp.card.Card;

/**
 * Energy cards power up Pokemon's attacks.
 */
public abstract class Energy extends Card {
    protected double value;

    public Energy(String name) {
        super(name);
        this.value = Utils.generateNumberInRange(1, 100);
    }

    @Override
    public boolean isEnergy() {
        return true;
    }

    public void decrease() {
        this.value -= Utils.generateNumberInRange(1, (int) value + 1);
    }

    public boolean hasEnergy() {
        return this.value > 0;
    }
}
