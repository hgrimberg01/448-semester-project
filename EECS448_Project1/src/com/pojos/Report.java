/**
 * Name: Report.java
 * Description: Java object for the reports
 * 
 * @author Tim Lamb
 * Date Created: 11/14/12
 */

package com.pojos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import com.managers.SortableListModel;

/**
 * @author tlamb
 * 
 *         Class containing all information related to report object.
 */
public class Report {
	private static int MaxDimensions = 7;
	private static int MaxMetrics = 10;

	private int reportId;
	private String Title;
	private String StartDate;
	private String EndDate;
	private Vector<String> Dimensions = new Vector<String>();
	private Vector<String> Metrics = new Vector<String>();
	private String Filter;
	private String Sort;
	private String MaxResults;
	private Vector<Contact> Recipients;
	private Vector<Department> Departments;
	private SortableListModel<Contact> reportContactModel;
	private SortableListModel<Department> reportDepartmentModel;
	private DefaultComboBoxModel<String> dimensionMetricMenuModel;

	/**
	 * Constructor for a Report object
	 * 
	 * @param repId
	 *            Report ID number
	 * @param name
	 *            Report title
	 * @param Sdate
	 *            Start date
	 * @param Edate
	 *            End date
	 * @param dim
	 *            Dimensions
	 * @param met
	 *            Metrics
	 * @param fil
	 *            Filter parameters
	 * @param sort
	 *            Sort parameters
	 * @param max
	 *            Max results
	 * @param rec
	 *            Recipients of report
	 * @param dep
	 *            Department recipients of report
	 */
	public Report(int repId, String name, String Sdate, String Edate,
			String dim, String met, String fil, String sort, String max,
			Vector<Department> dep, Vector<Contact> rec) {
		this.reportId = repId;
		this.Title = name;
		this.StartDate = Sdate;
		this.EndDate = Edate;
		this.SetDimensions(dim);
		this.SetMetrics(met);
		this.Filter = fil;
		this.Sort = sort;
		this.MaxResults = max;
		this.Departments = dep;
		this.Recipients = rec;
		this.dimensionMetricMenuModel = new DefaultComboBoxModel<String>();
		return;
	};

	public Report() {
		this.Title = "Report Title";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date defaultDate = new Date();
		this.EndDate = format.format(defaultDate);
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(defaultDate);
		calendar.add(Calendar.DAY_OF_MONTH, -30);
		
		this.StartDate = format.format(calendar.getTime());
		this.reportContactModel = new SortableListModel<Contact>();
		this.reportDepartmentModel = new SortableListModel<Department>();
		
	}

	/**
	 * Gets the Report ID number
	 * 
	 * @return ID number of report
	 */
	public int getReportId() {
		return reportId;
	};

	/**
	 * Gets the value for MaxDimensions
	 * 
	 * @return Maximum number of dimensions that one is able to select
	 */
	public int getMaxDimensions() {
		return MaxDimensions;
	};

	/**
	 * Gets value of MaxMetrics
	 * 
	 * @return Maximum number of metrics that one is able to select
	 */
	public int getMaxMetrics() {
		return MaxMetrics;
	};

	/**
	 * Gets Title of the Report object
	 * 
	 * @return Title of the report
	 */
	public String getTitle() {
		return Title;
	};

	/**
	 * Gets StartDate of the Report object
	 * 
	 * @return Start date of the report
	 */
	public String getStartDate() {
		return StartDate;
	};

	/**
	 * Gets EndDate of the Report Object
	 * 
	 * @return End date of the report
	 */
	public String getEndDate() {
		return EndDate;
	};

	/**
	 * Gets the selected Dimensions
	 * 
	 * @return String of all chosen dimensions
	 */
	public String getDimensions() {
		return Dimensions.toString().substring(1,
				Dimensions.toString().length() - 1);
	};

	/**
	 * Gets the dimension at the index specified in the Dimensions vector
	 * 
	 * @param index
	 *            Desired index
	 * @return Dimension at the selected index
	 */
	public String getDimension(int index) {
		return Dimensions.get(index);
	}

	/**
	 * Gets the selected Metrics
	 * 
	 * @return String of all chosen metrics
	 */
	public String getMetrics() {
		return Metrics.toString().substring(1, Metrics.toString().length() - 1);
	};

	/**
	 * Gets the metric at the index specified in the Metrics vector
	 * 
	 * @param index
	 *            Desired index
	 * @return Metrics at the selected index
	 */
	public String getMetric(int index) {
		return Metrics.get(index);
	}

	/**
	 * Gets the filter parameters of the Report object
	 * 
	 * @return Filter parameters
	 */
	public String getFilter() {
		return Filter;
	};

	/**
	 * Gets the Sort parameters of the Report object
	 * 
	 * @return Sort parameters
	 */
	public String getSort() {
		return Sort;
	};

	/**
	 * Gives the count of total dimensions in the Dimensions vector
	 * 
	 * @return length of Dimensions vector
	 */
	public int DimensionCount() {
		return Dimensions.size();
	}

	/**
	 * Gives the count of total metrics in the Metrics vector
	 * 
	 * @return length of Metrics vector
	 */
	public int MetricCount() {
		return Metrics.size();
	}

	/**
	 * Gets MaxResults of the Reports object
	 * 
	 * @return
	 */
	public int getMaxResults() {
		return Integer.parseInt(MaxResults);
	};

	/**
	 * Sets Title of the Report object
	 * 
	 * @param name
	 *            String to be set as the report title
	 */
	public void SetTitle(String name) {
		this.Title = name;
	};

	/**
	 * Sets StartDate for the Report object
	 * 
	 * @param Sdate
	 *            String to be set as the start date
	 */
	public void SetStartDate(String Sdate) {
		this.StartDate = Sdate;
	};

	/**
	 * Sets EndDate for the Report object
	 * 
	 * @param Edate
	 *            String to be set as the end date
	 */
	public void SetEndDate(String Edate) {
		EndDate = Edate;
	};

	/**
	 * Sets Dimensions vector for the Report object
	 * 
	 * @param dim
	 *            String of metrics to add to the vector
	 */
	private void SetDimensions(final String dim) {
		if (dim.length() == 0)
			return;

		Vector<String> dimList = new Vector<String>();
		String temp = "";
		StringTokenizer dimToken = new StringTokenizer(dim);

		while (dimToken.hasMoreTokens()) {
			temp = temp + dimToken.nextToken();

			if (temp.contains(",")) {
				temp = temp.replaceAll(",", "");
				dimList.add(temp);
				temp = "";
			} else {
				temp = temp + " ";
			}
		}
		dimList.add(temp);

		Dimensions = dimList;
	}

	/**
	 * Sets Metrics vector for the Report object
	 * 
	 * @param met
	 *            String of metrics to add to the vector
	 */
	private void SetMetrics(final String met) {
		Vector<String> metList = new Vector<String>();
		String temp = "";
		StringTokenizer metToken = new StringTokenizer(met);

		while (metToken.hasMoreTokens()) {
			temp = temp + metToken.nextToken();

			if (temp.contains(",")) {
				temp = temp.replaceAll(",", "");
				metList.add(temp);
				temp = "";
			} else {
				temp = temp + " ";
			}
		}
		metList.add(temp);

		Metrics = metList;
	}

	/**
	 * Adds a dimension to the Dimension vector
	 * 
	 * @param dim
	 *            Dimension to be added
	 * @return True if successful, false if otherwise.
	 */
	public boolean addDimension(String dim) {
		if (Dimensions.size() < MaxDimensions) {
			for (int i = 0; i < Dimensions.size(); i++) {
				if (Dimensions.get(i).equals(dim)) {
					return true;
				}
			}
			Dimensions.add(dim);
			dimensionMetricMenuModel.addElement(dim);
			return true;
		} else {
			return false;
		}
	};

	/**
	 * Adds a metric to the Metric vector
	 * 
	 * @param met
	 *            Metric to be added
	 * @return True if successful, false if otherwise
	 */
	public boolean AddMetric(String met) {
		if (Metrics.size() < MaxMetrics) {
			for (int i = 0; i < Metrics.size(); i++) {
				if (Metrics.get(i).equals(met)) {
					return true;
				}
			}
			Metrics.add(met);
			dimensionMetricMenuModel.addElement(met);
			return true;
		} else {
			return false;
		}
	};

	/**
	 * Sets Filter of Report object
	 * 
	 * @param fil
	 *            Filter to be set
	 */
	public void SetFilter(String fil) {
		Filter = fil;
	};

	/**
	 * Sets Sort of Report object
	 * 
	 * @param sort
	 *            Sort to be set
	 */
	public void SetSort(String sort) {
		Sort = sort;
	};

	/**
	 * Sets MaxResults of Report object
	 * 
	 * @param max
	 *            Max results to be set
	 */
	public void SetMaxResults(String max) {
		MaxResults = max;
	};

	/**
	 * Sets Title of the Report object to an empty string
	 * 
	 */
	public void RemoveTitle() {
		Title = "";
	};

	/**
	 * Sets StartDate of the Report object to an empty string
	 * 
	 */
	public void RemoveStartDate() {
		StartDate = "";
	};

	/**
	 * Sets EndDate of the Report object to an empty string
	 * 
	 */
	public void RemoveEndDate() {
		EndDate = "";
	};

	/**
	 * Removes selected dimension from the Dimensions vector, if it exists
	 * 
	 * @param dim
	 *            Dimension to be removed from the vector
	 * @return True if successful, false otherwise
	 */
	public boolean RemoveDimension(String dim) {
		for (int i = 0; i < Dimensions.size(); i++) {
			if (Dimensions.get(i).equals(dim)) {
				Dimensions.remove(i);
				return true;
			}
		}
		return false;
	};

	/**
	 * Removes all elements from the Dimensions vector
	 * 
	 */
	public void RemoveAllDimensions() {
		Dimensions.clear();
		return;
	}

	/**
	 * Removes selected metric from the Metrics vector, if it exists
	 * 
	 * @param met
	 *            Metric to be removed
	 * @return True if successful, false if otherwise
	 */
	public boolean RemoveMetric(String met) {
		for (int i = 0; i < Metrics.size(); i++) {
			if (Metrics.get(i).equals(met)) {
				Metrics.remove(i);
				return true;
			}
		}
		return false;

	};

	/**
	 * Removes all elements from the Metrics vector
	 * 
	 */
	public void RemoveAllMetrics() {
		Metrics.clear();
		return;
	}

	/**
	 * Sets Filter of the Report object to an empty string
	 * 
	 */
	public void RemoveFilter() {
		Filter = "";
	};

	/**
	 * Sets Sort of the Report object to an empty string
	 * 
	 */
	public void RemoveSort() {
		Sort = "";
	};

	/**
	 * Sets MaxResults of the Report object to an empty string
	 * 
	 */
	public void RemoveMaxResults() {
		MaxResults = "";
	};

	/**
	 * Returns Report Title
	 * 
	 * @return Report Title
	 */
	public String toString() {
		return Title;

	}

	/**
	 * Sets the related departments vector.
	 * 
	 * @param newDepartments
	 */
	public void setDepartments(Vector<Department> newDepartments) {
		this.Departments = newDepartments;
		return;
	}

	/**
	 * Sets the related contacts vector.
	 * 
	 * @param newContacts
	 */
	public void setRecipients(Vector<Contact> newContacts) {
		this.Recipients = newContacts;
		return;
	}

	/**
	 * Returns related departments.
	 * 
	 * @return
	 */
	public Vector<Department> getDepartments() {

		return Departments;

	}

	/**
	 * Returns related contacts.
	 * 
	 * @return
	 */
	public Vector<Contact> getRecipients() {

		return Recipients;

	}

	/**
	 * Gets the related contacts list model for reports.
	 * 
	 * @return
	 */
	public SortableListModel<Contact> getContactListModelForReports() {
		reportContactModel = new SortableListModel<Contact>();
		for (Contact addToModel : getRecipients()) {
			System.out.println(addToModel);
			reportContactModel.addElement(addToModel);
		}
		reportContactModel.sort();
		return reportContactModel;
	}

	/**
	 * Gets the related department list model for reports.
	 * 
	 * @return
	 */
	public SortableListModel<Department> getDepartListModelForReports() {

		reportDepartmentModel = new SortableListModel<Department>();
		for (Department addToModel : getDepartments()) {
			reportDepartmentModel.addElement(addToModel);
		}
		reportDepartmentModel.sort();
		return reportDepartmentModel;
	}

	/**
	 * Sets the report ID
	 * 
	 * @param newId
	 */
	public void setID(int newId) {
		this.reportId = newId;
	}

	/**
	 * Gets the combobox model of the combined dimensions and metrics.
	 * 
	 * @return
	 */
	public DefaultComboBoxModel<String> getDimensionMetricsMenuModel() {
		dimensionMetricMenuModel = new DefaultComboBoxModel<String>();
		for (String addDimension : Dimensions) {
			dimensionMetricMenuModel.addElement(addDimension);
		}
		for (String addMetric : Metrics) {
			dimensionMetricMenuModel.addElement(addMetric);
		}
		return dimensionMetricMenuModel;

	}
}