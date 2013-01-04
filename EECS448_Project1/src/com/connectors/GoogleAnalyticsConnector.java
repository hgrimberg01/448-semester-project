/*
Copyright (C) 2012 TeamPlusPlus

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */
/**
 * Name: GoogleAnalyticsConnector.java
 * Description: The connector class that communicates with Google Analytics and retrieves the results.
 * 
 * @author Google (original license info below)
 * 
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.connectors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.model.Accounts;
import com.google.api.services.analytics.model.GaData;
import com.google.api.services.analytics.model.GaData.ColumnHeaders;
import com.google.api.services.analytics.model.Profiles;
import com.google.api.services.analytics.model.Webproperties;
import com.google.api.services.samples.shared.cmdline.oauth2.OAuth2Native;
import com.google.api.services.samples.shared.cmdline.oauth2.LocalServerReceiver;
import com.pojos.Report;

/**
 * GoogleAnalyticsConnector
 * 
 * Connector class for initiating a connection with Google Analytics and
 * returning data.
 */
public class GoogleAnalyticsConnector {

	/** Instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	private static Analytics analytics;
	private static String profileId;

	/** Instance of the JSON factory. */
	private static JsonFactory JSON_FACTORY;

	public GoogleAnalyticsConnector() {
		HTTP_TRANSPORT = new NetHttpTransport();
		JSON_FACTORY = new JacksonFactory();
	}

	public void start() throws Throwable {
		try {
			analytics = initializeAnalytics();
			profileId = getFirstProfileId(analytics);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Connects to Google Analytics and uses the Management APIs to get the
	 * first profile ID for the user. The requested data is then returned for
	 * that profile.
	 * 
	 * @param request
	 *            A report profile that will be queried on the GA servers
	 * @param format
	 *            A boolean that determines whether to format as plain text or
	 *            HTML; 0 for plain text, 1 for HTML
	 * @return The results from Google Analytics as a string
	 * 
	 * @throws Throwable
	 */
	public String getData(Report request, boolean format) throws Throwable {
		try {

			if (profileId == null) {
				throw (new Exception("No profiles found."));
			} else {

				GaData gaData = executeDataQuery(analytics, profileId, request);
				return printGaData(gaData, request.getTitle(),
						request.getStartDate(), request.getEndDate(), format);
			}
		} catch (GoogleJsonResponseException e) {
			throw (new Exception("There was a service error: "
					+ e.getDetails().getCode() + " : "
					+ e.getDetails().getMessage()));
		}
	}

	/**
	 * Performs all necessary setup steps for running requests against the API.
	 * 
	 * @return An initialized Analytics service object.
	 * 
	 * @throws Exception
	 *             if an issue occurs with OAuth2Native authorize.
	 */
	private static Analytics initializeAnalytics() throws Exception {
		// Authorization.
		Credential credential = OAuth2Native.authorize(HTTP_TRANSPORT,
				JSON_FACTORY, new LocalServerReceiver(),
				Arrays.asList(AnalyticsScopes.ANALYTICS_READONLY));

		// Set up and return Google Analytics API client.
		return new Analytics.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(
						"Google-Analytics-Hello-Analytics-API-Sample").build();
	}

	/**
	 * Returns the first profile id by traversing the Google Analytics
	 * Management API. This makes 3 queries, first to the accounts collection,
	 * then to the web properties collection, and finally to the profiles
	 * collection. In each request the first ID of the first entity is retrieved
	 * and used in the query for the next collection in the hierarchy.
	 * 
	 * @param analytics
	 *            the analytics service object used to access the API.
	 * @return the profile ID of the user's first account, web property, and
	 *         profile.
	 * @throws IOException
	 *             if the API encounters an error.
	 */
	private static String getFirstProfileId(Analytics analytics)
			throws IOException {
		String profileId = null;

		// Query accounts collection.
		Accounts accounts = analytics.management().accounts().list().execute();

		if (accounts.getItems().isEmpty()) {
			System.err.println("No accounts found");
		} else {
			String firstAccountId = accounts.getItems().get(0).getId();

			// Query webproperties collection.
			Webproperties webproperties = analytics.management()
					.webproperties().list(firstAccountId).execute();

			if (webproperties.getItems().isEmpty()) {
				System.err.println("No Webproperties found");
			} else {
				String firstWebpropertyId = webproperties.getItems().get(0)
						.getId();

				// Query profiles collection.
				Profiles profiles = analytics.management().profiles()
						.list(firstAccountId, firstWebpropertyId).execute();

				if (profiles.getItems().isEmpty()) {
					System.err.println("No profiles found");
				} else {
					profileId = profiles.getItems().get(0).getId();
				}
			}
		}
		return profileId;
	}

	/**
	 * Returns the top 25 organic search keywords and traffic source by visits.
	 * The Core Reporting API is used to retrieve this data.
	 * 
	 * @param analytics
	 *            the analytics service object used to access the API.
	 * @param profileId
	 *            the profile ID from which to retrieve data.
	 * @return the response from the API.
	 * @throws IOException
	 *             if an API error occurred.
	 */
	private static GaData executeDataQuery(Analytics analytics,
			String profileId, Report request) throws IOException {
		if (request.getFilter().equals("EMPTY")) {
			return analytics.data().ga()
					.get("ga:" + profileId, // Table Id. ga: + profile id.
							request.getStartDate(), // Start date.
							request.getEndDate(), // End date.
							request.getMetrics())
					// Metrics.
					.setDimensions(request.getDimensions().toString())
					.setSort(request.getSort().toString())
					.setMaxResults(request.getMaxResults()).execute();

		} else {
			return analytics.data()
					.ga()
					.get("ga:" + profileId, // Table Id. ga: + profile id.
							request.getStartDate(), // Start date.
							request.getEndDate(), // End date.
							request.getMetrics())
					// Metrics.
					.setDimensions(request.getDimensions().toString())
					.setSort(request.getSort().toString())
					.setFilters(request.getFilter())
					.setMaxResults(request.getMaxResults()).execute();

		}
	}

	/**
	 * Prints the output from the Core Reporting API to a buffer. The profile
	 * name is printed along with each column name and all the data in the rows.
	 * 
	 * @param results
	 *            Data returned from the Core Reporting API
	 * @param title
	 *            Title of the result set
	 * @param startDate
	 *            Starting date for the result set
	 * @param endDate
	 *            Ending date for the result set
	 * @param format
	 *            flag sets whether to format as plain text or HTML table
	 * @return string containing the printed data.
	 */
	private static String printGaData(GaData results, String title,
			String start, String end, boolean format) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		PrintStream output = new PrintStream(buffer);

		if (!format) {
			output.println("Title: " + title);
			output.println("Start Date: " + start);
			output.println("End Date: " + end);
			output.println("Printing results for the profile: "
					+ results.getProfileInfo().getProfileName());
			output.println();

			if (results.getRows() == null || results.getRows().isEmpty()) {
				output.println("No results found.");
			} else {

				// Print column headers.
				for (ColumnHeaders header : results.getColumnHeaders()) {
					output.printf("%35s", header.getName());
				}
				output.println();

				// Print actual data.
				for (List<String> row : results.getRows()) {
					for (String column : row) {
						output.printf("%35s", column);
					}
					output.println();
				}

				output.println();
			}
		} else {
			output.println("<html><body>");
			output.println("<p>Title: " + title + "</p>");
			output.println("<p>Start Date: " + start + "</p>");
			output.println("<p>End Date: " + end + "</p>");
			output.println("<p>Printing results for the profile: "
					+ results.getProfileInfo().getProfileName() + "</p>");

			if (results.getRows() == null || results.getRows().isEmpty()) {
				output.println("<p>No results found.</p>");
			} else {
				output.println("<table cellpadding='5px'>");
				output.println("<tr>");
				for (ColumnHeaders header : results.getColumnHeaders()) {
					output.println("<th>" + header.getName() + "</th>");
				}
				output.println("</tr>");
				// Print actual data.
				for (List<String> row : results.getRows()) {
					output.println("<tr>");
					for (String column : row) {
						output.println("<td>" + column + "</td>");
					}
					output.println("</tr>");
				}
				output.println("</table>");
			}
			output.println("</body></html>");
		}
		return buffer.toString();
	}
}
