package server;

public class Organisation {

    private final String name;
    private int budget;

    public Organisation(String name) {
        this(name, 0);
    }

    /**
     * Creates a new organisation
     * @param name
     * @param budget
     */

    public Organisation(String name, int budget) {
        this.name = name;
        this.budget = budget;
    }

    /**
     * Gets name of organisation
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     * Gets current budget of organisation
     * @return
     */

    public int getBudget() {
        return budget;
    }

    /**
     * Adds credits to organisation's budget
     * @param amount
     */

    public void addCredits(int amount) {
        this.budget = amount;
    }

    /**
     * Removes credits from the organisation's budget as long as its able to.
     * @param amount
     */

    /*TODO
        Maybe change this to a max function.
        So instead of rejecting a call because its unable to remove the whole amount,
        it instead removes what it can.
     */

    public void removeCredits(int amount) {
        if (budget < amount) {
            return;
        }
        this.budget -= amount;
    }

}
