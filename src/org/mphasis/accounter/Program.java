package org.mphasis.accounter;

import org.mphasis.accounts.Bank;
import org.mphasis.states.*;

// Initial program
public class Program {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Intiate Bank (Singleton) and run that instance on its own thread
		Bank bank = Bank.Instance();
		Thread t = new Thread(bank);
		t.start();
		
		// Intiate context starting at the main menu
		Context context = new Context();
		context.setState(new MainMenu(context));
	}
	
}
