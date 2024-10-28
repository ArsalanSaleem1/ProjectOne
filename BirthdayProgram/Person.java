/**
 * Represents a person with a birthday.
 */
public class Person {
    /**
     * Birthday date
     */
    private int month;
    private int day;

    /**
     * Constructor.
     * @param month birthday month.
     * @param day birthday year.
     */
    public Person(int month, int day) {
        this.month = month;
        this.day = day;
    }

    /**
     * Returns birthday month.
     * @return birthday month.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns birthday day.
     * @return birthday day.
     */
    public int getDay() {
        return day;
    }
}