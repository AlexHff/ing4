package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jean-Michel Busca
 *
 */
public class DataAccess {

	//
	// INSTANCE FIELDS
	//
	private final Connection connection;

	public DataAccess(String url, String user, String password) throws SQLException {
		connection = DriverManager.getConnection(url, user, password);
		//connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	}

	@Override
	public String toString() {
		try {
			return "DataAccess{" + "connection=" + connection + " autoCommitMode=" + connection.getAutoCommit()
					+ " transactionIsolation=" + connection.getTransactionIsolation() + "}";
		} catch (SQLException e) {
			return e.getMessage();
		}
	}

	public boolean createDatabase() {
		try {
			String sql = "drop table accounts";
			Statement statement = connection.createStatement();
			statement.execute(sql);
		} catch (SQLException e) {
			System.err.println(getThread() + ": could not drop table accounts");
		}

		try {
			String sql = "create table accounts (" + "    number int primary key," + "    balance int"
					+ ") engine=InnoDB";
			Statement statement = connection.createStatement();
			statement.execute(sql);
			return true;
		} catch (SQLException e) {
			System.err.println(getThread() + ": could not create table accounts");
			return false;
		}
	}

	public boolean createAccount(int number) {
		String sql = "insert into accounts values (" + number + ", 0.0)";
		try {
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(sql);
			return result == 1;
		} catch (SQLException e) {
			return false;
		}
	}

	public synchronized List<Integer> getAccounts() throws SQLException {
		String sql = "select number from accounts";
		try {
			// Disable auto-commit mode
			Statement statement = connection.createStatement();
			connection.setAutoCommit(false);
			ResultSet result = statement.executeQuery(sql);
			List<Integer> list = new ArrayList<>();
			while (result.next()) {
				list.add(result.getInt(1));
			}
			// Commit the transaction making it exit successfully
			connection.commit();
			return list;
		} catch (SQLException e) {
			System.err.println(e.getErrorCode());
			connection.rollback();
			return Collections.emptyList();
		} finally {
			connection.setAutoCommit(true);
		}
	}

	public synchronized long getBalance(int number) throws SQLException {
		String sql = "select balance from accounts where number = " + number;
		try {
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			if (!result.next()) {
				throw new SQLException();
			}
			long balance = result.getLong(1);
			if (result.next()) {
				throw new SQLException();
			}
			connection.commit();
			return balance;
		} catch (SQLException e) {
			System.err.println(e.getErrorCode());
			connection.rollback();
			return -1;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	public synchronized boolean setBalance(int number, long balance) throws SQLException {
		String sql = "update accounts set balance = " + balance + " where number = " + number;
		try {
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(sql);
			if (result == 1) {
				connection.commit();
				return true;
			}
			throw new SQLException();
		} catch (SQLException e) {
			System.err.println(e.getErrorCode());
			connection.rollback();
			return false;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	public synchronized boolean transfert(int from, int to, long amount) throws SQLException {
		// check from account
		long balance = getBalance(from);
		if (balance == -1.0) {
			System.out.println(getThread() + ": account #" + from + " does not exists");
			return false;
		}
		if (balance < 0.0) {
			System.out.println(getThread() + ": account #" + from + " overdrawn");
			return false;
		}
		if (balance < amount) {
			// System.out.println(
			// this + ": account #" + from + " cannot be overdrawn");
			return false;
		}
		balance -= amount;
		if (!setBalance(from, balance)) {
			System.out.println(getThread() + ": account #" + from + ": failed to withdraw € " + amount);
		}

		// check to account
		balance = getBalance(to);
		if (balance == -1.0) {
			System.out.println(getThread() + ": account #" + to + " does not exists");
			return false;
		}
		balance += amount;
		if (!setBalance(to, balance)) {
			System.out.println(getThread() + ": account #" + from + ": failed to deposit €" + amount);
		}
		return true;
	}

	public synchronized long getHoldings() throws SQLException {
		// get account list
		List<Integer> accounts = getAccounts();
		System.out.println(getThread() + ": accounts = " + accounts);

		// sum all account balances
		long holdings = 0;
		for (int account : accounts) {
			long balance = getBalance(account);
			if (balance == -1.0) {
				System.out.println(getThread() + ": account #" + account + " does not exists");
				continue;
			}
			if (balance < 0.0) {
				System.out.println(getThread() + ": account #" + account + " overdrawn");
				continue;
			}
			holdings += balance;
		}
		return holdings;
	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {

		}
	}

	private static String getThread() {
		return Thread.currentThread().getName();
	}
}
