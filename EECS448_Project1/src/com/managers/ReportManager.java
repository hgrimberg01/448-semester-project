/**
 * Name: ReportManager.java
 * Description: Managers all information associated with reports on the database
 * 
 * @author Howard Grimberg
 * Date Created: 11/16/12
 */

package com.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.connectors.GoogleAnalyticsConnector;
import com.connectors.MailConnector;
import com.pojos.Contact;
import com.pojos.Department;
import com.pojos.Report;

/**
 * @author hgrimberg
 * 
 * Class for managing Report objects
 * 
 */

public class ReportManager {

	private Connection connection;
	private GoogleAnalyticsConnector gaConnection = new GoogleAnalyticsConnector();
	private MailConnector mailConnection;
	private HashMap<Integer, Report> reports;
	private final ContactManager contactManager;
	private final DepartmentManager departmentManager;
	SortableListModel<Report> reportsSearchModel;

	SortableListModel<Report> reportsModel;
	SortableListModel<Department> reportDepartmentModel;
	SortableListModel<Contact> reportContactModel;
	private boolean isDirty = true;
	private boolean gaConfigured = false;

	/**
	 * Initializes a ReportManager object
	 * 
	 * @param conn
	 * @param parentContacts
	 * @param parentDepartments
	 */
	public ReportManager(Connection conn, ContactManager parentContacts,
			DepartmentManager parentDepartments) {
		this.connection = conn;
		reports = new HashMap<Integer, Report>();
		this.contactManager = parentContacts;
		this.departmentManager = parentDepartments;
		this.loadReports();
		reportsSearchModel = new SortableListModel<Report>();
	}

	/**
	 * Establishes a mail connection
	 * 
	 * @param host
	 *            Host of connection
	 * @param address
	 *            Address of connection
	 */
	public void configureMailConnection(String host, String address) {
		mailConnection = new MailConnector(host, address);
	}

	
	
	/**
	 * Inserts a new Report into the database
	 * @param insert
	 */
	public void insert(Report insert){
		String name=insert.getTitle();
		String Sdate=insert.getStartDate();
		String Edate=insert.getEndDate();
		String dim=insert.getDimensions();
		String met=insert.getMetrics();
		String fil=insert.getFilter();
		String sort=insert.getSort();
		String max=Integer.toString(insert.getMaxResults());
		Vector<Department> dep= insert.getDepartments();
		Vector<Contact> rec= insert.getRecipients();
		
		try {
			assert connection.isValid(0);
			PreparedStatement prepStmt = connection
					.prepareStatement(
							"INSERT INTO `TeamProject_Reports` (`ReportID`, `Title`, `StartDate`, `EndDate`, `Dimensions`, `Metrics`, `Filter`, `Sort`, `MaxResults`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?);",
							Statement.RETURN_GENERATED_KEYS);

			prepStmt.setString(1, name);
			prepStmt.setString(2, Sdate);
			prepStmt.setString(3, Edate);
			prepStmt.setString(4, dim);
			prepStmt.setString(5, met);
			prepStmt.setString(6, fil);
			prepStmt.setString(7, sort);
			prepStmt.setInt(8, new Integer(max));
			prepStmt.execute();

			ResultSet rs = prepStmt.getGeneratedKeys();
			int key = -1;
			if (rs != null && rs.next()) {
				key = rs.getInt(1);
			}
			insertReportContactRelations(key, rec);
			insertReportDepartmentRelations(key, dep);

			Report newReport = new Report(key, name, Sdate, Edate, dim, met,
					fil, sort, max, dep, rec);
			reports.put(key, newReport);
			isDirty = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Updates a specified Report and all information associated to it
	 * 
	 * @param updated
	 *            Report to be updated
	 */
	public void update(Report updated) {
		final int key = updated.getReportId();
		System.out.println(key);
		try {
			PreparedStatement prepStmt = connection
					.prepareStatement("UPDATE `TeamProject_Reports` SET `Title` = ?, `StartDate` = ?, `EndDate` = ? ,`Dimensions` = ?,`Metrics` = ?,`Filter` = ?,`Sort` = ?,`MaxResults`= ? WHERE `ReportID` = ? ;");

			prepStmt.setString(1, updated.getTitle());
			prepStmt.setString(2, updated.getStartDate());
			prepStmt.setString(3, updated.getEndDate());
			prepStmt.setString(4, updated.getDimensions());
			prepStmt.setString(5, updated.getMetrics());
			prepStmt.setString(6, updated.getFilter());
			prepStmt.setString(7, updated.getSort());
			prepStmt.setInt(8, updated.getMaxResults());
			prepStmt.setInt(9, key);
			prepStmt.execute();

			purgeReportDepartmentRelations(key);
			purgeReportContactRelations(key);

			insertReportContactRelations(key, updated.getRecipients());
			insertReportDepartmentRelations(key, updated.getDepartments());

			reports.put(key, updated);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}

	/**
	 * Deletes a specified Report
	 * 
	 * @param reportKey
	 *            Report identification key
	 */
	public void delete(int reportKey) {
		try {
			purgeReportDepartmentRelations(reportKey);
			purgeReportContactRelations(reportKey);

			PreparedStatement prepStmt = connection
					.prepareStatement("DELETE FROM `TeamProject_Reports` WHERE `ReportID` = ? ;");
			prepStmt.setInt(1, reportKey);
			prepStmt.execute();
			reportsModel.removeElement(getReport(reportKey));
			reportsSearchModel.removeElement(getReport(reportKey));
			reports.remove(reportKey);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets a report based on the Report key
	 * 
	 * @param reportKey
	 *            Report identification key
	 * @return
	 */
	public Report getReport(int reportKey) {
		return reports.get(reportKey);
	}

	/**
	 * Gets all Reports in the database and loads them into a HashMap<Integer,
	 * Report>
	 * 
	 * @return HashMap containing all Reports in the database
	 */
	public HashMap<Integer, Report> getReports() {
		if (isDirty) {
			loadReports();
			isDirty = false;
			return getReports();
		} else {
			return this.reports;
		}
	}

	/**
	 * Gets all Reports from the database
	 */
	private void loadReports() {

		try {
			assert connection.isValid(0);
			PreparedStatement allReports = connection
					.prepareStatement("SELECT * FROM `TeamProject_Reports`;");
			allReports.execute();
			ResultSet allReportResults = allReports.getResultSet();
			allReportResults.beforeFirst();
			reports.clear();

			while (allReportResults.next()) {

				Report newReport = new Report(
						allReportResults.getInt("ReportID"),
						allReportResults.getString("Title"),
						allReportResults.getDate("StartDate").toString(),
						allReportResults.getDate("EndDate").toString(),
						allReportResults.getString("Dimensions"),
						allReportResults.getString("Metrics"),
						allReportResults.getString("Filter"),
						allReportResults.getString("Sort"),
						Integer.toString(allReportResults.getInt("MaxResults")),
						getReportDepartmentRelations(allReportResults
								.getInt("ReportID")),
						getReportContactRelations(allReportResults
								.getInt("ReportID")));
				reports.put(allReportResults.getInt("ReportID"), newReport);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	/**
	 * Gets a SortableListModel containing all Reports in the database
	 * 
	 * @return Sortable list model of all Reports
	 */
	public SortableListModel<Report> getListModelReports() {

		reportsModel = new SortableListModel<Report>();
		for (Report addToModel : getReports().values()) {
			reportsModel.addElement(addToModel);

		}
		reportsModel.sort();
		return reportsModel;
	}

	/** Deletes associations between Reports and Contacts
	 * 
	 * @param reportKey				Report identification key
	 * @throws SQLException			Thrown if interaction between the database and program results in an error
	 */
	private void purgeReportContactRelations(int reportKey) throws SQLException {
		PreparedStatement prepStmt = connection
				.prepareStatement("DELETE FROM `TeamProject_ContactsToReports` WHERE `ReportID` = ?");
		prepStmt.setInt(1, reportKey);
		prepStmt.execute();
		return;

	}

	/** Deletes associations between Reports and Departments
	 * 
	 * @param reportKey			Report identification key
	 * @throws SQLException		Thrown if interaction between the database and program results in an error
	 */
	private void purgeReportDepartmentRelations(int reportKey)
			throws SQLException {
		PreparedStatement prepStmt = connection
				.prepareStatement("DELETE FROM `TeamProject_DepartmentsToReports` WHERE `ReportID` = ?");
		prepStmt.setInt(1, reportKey);
		prepStmt.execute();
		return;

	}

	/** Gets the Report-to-Department relations and puts them in a Vector of Departments
	 * 
	 * @param reportKey			Report identification key
	 * @return					Vector of Departments that are associated to the Report
	 * @throws SQLException		Thrown if interaction between the database and program results in an error
	 */
	private Vector<Department> getReportDepartmentRelations(int reportKey)
			throws SQLException {
		PreparedStatement prepStmt = connection
				.prepareStatement("SELECT `DepartmentID` FROM `TeamProject_DepartmentsToReports` WHERE `ReportID` = ? ;");
		prepStmt.setInt(1, reportKey);
		prepStmt.execute();

		ResultSet departmentReportRelations = prepStmt.getResultSet();

		Vector<Department> associatedDepartments = new Vector<Department>();

		departmentReportRelations.beforeFirst();

		while (departmentReportRelations.next()) {
			associatedDepartments.add(departmentManager
					.getDepartment(departmentReportRelations
							.getInt("DepartmentID")));

		}

		return associatedDepartments;

	}

	/** Gets the Report-to-Contact relations and puts them in a Vector of Contacts
	 * 
	 * @param reportKey			Report identification key
	 * @return					Vector of Contacts that are associated to the Report
	 * @throws SQLException		Thrown if interaction between the database and program results in an error
	 */
	private Vector<Contact> getReportContactRelations(int reportKey)
			throws SQLException {

		PreparedStatement prepStmt = connection
				.prepareStatement("SELECT `ContactID` FROM `TeamProject_ContactsToReports` WHERE `ReportID` = ? ;");
		prepStmt.setInt(1, reportKey);
		prepStmt.execute();

		ResultSet contactReportRelations = prepStmt.getResultSet();

		Vector<Contact> associatedContacts = new Vector<Contact>();

		contactReportRelations.beforeFirst();

		while (contactReportRelations.next()) {
			associatedContacts.add(contactManager
					.getContact(contactReportRelations.getInt("ContactID")));

		}

		return associatedContacts;
	}

	/** Creates a relationship between the specified Contacts and a Report
	 * 
	 * @param reportKey				Report identification key
	 * @param contactsToInsert		Vector of Contacts to associate to the Report
	 * @throws SQLException			Thrown if interaction between the database and program results in an error
	 */
	private void insertReportContactRelations(int reportKey,
			Vector<Contact> contactsToInsert) throws SQLException {
		for (Contact newContact : contactsToInsert) {

			PreparedStatement prepStmt = connection
					.prepareStatement("INSERT INTO `TeamProject_ContactsToReports` (`RowID`, `ReportID`, `ContactID`) VALUES (NULL, ?, ?)");
			prepStmt.setInt(1, reportKey);
			prepStmt.setInt(2, newContact.getEmployeeID());
			prepStmt.execute();

		}
		return;
	}

	/** Creates a relationship between the specified Departments and a Report
	 * 
	 * @param reportKey				Report identification key
	 * @param contactsToInsert		Vector of Departments to associate to the Report
	 * @throws SQLException			Thrown if interaction between the database and program results in an error
	 */
	private void insertReportDepartmentRelations(int reportKey,
			Vector<Department> departmentsToInsert) throws SQLException {

		for (Department newDepartment : departmentsToInsert) {

			PreparedStatement prepStmt = connection
					.prepareStatement("INSERT INTO `TeamProject_DepartmentsToReports` (`RowID`, `DepartmentID`, `ReportID`) VALUES (NULL, ?, ?)");
			prepStmt.setInt(1, newDepartment.getId());
			prepStmt.setInt(2, reportKey);
			prepStmt.execute();

		}
		return;
	}

	/** Gets the Report associated with the given Report ID
	 * 
	 * @param reportID		Report ID number
	 * @return				Report with the given ID
	 */
	public Report getReports(int reportID) {
		return reports.get(reportID);
	}

	/** Searches all Reports in the database using a given string. Search is front-matching.
	 * 
	 * @param searchStr			String used to search
	 * @return					SortableListModel of all Reports that match the search string
	 */
	public SortableListModel<Report> searchReportsByName(String searchStr) {
		reportsSearchModel.removeAllElements();

		try {
			assert connection.isValid(0);

			PreparedStatement searchReports = connection
					.prepareStatement("SELECT ReportID FROM `TeamProject_Reports` WHERE "
							+ "Title LIKE '%" + searchStr + "%'");
			searchReports.execute();

			ResultSet searchReportsResults = searchReports.getResultSet();
			searchReportsResults.beforeFirst();

			while (searchReportsResults.next()) {
				reportsSearchModel.addElement(this
						.getReports(searchReportsResults.getInt("ReportID")));
			}
			reportsSearchModel.sort();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reportsSearchModel;
	}

	/** Returns a String representation of the Report sent to Google Analytics
	 * 
	 * @param toPreview 		The report to get preview
	 * @param format 			Specifies whether to format as plain text (0) or HTML (1)
	 * @return					String of Report results
	 * @throws Throwable		Thrown if interaction between Google Analytics and the program results in an error
	 */
	public String previewReport(Report toPreview, boolean format) throws Throwable {
		readyGA();
		return this.getResults(toPreview, format);
	}

	/** Sends the sepcified report to the specified recipients
	 * 
	 * @param toSend 					The report to send
	 * @param additionalRecipients 		Any extra recipients for the outgoing message, may be null or empty
	 * @param subject					The title of the outgoing message
	 * @param format 					Specifies whether to format as plain text (0) or HTML (1)
	 * @return							True if sending was successful
	 * @throws Throwable				Thrown if there was an error when sending the email
	 */
	public boolean sendReport(Report toSend, String additionalRecipients,
			String subject, boolean format) throws Throwable {
		String body = this.previewReport(toSend, format);

		List<String> recipients = new ArrayList<String>();

		if (additionalRecipients != null)
		{
			if (!additionalRecipients.equals(""))
			{
				for (String address : additionalRecipients.trim().split(",")) {
					recipients.add(address);
				}
			}
		}

		for (Contact c : toSend.getRecipients()) {
			recipients.add(c.getEmail());
		}
		for (Department d : toSend.getDepartments()) {
			for (Contact c : d.getEmployees()) {
				if (!recipients.contains(c.getEmail())) {
					recipients.add(c.getEmail());

				}

			}

		}

		System.out.println(recipients);
		try {
			if (!mailConnection.sendEmail(subject, recipients, body, format))
				return false;
		} catch (Throwable e) {
			throw (new Throwable("Mailing Error: " + e.getMessage()));
		}

		return true;
	}

	/** Gets the results of a Report sent to Google Analytics
	 * 
	 * @param report		Report sent to Google Analytics
	 * @param format 		Specifies whether to format as plain text (0) or HTML (1)
	 * @return				Report results
	 * @throws Throwable	Thrown if there was an error when interacting with Google Analytics
	 */
	private String getResults(Report report, boolean format) throws Throwable {
		try {
			return gaConnection.getData(report, format);
		} catch (Throwable e) {
			throw (new Throwable("Google Analytics Retrieval Error: "
					+ e.getMessage()));
		}
	}

	/** Initializes the Google Analytics connection
	 *  
	 * @throws Throwable	Thrown if there was an error initializing the connection
	 */
	public void readyGA() throws Throwable {
		if (!gaConfigured) {
			try {
				gaConnection.start();
				gaConfigured = true;
			} catch (Throwable e) {
				throw (new Throwable("Google Analytics Initialization Error: "
						+ e.getMessage()));
			}

		}
		return;
	}

	/**
	 * Reload the Report-to-Contact relations
	 */
	public void reloadReportContactRelations() {
		for (Report update : reports.values()) {
			try {
				update.setRecipients(getReportContactRelations(update
						.getReportId()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Reload the Report-to-Department relations
	 */
	public void reloadReportDepartmentRelations() {
		for (Report update : reports.values()) {
			try {
				update.setDepartments(getReportDepartmentRelations(update
						.getReportId()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
