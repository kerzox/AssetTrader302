package server;

import java.util.HashMap;

public class Organisation {

    private String name;
    private int budget;

    /**
     * Default budget constructor
     * @param name
     */
    public Organisation(String name) {
        this(name, 0);
    }

    /**
     * Constructor of organisation object
     * @param name
     * @param budget
     * @throws BudgetException when budget is given negative.
     */
    public Organisation(String name, int budget) throws BudgetException {
        if (budget < 0) {
            throw new BudgetException("Budget cannot be negative");
        }
        this.name = name;
        this.budget = budget;
    }

    /**
     * Adds to organisation budget
     * @param budget
     * @throws BudgetException when added funds are negative
     */
    public void addBudget(int budget) throws BudgetException {
        if (budget < 0) {
            throw new BudgetException("Added funds can not be negative");
        }
        this.budget += budget;
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
     * Subtracts budget of organisation
     * @param value
     * @throws BudgetException when subtracted funds are negative or if it is greater
     *                         than the initial budget
     */
    public void subtractBudget(int value) throws BudgetException {
        if (value < 0) {
            throw new BudgetException("Value cannot be negative");
        }
        if (value > budget) {
            throw new BudgetException("Value cannot be greater than budget");
        }
        budget = budget - value;
    }
}
