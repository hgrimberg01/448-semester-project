/**
 * Name: Department.java
 * Description: Java object for the departments
 * 
 * @author Pho Hale
 * Date Created: 11/8/12
 */

package com.pojos;

import java.util.Vector;

import com.managers.SortableListModel;

/**
 * @author chale 
 * 
 * Class containing all information for a given department
 * 
 */
public class Department {
	private int DeptID;
	private String Name;
	private String Email;
	private String Phone;
	private String MailingAddress;
	private Vector<Contact> Employees;
	private SortableListModel<Contact> departmentContactModel;

	/**
	 * Creates a new Department object
	 * 
	 * @param id
	 *            Department ID number
	 * @param name
	 *            Department name
	 * @param email
	 *            Department email
	 * @param phone
	 *            Department phone
	 * @param address
	 *            Department address
	 * @param employees
	 *            Vector of employees as Contact objects associated with the
	 *            department
	 */
	public Department(final int id, String name, String email, String phone,
			String address, Vector<Contact> employees) {
		this.DeptID = id;
		this.Name = name;
		this.Email = email;
		this.Phone = phone;
		setMailingAddress(address);
		this.Employees = employees;
		if (employees == null) {
			this.Employees = new Vector<Contact>();
		}

	}

	/**
	 * Basic constructor for Department object. Sets Department's name to
	 * "Department Name".
	 * 
	 */
	public Department() {
		this.DeptID = -1;
		this.Name = "Department Name";
		this.Email = "";
		this.Phone = "";
		this.MailingAddress = "";
		this.Employees = new Vector<Contact>();
	}

	/**
	 * Gets the name of the Department object
	 * 
	 * @return Name of department
	 */
	public String getName() {
		return Name;
	}

	/**
	 * Gets the email of the Department object
	 * 
	 * @return Email of department
	 */
	public String getEmail() {
		return Email;
	}

	/**
	 * Gets the phone number of the Department object
	 * 
	 * @return Phone number of department
	 */
	public String getPhone() {
		return Phone;
	}

	/**
	 * Sets the name of the Department object
	 * 
	 * @param name
	 *            Name of the department
	 */
	public void setName(String name) {
		this.Name = name;
	}

	/**
	 * Sets the email of the Department object
	 * 
	 * @param email
	 *            Email of the department
	 */
	public void setEmail(String email) {
		this.Email = email;
	}

	/**
	 * Sets the phone number of the Department object
	 * 
	 * @param phone
	 *            Phone number of the department
	 */
	public void setPhone(String phone) {
		this.Phone = phone;
	}

	/**
	 * Sets the address of the Department object
	 * 
	 * @param address
	 *            Address of the department
	 */
	public void setAddress(String address) {
		setMailingAddress(address);
	}

	/**
	 * Gets the mailing address of the Department object
	 * 
	 * @return Mailing address of the department
	 */
	public String getMailingAddress() {
		return MailingAddress;
	}

	/**
	 * Sets the mailing address for the Department object
	 * 
	 * @param mailingAddress
	 *            Mailing address of the department
	 */
	public void setMailingAddress(String mailingAddress) {
		this.MailingAddress = mailingAddress;
	}

	/**
	 * Returns the name of the Department object. Overrides normal toString()
	 * functionality
	 * 
	 * @return Name of the department
	 */
	public String toString() {
		return Name;
	}

	/**
	 * Returns the vector of Contact objects associated with the department
	 * 
	 * @return
	 */
	public Vector<Contact> getEmployees() {
		return Employees;
	}

	/**
	 * Sets the vector of Contact objects associated to the department
	 * 
	 * @param employees
	 *            Vector of Contact objects
	 */
	public void setEmployees(Vector<Contact> employees) {
		this.Employees = employees;
	}

	/**
	 * Adds a single Contact at the end of the vector of Contacts associated to
	 * the Department
	 * 
	 * @param person
	 *            Contact to be added to the vector
	 */
	public void addEmployee(Contact person) {
		this.Employees.addElement(person);

	}

	/**
	 * Gets the Department ID number
	 * 
	 * @param primaryKey
	 * 
	 * @return Department ID number
	 */
	public int getId() {
		return DeptID;
	}

	/**
	 * Sets the Department id number.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.DeptID = id;
		return;
	}

	/**
	 * Gets a SortableListModel that contains all of the department's contacts.
	 * 
	 * @return
	 */
	public SortableListModel<Contact> getEmbeddedContactsModel() {

		departmentContactModel = new SortableListModel<Contact>();
		for (Contact addToModel : getEmployees()) {
			departmentContactModel.addElement(addToModel);
		}
		departmentContactModel.sort();
		return departmentContactModel;
	}

}