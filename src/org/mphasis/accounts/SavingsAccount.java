package org.mphasis.accounts;

public class SavingsAccount extends Account {
    private double interestRate = 0.05;

    public SavingsAccount(long id, int pin) {
        super(id, pin);
    }
    public SavingsAccount(long id, int pin, double interestRate) {
        super(id, pin);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public boolean canClose() { return true; }

    public void addInterest() {
        double oldBalance = getBalance();
        double interest = oldBalance * this.interestRate;
        double roundedInterest = Utility.roundToPenny(interest);
        deposit(roundedInterest);

        double rate = Utility.roundToPenny(interestRate * 100.0);
        addMessage("£" + roundedInterest + " (" + rate + "% on £" + oldBalance + ") interest has been added to your account.");
    }

    @Override
    public void update() {
        addInterest();
    }
}
