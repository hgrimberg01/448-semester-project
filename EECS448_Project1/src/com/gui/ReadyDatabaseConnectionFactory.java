/**
 * Name: ReadyDatabaseConnectionFactory.java
 * Description: Readies the connection to the database
 * 
 * @author Howard Grimberg
 * Date Created: 12/2/12
 */

package com.gui;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.pojos.Settings;

/**
 * @author hgrimberg 
 * Produces ready database connections given settings.
 */
public class ReadyDatabaseConnectionFactory {
	Settings settings;
	Connection conn;

	/**
	 * Initializes database settings
	 * 
	 * @param readySettings
	 *            Settings to be initialized
	 */
	public ReadyDatabaseConnectionFactory(Settings readySettings) {
		this.settings = readySettings;
	}

	/**
	 * Initializes a connection to the database
	 * 
	 * @return Connection conn : Connection to the database
	 */
	public Connection getReadyDatabaseConnection() {
		try {
			String url = "jdbc:mysql://" + settings.getDatabaseHost() + "/"
					+ settings.getDatabaseSchema();

			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(url, settings.getDatabaseUser(),
					settings.getDatabasePassword());

		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
