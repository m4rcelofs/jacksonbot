package com.marcelofs.jackson;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;

import static javax.mail.Session.getInstance;
import static javax.mail.Transport.send;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.appengine.api.utils.SystemProperty;

public class Mailer {

	public class MailAddress {
		public String name;
		public String address;

		public MailAddress() {
		}

		public MailAddress(String address) {
			this.address = address;
		}

		public MailAddress(String name, String address) {
			this.name = name;
			this.address = address;
		}

		public String toString() {
			return name + ":" + address;
		}
	}

	private static Logger logger = Logger.getLogger(Mailer.class.getName());

	public void mail(String subject, String message, MailAddress... emails)
			throws IOException {

		try {
			Session session = getInstance(new Properties());

			Message msg = new MimeMessage(session);

			String from = "notifications@" + SystemProperty.applicationId.get()
					+ ".appspotmail.com";
			msg.setFrom(new InternetAddress(from, "Jackson"));
			msg.setSubject(subject);
			msg.setContent(message, "text/html");

			for (MailAddress email : emails) {
				if (email.name == null || email.name.isEmpty())
					msg.addRecipient(Message.RecipientType.TO,
							new InternetAddress(email.address));
				else
					msg.addRecipient(Message.RecipientType.TO,
							new InternetAddress(email.address, email.name));
				logger.log(
						Level.WARNING,
						"Emailing: "
								+ Arrays.toString(msg
										.getRecipients(Message.RecipientType.TO)));
				send(msg);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e);
		}
	}

}
