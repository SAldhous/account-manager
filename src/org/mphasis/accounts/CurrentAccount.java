package org.mphasis.accounts;

public class CurrentAccount extends Account {
    private double overdraft = 200.0;

    public CurrentAccount(long id, int pin) {
        super(id, pin);
        withdrawLimit = -overdraft;
    }
    
    public CurrentAccount(long id, int pin, double overdraft) {
        super(id, pin);
        this.overdraft = overdraft;
        withdrawLimit = -overdraft;
    }

    public double getOverdraft() {
        return overdraft;
    }

    @Override
    public boolean canClose() {
        if (getBalance() >= 0) {
            return true;
        }

        return false;
    }
    
    public void update() {
        String message = "The account is currently not overdrawn.";

        double balance = getBalance();
        if (balance < 0) {
            if (balance < 0 && balance > withdrawLimit) {
                message = "The account is currently overdrawn and must be paid!";
            }
        }

        addMessage(message);
    }
}
