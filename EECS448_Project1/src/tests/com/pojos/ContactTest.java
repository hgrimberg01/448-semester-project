package tests.com.pojos;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.pojos.Contact;

public class ContactTest {
	
	private Contact testContact;
	
	@Before
	public void initContact() {
		testContact = new Contact(1, "Santa", "Claus", "Pizza Delivery", "santa@northpole.com", "555-101-2020", "", "123 North Pole Lane","None", "1800-12-25");
	}

	@Test
	public void testContact() {
		assertEquals(testContact.getEmployeeID(), 1);
		assertEquals(testContact.getFullName(), "Santa Claus");
		assertEquals(testContact.getBirthDate(), "1800-12-25");
	}

	@Test
	public void testUpdateFields() {
		testContact.updateFields("Saint", "Nicholas", "Gift Giving", "", "", "555-123-4567", "", "", "");
		
		assertEquals(testContact.getFullName(), "Saint Nicholas");
		assertEquals(testContact.getJobTitle(), "Gift Giving");
		assertEquals(testContact.getHomePhone(), "555-123-4567");
	}



	@Test
	public void testGetFirstName() {
		assertEquals(testContact.getFirstName(), "Santa");
	}

	@Test
	public void testGetLastName() {
		assertEquals(testContact.getLastName(), "Claus");
	}

	@Test
	public void testGetFullName() {
		assertEquals(testContact.getFullName(), "Santa Claus");
	}

	@Test
	public void testGetJobTitle() {
		assertEquals(testContact.getJobTitle(), "Pizza Delivery");
	}

	@Test
	public void testGetEmail() {
		assertEquals(testContact.getEmail(), "santa@northpole.com");
	}

	@Test
	public void testGetWorkPhone() {
		assertEquals(testContact.getWorkPhone(), "555-101-2020");
	}

	@Test
	public void testGetHomePhone() {
		assertEquals(testContact.getHomePhone(), "");
	}

	@Test
	public void testGetWorkAddress() {
		assertEquals(testContact.getWorkAddress(), "123 North Pole Lane");
	}

	@Test
	public void testGetHomeAddress() {
		assertEquals(testContact.getHomeAddress(), "None");
	}

	@Test
	public void testGetBirthDate() {
		assertEquals(testContact.getBirthDate(), "1800-12-25");
	}

	@Test
	public void testSetFirstName() {
		testContact.setFirstName("Easter");
		
		assertEquals(testContact.getFirstName(), "Easter");
	}

	@Test
	public void testSetLastName() {
		testContact.setLastName("Bunny");
		
		assertEquals(testContact.getLastName(), "Bunny");
	}

	@Test
	public void testSetJobTitle() {
		testContact.setJobTitle("Gift Giving");
		
		assertEquals(testContact.getJobTitle(), "Gift Giving");
	}

	@Test
	public void testSetEmail() {
		testContact.setEmail("sclaus@gmail.com");
		
		assertEquals(testContact.getEmail(), "sclaus@gmail.com");
	}

	@Test
	public void testSetWorkPhone() {
		testContact.setWorkPhone("555-467-2682");
		
		assertEquals(testContact.getWorkPhone(), "555-467-2682");
	}

	@Test
	public void testSetHomePhone() {
		testContact.setHomePhone("555-467-2682");
		
		assertEquals(testContact.getHomePhone(), "555-467-2682");
	}

	@Test
	public void testSetWorkAddress() {
		testContact.setWorkAddress("102 Santa's Workshop");
		
		assertEquals(testContact.getWorkAddress(), "102 Santa's Workshop");
	}

	@Test
	public void testSetHomeAddress() {
		testContact.setHomeAddress("102 Santa's Workshop");
		
		assertEquals(testContact.getHomeAddress(), "102 Santa's Workshop");
	}

	@Test
	public void testSetBirthDate() {
		testContact.setBirthDate("2010-01-01");
		
		assertEquals(testContact.getBirthDate(), "2010-01-01");
	}

	@Test
	public void testGetEmployeeID() {
		assertEquals(testContact.getEmployeeID(), 1);
	}


	@Test
	public void testToString() {
		assertEquals(testContact.toString(), testContact.getFullName());
	}

}
