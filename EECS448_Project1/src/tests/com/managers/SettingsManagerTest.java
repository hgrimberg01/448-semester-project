package tests.com.managers;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.managers.SettingsManager;
import com.pojos.Settings;

public class SettingsManagerTest {

	SettingsManager SettingsMan;
	Settings settings;
	
	@Before
	public void intial(){
		
		settings = new Settings("mysql.eecs.ku.edu", "chale", "Ao0aU2gV", 
				"chale", "smtp.ku.edu", "s499p797@ku.edu",true);
		SettingsMan = new SettingsManager();
	}
	
	@After
	public void cleanUp(){

		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("database.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prop.setProperty("dbhost", "mysql.eecs.ku.edu");
		prop.setProperty("dbusername", "chale");
		prop.setProperty("dbpassword", "Ao0aU2gV");
		prop.setProperty("database", "chale");
		prop.setProperty("Eserver", "smtp.ku.edu");
		prop.setProperty("Eusername", "s499p797@ku.edu");
		prop.setProperty("HTMLResults", "1");
	}
	
	@Test
	public void TestIsLoaded(){
		assertFalse(SettingsMan.getIsLoaded());
		assertTrue(SettingsMan.writeSettings(settings));
		assertTrue(SettingsMan.getIsLoaded());
	}
	
	@Test
	public void TestGetSettings(){
		SettingsMan.writeSettings(settings);
		Settings compare = SettingsMan.getSettings();
		
		assertEquals(settings.getDatabaseHost(), compare.getDatabaseHost());
		assertEquals(settings.getDatabaseUser(), compare.getDatabaseUser());
		assertEquals(settings.getDatabasePassword(), compare.getDatabasePassword());
		assertEquals(settings.getDatabasePort(), compare.getDatabasePort());
		assertEquals(settings.getDatabaseSchema(), compare.getDatabaseSchema());
		assertEquals(settings.getEmailPassword(), compare.getEmailPassword());
		assertEquals(settings.getEmailPort(), compare.getEmailPort());
		assertEquals(settings.getEmailServerHost(), compare.getEmailServerHost());
		assertEquals(settings.getEmailUsername(), compare.getEmailUsername());
		assertEquals(settings.getFormatResults(), compare.getFormatResults());
	}
	
	@Test
	public void TestIsSettingFileExist() {
		assertTrue(SettingsMan.isSettingFileExist());
	}

}
