/**
 * Name: Settings.java
 * Description: Java object for the settings
 * 
 * @author Howard Grimberg
 * Date Created: 12/2/12
 */

package com.pojos;

/**
 * @author hgrimberg
 * Class containing all possible settings.
 */
public class Settings {

	private String databaseHost;
	private String databaseUser;
	private String databasePassword;
	private String databaseSchema;
	private String databasePort;
	private String emailServerHost;
	private String emailUsername;
	private String emailPassword;
	private String emailPort;
	private boolean formatResults;

	/**
	 * Constructor for Settings
	 * 
	 * @param dbHost
	 * @param dbUser
	 * @param dbPass
	 * @param dbSchema
	 * @param emHost
	 * @param emUser
	 * @param format
	 */
	public Settings(String dbHost, String dbUser, String dbPass,
			String dbSchema, String emHost, String emUser, boolean format) {
		this.databaseHost = dbHost;
		this.databasePassword = dbPass;
		this.databaseUser = dbUser;
		this.databaseSchema = dbSchema;
		this.emailServerHost = emHost;
		this.emailUsername = emUser;
		this.formatResults = format;

	}

	/**
	 * Gets database host URL/IP
	 * @return String
	 */
	public String getDatabaseHost() {
		return databaseHost;
	}

	/**
	 * Sets database host URL/IP
	 * @param databaseHost
	 */
	public void setDatabaseHost(String databaseHost) {
		this.databaseHost = databaseHost;
	}

	/**
	 * Gets database user
	 * @return
	 */
	public String getDatabaseUser() {
		return databaseUser;
	}

	/**
	 * Sets database user
	 * @param databaseUser
	 */
	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	/**
	 * Gets database password
	 * @return String
	 */
	public String getDatabasePassword() {
		return databasePassword;
	}

	/**
	 * Sets database password
	 * @param databasePassword
	 */
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	/**
	 * Gets database scheme(aka database name)
	 * @return String
	 */
	public String getDatabaseSchema() {
		return databaseSchema;
	}

	/**
	 * Sets database schema(aka database name)
	 * @param databaseSchema
	 */
	public void setDatabaseSchema(String databaseSchema) {
		this.databaseSchema = databaseSchema;
	}

	/**
	 * Gets the database port
	 * @return String
	 */
	public String getDatabasePort() {
		return databasePort;
	}

	/**
	 * Sets the database port
	 * @param databasePort
	 */
	public void setDatabasePort(String databasePort) {
		this.databasePort = databasePort;
	}

	/**
	 * Gets the email server URL/IP
	 * @return String
	 */
	public String getEmailServerHost() {
		return emailServerHost;
	}

	/**
	 * Sets the email server URL/IP
	 * @param emailServerHost
	 */
	public void setEmailServerHost(String emailServerHost) {
		this.emailServerHost = emailServerHost;
	}

	/**
	 * Gets the email username
	 * @return String
	 */
	public String getEmailUsername() {
		return emailUsername;
	}

	/**
	 * Sets the email username
	 * @param emailUsername
	 */
	public void setEmailUsername(String emailUsername) {
		this.emailUsername = emailUsername;
	}

	/**
	 * Gets the plaintext email password
	 * @return String
	 */
	public String getEmailPassword() {
		return emailPassword;
	}

	/**
	 * Sets the plaintext email password
	 * @param emailPassword
	 */
	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	/**
	 * Gets the email port
	 * @return String
	 */
	public String getEmailPort() {
		return emailPort;
	}

	/**
	 * Sets the email port
	 * @param emailPort
	 */
	public void setEmailPort(String emailPort) {
		this.emailPort = emailPort;
	}
	
	/**
	 * Gets the format for the results
	 * @return boolean
	 */
	public boolean getFormatResults() {
		return formatResults;
	}

	/**
	 * Sets the format for report results
	 * @param format An integer specifying plain text (0) or HTML (1) output
	 */
	public void setFormatResults(boolean format) {
		this.formatResults = format;
	}

}
