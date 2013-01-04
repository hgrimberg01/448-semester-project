package tests.com.pojos;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import com.pojos.Contact;
import com.pojos.Department;

public class DepartmentTest {

	private Department testDepartment;
	private Vector<Contact> contacts;
	private Contact santa;
	private Contact bunny;
	
	@Before
	public void initDepartment() {
		santa = new Contact(200, "Santa", "Claus", "Pizza Delivery", "santa@northpole.com", "555-101-2020", "", "123 North Pole Lane","None", "1800-12-25");
		bunny = new Contact(100, "Easter", "Bunny", "Janitor", "eb@yahoo.com", "", "", "", "", "");
		
		contacts = new Vector<Contact>();
		contacts.add(santa);
		
		testDepartment = new Department(1, "Marketing", "555-123-3452", "", "3423 South Street Topeka, KS", contacts);
	}
	
	@Test
	public void testDepartmentInit() {
		Department department = new Department();
		
		assertNotNull(department);
	}


	@Test
	public void testGetName() {
		assertEquals(testDepartment.getName(), "Marketing");
	}

	@Test
	public void testSetName() {
		testDepartment.setName("Quality Assurance");
		
		assertEquals(testDepartment.getName(), "Quality Assurance");
	}

	@Test
	public void testSetPhone() {
		testDepartment.setPhone("555-000-8000");
		
		assertEquals(testDepartment.getPhone(), "555-000-8000");
	}

	@Test
	public void testGetMailingAddress() {
		assertEquals(testDepartment.getMailingAddress(), "3423 South Street Topeka, KS");
	}

	@Test
	public void testSetMailingAddress() {
		testDepartment.setMailingAddress("123 First St");
		
		assertEquals(testDepartment.getMailingAddress(), "123 First St");
	}

	@Test
	public void testToString() {
		assertEquals(testDepartment.toString(),testDepartment.getName());
	}

	@Test
	public void testGetEmployees() {
		assertEquals(testDepartment.getEmployees(), contacts);
	}

	@Test
	public void testSetEmployees() {
		Vector<Contact> newContacts = new Vector<Contact>();
		newContacts.add(bunny);
		
		testDepartment.setEmployees(newContacts);
		
		assertEquals(testDepartment.getEmployees(), newContacts);
		
	}

	@Test
	public void testAddEmployee() {
		contacts.add(bunny);
		
		testDepartment.addEmployee(bunny);
		
		assertEquals(testDepartment.getEmployees(), contacts);
	}

	@Test
	public void testGetId() {
		assertEquals(testDepartment.getId(), 1);
	}



}
