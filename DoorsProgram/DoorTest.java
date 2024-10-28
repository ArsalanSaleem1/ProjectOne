import java.util.Random;

/**
 * Contains the Monte Carlo simulation that
 * compares the outcomes for two strategies in the doors game.
 *
 * Answers:
 *
 * a. If the contestant selects a door at random, the probability
 * that the contestant selects the winning door is 1/number of doors.
 * If there are 3 doors, that probability is 1/3 ~ 33%
 *
 * b. Switching to another curtain/door would maximize the contestant's probability
 * of winning.
 *
 * i. If the contestant sticks to their original choice, they still have 1/3 probability of
 * winning (1/number of doors), because they didn't act on the new information.
 *
 * ii. If the contestant switches to the remaining unopened curtain/door, while
 * having originally chosen the winning door G, they would lose, which would happen in 1/3
 * of the cases.
 *
 * iii. If the contestant switches after having originally selected one of the losing/dud doors,
 * they would win
 *
 * iv. Originally, the contestant had the probability of losing being equal to 2/3,
 * because likely the winning door was among the not selected ones. Therefore, after one of the 2 remaining
 * doors has been opened the chances of the remaining unopened door having the prize are 2/3, so the contestant
 * would win with the probability of 2/3 ~ 66%
 *
 * v. Switching maximizes the contestant's probability of winning.
 *
 * The best way to illustrate this would be to pick a very high number of doors.
 * If there is a thousand of doors, the chances of the contestant picking the correct door are 1/1,000.
 * if the host eliminates 998 unopened doors, leaving only one other door closed, it becomes obvious
 * that it's very likely that the only door the host refused to open has the prize.
 *
 * My code allows to tweak the number of doors as well as the number of runs.
 *
 * If the user selects 3 doors, the displayed odds of winning would be 33% for a contestant
 * that doesn't switch and 66% for a contestant that switches.
 *
 * If the user selects 10 doors, the odds become 9% and 90%.
 *
 * If the user selects 100 doors, the odds become 1% and 99%.
 *
 */
public class DoorTest {
    private static final int RUNS = 10_000;
    private static final int DOORS = 3;
    private static final Random random = new Random();

    /**
     * Starts the program.
     * @param args is not used.
     */
    public static void main(String[] args) {
        // simulate without changing the door
        double notChangingDoorResult = simulate(RUNS, DOORS, false);
        // simulate with changing the door
        double changingDoorResult = simulate(RUNS, DOORS, true);
        // display the results
        System.out.println("Wins (the door is not changed): " + notChangingDoorResult);
        System.out.println("Wins (the door is changed): " + changingDoorResult);
    }

    /**
     * Simulates the contest for the given number of runs and doors.
     * @param numberOfRuns the number of runs.
     * @param numberOfDoors the number of doors.
     * @param changeDoor the player's strategy.
     * @return the odds of winning.
     */
    private static double simulate(int numberOfRuns, int numberOfDoors, boolean changeDoor) {
        double countWins = 0;

        for (int i = 0; i < numberOfRuns; i++) {
            Door[] doors = generateDoors(numberOfDoors);
            // the initial pick
            int pickedDoorIndex = generateNumberInRange(0, doors.length - 1);
            int doorToKeepClosedIndex;
            if (doors[pickedDoorIndex].isWin()) {
                // if the player has picked a winning door, choose a random losing door to keep closed
                do {
                    doorToKeepClosedIndex = generateNumberInRange(0, doors.length - 1);
                } while (doorToKeepClosedIndex == pickedDoorIndex);
            } else {
                // the player picked a losing door, so the remaining closed door should be the winning door
                doorToKeepClosedIndex = -1;
                for (int j = 0; j < doors.length; j++) {
                    if (doors[j].isWin()) {
                        // it's a winning door, keep it closed
                        doorToKeepClosedIndex = j;
                        break;
                    }
                }
            }

            // the game show host opens another closed door
            for (int j = 0; j < doors.length; j++) {
                Door door = doors[j];

                // open all the losing doors, so that only the winning door and the picked door remain
                if (j != pickedDoorIndex && j != doorToKeepClosedIndex) {
                    door.setOpened(true);
                }
            }

            // change the door
            if (changeDoor) {
                // pick the other remaining door
                pickedDoorIndex = doorToKeepClosedIndex;
            }

            // check if the contestant has won
            if (doors[pickedDoorIndex].isWin()) {
                countWins++;
            }
        }

        return countWins / numberOfRuns;
    }

    /**
     * Generates the given number of doors.
     * @param numberOfDoors number of doors.
     * @return the array with doors.
     */
    private static Door[] generateDoors(int numberOfDoors) {
        Door[] doors = new Door[numberOfDoors];
        int winDoorIndex = generateNumberInRange(0, doors.length - 1);
        for (int i = 0; i < doors.length; i++) {
            // all doors are closed and have no prize by default
            Door door = new Door();
            doors[i] = door;

            if (i == winDoorIndex) {
                door.setWin(true);
            }
        }

        return doors;
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