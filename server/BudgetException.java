package server;

public class BudgetException extends RuntimeException {
    public BudgetException(String error) {
        super(error);
    }
}
