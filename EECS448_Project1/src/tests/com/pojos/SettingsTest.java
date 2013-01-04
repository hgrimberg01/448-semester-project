package tests.com.pojos;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.pojos.Settings;

public class SettingsTest {
	
	Settings testset;
	
	@Before
	public void initial(){
		testset = new Settings("dbHost", "dbUser", "dbPass",
				"dbSchema", "emHost", "emUser", false);
	}
	
	

	@Test
	public void getDatabaseHostTest(){
		assertEquals("dbHost", testset.getDatabaseHost());
	}
	
	@Test
	public void getDatabaseUsernameTest(){
		assertEquals("dbUser", testset.getDatabaseUser());
	}
	
	
	@Test
	public void getDatabasePasswordTest(){
		assertEquals("dbPass", testset.getDatabasePassword());
		
	}
	
	@Test
	public void getDatabaseSchemaTest(){
		assertEquals("dbSchema",testset.getDatabaseSchema());
	}
	
	@Test
	public void getEmailServerHostTest(){
		assertEquals("emHost", testset.getEmailServerHost());
	}
	
	@Test
	public void getEmailUsernameTest(){
		assertEquals("emUser", testset.getEmailUsername());
	}
	
	@Test
	public void getFormatResultsTest(){
		assertFalse(testset.getFormatResults());
	}
	
	@Test
	public void setFormateResultsTest(){
		testset.setFormatResults(true);
		assertTrue(testset.getFormatResults());
	}
	
	@Test
	public void setEmailUsernameTest(){
		testset.setEmailUsername("newEMuser");
		assertEquals("newEMuser", testset.getEmailUsername());
	}
	
	@Test
	public void setEmailServerHostTest(){
		testset.setEmailServerHost("newEMhost");
		assertEquals("newEMhost", testset.getEmailServerHost());
	}
	
	
	@Test
	public void setDatabaseSchemaTest(){
		testset.setDatabaseSchema("newDBschema");
		assertEquals("newDBschema", testset.getDatabaseSchema());
	}
	
	
	@Test
	public void setDatabasePasswordTest(){
		testset.setDatabasePassword("newDBpass");
		assertEquals("newDBpass", testset.getDatabasePassword());
	}
	
	@Test
	public void setDatabaseHostTest(){
		testset.setDatabaseHost("newDBhost");
		assertEquals("newDBhost", testset.getDatabaseHost());
	}
	
	
}
