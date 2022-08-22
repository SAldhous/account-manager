package org.mphasis.accounts;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

// Bank operates as a singleton (only 1 instance (static) should exist)
public class Bank implements Runnable {
    private static Bank bank;
    public static Bank Instance() {
        if (bank == null) {
            bank = new Bank();
        }

        return bank;
    }

    private HashMap<Long, Account> accounts;
    private Instant lastUpdate;
    private boolean running = true;

    private Bank() {
        accounts = new HashMap<Long, Account>();
        
        // Initialise some debug accounts
        Account[] newAccounts = {
            new SavingsAccount(10071234, 1234),
            new CurrentAccount(10075678, 1234),
            new Account(10076666, 1234),
            new SavingsAccount(10079999, 1234),
            new CurrentAccount(10071111, 1234),
            new CurrentAccount(10072222, 1234)
        };

        for (int i = 0; i < newAccounts.length; i++) {
            accounts.put(newAccounts[i].getAccountNumber(), newAccounts[i]);
        }

        lastUpdate = Instant.now();
    }

    public boolean accountExists(long accountID) {
        return accounts.containsKey(accountID);
    }
    // Verify the pin entered against the account it is entered for
    public boolean pinCheck(long accountID, int pin) {
        return accounts.get(accountID).checkPin(pin);
    }

    public synchronized void close() {
        running = false;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized Account getAccount(long accountID) {
        return accounts.get(accountID);
    }

    // Create a new account it generated ID and return it
    public synchronized Account openAccount(String type, int pin) {
        if (accounts.size() >= 89999999) {
            return null;
        }

        Random rng = new Random();
        long number = Math.abs(rng.nextLong(10000000, 99999999));

        if (!accounts.containsKey(number))
            return genAccount(type, number, pin);

        while (accounts.containsKey(number)) {
            number = Math.abs(rng.nextLong(10000000, 99999999));
            
            return genAccount(type, number, pin);
        }

        return null;
    }

    // Generate a new account type based on case
    public synchronized Account genAccount(String type, long id, int pin) {
        switch (type) {
            case "REGULAR":
                accounts.put(id, new Account(id, pin));
                return getAccount(id);
            case "CURRENT":
                accounts.put(id, new CurrentAccount(id, pin));
                return getAccount(id);
            case "SAVINGS":
                accounts.put(id, new SavingsAccount(id, pin));
                return getAccount(id);
            default:
                return null;
        }
    }

    public synchronized boolean closeAccount(long accountNum) {
        if (!accounts.containsKey(accountNum)) {
            return false;
        }
        
        accounts.remove(accountNum);
        return true;
    }

    // Main run loop for the thread that updates the accounts such as overdraft messages and interest payments
    @Override
    public void run() {
        while (isRunning()) {
            Instant now = Instant.now();
            Duration timeDuration = Duration.between(lastUpdate, now);

            if (timeDuration.toSeconds() >= 60) {
                lastUpdate = now;

                for (Account account : accounts.values()) {
                    update(account);
                }
            }
        }
    }

    public synchronized void update(Account account) {
        account.update();
    }
}
