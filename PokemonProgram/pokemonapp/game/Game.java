package pokemonapp.game;

import pokemonapp.Utils;
import pokemonapp.card.Card;
import pokemonapp.card.energy.Energy;
import pokemonapp.card.pokemon.EnlightenedPokemon;
import pokemonapp.card.pokemon.Pokemon;
import pokemonapp.player.Hand;
import pokemonapp.player.Player;
import pokemonapp.player.Prize;

/**
 * Contains the game data, the game loop and the main method.
 */
public class Game {
    private Talker talker;

    private Player first;
    private Player second;
    private Player currentPlayer;

    // count attempts to get the first pokemon
    private int firstAttemptsToGetPokemon;
    private int secondAttemptsToGetPokemon;

    // count the number of enlightened pokemons
    private int enlightenedPokemonCount;

    /**
     * Creates and sets up a new game.
     */
    public Game() {
        // is used to communicate with the user
        this.talker = new Talker();

        String firstName = talker.getPlayerName("first");
        String secondName = talker.getPlayerName("second", firstName);

        this.config(firstName, secondName);
    }

    public Game(String firstName, String secondName) {
        this.config(firstName, secondName);
    }

    public Game(Player f, Player s) {
        this.config(f, s);
    }

    private void config(String firstName, String secondName) {
        this.first = new Player(firstName);
        this.second = new Player(secondName);

        config(this.first, this.second);
    }

    private void config(Player f, Player s) {
        this.first = f;
        this.second = s;

        // flips a coin to choose the player that starts the game
        this.currentPlayer = this.getStartingPlayer();

        // setup before the game begins
        this.setup(this.currentPlayer);
        this.currentPlayer = this.getOpponent(this.currentPlayer);
        this.setup(this.currentPlayer);
    }

    /**
     * Sets up the decks and hands of the players
     * before the game begins.
     * <p>
     * If the player doesn't have a Pokemon after drawing 7 cards,
     * they show their hand to their opponent, then shuffle them
     * back into their deck, and redraw their hand.
     * <p>
     * Each time a player does that, their opponent draws an extra card.
     */
    public void setup(Player player) {
        // draw 7 cards from the deck
        System.out.println("\n---------------- S E T U P (" + player.getName() +
                ") ----------------");

        Hand hand;
        do {
            player.getDeck().shuffle();

            hand = player.getDeck().drawHand();
            if (hand.hasPokemon()) {
                System.out.println(player.getName() + " got a pokemon!");

                // has pokemon, set it as an active pokemon,f ace down
                player.setActivePokemon(hand.getPokemon());

                // set aside the other 6 cards as prize cards
                player.setPrize(new Prize(hand.getNonPokemonAndNonCandyCards()));
                System.out.println(player.getName() + " has set aside the prize cards!");
            } else {
                // show the hand
                System.out.println(player.getName() + " didn't get a pokemon! Their hand:");
                System.out.println(player.getHand());

                // shuffle the hand back into the deck
                player.getDeck().returnHand(hand);
                System.out.println(player.getName() + " returned their cards to the deck...");

                // opponent takes one card from their deck
                Player opponent = this.getOpponent(player);
                Card card = opponent.getDeck().drawCard();
                if (card.isPokemon() && !opponent.getBench().isFull()) {
                    opponent.getBench().add((Pokemon) card);
                    System.out.println(opponent.getName() +
                            " added " + card.getName() + " to their bench!");
                } else {
                    opponent.getDeck().add(card);
                }
            }

            if (player == first) {
                firstAttemptsToGetPokemon++;
            } else {
                secondAttemptsToGetPokemon++;
            }
        } while (!hand.hasPokemon());

        System.out.println("-------------------------------------------------");
    }

    /**
     * Plays automatically, without prompting user for input.
     *
     * @return the winner.
     */
    public Player playAuto() {
        boolean isGameFinished;
        Player winner = null;
        do {
            Player opponent = this.getOpponent(this.currentPlayer);

            this.currentPlayer.getActivePokemon().attack(opponent.getActivePokemon());

            // game over if either of the decks gets empty
            if (this.currentPlayer.getDeck().isEmpty() || opponent.getDeck().isEmpty()) {
                isGameFinished = true;
            } else {

                this.drawCardMove();

                // force attack if there are active pokemons
                if (this.currentPlayer.allIsGoodWithActivePokemon() &&
                        opponent.allIsGoodWithActivePokemon()) {
                    System.out.println("Forcing a fight...");
                    isGameFinished = !this.fightMove();
                } else {
                    isGameFinished = true;
                }

                this.currentPlayer = opponent;
            }
        } while (!isGameFinished);

        Player opponent = this.getOpponent(this.currentPlayer);
        if (this.currentPlayer.getActivePokemon().isDead()) {
            winner = opponent;
        } else if (opponent.getActivePokemon().isDead()) {
            winner = this.currentPlayer;
        } else if (this.currentPlayer.getActivePokemon().getHp() > opponent.getActivePokemon().getHp()) {
            winner = this.currentPlayer;
        } else {
            winner = opponent;
        }
        return winner;
    }

    /**
     * Game loop.
     *
     * @return the winner.
     */
    public Player play() {
        boolean gameOver = false;
        boolean nextPlayerMove = false;
        do {
            // new player
            gameOver = false;
            nextPlayerMove = false;

            boolean exit = false;
            do {
                if (this.currentPlayer.getDeck().isEmpty()) {
                    gameOver = true;
                    break;
                }

                // player menu
                int menu = talker.actions(this.currentPlayer);
                System.out.println();
                switch (menu) {
                    // attack
                    case 1:
                        fightMove();

                        break;
                    // view active pokemon
                    case 2:
                        System.out.println(this.currentPlayer.getActivePokemon());
                        break;
                    // view bench
                    case 3:
                        System.out.println(this.currentPlayer.getBench());

                        break;
                    // view prize
                    case 4:
                        System.out.println(this.currentPlayer.getPrize());

                        break;
                    // change active pokemon
                    case 5:
                        if (this.currentPlayer.getBench().getCards().isEmpty()) {
                            System.out.println("The bench is empty!");
                        } else {
                            int choice = talker.numberRange("Select a new active pokemon:\n" +
                                    this.currentPlayer.getBench() +
                                    "\nYour choice: ", 1, this.currentPlayer.getBench().getCards().size());

                            Pokemon pokemon = this.currentPlayer.getBench().getCards().get(choice - 1);
                            this.currentPlayer.getBench().getCards().remove(pokemon);
                            this.currentPlayer.getBench().add(this.currentPlayer.getActivePokemon());

                            this.currentPlayer.setActivePokemon(pokemon);
                            System.out.println("--> New active pokemon: " + pokemon);
                        }

                        break;
                    // draw card
                    case 6:
                        if (this.currentPlayer.getDeck().isEmpty()) {
                            System.out.println("The deck is empty!");
                        } else {
                            this.drawCardMove();
                            nextPlayerMove = true;

                            Player opponent = this.getOpponent(this.currentPlayer);
                            // force attack if there are active pokemons
                            if (this.currentPlayer.allIsGoodWithActivePokemon() &&
                                    opponent.allIsGoodWithActivePokemon()) {
                                System.out.println("Forcing a fight...");
                                gameOver = !this.fightMove();
                            }
                        }

                        break;
                    // exit
                    case 7:
                        exit = true;
                        break;
                }

                if (gameOver || nextPlayerMove) {
                    break;
                }
            } while (!exit);

            this.currentPlayer = this.getOpponent(this.currentPlayer);
        } while (!gameOver);


        Player winner = this.first.getActivePokemon().isDead() ? this.second :
                this.second.getActivePokemon().isDead() ? this.first : null;
        if (winner == null) {
            winner = this.first.getActivePokemon().getHp() > this.second.getActivePokemon().getHp() ?
                    this.first : this.second;
        }

        return winner;
    }

    private boolean fightMove() {
        Player opponent = this.getOpponent(this.currentPlayer);

        System.out.println("\n----------->    " + this.currentPlayer.getActivePokemon().getName() +
                " vs " + opponent.getActivePokemon().getName() + "    <-----------");
        this.currentPlayer.getActivePokemon().attack(opponent.getActivePokemon());
        if (opponent.getActivePokemon().isDead()) {
            System.out.println("Opponent's " + opponent.getActivePokemon().getName() +
                    " is defeated!");
        }
        System.out.println("Opponent's fighter:\n" + opponent.getActivePokemon());

        if (opponent.getActivePokemon().isDead()) {
            System.out.println("Searching for pokemon...");
            if (opponent.getBench().getCards().isEmpty()) {
                System.out.println("Taking from deck...");
                if (opponent.getDeck().hasPokemon()) {
                    Pokemon card = opponent.getDeck().findPokemon();
                    opponent.setActivePokemon(card);
                } else {
                    return false;
                }

                if (opponent.getActivePokemon().isDead()) {
                    // could not find a replacement pokemon, game over
                    System.out.println("Could not find a replacement Pokemon for " +
                            opponent.getName() + ", game over...");
                    return false;
                }
            } else {
                opponent.setActivePokemon(opponent.getBench().getCards()
                        .get(Utils.generateNumberInRange(0, opponent.getBench().getCards().size() - 1)));
                System.out.println("Updated active pokemon for " + opponent.getName() + " to " +
                        opponent.getActivePokemon().getName());
            }
        }

        return true;
    }

    private void drawCardMove() {
        Card card = this.currentPlayer.getDeck().drawCard();
        System.out.println("--> " + this.currentPlayer.getName() + " took " + card.getName());

        if (card.isEnergy() && (this.currentPlayer.getActivePokemon().getEnergy() == null ||
                !this.currentPlayer.getActivePokemon().getEnergy().hasEnergy())) {
            this.currentPlayer.getActivePokemon().setEnergy((Energy) card);
            System.out.println(this.currentPlayer.getActivePokemon().getName() + " got " +
                    card.getName());
        } else if (card.isPokemon() && !this.currentPlayer.getBench().isFull()) {
            this.currentPlayer.getBench().add((Pokemon) card);
            System.out.println("  --> " + this.currentPlayer.getName() + " added " +
                    card.getName() + " to bench");
        } else if (!card.isPokemon() && !card.isCandy()) {
            // add to prize
            this.currentPlayer.getPrize().add(card);
            System.out.println("  --> " + this.currentPlayer.getName() + " added " +
                    card.getName() + " to prize");
        } else if (card.isCandy()) {
            System.out.println("!!!!!!!!!!!");
            System.out.println(" --> " + this.currentPlayer.getName() +
                    " got a candy");
            if (this.currentPlayer.getActivePokemon() != null &&
                    this.currentPlayer.getActivePokemon().isBasic()) {
                this.currentPlayer.setActivePokemon(new EnlightenedPokemon());
                System.out.println("    --> " + this.currentPlayer.getName() + " now has " +
                        this.currentPlayer.getActivePokemon().getName());
                enlightenedPokemonCount++;
            }

            System.out.println("!!!!!!!!!!!");
        }

        System.out.println("Cards left in deck: " + this.currentPlayer.getDeck().getCards().size());
    }

    // helper methods

    private Player getStartingPlayer() {
        if (Utils.generateNumberInRange(0, 1) == 1) {
            return this.first;
        }

        return this.second;
    }

    private Player getOpponent(Player player) {
        return player == this.first ? this.second : this.first;
    }

    /**
     * Driver method.
     *
     * @param args is not used.
     */
    public static void main(String[] args) {
        Game game = new Game();
        Player winner = game.play();
        System.out.println("\n************************************\n" +
                "Game finished! Winner: " + winner.getName() +
                "\n************************************");
    }

    public int getFirstAttemptsToGetPokemon() {
        return firstAttemptsToGetPokemon;
    }

    public int getSecondAttemptsToGetPokemon() {
        return secondAttemptsToGetPokemon;
    }

    public int getEnlightenedPokemonCount() {
        return enlightenedPokemonCount;
    }
}