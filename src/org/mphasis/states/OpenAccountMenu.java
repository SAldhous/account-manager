package org.mphasis.states;

import java.util.Objects;
import java.util.Scanner;

import org.mphasis.accounts.Account;
import org.mphasis.accounts.Bank;
import org.mphasis.projectUtils.Reference;

public class OpenAccountMenu extends ProgramState{
    private Account account;

    public OpenAccountMenu(Context context) {
		super(context);
	}

    private void genAccount(String accountType, int pin) {
        Bank.Instance().openAccount(accountType, pin);
        //account = Bank.Instance().getAccount(accountID)
    }

    @Override
    public void updateLoop() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.isEmpty()) {
            changeState(new MainMenu(context));
        }
        else {
            // Choosing an account type to open
            String accountType = "";
            if (input.equals("1")) {
                accountType = "REGULAR";
            } else if (input.equals("2")) {
                accountType = "CURRENT";
            } else if (input.equals("3")) {
                accountType = "SAVINGS";
            }
            else {
                errorText = "Please enter a valid option!";
            }

            // Create a pin number
            System.out.println("");
            System.out.print("Please enter a 4 digit pin for your account: ");
            input = scanner.nextLine();

            Reference<Integer> pin = new Reference<Integer>(0);

            if (input.isEmpty()) {
                changeState(new MainMenu(context));
            }
            else if (parseInt("Error parsing string to integer", input, pin)) {
                int accountPin = pin.extract();

                // Generate a new account with a random available account number
                Account account = Bank.Instance().openAccount(accountType, accountPin);

                // If the account generated was not null (no number available) then inform user and display their new account number
                if (!Objects.isNull(account)) {
                    System.out.println("");
                    System.out.println("Your new " + accountType + " account has been opened!");
                    System.out.println("Your new account number is " + account.getAccountNumber());
                    System.out.print("Press RETURN to enter Account Management...");

                    input = scanner.nextLine();

                    AccountMenu menu = new AccountMenu(context);
                    menu.setAccount(account);

                    changeState(menu);
                }
            }
            else {
                errorText = "Please enter a valid pin!";
            }
        }
    }

    @Override
    protected void printGUI() {
        // TODO Auto-generated method stub
        System.out.println("##### Imaginary Bank #####");
        System.out.println("~~~~~ Open New Account ~~~~~");
        System.out.println("");
        System.out.println("- Account Types -");
        System.out.println("1. Regular Account");
        System.out.println("2. Current Account");
        System.out.println("3. Savings Account");
        System.out.println("");
        System.out.println("Type nothing and press RETURN at any point to cancel...");
        System.out.println("");
        System.out.println(errorText);
        System.out.print("Please enter and account option number: ");
        errorText = "";
    }
}
