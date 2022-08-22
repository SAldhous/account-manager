package org.mphasis.states;

import java.util.Scanner;

import org.mphasis.accounts.Account;
import org.mphasis.accounts.Utility;
import org.mphasis.projectUtils.Reference;

public class WithdrawMenu extends ProgramState implements AccountHandler{
    private Account account;

    public WithdrawMenu(Context context) {
		super(context);
		account = new Account(00000000, 0000);
	}

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void updateLoop() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        Reference<Double> amount = new Reference<Double>(0.0);
        parseDouble("The amount you entered is not valid!", input, amount);

        if (input.isEmpty()) {
            AccountMenu menu = new AccountMenu(context);

            menu.setAccount(account);
            changeState(menu);
        }
        else if (account.withdraw(amount.extract())) {
            System.out.println("");
            System.out.print("Are you sure? [y/n]: ");
            input = scanner.nextLine();

            // Enter another value
            if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                return;
            }

            System.out.println("");
            System.out.println("£" + Utility.roundToPenny(amount.extract())
                    + " has been successfully withdrawn your account!");
            System.out.println("");
            System.out.print("Do you wish to make another withdrawal? [y/n]: ");

            input = scanner.nextLine();

            // Return to account management if no more withdrawals to be done
            if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                AccountMenu menu = new AccountMenu(context);

                menu.setAccount(account);
                changeState(menu);
                return;
            }
        } else {
            errorText = "The amount you entered was not valid! Please enter another.";
        }
    }

    @Override
    protected void printGUI() {
        // TODO Auto-generated method stub
        System.out.println("##### Imaginary Bank #####");
        System.out.println("~~~~~ Withdrawal ~~~~~");
        System.out.println("Account: " + account.getAccountNumber());
        System.out.println("Current Balance: " + account.getBalance());
        System.out.println("");
        System.out.println("Type nothing and press RETURN to cancel...");
        System.out.println("");
        System.out.println(errorText);
        System.out.print("Please enter the withdrawal amount: £");
        errorText = "";
    }
}
