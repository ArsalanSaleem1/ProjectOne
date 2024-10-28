import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * Is the stats library class.
 */
public class Stats {
    // constants
    private static Random random = new Random();
    private static final int MIN_SIZE = 5;
    private static final int MAX_SIZE = 10;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 100;

    /**
     * Runs the code.
     * @param args is not used.
     */
    public static void main(String[] args) {
        ArrayList<Integer> list = randomList();
        System.out.println("List: " + list);

        double mean = mean(list);
        System.out.println("Mean: " + mean);

        double median = median(list);
        System.out.println("Median: " + median);

        Integer mode = mode(list);
        System.out.println("Mode: " + mode);

        double standardDeviation = standardDeviation(list);
        System.out.println("Standard Deviation: " + standardDeviation);

        testIntersections();
        testUnion();
        testBayes();
        testDependency();
        testPermutations();
        testCombinations();

        testBinomial();
        testGeometric();

        solveTextProblem();
    }

    /**
     * Finds the mean of the list of elements.
     * The mean is the average of all elements.
     * @param list the elements.
     * @return the mean.
     */
    public static Double mean(ArrayList<Integer> list) {
        if (list.isEmpty()) {
            return null;
        }

        // calculate the sum
        double sum = 0;
        for (int number: list) {
            sum += number;
        }

        // divide by the number of elements in the list
        return sum / list.size();
    }

    /**
     * Finds the median of the list of elements.
     * Median is the middle element of a sorted list.
     * If the list has an even number of elements, median
     * is the average of the two middle elements.
     *
     * @param list the elements.
     * @return the median.
     */
    public static Double median(ArrayList<Integer> list) {
        if (list.isEmpty()) {
            return null;
        }

        // sort the list
        bubbleSort(list);

        int size = list.size();
        // the list has an odd number of elements
        if (size % 2 == 1) {
            // return the middle element
            return Double.valueOf(list.get(size / 2));
        } else {
            // the list has an even number of elements
            double first = list.get((size / 2) - 1);
            double second = list.get(size / 2);
            // return the average of the two middle elements
            return (first + second) / 2d;
        }
    }

    /**
     * Sorts the list using bubble sort algorithm.
     * @param list the list to sort.
     */
    public static void bubbleSort(ArrayList<Integer> list) {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;

            for (int j = 0; j < list.size() - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    // swap elements
                    int temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    isSorted = false;
                }
            }
        }
    }


    /**
     * Finds the mode of the list of elements.
     * Mode is a most frequent element. If all elements are unique, there is no mode.
     * @param list the elements.
     * @return the mode.
     */
    public static Integer mode(ArrayList<Integer> list) {
        if (list.isEmpty()) {
            return null;
        }

        // calculate the frequencies for each element
        int[] frequencies = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            frequencies[i] = 1;

            for (int j = 0; j < list.size(); j++) {
                if (i != j && list.get(i) == list.get(j)) {
                    frequencies[i]++;
                }
            }
        }

        // find the maximum frequency and its corresponding element
        int maxFrequency = 0;
        int maxFrequencyIndex = -1;
        for (int i = 0; i < frequencies.length; i++) {
            int frequency = frequencies[i];
            if (frequency != 0 && (frequency > maxFrequency || maxFrequencyIndex == -1)) {
                maxFrequencyIndex = i;
                maxFrequency = frequency;
            }
        }

        if (maxFrequencyIndex == -1) {
            return null;
        }

        if (maxFrequency == 1 && list.size() > 1) {
            // all elements are unique and the list has more than one element
            return null;
        }

        return list.get(maxFrequencyIndex);
    }

    /**
     * Finds the standard deviation of the list of elements.
     * @param list the elements.
     * @return the standard deviation.
     */
    public static Double standardDeviation(ArrayList<Integer> list) {
        if (list.isEmpty()) {
           return null;
        }

        // calculate the mean
        double mean = mean(list);

        // calculate the squared sum of the differences between the mean and each element
        double differences = 0;
        for (int element: list) {
            differences += Math.pow(element - mean, 2);
        }
        differences /= list.size();

        // calculate the standard deviation
        return Math.sqrt(differences);
    }

    // formulas

    // intersections

    /**
     * Calculates the likelihood of two independent events
     * occurring simultaneously.
     * @param first the probability of the first event.
     * @param second the probability of the second event.
     * @return the probability of two events happening at the same time.
     */
    public static double calculateIndependentIntersection(double first, double second) {
        return first * second;
    }

    /**
     * Calculates the likelihood of two dependent events
     * occurring simultaneously.
     * @param first the probability of the first event.
     * @param secondGivenFirst the probability of the second event given
     * that the first event has occurred.
     * @return the probability of two events happening at the same time.
     */
    public static double calculateDependentIntersection(double first, double secondGivenFirst) {
        return first * secondGivenFirst;
    }

    // unions

    /**
     * Calculates the probability of at least one of the two
     * mutually exclusive events happening.
     * @param first the probability of the first event.
     * @param second the probability of the second event.
     * @return the probability of one of the events happening.
     */
    public static double calculateExclusiveUnion(double first, double second) {
        return first + second;
    }

    /**
     * Calculates the probability of at least one of the two
     * non-exclusive events happening.
     * @param first the probability of the first event.
     * @param second the probability of the second event.
     * @param intersection the probability of the two events happening at the same time.
     * @return the probability of one of the events happening.
     */
    public static double calculateNonExclusiveUnion(double first, double second, double intersection) {
        return first + second - intersection;
    }

    /**
     * Uses Bayes' Theorem to calculate the probability of the first event
     * occurring given that the second event has already occurred.
     * @param first the probability of the first event.
     * @param second the probability of the second event.
     * @param secondGivenFirst the probability of the second event given the
     * first event has already occurred.
     * @return the probability of the first event given the second event has already occurred.
     */
    public static double calculateFirstGivenSecond(double first, double second, double secondGivenFirst) {
        return (secondGivenFirst * first) / second;
    }


    // methods to determine independence or dependency

    /**
     * Checks whether the given two events are dependent.
     * @param first the probability of the first event.
     * @param second the probability of the second event.
     * @param intersection the probability of the two events happening at the same time.
     * @return true if the two events are dependent, false otherwise.
     */
    public static boolean areDependent(double first, double second, double intersection) {
        double independentIntersection = calculateIndependentIntersection(first, second);
        // it should not be equal to the intersection of independent events
        boolean areEqual = Math.abs(independentIntersection - intersection) < 0.02;
        return !areEqual;
    }

    /**
     * Checks whether the given two events are independent.
     * @param first the probability of the first event.
     * @param second the probability of the second event.
     * @param intersection the probability of the two events happening at the same time.
     * @return true if the two events are independent, false otherwise.
     */
    public static boolean areIndependent(double first, double second, double intersection) {
        return !areDependent(first, second, intersection);
    }

    // combinations and permutations

    /**
     * Calculates a factorial of a number recursively.
     * @param n the number.
     * @return the factorial of the number.
     */
    public static BigInteger factorial(int n) {
        // base case
        if (n == 0 || n == 1) {
            return BigInteger.valueOf(1);
        }

        return BigInteger.valueOf(n).multiply(factorial(n - 1));
    }

    /**
     * Calculates the number of ways the given number of elements
     * could be taken out of the total number of elements, in a unique order.
     *
     * Thus, a sequence of elements, taken out in different order constitutes a
     * new permutation.
     *
     * @param totalElements the total number of elements.
     * @param elementsToTake the number of elements we want to take.
     * @return the number of permutations.
     */
    public static BigInteger permutations(int totalElements, int elementsToTake) {
        if (elementsToTake > totalElements) {
            return BigInteger.valueOf(0);
        }

        return factorial(totalElements).divide(factorial(totalElements - elementsToTake));
    }

    /**
     * Calculates the probability of the given number of attempts yielding exactly
     * the given number of successes.
     * @param attempts the number of attempts.
     * @param successes the number of successes.
     * @param successProbability the probability of success on a single attempt.
     * @return the probability of getting the given number of successes.
     */
    public static double calculateBinomialProbability(int attempts, int successes, double successProbability) {
        double failureProbability = 1d - successProbability;
        BigInteger numberOfCombinations = combinations(attempts, successes);
        return numberOfCombinations.doubleValue() * Math.pow(successProbability, successes) * Math.pow(failureProbability, attempts - successes);
    }

    /**
     * Calculates the probability of getting success at the end of the series of attempts.
     * @param attempts the number of attempts.
     * @param successProbability the probability of success on a single attempt.
     * @return the probability of getting success on the last attempt.
     */
    public static double calculateGeometricProbability(int attempts, double successProbability) {
        double failureProbability = 1d - successProbability;
        return Math.pow(failureProbability, attempts - 1d) * successProbability;
    }

    /**
     * Calculates the number of ways the given number of elements
     * could be taken out of the total number of elements, in any particular order.
     *
     * Therefore, the same elements taken out in a different order would
     * be considered the same combination.
     *
     * @param totalElements the total number of elements.
     * @param elementsToTake the number of elements we want to take.
     * @return the number of permutations.
     */
    public static BigInteger combinations(int totalElements, int elementsToTake) {
        if (elementsToTake > totalElements) {
            return BigInteger.valueOf(0);
        }

        return factorial(totalElements).divide(
                factorial(elementsToTake).multiply(factorial(totalElements - elementsToTake)));
    }

    // test the methods

    /**
     * Tests the intersection method.
     */
    public static void testIntersections() {
        // independent events
        double rich = generateNumberInRange(1, 20) / 100d;
        double likesOrangeColor = generateNumberInRange(10, 40) / 100d;
        double richAndLikesOrangeColor = calculateIndependentIntersection(rich, likesOrangeColor);
        System.out.println("\nIndependent Probabilities:\n" +
                "Rich: " + rich +
                "\nLiked orange color: " + likesOrangeColor +
                "\nRich and likes orange color: " + richAndLikesOrangeColor);

        // dependent events
        double ceoGivenRich = generateNumberInRange(40, 70) / 100d;
        double richAndCeo = calculateDependentIntersection(rich, ceoGivenRich);
        System.out.println("\nDependent Probabilities:\n" +
                "Rich: " + rich +
                "\nCEO, given Rich: " + ceoGivenRich +
                "\nRich and CEO: " + richAndCeo);
    }

    /**
     * Tests the union method.
     */
    public static void testUnion() {
        // mutually exclusive events
        double hasOrangeCat = generateNumberInRange(20, 30) / 100d;
        double hasBlackCat = generateNumberInRange(40, 50) / 100d;
        double hasOrangeOrBlackCat = calculateExclusiveUnion(hasOrangeCat, hasBlackCat);
        System.out.println("\nUnion of exclusive events:\n" +
                "Has orange cat: " + hasOrangeCat +
                "\nHas black cat: " + hasBlackCat +
                "\nHas orange or black cat: " + hasOrangeOrBlackCat);

        // non-exclusive events
        double likesCats = generateNumberInRange(10, 30) / 100d;
        double likesDogs = generateNumberInRange(40, 50) / 100d;
        double likesCatsAndDogs = generateNumberInRange(5, 10) / 100d;
        double likesCatsOrDogs  = calculateNonExclusiveUnion(likesCats, likesDogs, likesCatsAndDogs);
        System.out.println("\nUnion of non-exclusive events:\n" +
                "Likes cats: " + hasOrangeCat +
                "\nLikes dogs: " + hasBlackCat +
                "\nLikes cats and dogs: " + likesCatsAndDogs +
                "\nLikes cats or dogs: " + likesCatsOrDogs);
    }

    /**
     * Tests the Bayes' Theorem method.
     */
    private static void testBayes() {
        double rich = generateNumberInRange(5, 10) / 100d;
        double ceo = generateNumberInRange(10, 15) / 100d;
        double ceoGivenRich = generateNumberInRange(20, 50) / 100d;
        double richGivenCeo = calculateFirstGivenSecond(rich, ceo, ceoGivenRich);

        System.out.println("\nBayes' Theorem:");
        System.out.println("Rich: " + rich +
                "\nCEO: " + ceo +
                "\nCEO given Rich: " + ceoGivenRich +
                "\nRich given CEO: " + richGivenCeo);
    }

    /**
     * Tests the dependency method.
     */
    private static void testDependency() {
        double hasCar = generateNumberInRange(30, 90) / 100d;
        double likesSwimming = generateNumberInRange(20, 40) / 100d;
        double hasCarAndLikesSwimming = hasCar * likesSwimming;
        System.out.println("\nChecks dependency of events:");
        System.out.println("Has car: " + hasCar +
                "\nLikes swimming: " + likesSwimming +
                "\nHas car and likes swimming: " + hasCarAndLikesSwimming +
                "\nAre dependent: " + areDependent(hasCar, likesSwimming, hasCarAndLikesSwimming));

        double likesSport = generateNumberInRange(30, 90) / 100d;
        double isHealthy = generateNumberInRange(20, 50) / 100d;
        double likesSportAndIsHealthy =  generateNumberInRange(50, 70) / 100d;
        System.out.println("\nLikes sport: " + likesSport +
                "\nIs healthy: " + isHealthy +
                "\nLikes sport and is healthy: " + likesSportAndIsHealthy +
                "\nAre dependent: " + areDependent(likesSport, isHealthy, likesSportAndIsHealthy));
    }

    /**
     * Tests the permutations method.
     */
    public static void testPermutations() {
        int cats = generateNumberInRange(30, 100);
        int catsToTake = generateNumberInRange(1, cats - 1);
        // order matters
        System.out.println("\nPermutations:" +
                "\nTotal number of cats: " + cats +
                "\nCats to give to students/number of students: " + catsToTake +
                "\nPermutations (a number of ways to distribute cats across students): " + permutations(cats, catsToTake));
    }

    /**
     * Tests the combinations method.
     */
    public static void testCombinations() {
        int dinners = generateNumberInRange(30, 100);
        int dinnersToTake = generateNumberInRange(1, dinners - 1);
        System.out.println("\nCombinations:" +
                "\nNumber of available grocery items: " + dinners +
                "\nItems to buy: " + dinnersToTake +
                "\nCombinations (a number of ways to " +
                "buy grocery items): " + combinations(dinners, dinnersToTake));
    }

    /**
     * Tests the binomial probability method.
     */
    public static void testBinomial() {
        double lotteryWinChance = generateNumberInRange(1, 5) / 100d;
        int attempts = generateNumberInRange(5, 100);
        int successes = generateNumberInRange(1, 5);
        System.out.println("\nBinomial Probability:" +
                "\nChance of winning a lottery: " + lotteryWinChance +
                "\nChance of getting " + successes + " success(es) in " + attempts + " attempts: " +
                calculateBinomialProbability(attempts, successes, lotteryWinChance));
    }

    /**
     * Tests the geometric probability method.
     */
    public static void testGeometric() {
        int attempts = generateNumberInRange(5, 100);
        double successChance = generateNumberInRange(1, 5) / 100d;
        System.out.println("\nGeometric Probability:" +
                "\nSuccess chance: " + successChance +
                "\nAttempts: " + attempts +
                "\nChance of getting a success at the end of the series of attempts: " +
                calculateGeometricProbability(attempts, successChance));
    }

    /**
     * Solves problem from the text.
     */
    public static void solveTextProblem() {
        System.out.println("\nAirline problem");
        int flightsFromNewYorkToCalifornia = 6;
        int flightsFromCaliforniaToHawaii = 7;
        System.out.println("Answer: " + permutations(flightsFromNewYorkToCalifornia, 1)
                .multiply(permutations(flightsFromCaliforniaToHawaii, 1)) + " flights");

        System.out.println("\nAssembly problem");
        int numberOfSteps = 3;
        System.out.println("Answer: " + factorial(numberOfSteps));
    }


    // helper methods

    /**
     * Generates a list of random size with random elements.
     * @return the random list.
     */
    private static ArrayList<Integer> randomList() {
        int size = generateNumberInRange(MIN_SIZE, MAX_SIZE);
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(generateNumberInRange(MIN_NUMBER, MAX_NUMBER));
        }

        return result;
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