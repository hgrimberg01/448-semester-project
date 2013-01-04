package tests.com.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Properties;

import javax.swing.DefaultListModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.managers.ContactManager;
import com.managers.SortableListModel;
import com.pojos.Contact;

public class ContactManagerTest {
	Connection testCon;
	ContactManager testManager;
	String testDB = "spolsley"; //hardcoded test database
	
	@Before
	public void initConnection() {
		//Initialize connection
		String user;
		String password;
		
		Properties prop = new Properties();

			try {
				//Attempt to load credentials from properties
				prop.load(new FileInputStream("database.properties"));
				
				user = prop.getProperty("dbusername");
				password = prop.getProperty("dbpassword");
			} catch (IOException e) {
				//Else revert to known user
				user = "chale";
				password = "Ao0aU2gV";
			}
		
		String url = "jdbc:mysql://mysql.eecs.ku.edu/" + testDB;
		
		try {
			testCon = DriverManager.getConnection(url, user,password);
			testManager = new ContactManager(testCon);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		//Clear table
		String clearCommand = "TRUNCATE TABLE TeamProject_Contacts";
		
		try {
			Statement stmt = testCon.createStatement();
			stmt.executeQuery(clearCommand);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@After
	public void clearTable() {
		String clearCommand = "TRUNCATE TABLE TeamProject_Contacts";
		
		try {
			Statement stmt = testCon.createStatement();
			stmt.executeQuery(clearCommand);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void testInsertContact() {
		Contact test = new Contact(-1,"Santa", "Claus", "Toy Maker", "santa@northpole.com", "555-101-2020", "", "123 North Pole Lane","None","Jan 27");
		testManager.insert(test);
		String testCommand = "SELECT * FROM TeamProject_Contacts WHERE FirstName='Santa';";

		try {
			
			Statement stmt = testCon.createStatement();
			ResultSet rs = stmt.executeQuery(testCommand);
			while (rs.next())
			{
				assertEquals(rs.getString("FirstName"),"Santa");
				assertEquals(rs.getString("LastName"),"Claus");
				assertEquals(rs.getString("JobTitle"),"Toy Maker");
				assertEquals(rs.getString("Email"),"santa@northpole.com");
				assertEquals(rs.getString("WorkPhone"),"555-101-2020");
				assertEquals(rs.getString("HomePhone"),"");
				assertEquals(rs.getString("WorkAddress"),"123 North Pole Lane");
				assertEquals(rs.getString("HomeAddress"),"None");
				assertEquals(rs.getString("Birthday"),"Jan 27");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testInsertMultipleContacts() {
		Contact insert1  = new Contact(-1,"Santa", "K Claus", "Toy Maker", "santa@northpole.com", "555-101-2020", "", "123 North Pole Lane","None","Jan 27");
		Contact insert2  = new Contact(-1,"Iman", "Elf", "Confidential", "santa@northpole.com", "555-101-2025", "555-555-5555", "123 North Pole Lane","123 North Pole Lane","Jun 4");
		testManager.insert(insert1);
		testManager.insert(insert2);
		
		String testCommand = "SELECT * FROM TeamProject_Contacts;";

		try {
		
		Statement stmt = testCon.createStatement();
		ResultSet rs = stmt.executeQuery(testCommand);
		while (rs.next())
		{
			assertEquals(rs.getString("Email"),"santa@northpole.com");
			assertEquals(rs.getString("WorkAddress"),"123 North Pole Lane");
		}
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testUpdate() {
		Contact test = new Contact(-1,"Santa", "Claus", "Toy Maker", "santa@northpole.com", "555-101-2020", "", "123 North Pole Lane","None","Jan 27");
		testManager.insert(test);		
		String testCommand = "SELECT * FROM TeamProject_Contacts;";
		int Id = -1;

		try {
		
		Statement stmt = testCon.createStatement();
		ResultSet rs = stmt.executeQuery(testCommand);
		while (rs.next())
		{
			assertEquals(rs.getString("Email"),"santa@northpole.com");
			Id = rs.getInt("ContactID");
		}
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		if (Id == -1)
		{
			fail("Could not find id");
		}
		
		Contact updatedSanta = new Contact(Id, "Santa", "Claus", "Toy Maker", "sclaus@yahoo.com", "555-101-2020", "", "123 North Pole Lane","None","Jan 27");
		
		testManager.update( updatedSanta); 
		
		try {
			
		Statement stmt = testCon.createStatement();
		ResultSet rs = stmt.executeQuery(testCommand);
		while (rs.next())
		{
			assertEquals(rs.getString("Email"),"sclaus@yahoo.com");
		}
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testUpdateContact() {
		Contact test = new Contact(-1,"Santa", "Claus", "Toy Maker", "santa@northpole.com", "555-101-2020", "", "123 North Pole Lane","None","Jan 27");
		testManager.insert(test);		
		String testCommand = "SELECT * FROM TeamProject_Contacts;";
		int Id = -1;

		try {
		
		Statement stmt = testCon.createStatement();
		ResultSet rs = stmt.executeQuery(testCommand);
		while (rs.next())
		{
			assertEquals(rs.getString("Email"),"santa@northpole.com");
			Id = rs.getInt("ContactID");
		}
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		if (Id == -1)
		{
			fail("Could not find id");
		}
		
		testManager.updateContact(Id, "Santa", "Claus", "Toy Maker", "sclaus@yahoo.com", "555-101-2020", "", "123 North Pole Lane","None","Jan 27"); 
		
		try {
			
		Statement stmt = testCon.createStatement();
		ResultSet rs = stmt.executeQuery(testCommand);
		while (rs.next())
		{
			assertEquals(rs.getString("Email"),"sclaus@yahoo.com");
		}
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
	
	
	@Test
	public void testGetListModelContacts() {
		SortableListModel<Contact> testList = new SortableListModel<Contact>();
		
		Contact test = new Contact(-1,"Santa", "Claus", "Toy Maker", "santa@northpole.com", "555-101-2020", "", "123 North Pole Lane","None","Jan 27");
		testManager.insert(test);
		testList = testManager.getListModelContacts();
		assertNotNull(testList);
	}
	
	
	@Test
	public void testGetContacts() {
		
		Contact testinsert = new Contact(-1,"Iman", "Elf", "Confidential", "santa@northpole.com", "555-101-2025", "555-555-5555", "123 North Pole Lane","123 North Pole Lane","Jun 4");
		testManager.insert(testinsert);
		
		Collection<Contact> test = testManager.getContacts().values();
		
		for (Contact c : test)
		{
			assertEquals(c.getFullName(),"Iman Elf");
			assertEquals(c.getWorkAddress(), "123 North Pole Lane");
		}
	}

}
