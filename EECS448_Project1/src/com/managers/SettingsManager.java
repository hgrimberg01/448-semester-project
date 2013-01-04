/**
 * Name: SettingsManager.java
 * Description: Managers all information associated with settings locally stored
 * 
 * @author Howard Grimberg
 * Date Created: 12/2/12
 */

package com.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.pojos.Settings;

/**
 * @author hgrimberg
 * 
 * Class for managing Settings objects
 * 
 */

public class SettingsManager {
	private Properties prop;
	private Settings settings;
	private boolean isLoaded = false;
	
	
	/** Asserts that the inputed email is valid
	 * 
	 * @param newSettings		Settings object containing email to verify
	 * @return					True if email is valid, false if email is invalid
	 */
	private boolean isValidEmail(Settings newSettings){
		// Recipient's email ID needs to be mentioned.
	      String to = "test@mailinator.com";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", newSettings.getEmailServerHost());

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(newSettings.getEmailUsername()));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("TEST");

	         // Now set the actual message
	         message.setText("Test");

	         // Send message
	         Transport.send(message);
	         return true;
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	         return false;
	      }
	}
	
	
	/** Asserts that the inputed database is valid
	 * 
	 * @param newSettings		Settings object containing database to verify
	 * @return					True if database is valid, false if database is invalid
	 */
	public boolean isValidDB(Settings newSettings){
		String url = "jdbc:mysql://" +newSettings.getDatabaseHost() + "/" + newSettings.getDatabaseSchema(); // server +
																	// database
																	// string
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			System.out.println(e1.getMessage());
			return false;
		}
		try {
			Connection con = DriverManager.getConnection(url, newSettings.getDatabaseUser(),
					newSettings.getDatabasePassword()); // creates a connection with the server
			assert con.isValid(0);
			
			return true;
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
			return false;
		}

	}

	
	/**
	 * Initializes a SEttingsManagerImproved object 
	 */
	public SettingsManager() {
		prop = new Properties();
	}

	
	/** Stores settings using the Properties class and writes them to a local file
	 * 
	 * @param newSettings		Settings object containing all settings to be stored
	 * 
	 * @return					True if writing to the file was successful, false if it was not successful
	 */
	public boolean writeSettings(Settings newSettings) {
		if(isValidDB(newSettings)){
		
			this.settings = newSettings;
			prop.clear();
			
			String format;
			if (settings.getFormatResults())
				format = "1";
			else
				format = "0";

			prop.setProperty("dbusername", settings.getDatabaseUser());
			prop.setProperty("dbpassword", settings.getDatabasePassword());
			prop.setProperty("dbhost", settings.getDatabaseHost());
			prop.setProperty("database", settings.getDatabaseSchema());
			prop.setProperty("Eserver", settings.getEmailServerHost());
			prop.setProperty("Eusername", settings.getEmailUsername());
			prop.setProperty("HTMLResults", format);

			try {
				File toWrite = new File("database.properties");

				OutputStream out = new FileOutputStream(toWrite);
				prop.store(out,
						"Settings for Google Analytics/Contact Manager Program");
				 
				this.isLoaded = true;
				return true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		else{
			return false;
		}

	}

	
	/** Gets the stored settings
	 * 
	 * @return		Settings object with all settings
	 */
	public Settings getSettings() {

		if (!isLoaded) {
			if (isSettingFileExist()) {
				try {
					// Attempt to load credentials from properties
					prop.load(new FileInputStream("database.properties"));
					String dbUser = prop.getProperty("dbusername");
					String dbPass = prop.getProperty("dbpassword");
					String dbHost = prop.getProperty("dbhost");
					String dbSchema = prop.getProperty("database");
					String emHost = prop.getProperty("Eserver");
					String emUser = prop.getProperty("Eusername");
					String format = prop.getProperty("HTMLResults");
					
					boolean formatOn = true;
					
					if (format != null)
					{
						if (format.equals("0"))
							formatOn = false;
					}
						

					Settings buffer = new Settings(dbHost, dbUser, dbPass,
							dbSchema, emHost, emUser, formatOn);

						this.settings = buffer;
						this.isLoaded = true;
					

				} catch (IOException e) {

				}
			}
		}
		return settings;
	}

	
	/** Gets the value of isLoaded
	 * 
	 * @return		Value of isLoaded
	 */
	public boolean getIsLoaded(){
		return this.isLoaded;
	}
	
	
	/** Asserts that the settings file exists
	 * 
	 * @return		True if the settings file exists, false if it does not exist
	 */
	public boolean isSettingFileExist() {

		File fileCheck = new File("database.properties");

		if (fileCheck.exists()) {
			return true;
		} else {
			return false;
		}

	}

}