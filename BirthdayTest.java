import java.util.Random;
import java.util.Scanner;

/**
 * Contains the Monte Carlo simulation that
 * helps to determine the probability of any 2 people
 * sharing a birthday in the class.
 */
public class BirthdayTest{
    /**
     * Constants
     */

    // getting user input
    private static final Scanner scanner = new Scanner(System.in);

    // generating random data
    private static final Random random = new Random();
    // generating months
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;
    // generating days
    private static final int MIN_DAY = 1;
    // number of people
    private static final int MIN_PEOPLE = 1;
    // so that people would fit into an array
    private static final int MAX_PEOPLE = Integer.MAX_VALUE - 1;
    // number of runs
    private static final int MIN_RUNS = 1;
    private static final int MAX_RUNS = Integer.MAX_VALUE;
    // for determining when to exit the program
    private static final String QUIT_YES = "y";
    private static final String QUIT_NO = "n";

    /**
     * Starts the program.
     * @param args is not used.
     */
    public static void main(String[] args) {
        boolean quit;
        do {
            // create the people
            int numberOfPeople = getNumberOfPeople();

            // determine number of runs
            int numberOfRuns = getNumberOfRuns();
            double probability = simulate(numberOfPeople, numberOfRuns);
            System.out.println("The probability of any two people " +
                    "sharing a birthday is " + probability);

            quit = getQuit();
        } while (!quit);
    }

    /**
     * Starts Monte Carlo simulation.
     * @param numberOfPeople the total number of people.
     * @param numberOfRuns the total number of runs.
     * @return the probability of any two people
     * sharing a birthday.
     */
    private static double simulate(int numberOfPeople, int numberOfRuns) {
        // keep track of the number of successful simulations
        double sharedBirthdayCount = 0;

        // simulate this many times
        for (int i = 0; i < numberOfRuns; i++) {
            // generate new people with random birthdays
            Person[] people = createPeople(numberOfPeople);
            // check if at least two people share a birthday
            boolean success = existDuplicateBirthdays(people);
            if (success) {
                sharedBirthdayCount++;
            }
         }

        // divide the number of successful simulations by the total number of simulations
        return sharedBirthdayCount / numberOfRuns;
    }

    /**
     * Checks if at least two people share a birthday.
     * @param people all people.
     * @return true if there are people with the same birthday.
     */
    private static boolean existDuplicateBirthdays(Person[] people) {
        for (int i = 0; i < people.length; i++) {
            Person first = people[i];

            /*
             only compare with people that come after this person in the array,
             because the current first person has already been compared
             with people before it in the array
             */
            for (int j = i + 1; j < people.length; j++) {
                Person second = people[j];

                if (first.getDay() == second.getDay() &&
                        first.getMonth() == second.getMonth()) {
                    // duplicate birthday found
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Generates the given number of people.
     * @param numberOfPeople the number of people to generate.
     * @return an array with People with random birthdays.
     */
    private static Person[] createPeople(int numberOfPeople) {
        Person[] people = new Person[numberOfPeople];
        for (int i = 0; i < people.length; i++) {
            int month = randomMonth();
            int day = randomDay(month);
            people[i] = new Person(month, day);
        }

        return people;
    }

    // get user input

    /**
     * Returns the number of people provided by the user.
     * @return the number of people.
     */
    private static int getNumberOfPeople() {
        boolean isValid;
        int numberOfPeople = MIN_PEOPLE;
        do {
            isValid = true;

            System.out.print("Enter the number of people: ");
            String input = scanner.nextLine();
            try  {
                numberOfPeople = Integer.parseInt(input);
                if (numberOfPeople < MIN_PEOPLE) {
                    System.out.println("The number of people can't " +
                            "be less than " + MIN_PEOPLE + "!");
                    isValid = false;
                } else if (numberOfPeople > MAX_PEOPLE) {
                    System.out.println("The number of people can't " +
                            "be less than " + MAX_PEOPLE + "!");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input is not numeric!");
                isValid = false;
            }
        } while (!isValid);

        return numberOfPeople;
    }

    /**
     * Returns the number of runs provided by the user.
     * @return the number of runs.
     */
    private static int getNumberOfRuns() {
        boolean isValid;
        int numberOfRuns = MIN_RUNS;
        do {
            isValid = true;

            System.out.print("Enter the number of runs: ");
            String input = scanner.nextLine();
            try  {
                numberOfRuns = Integer.parseInt(input);
                if (numberOfRuns < MIN_RUNS) {
                    System.out.println("The number of runs can't " +
                            "be less than " + MIN_RUNS + "!");
                    isValid = false;
                } else if (numberOfRuns > MAX_RUNS) {
                    System.out.println("The number of runs can't " +
                            "be less than " + MAX_RUNS + "!");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input is not numeric!");
                isValid = false;
            }
        } while (!isValid);

        return numberOfRuns;
    }

    /**
     * Asks the user if they want to quit.
     * @return true if the program should exit.
     */
    private static boolean getQuit() {
        boolean isValid;
        boolean quit = false;
        do {
            isValid = true;
            System.out.print("Try again? (y/n): ");
            String input = scanner.nextLine().toLowerCase();
            if (!input.equals(QUIT_YES) && !input.equals(QUIT_NO)) {
                isValid = false;
                System.out.println("Invalid input! Enter 'y' or 'n'!");
            } else {
                quit = input.equals(QUIT_NO);
            }
        } while (!isValid);

        return quit;
    }


    // generate random birthdays

    /**
     * Returns a random day.
     * @param month month (1-based).
     * @return the random day.
     */
    private static int randomDay(int month) {
        int maxDay = numberOfDaysInMonth(month);
        return generateNumberInRange(MIN_DAY, maxDay);
    }

    /**
     * Returns a random month (1-based).
     * @return the random month.
     */
    private static int randomMonth() {
        return generateNumberInRange(MIN_MONTH, MAX_MONTH);
    }

    /**
     * Returns the number of days in the given month.
     * @param month the month (1-based).
     * @return the number of days (assuming a non-leap year).
     */
    private static int numberOfDaysInMonth(int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return 28;
            default:
                return 0;
        }
    }

    /**
     * Generates a random number in the given range.
     * @param min lower bound.
     * @param max upper bound.
     * @return the random number.
     */
    private static int generateNumberInRange(int min, int max) {
        int number = random.nextInt(max - min + 1); // [0, max - min]
        number += min;  // [min, max]
        return number;
    }
}