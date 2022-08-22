package org.mphasis.states;

import java.util.Scanner;

import org.mphasis.accounts.Account;
import org.mphasis.accounts.Bank;
import org.mphasis.accounts.CurrentAccount;
import org.mphasis.accounts.SavingsAccount;

public class AccountMenu extends ProgramState implements AccountHandler {
	private Account account;
	
	public AccountMenu(Context context) {
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

		switch (input) {
			case "1":
				{
					DepositMenu menu = new DepositMenu(context);
					menu.setAccount(account);

					changeState(menu);
				}
				break;
			case "2":
				{
					WithdrawMenu menu = new WithdrawMenu(context);
					menu.setAccount(account);

					changeState(menu);
				}
				break;
			case "3":
				{
					MessageMenu menu = new MessageMenu(context);
					menu.setAccount(account);

					changeState(menu);
				}
				break;
			case "4": 
				{
					if (!account.canClose()) {
						errorText = "You cannot close your account until the overdraft has been paid.";
						return;
					}

					String output = "Are you sure you want to close your account? ";

					if (account.getBalance() > 0) {
						output += "Your account still contains £" + account.getBalance() + "!";
					}

					output += " [y/n]: ";

					System.out.print(output);

					input = scanner.nextLine();

					if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
						Bank.Instance().closeAccount(account.getAccountNumber());
						System.out.println("");

						if (Bank.Instance().accountExists(account.getAccountNumber())) {
							errorText = "Your account(" + account.getAccountNumber() + ") has failed to close!";
							return;
						}
						else {
							System.out.println("Your account(" + account.getAccountNumber() + ") has been closed successfully!");
						}

						System.out.println("");
						System.out.println("Press RETURN to continue to the main menu...");

						input = scanner.nextLine();
						changeState(new MainMenu(this.context));
						return;
					}
				}
				break;
			case "5":
				{
					changeState(new MainMenu(this.context));
				}
				break;
			default:
				errorText = "Please enter a valid option!";
				break;
		}
	}
	
	@Override
	protected void printGUI() {
		// TODO Auto-generated method stub
		System.out.println("##### Imaginary Bank #####");
		System.out.println("~~~~~ Account Management ~~~~~");
		System.out.println("Account: " + account.getAccountNumber());
		System.out.println("Current Balance: " + account.getBalance());
		if (account instanceof CurrentAccount) {
			CurrentAccount current = (CurrentAccount)account;
			System.out.println("Current Overdraft: " + current.getOverdraft());
		}
		else if (account instanceof SavingsAccount) {
			SavingsAccount current = (SavingsAccount) account;
			System.out.println("Current Interest Rate: " + current.getInterestRate());
		}
		System.out.println("");
		System.out.println("- Options -");
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. Account Messages");
		System.out.println("4. Close Account");
		System.out.println("5. Cancel");
		System.out.println("");
		System.out.println(errorText);
		System.out.print("Please enter an option number: ");
		errorText = "";
	}
}
