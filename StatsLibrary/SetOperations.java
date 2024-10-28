import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains methods for sets.
 */
public class SetOperations {
    /**
     * Runs the code.
     * @param args is not used.
     */
    public static void main(String[] args) {
        ArrayList<String> first = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Monday"));
        ArrayList<String> second = new ArrayList<>(Arrays.asList("Friday", "Tuesday",
                "Monday", "Sunday"));
        System.out.println("First List: " + first);
        System.out.println("Second List: " + second);

        // Monday, Tuesday, Friday, Sunday
        ArrayList<String> union = union(first, second);
        System.out.println("\nUnion: " + union);

        ArrayList<String> intersection = intersection(first, second);
        System.out.println("\nIntersection: " + intersection);

        ArrayList<String> allDays = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday", "Sunday"));
        System.out.println("\nFirst complement: " + complement(allDays, first));
        System.out.println("Second complement: " + complement(allDays, second));
    }

    /**
     * Unions two ArrayLists.
     * A union is a set of all unique elements from both lists.
     *
     * @param first the first list.
     * @param second the second list.
     * @return the union list.
     */
    public static ArrayList<String> union(ArrayList<String> first, ArrayList<String> second) {
        ArrayList<String> result = new ArrayList<>();

        // add elements from the first list
        for (int i = 0; i < first.size(); i++) {
            String element = first.get(i);
            if (!result.contains(element)) {
                result.add(element);
            }
        }
        // add elements from the second list
        for (int i = 0; i < second.size(); i++) {
            String element = second.get(i);
            if (!result.contains(element)) {
                result.add(element);
            }
        }

        return result;
    }

    /**
     * Creates an intersection of two lists.
     * Intersection contains the common elements that
     * are contained in both lists.
     *
     * @param first the first list.
     * @param second the second list.
     * @return the intersection list.
     */
    public static ArrayList<String> intersection(ArrayList<String> first, ArrayList<String> second) {
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < first.size(); i++) {
            String element = first.get(i);
            /*
             only add the element if it has not been added before
             and if both lists contain that element
             */
            if (!result.contains(element) && second.contains(element)) {
                result.add(element);
            }
        }

        return result;
    }

    /**
     * Finds the complement of the given subset.
     * A complement is a set of all elements, that are in the sample set,
     * but not in the subset.
     *
     * @param sample set with all available values.
     * @param subset the subset.
     * @return the complement of the subset.
     */
    public static ArrayList<String> complement(ArrayList<String> sample, ArrayList<String> subset) {
        ArrayList<String> result = new ArrayList<>();

        for (String element: sample) {
            if (!subset.contains(element)) {
                result.add(element);
            }
        }

        return result;
    }
}