package tests.com.managers;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import javax.swing.DefaultListModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.managers.ContactManager;
import com.managers.DepartmentManager;
import com.managers.ReportManager;
import com.managers.SortableListModel;
import com.pojos.Contact;
import com.pojos.Department;
import com.pojos.Report;

public class ReportManagerTest {

	Connection testCon;
	ReportManager testManager;
	DepartmentManager DepMan;
	ContactManager ConMan;
	Vector<Contact> contactList;
	Vector<Department> departmentList;
	Contact testContact;
	Department testDepartment;
	Report testReport;
	Statement stmt;
	Statement stmt2;
	Statement stmt3;

	String testDB = "spolsley"; // hardcoded test database

	@Before
	public void initConnection() {
		// Initialize connection
		String user;
		String password;

		contactList = new Vector<Contact>();
		departmentList = new Vector<Department>();
		testContact = new Contact(-1, "fname", "lname", "job", "email",
				"wphone", "hphone", "waddress", "haddress", "bdate");
		testDepartment = new Department(-1, "deptName", "deptEmail@email.com",
				"620-620-2222", "3000east West St", contactList);

		Properties prop = new Properties();
		

		try {
			// Attempt to load credentials from properties
			prop.load(new FileInputStream("database.properties"));

			user = prop.getProperty("dbusername");
			password = prop.getProperty("dbpassword");
		} catch (IOException e) {
			// Else revert to known user
			user = "spolsley";
			password = "G19zHQb6";
		}
		testReport = new Report(-1, "TestReport", "1970-01-01", "2004-07-25",
				"dim1,dim2", "met1,met2", "met1!=****", "-met1",
				Integer.toString(999), departmentList, contactList);

		String url = "jdbc:mysql://mysql.eecs.ku.edu/" + testDB;

		try {
			testCon = DriverManager.getConnection(url, user, password);
			ConMan = new ContactManager(testCon);
			DepMan = new DepartmentManager(testCon, ConMan);
			testManager = new ReportManager(testCon, ConMan, DepMan);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		// Clear table
		String clearCommand = "TRUNCATE TABLE TeamProject_Departments";
		String clearCommand2 = "TRUNCATE TABLE TeamProject_Contacts";
		String clearCommand3 = "TRUNCATE TABLE TeamProject_Reports";
		String clearCommand4 = "TRUNCATE TABLE TeamProject_ContactsToDepartments";
		String clearCommand5 = "TRUNCATE TABLE TeamProject_ContactsToReports";
		String clearCommand6 = "TRUNCATE TABLE TeamProject_DepartmentsToReports";
		try {
			Statement stmt = testCon.createStatement();
			stmt.executeQuery(clearCommand);
			stmt.executeQuery(clearCommand2);
			stmt.executeQuery(clearCommand3);
			stmt.executeQuery(clearCommand4);
			stmt.executeQuery(clearCommand5);
			stmt.executeQuery(clearCommand6);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		// Insert basic entries for contact and department
		ConMan.insert(testContact);
		testContact = (Contact) ConMan.getContacts().values().toArray()[0];
		DepMan.insert(testDepartment);
		testDepartment = (Department) DepMan.getDepartments().values()
				.toArray()[0];

		try {
			stmt = testCon.createStatement();
			stmt2 = testCon.createStatement();
			stmt3 = testCon.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@After
	public void clearTable() {
		String clearCommand = "TRUNCATE TABLE TeamProject_Reports";

		try {
			Statement stmt = testCon.createStatement();
			stmt.executeQuery(clearCommand);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void testInsertReportNoRelations() throws SQLException {
		testManager.insert(testReport);
		testReport = (Report) testManager.getReports().values().toArray()[0];
		String sql = "SELECT * FROM TeamProject_Reports WHERE ReportID = "
				+ testReport.getReportId();

		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {

			assertEquals(rs.getString("ReportID"),
					Integer.toString(testReport.getReportId()));
			assertEquals(rs.getString("Dimensions"), testReport.getDimensions());
			assertEquals(rs.getString("Metrics"), testReport.getMetrics());
			assertEquals(rs.getString("Filter"), testReport.getFilter());
		}

	}

	@Test
	public void testInsertReportWithRelations() throws SQLException {
		contactList.add(testContact);
		departmentList.add(testDepartment);
		testReport.setDepartments(departmentList);
		testReport.setRecipients(contactList);
		testManager.insert(testReport);
		testReport = (Report) testManager.getReports().values().toArray()[0];
		String sql = "SELECT ReportID,ContactID FROM TeamProject_ContactsToReports WHERE ReportID = "
				+ testReport.getReportId();
		String sql2 = "SELECT ReportID, DepartmentID FROM TeamProject_DepartmentsToReports WHERE ReportID="
				+ testReport.getReportId();
		ResultSet rs = stmt.executeQuery(sql);
		ResultSet rs2 = stmt2.executeQuery(sql2);
		while (rs.next()) {
			assertEquals(rs.getString("ContactID"),
					Integer.toString(testContact.getEmployeeID()));
		}

		while (rs2.next()) {
			assertEquals(rs2.getString("DepartmentID"),
					Integer.toString(testDepartment.getId()));

		}

	}

	@Test
	public void testUpdateReportNoRelations() throws SQLException {
		testManager.insert(testReport);
		testReport = (Report) testManager.getReports().values().toArray()[0];
		testReport.SetEndDate("2012-01-01");
		testReport.SetMaxResults(Integer.toString(666));
		testReport.SetStartDate("1992-03-31");
		testManager.update(testReport);
		testReport = (Report) testManager.getReports().values().toArray()[0];

		String sql = "SELECT * FROM TeamProject_Reports WHERE ReportID = "
				+ testReport.getReportId();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			assertEquals(rs.getString("StartDate"), testReport.getStartDate());
			assertEquals(rs.getString("EndDate"), testReport.getEndDate());
			assertEquals(rs.getString("MaxResults"),
					Integer.toString(testReport.getMaxResults()));
		}

	}

	@Test
	public void testUpdateReportWithRelations() throws SQLException {
		testManager.insert(testReport);
		testReport = (Report) testManager.getReports().values().toArray()[0];
		testReport.SetEndDate("2012-01-01");
		testReport.SetMaxResults(Integer.toString(666));
		testReport.SetStartDate("1992-03-31");
		contactList.add(testContact);
		departmentList.add(testDepartment);
		testReport.setDepartments(departmentList);
		testReport.setRecipients(contactList);
		testManager.update(testReport);
		testReport = (Report) testManager.getReports().values().toArray()[0];
		String sql = "SELECT * FROM TeamProject_Reports WHERE ReportID = "
				+ testReport.getReportId();
		String sql2 = "SELECT ReportID,ContactID FROM TeamProject_ContactsToReports WHERE ReportID = "
				+ testReport.getReportId();
		String sql3 = "SELECT ReportID, DepartmentID FROM TeamProject_DepartmentsToReports WHERE ReportID="
				+ testReport.getReportId();
		// SQL To Confirm
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			assertEquals(rs.getString("StartDate"), testReport.getStartDate());
			assertEquals(rs.getString("EndDate"), testReport.getEndDate());
			assertEquals(rs.getString("MaxResults"),
					Integer.toString(testReport.getMaxResults()));
		}
		ResultSet rs2 = stmt2.executeQuery(sql2);
		ResultSet rs3 = stmt3.executeQuery(sql3);
		while (rs2.next()) {
			assertEquals(rs2.getString("ContactID"),
					Integer.toString(testContact.getEmployeeID()));
		}

		while (rs3.next()) {
			assertEquals(rs3.getString("DepartmentID"),
					Integer.toString(testDepartment.getId()));

		}

	}

	@Test
	public void testGetReportNoRelations() {
		testManager.insert(testReport);
		testReport = (Report) testManager.getReports().values().toArray()[0];
		Report compareTo = testManager.getReport(testReport.getReportId());
	}

	@Test
	public void testReportDeletion() throws SQLException {
		contactList.add(testContact);
		departmentList.add(testDepartment);
		testReport.setDepartments(departmentList);
		testReport.setRecipients(contactList);
		testManager.insert(testReport);
		testReport = (Report) testManager.getReports().values().toArray()[0];
		String sql = "SELECT ReportID,ContactID FROM TeamProject_ContactsToReports WHERE ReportID = "
				+ testReport.getReportId();
		String sql2 = "SELECT ReportID,ContactID FROM TeamProject_ContactsToReports WHERE ReportID = "
				+ testReport.getReportId();
		String sql3 = "SELECT ReportID, DepartmentID FROM TeamProject_DepartmentsToReports WHERE ReportID="
				+ testReport.getReportId();
		ResultSet rs = stmt.executeQuery(sql);
		ResultSet rs2 = stmt2.executeQuery(sql2);
		ResultSet rs3 = stmt3.executeQuery(sql3);

		assertEquals(rs.getFetchSize(), 0);
		assertEquals(rs2.getFetchSize(), 0);
		assertEquals(rs3.getFetchSize(), 0);
	}

}
