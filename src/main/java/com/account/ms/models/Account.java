package com.account.ms.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
	private String status;
	
	@Transient
	@Valid
	@NotNull
	private List<PersonClient> personclients;
	//private SavingAccount savingAccount;
	
	public Account(List<PersonClient> personclients) {
		this.personclients = personclients;
	}

    public Account() {
    		
	}
    
	public Account(String id, String numberAccount, BigDecimal currentBalance, String createdAt, String typeAccount,
		String status) {
		this.id = id;
		this.numberAccount = numberAccount;
		this.currentBalance = currentBalance;
		this.createdAt = createdAt;
		this.typeAccount = typeAccount;
		this.status = status;
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
	public List<PersonClient> getPersons() {
		return personclients;
	}
	public void setPersons(List<PersonClient> personclients) {
		this.personclients = personclients;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", numberAccount=" + numberAccount + ", currentBalance=" + currentBalance + "]";
	}

}
