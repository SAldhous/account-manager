package org.mphasis.accounts;

import java.util.ArrayList;
import java.util.List;

public class Account implements Messagable {
	
	private double bal; // The current balance
    protected double withdrawLimit; // The limit to which you can withdraw money
    private long accnum; // The account number

    private int pin; // The account pin code

    // A series of messages sent to the account including managment, deposits, and withdrawals
    protected List<AccountMessage> messages = new ArrayList<AccountMessage>();

    public Account(long id, int pin) {
        bal = 0.0;
        withdrawLimit = 0.0;
        accnum = id;
        this.pin = pin;
    }

    public boolean canClose() { return true; }

    public boolean checkPin(int input) {
        return pin == input;
    }

    public synchronized boolean deposit(double sum) {
        sum = Utility.roundToPenny(sum);

        if (sum > 0) {
            bal += sum;
            bal = Utility.roundToPenny(bal);

            addMessage("£" + sum + " has been deposited into your account.");

            return true;
        }
        
        return false;
    }

    // Withdraw method taking into account withdrawal limits (changed by overdraft)
    public synchronized boolean withdraw(double sum) {
        sum = Utility.roundToPenny(sum);

        if (sum > 0) {
            if (bal - sum >= withdrawLimit) {
                bal -= sum;
                bal = Utility.roundToPenny(bal);

                addMessage("£" + sum + " has been withdrawn from your account.");

                return true;
            }
        }

        return false;
    }

    public synchronized double getBalance() {
        return bal;
    }

    public long getAccountNumber() {
        return accnum;
    }

    @Override
    public synchronized void addMessage(String message) {
        messages.add(new AccountMessage(message));
    }

    public void update() {
        return;
    }

    // Get a collection of messages to display to the user
    public synchronized String getMessages() {
        String output = "";
        for (int i = 0; i < messages.size(); i++) {
            output += "\n" + messages.get(i).toString();
        }

        return output;
    }

    public synchronized String toString() {
        return "Acc " + accnum + ": " + "balance = " + getBalance();
    }

    public final void print() {
        // Don't override this,
        // override the toString method
        System.out.println(toString());
    }
}
