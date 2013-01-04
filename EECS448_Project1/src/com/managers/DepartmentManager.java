/**
 * Name: DepartmentManager.java
 * Description: Manages all information associated with departments on the database
 * 
 * @author Howard Grimberg
 * Date Created: 11/12/12
 */

package com.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.pojos.Contact;
import com.pojos.Department;

/**
 * @author hgrimberg
 * 
 * Class for managing Department objects
 * 
 */
public class DepartmentManager {
	private Connection connection;
	private final ContactManager contactManager;
	private HashMap<Integer, Department> departments;
	private SortableListModel<Department> departmentsModel;
	private SortableListModel<Department> departmentsSearchModel;

	private boolean isDirty = true;

	/**
	 * Initializes a DepartmentManager object
	 * 
	 * @param con
	 *            Connection to the database
	 * @param cmanage
	 *            ContactManager object
	 */
	public DepartmentManager(java.sql.Connection con, ContactManager cmanage) {
		this.connection = con;
		this.departments = new HashMap<Integer, Department>();
		this.contactManager = cmanage;
		this.loadDepartments();
		departmentsSearchModel = new SortableListModel<Department>();
		departmentsModel = new SortableListModel<Department>();
	}

	/**
	 * Loads all Departments in the database into a HashMap<Integer,
	 * Departments>
	 */
	private void loadDepartments() {

		try {
			assert connection.isValid(0);
			PreparedStatement allDepartments = connection
					.prepareStatement("SELECT * FROM `TeamProject_Departments`;");
			allDepartments.execute();
			ResultSet allDepartmentsResults = allDepartments.getResultSet();
			allDepartmentsResults.beforeFirst();
			departments.clear();

			while (allDepartmentsResults.next()) {

				Department temp = new Department(
						allDepartmentsResults.getInt("DepartmentID"),
						allDepartmentsResults.getString("DepartmentName"),
						allDepartmentsResults.getString("DepartmentEmail"),
						allDepartmentsResults.getString("DepartmentPhone"),
						allDepartmentsResults
								.getString("DepartmentMailingAddress"),
						getDepartmentRelations(allDepartmentsResults
								.getInt("DepartmentID")));
				departments.put(allDepartmentsResults.getInt("DepartmentID"),
						temp);
			}
			isDirty = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;

	}

	/**
	 * Inserts a new department.
	 * 
	 * @param insert
	 */
	public void insert(Department insert) {

		String deptName = insert.getName();
		String deptEmail = insert.getEmail();
		String deptPhone = insert.getPhone();
		String deptMailAddress = insert.getMailingAddress();
		Vector<Contact> deptContacts = insert.getEmployees();

		try {
			assert connection.isValid(0);
			PreparedStatement insertDept = connection
					.prepareStatement(
							"INSERT INTO `TeamProject_Departments` (`DepartmentID`, `DepartmentName`, `DepartmentEmail`, `DepartmentPhone`, `DepartmentMailingAddress`) VALUES (NULL, ?,?,?,?);",
							Statement.RETURN_GENERATED_KEYS);
			insertDept.setString(1, deptName);
			insertDept.setString(2, deptEmail);
			insertDept.setString(3, deptPhone);
			insertDept.setString(4, deptMailAddress);

			insertDept.execute();

			ResultSet keys = insertDept.getGeneratedKeys();
			keys.first();
			int key = keys.getInt(1);

			insertDepartmentRelations(key, deptContacts);
			Department temp = new Department(key, deptName, deptEmail,
					deptPhone, deptMailAddress, deptContacts);

			departments.put(key, temp);
			isDirty = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets the Contacts that are associated to the Department and puts them
	 * into a Vector of Contact objects
	 * 
	 * @param departmentID
	 *            ID of the Department
	 * @return Vector of Contacts associated with the department
	 */
	private Vector<Contact> getDepartmentRelations(int departmentID) {

		try {
			PreparedStatement contactToDepartment = connection
					.prepareStatement("SELECT `ContactID` FROM  `TeamProject_ContactsToDepartments` WHERE `DepartmentId` = ?;");
			contactToDepartment.setInt(1, departmentID);
			contactToDepartment.execute();

			ResultSet contactsInDepartment = contactToDepartment.getResultSet();
			contactsInDepartment.beforeFirst();
			Vector<Contact> deptContacts = new Vector<Contact>();
			while (contactsInDepartment.next()) {

				deptContacts.addElement(contactManager
						.getContact(contactsInDepartment.getInt("ContactID")));
			}
			return deptContacts;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Inserts new Contacts into the Department (creates new associations
	 * between a Contact and Department)
	 * 
	 * @param departmentID
	 *            ID of the Department
	 * @param deptContacts
	 *            Vector of Contacts to add
	 */
	private void insertDepartmentRelations(int departmentID,
			Vector<Contact> deptContacts) {

		Iterator<Contact> contactInsert = deptContacts.iterator();

		while (contactInsert.hasNext()) {

			try {
				assert connection.isValid(0);
				PreparedStatement insertContactToDepartments = connection
						.prepareStatement("INSERT INTO `TeamProject_ContactsToDepartments` (`RowID`, `ContactID`, `DepartmentID`) VALUES (NULL, ?, ?);");
				insertContactToDepartments.setInt(1, contactInsert.next()
						.getEmployeeID());
				insertContactToDepartments.setInt(2, departmentID);
				insertContactToDepartments.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * Deletes all relations associated with a specified department
	 * 
	 * @param departmentID
	 *            Department chosen to purge
	 */
	private void purgeDepartmentRelations(int departmentID) {

		try {
			assert connection.isValid(0);

			PreparedStatement prepStmt = connection
					.prepareStatement("DELETE FROM `TeamProject_ContactsToDepartments` WHERE `DepartmentID` = ? ;");
			prepStmt.setInt(1, departmentID);
			prepStmt.execute();

			System.gc();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	/**
	 * Deletes a Department and all associated relations
	 * 
	 * @param primaryKey
	 *            Primary key of the Department to delete
	 */
	public void delete(int primaryKey) {

		try {
			assert connection.isValid(0);
			purgeDepartmentRelations(primaryKey);

			PreparedStatement deleteFromReports = connection
					.prepareStatement("DELETE FROM `TeamProject_DepartmentsToReports` WHERE `DepartmentID` = ?");
			deleteFromReports.setInt(1, primaryKey);
			deleteFromReports.execute();

			PreparedStatement prepStmt = connection
					.prepareStatement("DELETE FROM `TeamProject_Departments` WHERE `DepartmentID` = ? ;");
			prepStmt.setInt(1, primaryKey);
			prepStmt.execute();

			departmentsModel.removeElement(getDepartment(primaryKey));
			departments.remove(primaryKey);
			System.gc();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}

	/**
	 * Updates a specified Department in the database and all information
	 * associated to it
	 * 
	 * @param updated
	 *            Department to be updated
	 */
	public void update(Department updated) {
		int primaryKey = updated.getId();

		try {
			assert connection.isValid(0);

			purgeDepartmentRelations(primaryKey);
			insertDepartmentRelations(primaryKey, updated.getEmployees());

			PreparedStatement prepStmt = connection
					.prepareStatement("UPDATE `TeamProject_Departments` SET `DepartmentName` = ? ,`DepartmentEmail` = ?,`DepartmentPhone` = ?,`DepartmentMailingAddress` = ? WHERE `DepartmentID` = ? ;");
			prepStmt.setString(1, updated.getName());
			prepStmt.setString(2, updated.getEmail());
			prepStmt.setString(3, updated.getPhone());
			prepStmt.setString(4, updated.getMailingAddress());
			prepStmt.setInt(5, primaryKey);
			prepStmt.execute();
			departments.put(primaryKey, updated);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	};

	/**
	 * Gets all Departments and loads them into a HashMap<Integer, Departments>
	 * 
	 * @return HashMap containing all Departments
	 */
	public HashMap<Integer, Department> getDepartments() {
		if (isDirty) {
			loadDepartments();
			return getDepartments();
		} else {
			return departments;
		}
	}

	/**
	 * Gets a Department given an ID
	 * 
	 * @param deptID
	 *            ID of desired Department
	 * @return Department object with the matching ID
	 */
	public Department getDepartment(int deptID) {
		return departments.get(deptID);
	}

	/**
	 * Gets the SortableListModel containing all Departments
	 * 
	 * @return SortableListModel containing all Departments
	 */
	public SortableListModel<Department> getListModelDepartment() {

		departmentsModel = new SortableListModel<Department>();

		Iterator<Department> makeList = this.getDepartments().values()
				.iterator();
		while (makeList.hasNext()) {
			departmentsModel.addElement(makeList.next());
		}
		departmentsModel.sort();
		return departmentsModel;
	}

	/**
	 * Searches all Departments in the database using a given string. Search is
	 * front-matching.
	 * 
	 * @param searchStr			String used to search
	 *            
	 * @return 					SortableListModel of all Departments that match the search string
	 */
	public SortableListModel<Department> searchDepartmentsByName(
			String searchStr) {
		departmentsSearchModel.removeAllElements();

		try {
			assert connection.isValid(0);

			PreparedStatement searchDepartments = connection
					.prepareStatement("SELECT DepartmentID FROM `TeamProject_Departments` WHERE "
							+ "DepartmentName LIKE '%" + searchStr + "%'");
			searchDepartments.execute();

			ResultSet searchDepartmentResults = searchDepartments
					.getResultSet();
			searchDepartmentResults.beforeFirst();

			while (searchDepartmentResults.next()) {
				departmentsSearchModel.addElement(this
						.getDepartment(searchDepartmentResults
								.getInt("DepartmentID")));
			}
			departmentsSearchModel.sort();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return departmentsSearchModel;
	}

	/**
	 * Reloads the Contact-to-Department relations
	 */
	public void reloadDepartmentContactRelations() {
		for (Department reload : departments.values()) {
			reload.setEmployees(getDepartmentRelations(reload.getId()));
		}
		return;
	}
}
