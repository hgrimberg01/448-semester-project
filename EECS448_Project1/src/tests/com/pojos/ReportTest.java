package tests.com.pojos;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import com.pojos.Contact;
import com.pojos.Department;
import com.pojos.Report;

public class ReportTest {

	private Report testReport;
	private Vector<String> dimensions = new Vector<String>();
	private Vector<String> metrics = new Vector<String>();
	private Vector<Contact> recipients = new Vector<Contact>();
	private Vector<Department> departments = new Vector<Department>();
	
	
	@Before
	public void initReport() {
		metrics.add("ga:visits");
		metrics.add("ga:visitors");
		
		dimensions.add("ga:date");
		dimensions.add("ga:source");
		
		recipients.add(new Contact(1, "Santa", "Claus", "Pizza Delivery", "santa@northpole.com", "555-101-2020", "", "123 North Pole Lane","None", "1800-12-25"));
		
		
		departments.add(new Department(1, "Hackers", "666-598-3412", "", "666 west Street Nowhere, KN", recipients));
		
		testReport = new Report(1, "Report 1", "01-01-2012", "01-02-2012", "ga:date, ga:source", "ga:visits, ga:visitors", "", "", "100", departments, recipients);
	
		recipients.add(new Contact(1, "Easter", "Bunny", "Mikey D's", "egg@head.com", "555-222-9898", "", "18th Over the Rvr N Thr Wds","None", "1775-08-05"));
		departments.add(new Department(1, "Marketing", "555-123-3452", "", "3423 South Street Topeka, KS", recipients));
	}
	
	
	@Test
	public void testInitReport() {
		Report report = new Report();
		
		assertNotNull(report);
		assertEquals(report.getTitle(), "Report Title");
	}
	
	@Test
	public void testReport() {
		assertEquals(testReport.getReportId(), 1);
		assertEquals(testReport.getTitle(), "Report 1");
		assertEquals(testReport.getStartDate(), "01-01-2012");
		assertEquals(testReport.getEndDate(), "01-02-2012");
	}

	@Test
	public void testGetReportId() {
		assertEquals(testReport.getReportId(), 1);
	}

	@Test
	public void testGetTitle() {
		assertEquals(testReport.getTitle(), "Report 1");
	}

	@Test
	public void testGetStartDate() {
		assertEquals(testReport.getStartDate(), "01-01-2012");
	}

	@Test
	public void testGetEndDate() {
		assertEquals(testReport.getEndDate(), "01-02-2012");
	}

	@Test
	public void testGetFilter() {
		assertEquals(testReport.getFilter(), "");
	}

	@Test
	public void testGetSort() {
		assertEquals(testReport.getSort(), "");
	}

	@Test
	public void testGetMaxResults() {
		assertEquals(testReport.getMaxResults(), 100);
	}

	
	@Test
	public void testSetTitle() {
		testReport.SetTitle("Weekly IT Report");
		
		assertEquals(testReport.getTitle(), "Weekly IT Report");
	}

	@Test
	public void testSetStartDate() {
		testReport.SetStartDate("10-03-2012");
		
		assertEquals(testReport.getStartDate(), "10-03-2012");
	}

	@Test
	public void testSetEndDate() {
		testReport.SetStartDate("10-07-2012");
		
		assertEquals(testReport.getStartDate(), "10-07-2012");
	}

	@Test
	public void testDimensionCount() {
		assertEquals(testReport.DimensionCount(), 2);
	}


	@Test
	public void testSetFilter() {
		testReport.SetFilter("ga:visits>2");
		
		assertEquals(testReport.getFilter(), "ga:visits>2");
	}

	@Test
	public void testSetSort() {
		testReport.SetSort("-ga:date");
		
		assertEquals(testReport.getSort(), "-ga:date");
	}

	@Test
	public void testSetMaxResults() {
		testReport.SetMaxResults("25");
		
		assertEquals(testReport.getMaxResults(), 25);
	}


	@Test
	public void testRemoveTitle() {
		testReport.RemoveTitle();
		assertEquals(testReport.getTitle(),"");
	}

	@Test
	public void testRemoveStartDate() {
		testReport.RemoveStartDate();
		assertEquals(testReport.getStartDate(),"");
	}

	@Test
	public void testRemoveEndDate() {
		testReport.RemoveEndDate();
		assertEquals(testReport.getEndDate(),"");
	}

	@Test
	public void testRemoveFilter() {
		testReport.RemoveFilter();
		assertEquals(testReport.getFilter(),"");
	}

	@Test
	public void testRemoveSort() {
		testReport.RemoveSort();
		assertEquals(testReport.getSort(),"");
	}

	@Test
	public void testToString() {
		assertEquals(testReport.toString(),testReport.getTitle());
	}

}
