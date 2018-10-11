package com.cg.banking.util.main;

import com.cg.banking.util.ConnectionProvider;

public class MainClass {

	public static void main(String[] args) {
		if(ConnectionProvider.getDBConnection()!=null) System.out.println("Connection Open!!");
		else System.out.println("Some Problem :/");
	}

}
