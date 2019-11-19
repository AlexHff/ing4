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

	/**
	 * Creates a new database and return its status. 
	 * @return
	 */
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

	/**
	 * Creates a new account given a number and returns the status.
	 * @param number
	 * @return
	 */
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

	/**
	 * Returns a lists of all accounts in the database.
	 * @return
	 * @throws SQLException
	 */
	public synchronized List<Integer> getAccounts() throws SQLException {
		String sql = "select number from accounts";
		try {
			Statement statement = connection.createStatement();

			// Disable auto-commit mode which starts the transaction
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
			// Catch SQLException and rollback
			System.err.println(e.getErrorCode());
			connection.rollback();
			return Collections.emptyList();
		} finally {
			// It is good practice to set auto-commit to enabled to avoid other locks
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Returns the balance of a given account number or -1 if it doesn't exist. 
	 * @param number
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * Set the balance for a given account. 
	 * @param number
	 * @param balance
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * Transfers money from one account to another.
	 * @param from
	 * @param to
	 * @param amount
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * Returns the total value of holdings in the bank.
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * Closes the connection.
	 */
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {

		}
	}

	/**
	 * Return the current thread's name.
	 * @return
	 */
	private static String getThread() {
		return Thread.currentThread().getName();
	}
}
