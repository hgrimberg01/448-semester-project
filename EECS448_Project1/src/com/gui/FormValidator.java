/**
 * Name: FormValidator.java
 * Description: A validator for a specific form within the user window.
 * 
 * @author spolsley
 * Date Created: 11/11/12
 */

package com.gui;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Character;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.sun.xml.internal.fastinfoset.util.CharArray;

/** 
 * FormValidator
 * 
 * A validator for a specific form within the user window.
 * Each type of form (saving a contact, saving a department,
 * and creating a report, etc.) should have its own validator.
 * The form validator will keep a list of errors in the form.
 */
public class FormValidator
{
	ArrayList<String> formErrors = new ArrayList<String>();
	
	private Color goodColor = Color.white;
	private Color badColor = Color.red;
	
	
	/** Form validator constructor for setting good and bad color feedback.
	 * 
	 * @param good 		The Color that will be displayed for safe fields
	 * @param bad 		The Color that will be displayed for invalid fields
	 */
	public FormValidator(Color good, Color bad)
	{
		goodColor = good;
		badColor = bad;
	}
	
	public FormValidator()
	{
		//Do nothing
	}

	/** Returns the Color for safe fields.
	 * 
	 * @return goodColor
	 */
	public Color getGoodColor()
	{
		return goodColor;
	}
	
	/** Returns the Color for invalid fields.
	 * 
	 * @return badColor
	 */
	public Color getBadColor()
	{
		return badColor;
	}
	
	/** Asserts that there are no empty fields that are required
	 * 
	 * @param currentTab		Tab that is being validated
	 * @return					Boolean: True if all fields are filled, false if there are required fields that are empty
	 */
	public boolean noEmptyFields(JPanel currentTab) {
		boolean noEmptyFields = true;
		Component[] components = currentTab.getComponents();

		for (Component c : components) {
			if (c instanceof JTextField) {
				if ((((JTextField) c).getText() == null) || (((JTextField) c).getText().length() == 0))
				{
					c.setBackground(badColor);
					noEmptyFields = false;
				}
			}
		}
		if (!noEmptyFields)
			formErrors.add("Fields cannot be left blank.  Enter 'N/A' if there is no value.");
		return noEmptyFields;
	}
	 
	/** Asserts that the field value is not blank or equal to the default value
	 * 
	 * @param fieldName			Default value of the field being validated (Name of the field)
	 * @param toCheckName		Value that has been entered into the field to be checked
	 * 
	 * @return					Boolean: True if fields are valid, false if the field is not valid
	 */
	public boolean validateName(String fieldName, String toCheckName)
	{
		String message = fieldName.concat(": Invalid name.");
		
		if (toCheckName.equalsIgnoreCase(fieldName) || toCheckName.equalsIgnoreCase(""))
		{
			addError(message);
			return false;
		}
		else
		{
			removeError(message);
			return true;
		}
		
	}
	
	/** Validates the phone number entered
	 * 
	 * @param fieldName			Name of the field
	 * @param toCheckPhone		Phone number entered to be checked
	 * 
	 * @return					Boolean: True if phone number is valid, false if otherwise.
	 */
	public boolean validatePhone(String fieldName, String toCheckPhone)
	{
		String message = fieldName.concat(": Invalid phone number. The phone number should be at least 7 digits.  Provide the area code if the number has an extension.");
		
		if (toCheckPhone.equalsIgnoreCase(""))
		{
			removeError(message);
			return true;
		}
		
		//Use regex to strip non-digits
		toCheckPhone = toCheckPhone.replaceAll("\\D+","");
		
		//No international codes currently supported
		if (toCheckPhone.startsWith("1"))
			toCheckPhone = toCheckPhone.replaceFirst("1","");
		if ((toCheckPhone.length() < 10)&&(toCheckPhone.length() != 7))
		{
			addError(message);
			return false;
		}
		else
		{
			removeError(message);
			return true;
		}
	}
	
	/** Formats the phone to XXX-XXX-XXXXextXX format (extension is optional)
	 * 
	 * @param toFormatPhone		Phone number to be formatted
	 * 
	 * @return					String of formatted phone number
	 */
	public String formatPhone(String toFormatPhone)
	{
		String phone = toFormatPhone;
		
		//Use regex to strip non-digits
		phone = phone.replaceAll("\\D+","");
		
		//Re-format numbers
		//The current supported format is Area-PhonexExt, no international numbers
		if (phone.startsWith("1"))
			phone = phone.replaceFirst("1","");
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < phone.length(); i++)
		{
			final char c = phone.charAt(i);
			
			if (i==10)
				sb.append("x");
			
			sb.append(c);
			
			if ((i==2))
				sb.append("-");
			
			if ((phone.length()>7)&&(i==5))
				sb.append("-");
		}
		return sb.toString();
	}
	
	/** Validates a specified date.
	 * 
	 * @param fieldName String name of the field that is being validated (eg. Contact Birthdate)
	 * @param toCheckDate String representation of the date to be validated
	 * 
	 * @return boolean; true if the date is valid
	 */
	public boolean validateDate(String fieldName, Date toCheckDate, DateFormat format)
	{	
		String message = fieldName.concat(": Invalid date. Check the date format.");
		
		try
		{
				format.format(toCheckDate);
		} catch (Exception e)
		{
			 //Date unparseable
			addError(message);
			return false;
		}
		//Date parseable
		removeError(message);
		return true;
	}
	
	/** Validates a specified email address.
	 * 
	 * @param fieldName String name of the field that is being validated (eg. Contact email)
	 * @param toCheckEmail String representation of the email address to be validated
	 * 
	 * @return boolean; true if the email address is valid
	 */
	public boolean validateEmail(String fieldName, String toCheckEmail) {
		String message = fieldName.concat(": Invalid email address");
		
		if (toCheckEmail.equalsIgnoreCase(""))
		{
			removeError(message);
			return true;
		}
		
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*" +
				"@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pattern.matcher(toCheckEmail);

		if (matcher.matches())
		{
			removeError(message);
			return true;
		}
		else
		{
			addError(message);
			return false;
		}
	}
	
	/** Checks if all form fields have valid input
	 * 
	 * @return	True if there are no errors saved for the form, false if there are one or more errors
	 */
	public boolean isValid()
	{
		if (formErrors.size() == 0)
			return true;
		else
			return false;
	}
	
	/** The typical method for validating a whole form.
	 * This method should be called when a form is to be submitted.
	 * If any errors are associated with the form, an exception detailing
	 * the error messages will be thrown.
	 * 
	 * @throws Exception All of the form errors are saved in the exception message
	 */
	public boolean valid() throws Exception
	{
		if (!this.isValid())
		{
			String message = "The following errors were found: \n\n";
			for (String error : formErrors)
				message += error + "\n";
			
			throw (new Exception(message));
		}
		return true;
	}
	
	
	/** Adds error message to list of errors in the form
	 * 
	 * @param message		Error message to be added
	 */
	public void addError(String message)
	{
		for (String error : formErrors)
		{
			//Check if error is already in list
			if (message.equalsIgnoreCase(error))
				return;
		}
		
		formErrors.add(message);
	}
	
	/**	Removes an error form the list of errors in the form
	 * 
	 * @param message		Message to be removed
	 */
	public void removeError(String message)
	{
		for (int i = 0; i < formErrors.size(); i++)
		{
			//Find error in list
			if (message.equalsIgnoreCase(formErrors.get(i)))
				formErrors.remove(i);
		}
	}

}
