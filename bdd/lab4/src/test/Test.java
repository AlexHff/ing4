package test;

import java.sql.SQLException;
import java.util.Arrays;
import model.DataAccess;

/**
 *
 * @author Jean-Michel Busca
 */
public class Test {

  /**
   * @param args the command line arguments
   *
   * @throws SQLException for convenience
   */
  public static void main(String[] args) throws SQLException {
	/*
	args = new String[3];
    args[0] = "jdbc:mysql://localhost/company";
    args[1] = "root";
    args[2] = "";
    */
    

    // work around Netbeans bug
    if (args.length == 2) {
      args = Arrays.copyOf(args, 3);
      args[2] = "";
    }

    DataAccess da = new DataAccess(args[0], args[1], args[2]);
    
    System.out.println(da.toString());

    System.out.println("Testing getEmployees():");
    System.out.println("getEmployees()=" + da.getEmployees());
    System.out.println("getEmployeesPS()=" + da.getEmployeesPS());

    System.out.println("\nTesting raiseSalary():");
    System.out.println(
        "raiseSalary(\"ANALYST\", 1000.0f)="
        + da.raiseSalary("ANALYST", 1000.0f));
    System.out.println(
        "raiseSalaryPS(\"MANAGER\", 2000.0f)="
        + da.raiseSalaryPS("MANAGER", 2000.0f));
    System.out.println("getEmployees()=" + da.getEmployees());
    // SQL injection: WORKS with plain statements
    /*
    System.out.println(
        "raiseSalary(\"' or true; -- \", -5000.0f)="
        + da.raiseSalary("' or true; -- ", -5000.0f));
    System.out.println("getEmployees()=" + da.getEmployees());
    */
    // SQL injection: FAILS with prepared statements
    /*
    System.out.println(
        "raiseSalaryPS(\"' or true; -- \", -5000.0f)="
        + da.raiseSalaryPS("' or true; -- ", -5000.0f));
    System.out.println("getEmployees()=" + da.getEmployees());
    */

    System.out.println("\nTesting getDepartments():");
    System.out.println(
        "getDepartments(null, null, null)="
        + da.getDepartments(null, null, null));
    System.out.println(
        "getDepartmentsPS(null, null, null)="
        + da.getDepartmentsPS(null, null, null));
    System.out.println(
        "getDepartments(null, null, \"NEW-YORK\")="
        + da.getDepartments(null, null, "NEW-YORK"));
    System.out.println(
        "getDepartmentsPS(null, null, \"NEW-YORK\")="
        + da.getDepartmentsPS(null, null, "NEW-YORK"));
    System.out.println(
        "getDepartments(null, \"RESEARCH\", \"DALLAS\")="
        + da.getDepartments(null, "RESEARCH", "DALLAS"));
    System.out.println(
        "getDepartmentsPS(null, \"RESEARCH\", \"DALLAS\")="
        + da.getDepartmentsPS(null, "RESEARCH", "DALLAS"));
    System.out.println(
        "getDepartments(null, \"RESEARCH\", \"NEW-YORK\")="
        + da.getDepartments(null, "RESEARCH", "NEW-YORK"));
    System.out.println(
        "getDepartmentsPS(null, \"RESEARCH\", \"NEW-YORK\")="
        + da.getDepartmentsPS(null, "RESEARCH", "NEW-YORK"));
    System.out.println(
        "getDepartments(30, \"SALES\", \"CHICAGO\")="
        + da.getDepartments(30, "SALES", "CHICAGO"));
    System.out.println(
        "getDepartmentsPS(30, \"SALES\", \"CHICAGO\")="
        + da.getDepartmentsPS(30, "SALES", "CHICAGO"));

    System.out.println("\nTesting executeQuery():");
    System.out.println("executeQuery(\"select EID, ENAME, SAL from EMP\")=");
    for (String row : da.executeQuery("select EID, ENAME, SAL from EMP")) {
      System.out.println(row);
    }
    System.out.println("executeQuery(\"select * from DEPT\")=");
    for (String row : da.executeQuery("select * from DEPT")) {
      System.out.println(row);
    }

    System.out.println("\nTesting executeStatement():");
    System.out.println("executeStatement(\"select * from DEPT\")=");
    for (String row : da.executeStatement("select * from DEPT")) {
      System.out.println(row);
    }
    System.out.println(
        "executeStatement(\"update DEPT set DLOC = 'Paris' where DID = 40\")="
        + da.executeStatement(
            "update DEPT set DLOC = 'Paris' where DID = 40"));
    System.out.println("executeStatement(\"select * from DEPT\")=");
    for (String row : da.executeStatement("select * from DEPT")) {
      System.out.println(row);
    }

    System.out.println("\ndone.");
    da.close();

  }

}
