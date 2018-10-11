package com.cg.banking.daoservices;

import java.sql.SQLException;
import java.util.ArrayList;

import com.cg.banking.beans.Account;
import com.cg.banking.beans.Transaction;

public interface AccountDAO {
	Account save(Account account) throws SQLException;
	Account findOne(long AccountNo) throws SQLException;
	ArrayList<Account> findAll() throws SQLException;
	ArrayList<Transaction> allTransaction(long accountNo) throws SQLException;
	float updateDeposit(long accountNo,float amount) throws SQLException;
	float updateWithdraw(long accountNo,float amount) throws SQLException;
}