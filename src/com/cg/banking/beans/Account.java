package com.cg.banking.beans;

import java.util.ArrayList;

public class Account {
	private int pinNumber;
	private String accountType,status;
	private float accountBalance;
	private long accountNo;
	private static long ACCOUNT_COUNTER = 100;
	private static int PIN_COUNTER = 1000;
	private Transaction transaction;
	private ArrayList<Transaction> transList = new ArrayList<>();
	
	public ArrayList<Transaction> getAllTransaction() {
		for (Transaction transaction : transList) {
			System.out.println(transaction);
		}
		return transList;
	}
	public void setTransaction(Transaction transaction) {
		//this.transaction = transaction;
		transList.add(transaction);
	}
	
	public Account() {
		super();
	}
	public Account(String accountType, float accountBalance) {
		super();
		this.pinNumber = PIN_COUNTER++;
		this.accountType = accountType;
		this.status = "Active";
		this.accountBalance = accountBalance;
		this.accountNo = ACCOUNT_COUNTER++;
	}
	
	public Account(int pinNumber, String accountType, String status,
			float accountBalance, long accountNo) {
		super();
		this.pinNumber = pinNumber;
		this.accountType = accountType;
		this.status = status;
		this.accountBalance = accountBalance;
		this.accountNo = accountNo;
	}
	
	public Account(int pinNumber, String accountType, String status,
			float accountBalance, long accountNo, Transaction transaction) {
		super();
		this.pinNumber = pinNumber;
		this.accountType = accountType;
		this.status = status;
		this.accountBalance = accountBalance;
		this.accountNo = accountNo;
		this.transaction = transaction;
	}
	public int getPinNumber() {
		return pinNumber;
	}
	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public float getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}
	public long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	@Override
	public String toString() {
		return "Account [pinNumber=" + pinNumber + ", accountType="
				+ accountType + ", status=" + status + ", accountBalance="
				+ accountBalance + ", accountNo=" + accountNo + "]";
	}	
	
}