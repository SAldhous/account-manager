package org.mphasis.states;

import java.util.Scanner;

import org.mphasis.accounts.Account;
import org.mphasis.accounts.Bank;
import org.mphasis.projectUtils.Reference;

public class MainMenu extends ProgramState {
    public MainMenu(Context context) {
		super(context);
	}

    @Override
    public void updateLoop() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.equals("1")) {
            changeState(new SignInMenu(context));
        }
        if (input.equals("2")) {
            changeState(new OpenAccountMenu(context));
        }
        else {
            errorText = "The option number you enter was not valid!";
        }
    }

    @Override
    protected void printGUI() {
        System.out.println("##### Imaginary Bank #####");
        System.out.println("~~~~~ Welcome to Imaginary Bank ATM Services ~~~~~");
        System.out.println("");
        System.out.println("- Options -");
        System.out.println("1. Sign In");
        System.out.println("2. Open New Account");
        System.out.println("");
        System.out.println(errorText);
        System.out.print("Please enter an option number: ");
        errorText = "";
    }
}
