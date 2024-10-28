package pokemonapp.card.pokemon;

import pokemonapp.Utils;
import pokemonapp.card.Card;
import pokemonapp.card.energy.Energy;
import pokemonapp.card.energy.WaterEnergy;
import pokemonapp.card.trainer.Trainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Character cards represent Pokemon creatures
 * with different types.
 * <p>
 * Each Character has two months: attacks or abilities.
 */
public abstract class Pokemon extends Card {
    protected int maxHp;
    protected int hp;
    /**
     * Influences the attack's damage.
     */
    protected int attack;
    /**
     * Influences defense against attacks.
     */
    protected int defense;
    protected List<Trainer> trainers;


    @Override
    public String toString() {
        return "[ " +
                this.getName() + ":" +
                ", HP: " + hp + "/" + maxHp +
                ", Attack: " + attack +
                ", Defense: " + defense +
                " ]";
    }


    /**
     * Each pokemon can have an energy attached.
     * An energy of any kind can be attached,
     * but only
     */
    protected Energy energy;


    public Pokemon(String name) {
        super(name);

        this.hp = Utils.generateNumberInRange(1, 100);
        this.maxHp = this.hp;

        this.attack = Utils.generateNumberInRange(1, 100);
        this.defense = Utils.generateNumberInRange(1, 100);

        this.trainers = new ArrayList<>();

        // basic energy
        this.energy = new WaterEnergy();
    }

    /**
     * To attack, a pokemon must have energy.
     * An attack deals damage to opponent's HP.
     *
     * @param opponent the opponent.
     */
    public void attack(Pokemon opponent) {
        if (this.energy == null) {
            // no energy, can't attack
            System.out.println(this.getName() + " has no energy, dealing 0 damage...");
            return;
        }

        // apply trainers before the attack
        for (Trainer trainer : this.trainers) {
            trainer.apply(this);
        }

        // otherwise calculate the damage
        int opponentsDefense = opponent.getDefense();
        int damage = opponentsDefense - this.attack;
        if (damage < 0) {
            damage = 0;
        }
        opponent.applyDamage(damage);
    }

    public boolean isDead() {
        return this.hp <= 0;
    }

    public boolean isAlive() {
        return !isDead();
    }

    public void applyDamage(double damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    public boolean isBasic() {
        return true;
    }

    public boolean isEnlightened() {
        return false;
    }

    /**
     * Character cards are Pokemon cards.
     *
     * @return true.
     */
    @Override
    public boolean isPokemon() {
        return true;
    }

    public Energy getEnergy() {
        return energy;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setEnergy(Energy energy) {
        this.energy = energy;
    }


    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }


    public int getMaxHp() {
        return maxHp;
    }
}
