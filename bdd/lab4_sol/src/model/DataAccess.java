package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jean-Michel Busca
 */
public class DataAccess {

	// class fields
	private static final String[] GET_DEPARTMENTS_QUERIES = { "select DID, DNAME, DLOC from DEPT",
			"select DID, DNAME, DLOC from DEPT where DLOC = ?", "select DID, DNAME, DLOC from DEPT where DNAME = ?",
			"select DID, DNAME, DLOC from DEPT where DNAME = ? and DLOC = ?",
			"select DID, DNAME, DLOC from DEPT where DID = ?",
			"select DID, DNAME, DLOC from DEPT where DID = ? and DLOC = ?",
			"select DID, DNAME, DLOC from DEPT where DID = ? and DNAME = ?",
			"select DID, DNAME, DLOC from DEPT where DID = ? and DNAME = ? and DLOC = ?" };

	// instance fields
	private final Connection connection;
	private PreparedStatement getEmployeesPS;
	private PreparedStatement updateSalaryPS;
	private PreparedStatement[] getDepartmentsPSs;

	/**
	 * Important note on Prepared Statements:
	 * <p>
	 * Prepared statements are primarily intended to improve performances. As a side
	 * effect, they also prevent SQL injection. For both reasons, <b>you must
	 * use</b> prepared statements whenever you can. The only situation you can't
	 * use a prepared statement is when you don't know the SQL statement to execute
	 * beforehand.
	 * <p>
	 * For a prepared statement to be efficient:
	 * <ul>
	 * <li>It has to be created <b>only once</b>. Upon creation, the RDMS parses the
	 * statement of the SQL statement associated with the prepared statement and
	 * then computes its the execution plan
	 * <li>And then executed many times, possibly billions times. The execution plan
	 * computed at creation time is re-used again and again, saving processing time
	 * </ul>
	 * Therefore, a prepared statement must be:
	 * <ul>
	 * <li>an instance attribute; it cannot be a local variable, beacause such a
	 * variable is allocated every time you enter the method that declares it,
	 * <li>initialized only once, either: (a) beforehand, in the constructor or (b)
	 * at the very last time, in the method using the prepared statement; this is a
	 * design pattern called "lazy intialization".
	 * </ul>
	 *
	 * @param url      the database to connect to
	 * @param login    the login to use for the connection
	 * @param password the password to use for the connection
	 *
	 * @throws SQLException if an SQL error occurs
	 */
	public DataAccess(String url, String login, String password) throws SQLException {
		connection = DriverManager.getConnection(url, login, password);
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

	public synchronized List<EmployeeInfo> getEmployees() throws SQLException {
		try {
			Statement statement = connection.createStatement();
			connection.setAutoCommit(false);
			ResultSet result = statement.executeQuery("select EID, ENAME, SAL from EMP");
			List<EmployeeInfo> list = new ArrayList<EmployeeInfo>();
			while (result.next()) {
				list.add(new EmployeeInfo(result.getInt(1), result.getString(2), result.getFloat(3)));
			}
			connection.commit();
			return list;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			connection.rollback();
			return new ArrayList<EmployeeInfo>();
		} finally {
			connection.setAutoCommit(true);
		}
	}

	public synchronized List<EmployeeInfo> getEmployeesPS() throws SQLException {
		if (getEmployeesPS == null) {
			getEmployeesPS = connection.prepareStatement("select EID, ENAME, SAL from EMP");
		}
		try {
			connection.setAutoCommit(false);
			ResultSet result = getEmployeesPS.executeQuery();
			List<EmployeeInfo> list = new ArrayList<EmployeeInfo>();
			while (result.next()) {
				list.add(new EmployeeInfo(result.getInt(1), result.getString(2), result.getFloat(3)));
			}
			connection.commit();
			return list;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			connection.rollback();
			return new ArrayList<EmployeeInfo>();
		} finally {
			connection.setAutoCommit(true);
		}
	}

	public synchronized boolean raiseSalary(String job, double amount) throws SQLException {
		try {
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			String query = "update EMP set SAL = SAL + " + amount + " where JOB = '" + job + "'";
			int r = statement.executeUpdate(query);
			connection.commit();
			return r >= 1;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			connection.rollback();
			return false;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	public synchronized boolean raiseSalaryPS(String job, double amount) throws SQLException {

		if (updateSalaryPS == null) {
			updateSalaryPS = connection.prepareStatement("update EMP set SAL = SAL + ? where JOB = ?");
		}
		try {
			connection.setAutoCommit(false);
			updateSalaryPS.setDouble(1, amount);
			updateSalaryPS.setString(2, job);
			int r = updateSalaryPS.executeUpdate();
			connection.commit();
			return r >= 1;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			connection.rollback();
			return false;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	public List<DepartmentInfo> getDepartments(Integer id, String name, String location) throws SQLException {
		String query = "select DID, DNAME, DLOC from DEPT where true";
		if (id != null) {
			query += " and DID = " + id;
		}
		if (name != null) {
			query += " and DNAME = '" + name + "'";
		}
		if (location != null) {
			query += " and DLOC = '" + location + "'";
		}
		try {
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			List<DepartmentInfo> list = new ArrayList<DepartmentInfo>();
			while (result.next()) {
				list.add(new DepartmentInfo(result.getInt(1), result.getString(2), result.getString(3)));
			}
			connection.commit();
			return list;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			connection.rollback();
			return new ArrayList<DepartmentInfo>();
		} finally {
			connection.setAutoCommit(true);
		}
	}

	public List<DepartmentInfo> getDepartmentsPS(Integer id, String name, String location) throws SQLException {
		int key = 0;
		key += (id == null ? 0 : 4);
		key += (name == null ? 0 : 2);
		key += (location == null ? 0 : 1);
		if (getDepartmentsPSs == null) {
			getDepartmentsPSs = new PreparedStatement[GET_DEPARTMENTS_QUERIES.length];
		}
		PreparedStatement getDepartementsPS = getDepartmentsPSs[key];
		if (getDepartementsPS == null) {
			getDepartementsPS = connection.prepareStatement(GET_DEPARTMENTS_QUERIES[key]);
			getDepartmentsPSs[key] = getDepartementsPS;
		}
		int index = 0;
		if (id != null) {
			index += 1;
			getDepartementsPS.setInt(index, id);
		}
		if (name != null) {
			index += 1;
			getDepartementsPS.setString(index, name);
		}
		if (location != null) {
			index += 1;
			getDepartementsPS.setString(index, location);
		}
		try {
			connection.setAutoCommit(false);
			ResultSet result = getDepartementsPS.executeQuery();
			List<DepartmentInfo> list = new ArrayList<DepartmentInfo>();
			while (result.next()) {
				list.add(new DepartmentInfo(result.getInt(1), result.getString(2), result.getString(3)));
			}
			connection.commit();
			return list;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			connection.rollback();
			return null;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	public List<String> executeQuery(String query) throws SQLException {
		try {
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			ResultSetMetaData metaData = result.getMetaData();
			List<String> list = new ArrayList<String>();
			String attributes = "| ";
			for (int index = 1; index <= metaData.getColumnCount(); index++) {
				attributes += metaData.getColumnName(index) + " | ";
			}
			list.add(attributes);
			while (result.next()) {
				String tuple = "| ";
				for (int index = 1; index <= metaData.getColumnCount(); index++) {
					tuple += result.getObject(index) + " | ";
				}
				list.add(tuple);
			}
			connection.commit();
			return list;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			connection.rollback();
			return new ArrayList<String>();
		} finally {
			connection.setAutoCommit(true);
		}
	}

	public List<String> executeStatement(String statement) throws SQLException {
		try {
			connection.setAutoCommit(false);
			if (statement.toLowerCase().startsWith("select")) {
				return executeQuery(statement);
			}
			Statement jdbcStatement = connection.createStatement();
			int r = jdbcStatement.executeUpdate(statement);
			List<String> list = new ArrayList<>();
			list.add(r + "");
			connection.commit();
			return list;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			connection.rollback();
			return new ArrayList<String>();
		} finally {
			connection.setAutoCommit(true);
		}
	}

	public void close() throws SQLException {
		try {
			if (getEmployeesPS != null) {
				getEmployeesPS.close();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		try {
			if (updateSalaryPS != null) {
				updateSalaryPS.close();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		if (getDepartmentsPSs != null) {
			for (PreparedStatement ps : getDepartmentsPSs) {
				try {
					if (ps != null) {
						ps.close();
					}
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		connection.close();
	}
}
