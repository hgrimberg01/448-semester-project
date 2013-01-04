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
 * Name: MailConnector.java
 * Description: Connects to the SMTP server and sends email messages
 * 
 * @author Seth Polsley
 * Date Created: 12/4/12
 */

package com.connectors;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * MailConnector
 * 
 * Connector to the SMTP server
 */
public class MailConnector {

	private String emailHost;
	private String emailAddress;

	/**
	 * Initialize the MailConnector
	 * 
	 * @param host
	 *            Mail server host
	 * @param username
	 *            Outgoing email address
	 */
	public MailConnector(String host, String username) {
		emailHost = host;
		emailAddress = username;
	}

	/**
	 * Sends an email with the specified subject, body, and format to the
	 * recipients
	 * 
	 * @param subject
	 *            Title of the message
	 * @param recipients
	 *            A list of addresses to send the message to
	 * @param body
	 *            The body of the message
	 * @param format
	 *            A boolean flag that toggles plain text or HTML emails
	 * @return boolean indicating success or failure
	 * @throws MessagingException
	 */
	public boolean sendEmail(String subject, List<String> recipients,
			String body, boolean format) throws MessagingException {

		if (recipients == null)
			return false;
		else if (recipients.isEmpty())
			return false;
		else {
			// Setup mail server
			Properties properties = System.getProperties();
			properties.setProperty("mail.smtp.host", emailHost);

			// Get the default Session object
			Session session = Session.getDefaultInstance(properties);
			MimeMessage message = new MimeMessage(session);

			// Set message fields

			message.setFrom(new InternetAddress(emailAddress));
			message.setSubject(subject);
			if (format)
				message.setContent(body, "text/html");
			else
				message.setText(body);

			for (String to : recipients) {
				message.addRecipient(Message.RecipientType.BCC,
						new InternetAddress(to));
			}

			// Send message
			Transport.send(message);
		}
		return true;
	}
}
