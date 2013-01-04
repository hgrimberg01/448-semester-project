/**
 * Name: treeDimensions.java
 * Description: Contains all of the dimensions in a tree form
 * 
 * @author Seth Polsley
 * Date Created: 11/13/12
 */

package com.gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * Extends a JTree that is pre-populated with all necessary dimensions
 * @author spolsley
 *
 */
public class treeDimensions extends JTree{
	private static final long serialVersionUID = 1L;
	public static DefaultMutableTreeNode dimensions = new DefaultMutableTreeNode("Dimensions");
	static DefaultMutableTreeNode visitor = new DefaultMutableTreeNode("Visitor");
	static DefaultMutableTreeNode session = new DefaultMutableTreeNode("Session");
	static DefaultMutableTreeNode trafficSources = new DefaultMutableTreeNode("Traffic Sources");
	static DefaultMutableTreeNode platformDevice = new DefaultMutableTreeNode("Platform & Device");
	static DefaultMutableTreeNode geoNetwork = new DefaultMutableTreeNode("Location");
	static DefaultMutableTreeNode system = new DefaultMutableTreeNode("System");
	static DefaultMutableTreeNode pageTracking = new DefaultMutableTreeNode("Page Tracking");
	static DefaultMutableTreeNode internalSearch = new DefaultMutableTreeNode("Internal Search");
	static DefaultMutableTreeNode time = new DefaultMutableTreeNode("Time");
	
	
	/** 
	 * Initializes dimensions of the dimensions tree 
	 */
	public treeDimensions() {
		
		super(dimensions);
		this.setRootVisible(false);
		
		dimensions.add(visitor);
		dimensions.add(session);
		dimensions.add(trafficSources);
		dimensions.add(platformDevice);		
		dimensions.add(geoNetwork);
		dimensions.add(system);		
		dimensions.add(pageTracking);
		dimensions.add(internalSearch);		
		dimensions.add(time);
	
		visitor.add(new DefaultMutableTreeNode("ga:visitorType"));
		visitor.add(new DefaultMutableTreeNode("ga:visitCount"));
		visitor.add(new DefaultMutableTreeNode("ga:daysSinceLastVisit"));
		visitor.add(new DefaultMutableTreeNode("ga:userDefinedValue"));
		session.add(new DefaultMutableTreeNode("ga:visitLength"));
		trafficSources.add(new DefaultMutableTreeNode("ga:referralPath"));
		trafficSources.add(new DefaultMutableTreeNode("ga:campaign"));
		trafficSources.add(new DefaultMutableTreeNode("ga:source"));
		trafficSources.add(new DefaultMutableTreeNode("ga:medium"));
		trafficSources.add(new DefaultMutableTreeNode("ga:keyword"));
		trafficSources.add(new DefaultMutableTreeNode("ga:adContent"));
		trafficSources.add(new DefaultMutableTreeNode("ga:socialNetwork"));
		trafficSources.add(new DefaultMutableTreeNode("ga:hasSocialSourceReferral"));
		platformDevice.add(new DefaultMutableTreeNode("ga:browser"));
		platformDevice.add(new DefaultMutableTreeNode("ga:browserVersion"));
		platformDevice.add(new DefaultMutableTreeNode("ga:operatingSystem"));
		platformDevice.add(new DefaultMutableTreeNode("ga:operatingSystemVersion"));
		platformDevice.add(new DefaultMutableTreeNode("ga:isMobile"));
		platformDevice.add(new DefaultMutableTreeNode("ga:mobileDeviceBranding"));
		platformDevice.add(new DefaultMutableTreeNode("ga:mobileDeviceModel"));
		platformDevice.add(new DefaultMutableTreeNode("ga:mobileInputSelector"));
		platformDevice.add(new DefaultMutableTreeNode("ga:mobileInputSelector"));
		platformDevice.add(new DefaultMutableTreeNode("ga:mobileDeviceInfo"));
		geoNetwork.add(new DefaultMutableTreeNode("ga:continent"));
		geoNetwork.add(new DefaultMutableTreeNode("ga:subContinent"));
		geoNetwork.add(new DefaultMutableTreeNode("ga:country"));
		geoNetwork.add(new DefaultMutableTreeNode("ga:region"));
		geoNetwork.add(new DefaultMutableTreeNode("ga:metro"));
		geoNetwork.add(new DefaultMutableTreeNode("ga:city"));
		geoNetwork.add(new DefaultMutableTreeNode("ga:latitude"));
		geoNetwork.add(new DefaultMutableTreeNode("ga:longitude"));
		geoNetwork.add(new DefaultMutableTreeNode("ga:networkDomain"));
		geoNetwork.add(new DefaultMutableTreeNode("ga:networkLocation"));
		system.add(new DefaultMutableTreeNode("ga:flashVersion"));
		system.add(new DefaultMutableTreeNode("ga:javaEnabled"));
		system.add(new DefaultMutableTreeNode("ga:language"));
		system.add(new DefaultMutableTreeNode("ga:screenColors"));
		pageTracking.add(new DefaultMutableTreeNode("ga:hostname"));
		pageTracking.add(new DefaultMutableTreeNode("ga:pagePath"));
		pageTracking.add(new DefaultMutableTreeNode("ga:pagePathLevel1"));
		pageTracking.add(new DefaultMutableTreeNode("ga:pagePathLevel2"));
		pageTracking.add(new DefaultMutableTreeNode("ga:pagePathLevel3"));
		pageTracking.add(new DefaultMutableTreeNode("ga:pagePathLevel4"));
		pageTracking.add(new DefaultMutableTreeNode("ga:pageTitle"));
		pageTracking.add(new DefaultMutableTreeNode("ga:landingPagePath"));
		pageTracking.add(new DefaultMutableTreeNode("ga:secondPagePath"));
		pageTracking.add(new DefaultMutableTreeNode("ga:exitPagePath"));
		pageTracking.add(new DefaultMutableTreeNode("ga:perviousPagePath"));
		pageTracking.add(new DefaultMutableTreeNode("ga:nextPagePath"));
		pageTracking.add(new DefaultMutableTreeNode("ga:pageDepth"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchUsed"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchKeyword"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchKeywordRefinement"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchCategory"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchStartPage"));
		internalSearch.add(new DefaultMutableTreeNode("ga:searchDestinationPage"));
		time.add(new DefaultMutableTreeNode("ga:date"));
		time.add(new DefaultMutableTreeNode("ga:year"));
		time.add(new DefaultMutableTreeNode("ga:month"));
		time.add(new DefaultMutableTreeNode("ga:week"));
		time.add(new DefaultMutableTreeNode("ga:day"));
		time.add(new DefaultMutableTreeNode("ga:hour"));
		time.add(new DefaultMutableTreeNode("ga:nthMonth"));
		time.add(new DefaultMutableTreeNode("ga:nthWeek"));
		time.add(new DefaultMutableTreeNode("ga:nthDay"));
		time.add(new DefaultMutableTreeNode("ga:dayOfWeek"));

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
