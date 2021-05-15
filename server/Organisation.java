package server;

import java.util.HashMap;

public class Organisation {

    private String name;
    private int budget;

    /**
     * Constructor
     * @param name
     * @param budget
     */
    public Organisation(String name, int budget) {
        this.name = name;
        this.budget = budget;
    }

    /**
     * retrieve name of organisation
     * @return name
     */
    public String getName() { return name; }

    /**
     * retrieve total budget
     * @return budget
     */
    public int getBudget() { return budget; }

    /**
     * Subtracts value from budget
     * @param value
     */
    public void subtractBudget(int value) {
        budget = budget - value;
    }


}
