package com.cg.banking.main;

import com.cg.banking.beans.Account;
import com.cg.banking.exceptions.BankingServicesDownException;
import com.cg.banking.services.BankingServices;
import com.cg.banking.services.BankingServicesImpl;

public class MainClass {
	public static void main(String[] args) {
		BankingServices Sbi = new BankingServicesImpl();

		try{
			Sbi.openAccount("Savings", 5000); //pinNumber=1000, accountNo=100
			Sbi.openAccount("Current", 3000);
		}catch(Exception e){
			e.printStackTrace();
		}

		try{
			Account account1 = Sbi.depositAmount(100, 1000);
			System.out.println("After Deposit "+account1.getAccountBalance());
		}catch(Exception e){
			e.printStackTrace();
		}
/*
		try{
			Account account1 = Sbi.withdrawAmount(100, 1000, 1000);
			System.out.println("After withdrawal "+account1.getAccountBalance());
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			Sbi.fundTransfer(101, 100, 1500, 1000);
			Account account1 = Sbi.getAccountDetails(101);
			Account account2 = Sbi.getAccountDetails(100);
			System.out.println("From Ka Balance: "+account1.getAccountBalance() + " And To Ka Balance: "+account2.getAccountBalance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Sbi.getAllAccountDetails();
		} catch (BankingServicesDownException e) {
			e.printStackTrace();
		}*/
		/*try{
			Account account1 = Sbi.getAccountDetails(101);
			account1.setStatus("BlOcked");
			System.out.println(Sbi.accountStatus(101));
		}catch(Exception e){
			e.printStackTrace();
		}*/
		/*try {
			Sbi.getAccountAllTransaction(101);
		} catch (Exception e) {
			e.printStackTrace();
		}*/



	}
}
