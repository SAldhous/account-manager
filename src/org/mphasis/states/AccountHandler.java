package org.mphasis.states;

import org.mphasis.accounts.Account;

// Implemented by menu states that handle bank accounts
public interface AccountHandler {
	public void setAccount(Account account);
}
