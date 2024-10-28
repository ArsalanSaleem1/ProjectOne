package pokemonapp.game;

import pokemonapp.player.Player;

import java.util.Scanner;

public class Talker {
    private final Scanner scanner = new Scanner(System.in);

    public String getPlayerName(String message, String... takenNames) {
        boolean isValid;
        String name;
        do {
            isValid = true;

            name = input("Enter the " + message + " player's name: ");
            for (String s : takenNames) {
                if (s.equalsIgnoreCase(name)) {
                    isValid = false;
                }
            }
        } while (!isValid);

        return name;
    }


    public int actions(Player player) {
        int choice = number("\nYour turn, " + player.getName() +
                ", choose your action:" +
                "\n1. Attack" +
                "\n2. View Active Pokemon" +
                "\n3. View Bench" +
                "\n4. View Prize" +
                "\n5. Change Active Pokemon" +
                "\n6. Draw card" +
                "\n7. Finish move" +
                "\nYour choice: ", 1, 2, 3, 4, 5, 6, 7);
        return choice;
    }

    // helper methods

    public String input(String message) {
        boolean isValid;
        String input;
        do {
            isValid = true;

            System.out.print(message);
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Can't be empty!");
                isValid = false;
            }
        } while (!isValid);

        return input;
    }

    public int number(String message) {
        boolean isValid;
        int number = 0;
        do {
            isValid = true;

            String input = input(message);
            try {
                number = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Not a number!");
                isValid = false;
            }
        } while (!isValid);

        return number;
    }

    public int number(String message, int... options) {
        do {
            int number = number(message);
            for (int o : options) {
                if (number == o) {
                    return number;
                }
            }

            System.out.println("Invalid input!");
        } while (true);
    }

    public int numberRange(String message, int min, int max) {
        do {
            int number = number(message);

            if (number >= min && number <= max) {
                return number;
            }

            System.out.println("Invalid input!");
        } while (true);
    }
}
