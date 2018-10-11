package com.cg.banking.services;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cg.banking.beans.Account;
import com.cg.banking.beans.Transaction;
import com.cg.banking.daoservices.AccountDAO;
import com.cg.banking.daoservices.AccountDAOImpl;
import com.cg.banking.exceptions.AccountBlockedException;
import com.cg.banking.exceptions.AccountNotFoundException;
import com.cg.banking.exceptions.BankingServicesDownException;
import com.cg.banking.exceptions.InsufficientAmountException;
import com.cg.banking.exceptions.InvalidAccountTypeException;
import com.cg.banking.exceptions.InvalidAmountException;
import com.cg.banking.exceptions.InvalidPinNumberException;

public class BankingServicesImpl implements BankingServices {
	private static AccountDAO accountDao = new AccountDAOImpl();
	
	@Override
	public long openAccount(String accountType, float initBalance)
			throws InvalidAmountException, InvalidAccountTypeException, BankingServicesDownException {
		Account account1 = new Account(accountType, initBalance);
		if(account1.getAccountBalance() < 1000) throw new InvalidAmountException("Minimum amount should be there!");
		try {
			account1 = accountDao.save(account1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		account1.setTransaction(new Transaction(initBalance, "New Account Opened.. "));
		return account1.getAccountNo();
	}

	@Override
	public Account depositAmount(long accountNo, float amount)
			throws AccountNotFoundException, BankingServicesDownException, AccountBlockedException {
		Account account1 = new Account();
		try {
			account1 = accountDao.findOne(accountNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(account1 == null) throw new AccountNotFoundException("Account not found!");
		account1.setAccountBalance(account1.getAccountBalance() + amount);
		account1.setTransaction(new Transaction(amount, "Amount Deposited.. "));
		try {
			accountDao.updateDeposit(accountNo, amount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account1;
	}

	@Override
	public Account withdrawAmount(long accountNo, float amount, int pinNumber) throws InsufficientAmountException,
			AccountNotFoundException, InvalidPinNumberException, BankingServicesDownException, AccountBlockedException {
		Account account1 = new Account();
		try {
			account1 = accountDao.findOne(accountNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(account1 == null) throw new AccountNotFoundException("Account not found!");
		else if (account1.getAccountBalance() - amount < 0) 
			throw new InsufficientAmountException("Insufficient balance. Please withraw less amount!");
		else if (account1.getPinNumber()!= pinNumber) 
			throw new InvalidPinNumberException("Invalid Pin Number");
		else{
			account1.setAccountBalance(account1.getAccountBalance() - amount);
			account1.setTransaction(new Transaction(amount, "Amount Withdrawed.. "));
		}
		return account1;
	}

	@Override
	public boolean fundTransfer(long accountNoTo, long accountNoFrom, float transferAmount, int pinNumber)
			throws InsufficientAmountException, AccountNotFoundException, InvalidPinNumberException,
			BankingServicesDownException, AccountBlockedException {
		Account accountTo = new Account();
		Account accountFrom = new Account();
		try {
			accountTo = accountDao.findOne(accountNoTo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			accountFrom = accountDao.findOne(accountNoFrom);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(accountTo == null || accountFrom == null) throw new AccountNotFoundException("One of the Accounts not found!");
		else if(accountFrom.getAccountBalance() - transferAmount < 0) 
			throw new InsufficientAmountException("Insufficient Amount");
		else if (accountFrom.getPinNumber()!= pinNumber) 
			throw new InvalidPinNumberException("Invalid Pin Number");
		accountFrom.setAccountBalance(accountFrom.getAccountBalance() - transferAmount);
		accountTo.setAccountBalance(accountTo.getAccountBalance() + transferAmount);
		accountFrom.setTransaction(new Transaction(transferAmount, "Amount Transferred.. "));
		accountTo.setTransaction(new Transaction(transferAmount, "Amount Received.. "));
		return true;
	}

	@Override
	public Account getAccountDetails(long accountNo) throws AccountNotFoundException, BankingServicesDownException {
		Account account1 = new Account();
		try {
			account1 = accountDao.findOne(accountNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(account1 == null) throw new AccountNotFoundException("Account not found!");
		else return account1;
	}

	@Override
	public List<Account> getAllAccountDetails() throws BankingServicesDownException {
		try {
			return accountDao.findAll() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Transaction> getAccountAllTransaction(long accountNo)
			throws BankingServicesDownException, AccountNotFoundException {
		Account account=null;
		try {
			account = accountDao.findOne(accountNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account.getAllTransaction();
	}

	@Override
	public String accountStatus(long accountNo)
			throws BankingServicesDownException, AccountNotFoundException, AccountBlockedException {
		Account account1 = new Account();
		try {
			account1 = accountDao.findOne(accountNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(account1 == null) throw new AccountNotFoundException("Account not found!");
		else if(account1.getStatus().equalsIgnoreCase("Blocked"))
			throw new AccountBlockedException("Account Blocked!");
		return account1.getStatus();
	}

}
