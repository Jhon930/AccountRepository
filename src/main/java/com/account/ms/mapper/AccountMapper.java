package com.account.ms.mapper;

import java.util.List;

import com.account.ms.models.Account;

public class AccountMapper {
	
	public static Account map(List<Account> accounts) {
		Account account = new Account();
		for (Account a : accounts) {
			if (a.getNumberAccount() != null) account.setNumberAccount(a.getNumberAccount());
			if (a.getCreatedAt() != null) account.setCreatedAt(a.getCreatedAt());
			if (a.getCurrentBalance() != null) account.setCurrentBalance(a.getCurrentBalance());
			if (a.getPersons() != null) account.setPersons(a.getPersons());
		}
		return account;
	}

}
