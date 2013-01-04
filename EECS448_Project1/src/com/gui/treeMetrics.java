/**
 * Name: treeMetrics.java
 * Description: Contains all of the metrics in a tree form
 * 
 * @author Seth Polsley
 * Date Created: 11/13/12
 */

package com.gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * Extends a JTree that is pre-populated with all necessary metrics
 * @author spolsley
 *
 */
public class treeMetrics extends JTree{
	private static final long serialVersionUID = 1L;
	public static DefaultMutableTreeNode metrics = new DefaultMutableTreeNode("Metrics");
	static DefaultMutableTreeNode visitor = new DefaultMutableTreeNode("Visitor");
	static DefaultMutableTreeNode session = new DefaultMutableTreeNode("Session");
	static DefaultMutableTreeNode trafficSources = new DefaultMutableTreeNode("Traffic Sources");
	static DefaultMutableTreeNode pageTracking = new DefaultMutableTreeNode("Page Tracking");
	static DefaultMutableTreeNode internalSearch = new DefaultMutableTreeNode("Internal Search");
	static DefaultMutableTreeNode siteSpeed = new DefaultMutableTreeNode("Site Speed");
	static DefaultMutableTreeNode exceptionTracking = new DefaultMutableTreeNode("Exception Tracking");
	
	
	/** 
	 * Initializes dimensions of the metrics tree 
	 */
	public treeMetrics() {
		
		super(metrics);
		this.setRootVisible(false);
		
		metrics.add(visitor);
		metrics.add(session);
		metrics.add(trafficSources);	
		metrics.add(pageTracking);
		metrics.add(internalSearch);		
		metrics.add(siteSpeed);
		metrics.add(exceptionTracking);
	
		visitor.add(new DefaultMutableTreeNode("ga:visitors"));
		visitor.add(new DefaultMutableTreeNode("ga:newVisits"));
		visitor.add(new DefaultMutableTreeNode("ga:percentNewVisits"));
		session.add(new DefaultMutableTreeNode("ga:visits"));
		session.add(new DefaultMutableTreeNode("ga:bounces"));
		session.add(new DefaultMutableTreeNode("ga:entranceBounceRate"));
		session.add(new DefaultMutableTreeNode("ga:visitBounceRate"));
		session.add(new DefaultMutableTreeNode("ga:timeOnSite"));
		session.add(new DefaultMutableTreeNode("ga:avgTimeOnSite"));
		trafficSources.add(new DefaultMutableTreeNode("ga:organicSearches"));
		pageTracking.add(new DefaultMutableTreeNode("ga:entrances"));
		pageTracking.add(new DefaultMutableTreeNode("ga:entranceRate"));
		pageTracking.add(new DefaultMutableTreeNode("ga:pageviews"));
		pageTracking.add(new DefaultMutableTreeNode("ga:pageviewsPerVisit"));
		pageTracking.add(new DefaultMutableTreeNode("ga:uniquePageviews"));
		pageTracking.add(new DefaultMutableTreeNode("ga:timeOnPage"));
		pageTracking.add(new DefaultMutableTreeNode("ga:avgTimeOnPage"));
		pageTracking.add(new DefaultMutableTreeNode("ga:exits"));
		pageTracking.add(new DefaultMutableTreeNode("ga:exitRate"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchResultViews"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchUniques"));
		internalSearch.add(new DefaultMutableTreeNode("ga:avgSearchResultViews"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchVisits"));
		internalSearch.add(new DefaultMutableTreeNode("ga:percentVisitsWithSearch"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchDepth"));
		internalSearch.add(new DefaultMutableTreeNode("ga:avgSearchDepth"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchRefinements"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchDuration"));
		internalSearch.add(new DefaultMutableTreeNode("ga:avgSearchDuration"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchExits"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchExitRate"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchGoal(n)ConversionRate"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchGoalConversionRateAll"));
		internalSearch.add(new DefaultMutableTreeNode("ga:goalValueAllPerSearch"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:pageLoadTime"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:pageLoadSample"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:avgPageLoadTime"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:domainLookupTime"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:avgDomainLookupTime"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:pageDownloadTime"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:avgPageDownloadTime"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:redirectionTime"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:avgRedirectionTime"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:serverConnectionTime"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:avgServerConnectionTime"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:serverResponseTime"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:avgServerResponseTime"));
		siteSpeed.add(new DefaultMutableTreeNode("ga:speedMetricsSample"));
		exceptionTracking.add(new DefaultMutableTreeNode("exceptions"));
		exceptionTracking.add(new DefaultMutableTreeNode("fatalExceptions"));
	}
	
	
	/**
	 * Add the selection path
	 */
	@Override
	public void setSelectionPath(TreePath path) {
		addSelectionPath(path);
		return;
	}
	
	
	/**
	 * Sets the selection path
	 */
	@Override
	public void setSelectionPaths(TreePath[] paths) {
		addSelectionPaths(paths);
		return;
	}

	
	/**
	 * Sets the selection row if there is only one selected
	 */
	@Override
	public void setSelectionRow(int row) {
		addSelectionRow(row);
		return;
	}

	
	/**
	 * Sets the selection rows if there is more than one selected
	 */
	@Override
	public void setSelectionRows(int[] rows) {
		addSelectionRows(rows);
		return;
	}
}
