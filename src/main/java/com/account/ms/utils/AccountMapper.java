package com.account.ms.utils;

import java.util.List;

import com.account.ms.models.Account;
import com.account.ms.models.Person;

public class AccountMapper {
	
	public static Account map(List<Account> accounts) {
		Account account = new Account();
		for (Account a : accounts) {
			if (a.getCurrentBalance() != null) account.setCurrentBalance(a.getCurrentBalance());
			if (a.getDeposit() != null) account.setDeposit(a.getDeposit());
			if (a.getWithdraw()!= null) account.setWithdraw(a.getWithdraw());
			if (a.getPersons() != null) account.setPersons(a.getPersons());
		}
		return account;
	}

}
