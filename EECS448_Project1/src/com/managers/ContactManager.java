/**
 * Name: ContactManager.java
 * Description: Manages all information associated with contacts on the database
 * 
 * @author Howard Grimberg
 * Date Created: 11/9/12
 */

package com.managers;

import com.pojos.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author hgrimberg
 * 
 * Class for managing Contact objects
 * 
 */
public class ContactManager {
	private Connection connection;
	private HashMap<Integer, Contact> contacts;
	private SortableListModel<Contact> contactsModel;
	private SortableListModel<Contact> contactsSearchModel;

	private boolean isDirty = true;

	/**
	 * Initializes and object of ContactManager
	 * 
	 * @param con
	 *            Connection; connection to the database
	 */
	public ContactManager(java.sql.Connection con) {
		this.connection = con;
		this.contacts = new HashMap<Integer, Contact>();
		this.loadContacts();
		this.contactsModel = new SortableListModel<Contact>();
		this.contactsSearchModel = new SortableListModel<Contact>();

	}

	/**
	 * Calls a function that inserts the Contact object into the database
	 * 
	 * FIXME:Remove the useless reading and rebuilding of object.
	 * 
	 * @param contact
	 *            Contact object to insert
	 */
	public void insert(Contact contact) {

		String FirstName = contact.getFirstName();
		String LastName = contact.getLastName();
		String JobTitle = contact.getJobTitle();
		String Email = contact.getEmail();
		String WorkPhone = contact.getWorkPhone();
		String HomePhone = contact.getHomePhone();
		String WorkAddress = contact.getWorkAddress();
		String HomeAddress = contact.getHomeAddress();
		String birthday = contact.getBirthDate();

		try {
			assert connection.isValid(0);
			PreparedStatement prepStmt = connection
					.prepareStatement(
							"INSERT INTO `TeamProject_Contacts` (`FirstName` ,`LastName` ,`JobTitle` ,`Email` ,`WorkPhone` ,`HomePhone` ,`WorkAddress` ,`HomeAddress` ,`Birthday`)VALUES(?,?,?,?,?,?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);

			prepStmt.setString(1, FirstName);
			prepStmt.setString(2, LastName);
			prepStmt.setString(3, JobTitle);
			prepStmt.setString(4, Email);
			prepStmt.setString(5, WorkPhone);
			prepStmt.setString(6, HomePhone);
			prepStmt.setString(7, WorkAddress);
			prepStmt.setString(8, HomeAddress);
			prepStmt.setString(9, birthday);
			prepStmt.execute();

			ResultSet rs = prepStmt.getGeneratedKeys();

			int key = -1;
			if (rs != null && rs.next()) {
				key = rs.getInt(1);
			}
			Contact newContact = new Contact(key, FirstName, LastName,
					JobTitle, Email, WorkPhone, HomePhone, WorkAddress,
					HomeAddress, birthday);
			contacts.put(key, newContact);
			isDirty = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	

	/**
	 * Updates a pre-existing contact in the database from the given Contact
	 * object
	 * 
	 * FIXME:Remove the useless reading and rebuilding of object.
	 * 
	 * @param contact
	 *            Contact object to update in the database
	 */
	public void update(Contact contact) {
		int primaryKey = contact.getEmployeeID();
		String FirstName = contact.getFirstName();
		String LastName = contact.getLastName();
		String JobTitle = contact.getJobTitle();
		String Email = contact.getEmail();
		String WorkPhone = contact.getWorkPhone();
		String HomePhone = contact.getHomePhone();
		String WorkAddress = contact.getWorkAddress();
		String HomeAddress = contact.getHomeAddress();
		String birthday = contact.getBirthDate();
		try {
			assert connection.isValid(0);
			PreparedStatement prepStmt = connection
					.prepareStatement("UPDATE `TeamProject_Contacts` SET `FirstName` = ?,`LastName`= ?,"
							+ "`JobTitle` = ?,`Email` = ?,`WorkPhone` = ?,`HomePhone` = ?,`WorkAddress` = ?,`HomeAddress` = ?,`Birthday` = ? WHERE `TeamProject_Contacts`.`ContactID` = ?;");
			prepStmt.setString(1, FirstName);
			prepStmt.setString(2, LastName);
			prepStmt.setString(3, JobTitle);
			prepStmt.setString(4, Email);
			prepStmt.setString(5, WorkPhone);
			prepStmt.setString(6, HomePhone);
			prepStmt.setString(7, WorkAddress);
			prepStmt.setString(8, HomeAddress);
			prepStmt.setString(9, birthday);
			prepStmt.setInt(10, primaryKey);
			prepStmt.execute();

			Contact toUpdate = getContacts().get(primaryKey);
			toUpdate.setBirthDate(birthday);
			toUpdate.setJobTitle(JobTitle);
			toUpdate.setEmail(Email);
			toUpdate.setFirstName(FirstName);
			toUpdate.setLastName(LastName);
			toUpdate.setHomeAddress(HomeAddress);
			toUpdate.setHomePhone(HomePhone);
			toUpdate.setWorkAddress(WorkAddress);
			toUpdate.setWorkPhone(WorkPhone);
			contacts.put(primaryKey, toUpdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Deletes all records of a Contact from the database
	 * 
	 * @param primaryKey
	 *            Primary key used to identify the Contact
	 */
	public void delete(int primaryKey) {
		try {
			assert connection.isValid(0);

			PreparedStatement deleteRelation = connection
					.prepareStatement("DELETE FROM `TeamProject_ContactsToDepartments` WHERE `ContactID` = ? ;");
			deleteRelation.setInt(1, primaryKey);
			deleteRelation.execute();

			PreparedStatement deleteFromReports = connection
					.prepareStatement("DELETE FROM `TeamProject_ContactsToReports` WHERE `ContactID` = ?");
			deleteFromReports.setInt(1, primaryKey);
			deleteFromReports.execute();

			PreparedStatement prepStmt = connection
					.prepareStatement("DELETE FROM `TeamProject_Contacts` WHERE `ContactID` = ? ;");
			prepStmt.setInt(1, primaryKey);
			prepStmt.execute();

			contactsModel.removeElement(getContact(primaryKey));
			contactsSearchModel.removeElement(getContact(primaryKey));
			contacts.remove(primaryKey);
			System.gc();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Updates a specified Contact in the database and all information
	 * associated to it
	 * 
	 * @param primaryKey
	 * @param FirstName
	 * @param LastName
	 * @param JobTitle
	 * @param Email
	 * @param WorkPhone
	 * @param HomePhone
	 * @param WorkAddress
	 * @param HomeAddress
	 * @param birthday
	 */
	public void updateContact(int primaryKey, String FirstName,
			String LastName, String JobTitle, String Email, String WorkPhone,
			String HomePhone, String WorkAddress, String HomeAddress,
			String birthday) {

		try {
			assert connection.isValid(0);
			PreparedStatement prepStmt = connection
					.prepareStatement("UPDATE `TeamProject_Contacts` SET `FirstName` = ?,`LastName`= ?,"
							+ "`JobTitle` = ?,`Email` = ?,`WorkPhone` = ?,`HomePhone` = ?,`WorkAddress` = ?,`HomeAddress` = ?,`Birthday` = ? WHERE `TeamProject_Contacts`.`ContactID` = ?;");
			prepStmt.setString(1, FirstName);
			prepStmt.setString(2, LastName);
			prepStmt.setString(3, JobTitle);
			prepStmt.setString(4, Email);
			prepStmt.setString(5, WorkPhone);
			prepStmt.setString(6, HomePhone);
			prepStmt.setString(7, WorkAddress);
			prepStmt.setString(8, HomeAddress);
			prepStmt.setString(9, birthday);
			prepStmt.setInt(10, primaryKey);
			prepStmt.execute();

			Contact toUpdate = getContacts().get(primaryKey);
			toUpdate.setBirthDate(birthday);
			toUpdate.setJobTitle(JobTitle);
			toUpdate.setEmail(Email);
			toUpdate.setFirstName(FirstName);
			toUpdate.setLastName(LastName);
			toUpdate.setHomeAddress(HomeAddress);
			toUpdate.setHomePhone(HomePhone);
			toUpdate.setWorkAddress(WorkAddress);
			toUpdate.setWorkPhone(WorkPhone);
			contacts.put(primaryKey, toUpdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Loads all Contacts into a HashMap<Integer, Contact>
	 * 
	 * @return Returns the HashMap<Integer, Contact> with Contacts inserted
	 */
	public HashMap<Integer, Contact> getContacts() {

		if (isDirty) {
			loadContacts();
			isDirty = false;
			return contacts;
		} else {
			return contacts;
		}

	}

	/**
	 * Gets a SortableListModel of all Contacts
	 * 
	 * @return Contacts list model
	 */
	public SortableListModel<Contact> getListModelContacts() {

		contactsModel.removeAllElements();
		Iterator<Contact> makeList = getContacts().values().iterator();
		while (makeList.hasNext()) {
			contactsModel.addElement(makeList.next());
		}
		contactsModel.sort();
		return contactsModel;
	}

	/**
	 * Gets the SortableListModel of the searched Contact
	 * 
	 * @return Searched Contacts list model
	 */
	public SortableListModel<Contact> getSearchListModelContacts() {
		return contactsSearchModel;
	}

	/**
	 * Gets a Contact given the Contact's ID number
	 * 
	 * @param contactId
	 *            ID number of the desired Contact
	 * @return Contact object
	 */
	public Contact getContact(Integer contactId) {
		if (!isDirty) {
			return getContacts().get(contactId);
		} else {
			loadContacts();
			return getContact(contactId);
		}

	}

	/**
	 * Loads all contacts in the database into the Contact HashMap
	 */
	private void loadContacts() {
		try {
			assert connection.isValid(0);
			PreparedStatement allContacts = connection
					.prepareStatement("SELECT * FROM `TeamProject_Contacts`;");
			allContacts.execute();
			ResultSet allContactsResults = allContacts.getResultSet();
			allContactsResults.beforeFirst();
			this.contacts.clear();

			while (allContactsResults.next()) {

				Contact newContact = new Contact(
						allContactsResults.getInt("ContactID"),
						allContactsResults.getString("FirstName"),
						allContactsResults.getString("LastName"),
						allContactsResults.getString("JobTitle"),
						allContactsResults.getString("Email"),
						allContactsResults.getString("WorkPhone"),
						allContactsResults.getString("HomePhone"),
						allContactsResults.getString("WorkAddress"),
						allContactsResults.getString("HomeAddress"),
						allContactsResults.getString("Birthday"));
				this.contacts.put(allContactsResults.getInt("ContactID"),
						newContact);

			}
			this.isDirty = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	/**
	 * Searches through all Contacts in the database using a given string.
	 * Search is front-matching.
	 * 
	 * @param searchStr
	 *            String to search for
	 * @return SortableListModel of all Contacts that match the search string
	 */
	public SortableListModel<Contact> searchContactsByName(String searchStr) {
		contactsSearchModel.removeAllElements();

		try {
			assert connection.isValid(0);
			PreparedStatement searchContacts;
			String[] searchStrings = searchStr.split(" ");
			if ((searchStr.contains(" ")) && (searchStrings.length > 1)) {
				searchContacts = connection
						.prepareStatement("SELECT ContactID FROM `TeamProject_Contacts` WHERE "
								+ "FirstName LIKE '"
								+ searchStrings[0]
								+ "%'"
								+ "and LastName LIKE '"
								+ searchStrings[1]
								+ "%';");
			} else {
				searchContacts = connection
						.prepareStatement("SELECT ContactID FROM `TeamProject_Contacts` WHERE "
								+ "FirstName LIKE '"
								+ searchStrings[0]
								+ "%'"
								+ "or LastName LIKE '"
								+ searchStrings[0]
								+ "%';");
			}
			searchContacts.execute();

			ResultSet searchContactResults = searchContacts.getResultSet();
			searchContactResults.beforeFirst();

			while (searchContactResults.next()) {
				contactsSearchModel.addElement(this
						.getContact(searchContactResults.getInt("ContactID")));
			}
			contactsSearchModel.sort();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contactsSearchModel;
	}

}
