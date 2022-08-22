package org.mphasis.states;

import java.util.Scanner;

import org.mphasis.accounts.Account;
import org.mphasis.accounts.Bank;
import org.mphasis.projectUtils.Reference;

public class SignInMenu extends ProgramState {
	
	public SignInMenu(Context context) {
		super(context);
	}
	
	@Override
	public void updateLoop() {
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();

		
		if (input.isEmpty()) {
			changeState(new MainMenu(context));
		}
		else if (input.length() == 8) {
			Reference<Long> accountNum = new Reference<Long>((long) 0);
			parseLong("This is not a valid account number!", input, accountNum);

			// Check for account by ID
			if (Bank.Instance().accountExists(accountNum.extract())) {
				System.out.print("Please enter your pin: ");
				input = scanner.nextLine();

				if (input.isEmpty()) {
					changeState(new MainMenu(context));
				}
				else if (input.length() == 4) {
					Reference<Integer> pin = new Reference<Integer>(0);
					parseInt("This is not a valid pin number!", input, pin);

					// Verify pin against the account pin
					if (Bank.Instance().pinCheck(accountNum.extract(), pin.extract())) {
						AccountMenu menu = new AccountMenu(context);

						Account account = Bank.Instance().getAccount(accountNum.extract());
						menu.setAccount(account);

						changeState(menu);
					}
					else {
						errorText = "This is not a valid pin number!";
					}
				} else {
					errorText = "This is not a valid pin number!";
				}
			} else {
				errorText = "This is not a valid account number!";
			}
		} else {
			errorText = "This is not a valid account number!";
		}
	}
	
	@Override
	protected void printGUI() {
		System.out.println("##### Imaginary Bank #####");
		System.out.println("~~~~~ Welcome to Imaginary Bank ATM Services ~~~~~");
		System.out.println("");
		System.out.println("Type nothing and press RETURN at any point to cancel...");
		System.out.println("");
		System.out.println(errorText);
		System.out.print("Please enter your account pin code or enter EXIT to stop the service: ");
		errorText = "";
	}
}
