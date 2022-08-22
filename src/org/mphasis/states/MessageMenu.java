package org.mphasis.states;

import java.util.Scanner;

import org.mphasis.accounts.Account;

public class MessageMenu extends ProgramState implements AccountHandler {
    private Account account;

    public MessageMenu(Context context) {
		super(context);
	}

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void updateLoop() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.isEmpty()) {
            AccountMenu menu = new AccountMenu(context);
            menu.setAccount(account);

            changeState(menu);
        }
    }

    @Override
    protected void printGUI() {
        System.out.println("##### Imaginary Bank #####");
        System.out.println("~~~~~ Account Messages ~~~~~");
        System.out.println("Account: " + account.getAccountNumber());
        System.out.println("");
        System.out.println("Messages");
        System.out.println(account.getMessages());
        System.out.println("");
        System.out.println(errorText);
        System.out.print("Press RETURN key to go back...");
        errorText = "";
    }
}
