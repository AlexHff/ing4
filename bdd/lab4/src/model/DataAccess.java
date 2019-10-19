package model;

import java.sql.*;

public class DataAccess {
	public DataAccess(String url, String login, String password) throws
    		SQLException {
	    Connection c = DriverManager.getConnection(url, login, password);
	    System.out.println("connected to " + url);
	    Statement stmt = c.createStatement();
	    ResultSet rset = stmt.executeQuery("select * from EMP");
	    ResultSetMetaData rsmd = rset.getMetaData();
	    int n = rsmd.getColumnCount();
	    while(rset.next()) {
	    	for (int i = 1; i < n; i++) {
	    		if (i > 1) System.out.print(", ");
	    		String values = rset.getString(i);
	    		System.out.print(values + " " + rsmd.getColumnName(i));
	    	}
	    	System.out.println();
	    }
	    c.close();
	}
}
