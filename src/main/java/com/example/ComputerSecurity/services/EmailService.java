package com.example.ComputerSecurity.services;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailService {
    public static void sendEmail(String recipientEmail) {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = "rishabhgarg9818@gmail.com";//
        final String password = "Qweasdzxc123";
        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("rishabhgarg9818@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail, false));
            msg.setSubject("Unauthorised access to your Secure-Shop account");
            msg.setText("Seems like someone has been trying to repeatedly make password attempts to your account." +
                    " We recommend you to change your password to strong if it wasn't you.");
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
        } catch (MessagingException e) {
            System.out.println("Issue: " + e);
        }
    }
}
