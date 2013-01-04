/**
 * Name: Contact.java
 * Description: Java object for the contacts
 * 
 * @author Allison Moses
 * Date Created: 11/7/12
 */

package com.pojos;

/**
 * @author amoses
 * 
 * Class containing all information for a contact.
 *
 */
public class Contact {
	private int EmployeeID;
	private String FirstName;
	private String LastName;
	private String JobTitle;
	private String Email;
	private String WorkPhone;
	private String HomePhone;
	private String WorkAddress;
	private String HomeAddress;
	private String BirthDate;

	/**
	 * Initializes a new Contact object.
	 * 
	 * @param id
	 *            <int> Employee ID number
	 * @param fname
	 *            <String> Employee first name
	 * @param lname
	 *            <String> Employee last name
	 * @param job
	 *            <String> Employee job title
	 * @param email
	 *            <String> Employee email address
	 * @param wphone
	 *            <String> Employee work phone number
	 * @param hphone
	 *            <String> Employee home phone number
	 * @param waddress
	 *            <String> Employee work address
	 * @param haddress
	 *            <String> Employee home address
	 * @param bdate
	 *            <String> Employee birth date
	 * @return none
	 */
	public Contact(final int id, String fname, String lname, String job,
			String email, String wphone, String hphone, String waddress,
			String haddress, String bdate) {
		this.EmployeeID = id;
		this.FirstName = fname;
		this.LastName = lname;
		this.JobTitle = job;
		this.Email = email;
		this.WorkPhone = wphone;
		this.HomePhone = hphone;
		this.WorkAddress = waddress;
		this.HomeAddress = haddress;
		this.BirthDate = bdate;
	}

	/**
	 * Initializes a new Contact object with only FirstName set to "Contact" and LastName set to "Name"
	 * 
	 **/
	public Contact() {
		this.EmployeeID = -1;
		this.FirstName = "Contact";
		this.LastName = "Name";
		this.JobTitle = "";
		this.Email = "";
		this.WorkPhone = "";
		this.HomePhone = "";
		this.WorkAddress = "";
		this.HomeAddress = "";
		this.BirthDate = "";
	}

	/**
	 * Updates all fields for the Contact object except for ID number
	 * 
	 * @param fname
	 *            <String> Employee first name
	 * @param lname
	 *            <String> Employee last name
	 * @param job
	 *            <String> Employee job title
	 * @param email
	 *            <String> Employee email address
	 * @param wphone
	 *            <String> Employee work phone number
	 * @param hphone
	 *            <String> Employee home phone number
	 * @param waddress
	 *            <String> Employee work address
	 * @param haddress
	 *            <String> Employee home address
	 * @param bdate
	 *            <String> Employee birth date
	 * 
	 * @return none
	 */
	public void updateFields(String fname, String lname, String job,
			String email, String wphone, String hphone, String waddress,
			String haddress, String bdate) {
		this.FirstName = fname;
		this.LastName = lname;
		this.JobTitle = job;
		this.Email = email;
		this.WorkPhone = wphone;
		this.HomePhone = hphone;
		this.WorkAddress = waddress;
		this.HomeAddress = haddress;
		this.BirthDate = bdate;
	}

	/**
	 * Gets FirstName of the Contact object
	 * 
	 * @return First name of employee
	 */
	public String getFirstName() {
		return FirstName;
	}

	/**
	 * Gets LastName of the Contact object
	 * 
	 * @return Last name of contact
	 */
	public String getLastName() {
		return LastName;
	}

	/**
	 * Gets full name of Contact by concatenating FirstName and LastName
	 * 
	 * @return Full name of contact
	 */
	public String getFullName() {
		return this.toString();
	}

	/**
	 * Gets JobTitle of the Contact object
	 * 
	 * @return Job title of contact
	 */
	public String getJobTitle() {
		return JobTitle;
	}

	/**
	 * Gets the email of the Contact object
	 * 
	 * @return Email of contact
	 */
	public String getEmail() {
		return Email;
	}

	/**
	 * Gets WorkPhone of the Contact object
	 * 
	 * @return Work phone of contact
	 */
	public String getWorkPhone() {
		return WorkPhone;
	}

	/**
	 * Gets HomePhone of the Contact object
	 * 
	 * @return Home phone of contact
	 */
	public String getHomePhone() {
		return HomePhone;
	}

	/**
	 * Gets WorkAddress of the Contact object
	 * 
	 * @return Work address of contact
	 */
	public String getWorkAddress() {
		return WorkAddress;
	}

	/**
	 * Gets HomeAddress of the Contact object
	 * 
	 * @return Home address of contact
	 */
	public String getHomeAddress() {
		return HomeAddress;
	}

	/**
	 * Gets BirthDate of the Contact object
	 * 
	 * @return Birth date of contact
	 */
	public String getBirthDate() {
		return BirthDate;
	}

	/**
	 * Sets FirstName of the Contact object
	 * @param fname
	 * @return none
	 */
	public void setFirstName(String fname) {
		this.FirstName = fname;
	}

	/**
	 * Sets LastName of the Contact object
	 * @param lname
	 * @return none
	 */
	public void setLastName(String lname) {
		this.LastName = lname;
	}

	/**
	 * Sets JobTitle of the Contact object
	 * @param job
	 * @return none
	 */
	public void setJobTitle(String job) {
		this.JobTitle = job;
	}

	/**
	 * Sets Email of the Contact object
	 * @param email
	 * @return none
	 */
	public void setEmail(String email) {
		this.Email = email;
	}

	/**
	 * Sets Work phone of the Contact object
	 * @param wphone
	 * @return none
	 */
	public void setWorkPhone(String wphone) {
		this.WorkPhone = wphone;
	}

	/**
	 * Sets HomePhone of the Contact object
	 * @param hphone
	 * @return none
	 */
	public void setHomePhone(String hphone) {
		this.HomePhone = hphone;
	}

	/**
	 * Sets WorkAddress of the Contact object
	 * @param waddress
	 * @return none
	 */
	public void setWorkAddress(String waddress) {
		this.WorkAddress = waddress;
	}

	/**
	 * Sets HomeAddress of the Contact object
	 * @param haddress
	 * @return none
	 */
	public void setHomeAddress(String haddress) {
		this.HomeAddress = haddress;
	}

	/**
	 * Sets BirthDate of the Contact object
	 * @param bdate
	 * @return none
	 */
	public void setBirthDate(String bdate) {
		this.BirthDate = bdate;
	}

	/**
	 * Gets EmployeeID of the Contact object
	 * 
	 * @return Employee ID number
	 */
	public int getEmployeeID() {
		return EmployeeID;
	}

	/**
	 * Concatenates FirstName and LastName of the Object to get the full name
	 * 
	 * @return Full name of contact
	 */
	public String toString() {
		return getFirstName()+" "+getLastName();

	}

	/**
	 * Sets id of the contact class
	 * @param id
	 */
	public void setEmployeeId(int id) {
		this.EmployeeID = id;
		return;
	}
}