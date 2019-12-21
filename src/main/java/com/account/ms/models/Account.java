package com.account.ms.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="accounts")
public class Account {
	
	@Id
	private String id;
	private String numberAccount;
	private BigDecimal currentBalance;	
	private String  createdAt;
	private String typeAccount;
	private String personId;
	private BigDecimal deposit;
	private BigDecimal withdraw;
	
	@Transient
	private List<Person> persons;
	private SavingAccount savingAccount;
	
	public Account(List<Person> persons) {
		this.persons = persons;
	}

    public Account() {
    		
	}
    
	public Account(String id, String numberAccount, BigDecimal currentBalance, String createdAt, String typeAccount,
			String personId,BigDecimal deposit, BigDecimal withdraw, Person person,SavingAccount savingAccount) {
		this.id = id;
		this.numberAccount = numberAccount;
		this.currentBalance = currentBalance;
		this.createdAt = createdAt;
		this.typeAccount = typeAccount;
		this.personId = personId;
		this.deposit = deposit;
		this.withdraw = withdraw;
		this.savingAccount = savingAccount;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumberAccount() {
		return numberAccount;
	}
	public void setNumberAccount(String numberAccount) {
		this.numberAccount = numberAccount;
	}
	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getTypeAccount() {
		return typeAccount;
	}
	public void setTypeAccount(String typeAccount) {
		this.typeAccount = typeAccount;
	}
	
	public SavingAccount getSavingAccount() {
		return savingAccount;
	}

	public void setSavingAccounts(SavingAccount savingAccount) {
		this.savingAccount = savingAccount;
	}
	
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	
	
	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public void setSavingAccount(SavingAccount savingAccount) {
		this.savingAccount = savingAccount;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public BigDecimal getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(BigDecimal withdraw) {
		this.withdraw = withdraw;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", numberAccount=" + numberAccount + ", currentBalance=" + currentBalance + "]";
	}
	
	
	
}
