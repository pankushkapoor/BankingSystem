package com.cg.banking.daoservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cg.banking.beans.Account;
import com.cg.banking.beans.Transaction;
import com.cg.banking.util.ConnectionProvider;

public class AccountDAOImpl implements AccountDAO{
	//private static ArrayList<Account> accountList = new ArrayList<>();
	//CREATE TABLE Account(pinNumber number(10), accountType varchar2(20), status varchar2(20), accountBalance number(10,2), accountNumber number(10));
	//CREATE TABLE Transaction(accountNumber number(10), transactionId number(10), amount number(10,2), transactionType varchar(10));
	private Connection conn = new ConnectionProvider().getDBConnection();

	public Account save(Account account) throws SQLException {
		try{
			conn.setAutoCommit(false);
			String sql = "INSERT INTO Account(pinNumber, accountType, status, accountBalance, accountNumber)"
					+ "VALUES(?, ?, ?, ?, ?)";
			PreparedStatement pstmt1 = conn.prepareStatement(sql);
			pstmt1.setLong(1, account.getPinNumber());
			pstmt1.setString(2, account.getAccountType());
			pstmt1.setString(3, account.getStatus());
			pstmt1.setFloat(4, account.getAccountBalance());
			pstmt1.setLong(5, account.getAccountNo());
			pstmt1.executeUpdate();
			conn.commit();
			return account;
		}catch(SQLException e){
			e.printStackTrace();
			conn.rollback();
			throw e;
		}finally{
			conn.setAutoCommit(true);
		}
		/*accountList.add(account);
		return account;*/
	}
	public Account findOne(long accountNo) throws SQLException{
		String sql = "SELECT * FROM Account WHERE accountNumber=";
		PreparedStatement pstmt2 = conn.prepareStatement(sql+accountNo);
		
		ResultSet rs = pstmt2.executeQuery();
		if(rs.next()){ //initially the cursor will be at -1 position so that is why we did rs.next()
			int pinNumber = rs.getInt("pinNumber");
			String accountType = rs.getString("accountType");
			String status = rs.getString("status");
			float accountBalance = rs.getFloat("accountBalance");
			Account account1 = new Account(pinNumber, accountType, status, accountBalance, accountNo);
			
			return account1;
		}
		return null;
		
		/*for(Account account : accountList){
			if(account.getAccountNo() == accountNo) return account;
		}
		return null;*/
	}
	public ArrayList<Account> findAll() throws SQLException{
		ArrayList<Account> accList = new ArrayList<>();
		String sql = "SELECT * FROM Account";
		PreparedStatement pstmt3 = conn.prepareStatement(sql);
		ResultSet allRS = pstmt3.executeQuery();
		while(allRS.next()){ //To get every value in the result set.
			long accountNo = allRS.getLong("accountNo");
			String accountType = allRS.getString("accountType");
			String status = allRS.getString("status");
			int pinNumber = allRS.getInt("pinNumber");
			float accountBalance = allRS.getFloat("accountBalance");
			Account account = new Account(pinNumber, accountType, status, accountBalance, accountNo);
			accList.add(account);
		}
		return accList;
		
		/*System.out.println(accountList);
		return accountList;*/
	}
	
	public ArrayList<Transaction> allTransaction(long accountNo) throws SQLException{
		ArrayList<Transaction> transList = new ArrayList<>();
		String sql = "select * from Transaction where accountNo=";
		PreparedStatement pstmt4 = conn.prepareStatement(sql+accountNo);
		ResultSet transRS = pstmt4.executeQuery();
		while(transRS.next()){
			int transactionId = transRS.getInt("transactionId");
			String transactionType = transRS.getString("transactionType");
			float amount = transRS.getFloat("amount");
			Transaction transaction = new Transaction(accountNo, transactionId, amount, transactionType);
			transList.add(transaction);
		}
		return transList;
	}
	@Override
	public float updateDeposit(long accountNo, float amount) throws SQLException {
		PreparedStatement pstmt5 = conn.prepareStatement("UPDATE Account SET accountBalance=? WHERE accountNumber=?");
		pstmt5.setFloat(1,findOne(accountNo).getAccountBalance() + amount);
		pstmt5.setLong(2, accountNo);
		pstmt5.executeQuery();
		
		PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Transaction(transactionId,accountNumber,amount,transactionType) VALUES(transaction_seq.nextval,?,?,?)");
		pstmt2.setLong(1, accountNo);
		pstmt2.setFloat(2,amount);
		pstmt2.setString(3, "Deposit");
		pstmt2.executeUpdate();
		return findOne(accountNo).getAccountBalance();
	}
	
	
	@Override
	public float updateWithdraw(long accountNo, float amount) throws SQLException {
		
		return 0;
	}
	
	
}
