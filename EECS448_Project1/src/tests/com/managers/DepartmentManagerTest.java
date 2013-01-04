package tests.com.managers;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.managers.ContactManager;
import com.managers.DepartmentManager;
import com.pojos.Contact;
import com.pojos.Department;

public class DepartmentManagerTest {

	Connection testCon;
	DepartmentManager testManager;
	ContactManager ConMan;
	Contact testContact;
	Contact testContact2;
	String testDB = "spolsley"; // hardcoded test database
	Vector<Contact> contactList = new Vector<Contact>();

	@Before
	public void initConnection() {
		// Initialize connection
		String user;
		String password;
		testContact = new Contact(1, "fname", "lname", "job", "email",
				"wphone", "hphone", "waddress", "haddress", "bdate");
		testContact2 = new Contact(-1, "Lemon", "Goat", "jobianJovial",
				"email", "wphone", "hphone", "waddress", "haddress", "bdate");
		Properties prop = new Properties();

		try {
			// Attempt to load credentials from properties
			prop.load(new FileInputStream("database.properties"));

			user = prop.getProperty("dbusername");
			password = prop.getProperty("dbpassword");
		} catch (IOException e) {
			// Else revert to known user
			user = "chale";
			password = "Ao0aU2gV";
		}

		String url = "jdbc:mysql://mysql.eecs.ku.edu/" + testDB;

		try {
			testCon = DriverManager.getConnection(url, user, password);
			ConMan = new ContactManager(testCon);
			testManager = new DepartmentManager(testCon, ConMan);
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
	}

	@After
	public void clearTable() {
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

	}

	@Test
	public void TestInsertDepartment1() {
		Department test = new Department(-1, "deptName", "deptEmail@email.com",
				"620-620-2222", "3000east West St", contactList);
		testManager.insert(test);

		String testCommand = "SELECT * FROM `TeamProject_Departments`;";
		try {
			Statement stmt = testCon.createStatement();
			ResultSet rs = stmt.executeQuery(testCommand);
			while (rs.next()) {
				assertEquals(rs.getString("DepartmentName"), "deptName");
				assertEquals(rs.getString("DepartmentEmail"),
						"deptEmail@email.com");
				assertEquals(rs.getString("DepartmentPhone"), "620-620-2222");
				assertEquals(rs.getString("DepartmentMailingAddress"),
						"3000east West St");
				rs.getString("DepartmentID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void TestInsertDepartment2() {
		contactList.add(testContact);
		Department test = new Department(-1, "deptName", "deptEmail@email.com",
				"620-620-2222", "3000east West St", contactList);
		testManager.insert(test);

		String testCommand = "SELECT * FROM `TeamProject_Departments`;";
		String testCommand2 = "SELECT * FROM `TeamProject_Departments`,`TeamProject_ContactsToDepartments` WHERE "
				+ "TeamProject_Departments.DepartmentID = TeamProject_ContactsToDepartments.DepartmentID;";

		try {
			Statement stmt = testCon.createStatement();
			ResultSet rs = stmt.executeQuery(testCommand);
			while (rs.next()) {
				assertEquals(rs.getString("DepartmentName"), "deptName");
				assertEquals(rs.getString("DepartmentEmail"),
						"deptEmail@email.com");
				assertEquals(rs.getString("DepartmentPhone"), "620-620-2222");
				assertEquals(rs.getString("DepartmentMailingAddress"),
						"3000east West St");
				rs.getString("DepartmentID");
			}
			rs = stmt.executeQuery(testCommand2);
			while (rs.next()) {
				assertEquals(rs.getString("ContactID"), "1");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void TestUpdateDepartmentWithoutContactChange() {
		contactList.add(testContact);
		Department test = new Department(-1, "deptName", "deptEmail@email.com",
				"620-620-2222", "3000east West St", contactList);
		testManager.insert(test);
		Department retrieved = (Department) testManager.getDepartments()
				.values().toArray()[0];
		retrieved.setName("NewName");
		retrieved.setEmail("newmail@mailinator.com");
		testManager.update(retrieved);

		String sql = "SELECT `DepartmentName`,`DepartmentEmail` FROM `TeamProject_Departments` WHERE `DepartmentID` = "
				+ retrieved.getId();
		Statement stmt;
		try {
			stmt = testCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.beforeFirst();
			while (rs.next()) {

				assertEquals(rs.getString(1), "NewName");
				assertEquals(rs.getString(2), "newmail@mailinator.com");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void TestUpdateDepartmentWithContactChange() {

		ConMan.insert(testContact);
		ConMan.insert(testContact2);

		testContact = (Contact) ConMan.getContacts().values().toArray()[0];
		testContact2 = (Contact) ConMan.getContacts().values().toArray()[1];
		contactList.clear();
		contactList.add(testContact);
		Department test = new Department(-1, "deptName", "deptEmail@email.com",
				"620-620-2222", "3000east West St", contactList);

		testManager.insert(test);
		Department retrieved = (Department) testManager.getDepartments()
				.values().toArray()[0];

		retrieved.setName("NewName");
		retrieved.setEmail("newmail@mailinator.com");
		contactList.add(testContact2);
		retrieved.setEmployees(contactList);

		testManager.update(retrieved);

		String sql = "SELECT DepartmentNAme,DepartmentEmail,ContactID FROM `TeamProject_Departments`,`TeamProject_ContactsToDepartments` WHERE "
				+ "TeamProject_Departments.DepartmentID = TeamProject_ContactsToDepartments.DepartmentID";

		try {
			Statement stmt = testCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.beforeFirst();

			while (rs.next()) {

				assertEquals(rs.getString(1), "NewName");
				assertEquals(rs.getString(2), "newmail@mailinator.com");

				if (rs.getInt(3) != testContact.getEmployeeID()
						&& rs.getInt(3) != testContact2.getEmployeeID()) {

					fail("Issue with the contact/department relationship");
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void TestDepartmentDeletion() {
		ConMan.insert(testContact);

		testContact = (Contact) ConMan.getContacts().values().toArray()[0];
		contactList.clear();
		contactList.add(testContact);

		Department test = new Department(-1, "deptName", "deptEmail@email.com",
				"620-620-2222", "3000east West St", contactList);

		testManager.insert(test);
		Department retrieved = (Department) testManager.getDepartments()
				.values().toArray()[0];
		final int key = retrieved.getId();
		testManager.delete(key);
		String sql = "SELECT DepartmentNAme,DepartmentEmail,ContactID FROM `TeamProject_Departments`,`TeamProject_ContactsToDepartments` WHERE "
				+ "TeamProject_Departments.DepartmentID = TeamProject_ContactsToDepartments.DepartmentID";

		String sql2 = "SELECT * FROM TeamProject_DepartmentsToReports WHERE DepartmentID = "
				+ key;
		try {
			Statement stmt = testCon.createStatement();
			Statement stmt2 = testCon.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs2 = stmt2.executeQuery(sql2);
			rs.beforeFirst();
			rs2.beforeFirst();
			assertEquals(rs.getFetchSize(), 0);
			assertEquals(rs2.getFetchSize(), 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	public void TestGetDepartment() {

		ConMan.insert(testContact);

		testContact = (Contact) ConMan.getContacts().values().toArray()[0];
		contactList.clear();
		contactList.add(testContact);

		Department test = new Department(-1, "deptName", "deptEmail@email.com",
				"620-620-2222", "3000east West St", contactList);

		testManager.insert(test);
		Department retrieved = (Department) testManager.getDepartments()
				.values().toArray()[0];

		assertEquals(retrieved.getName(), "deptName");
		assertEquals(retrieved.getEmail(), "deptEmail@email.com");
		assertEquals(retrieved.getPhone(), "620-620-2222");

	}

}
