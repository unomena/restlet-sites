package com.restlet.frontend.web.firewall.handler;

import java.security.Security;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.routing.Filter;

import com.restlet.frontend.web.firewall.counter.CounterFeedback;
import com.sun.mail.smtp.SMTPTransport;

public class AlertHandler extends ThresholdHandler {

    private String username;

    private String password;

    private String smtpHost;

    private Address[] receivers;

    private Session session;

    public AlertHandler(int limit, String username, String password,
            String smtpHost, String[] receivers) {
        super(limit);
        this.username = username;
        this.password = password;
        this.smtpHost = smtpHost;
        this.receivers = new Address[receivers.length];
        int count = 0;
        for (String receiver : receivers) {
            try {
                this.receivers[count] = new InternetAddress(receiver);
            } catch (AddressException e) {
                Context.getCurrentLogger().log(Level.WARNING,
                        "AlertHandler : Invalid email address");
            }
            count++;
        }
        initialize();
    }

    private void initialize() {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");
        session = Session.getInstance(props, null);
    }

    @Override
    public int thresholdActivated(Request request, Response response,
            CounterFeedback counterFeedback) {

        Context.getCurrentLogger().log(Level.FINE,
                "User " + counterFeedback.getCounterValue() + " alerted.");

        final MimeMessage msg = new MimeMessage(session);

        try {
            msg.setFrom(new InternetAddress(username));

            msg.setRecipients(Message.RecipientType.TO, receivers);

            msg.setSubject("Alert handler");
            msg.setText("User " + counterFeedback.getCounterValue()
                    + " alerted.", "utf-8");
            msg.setSentDate(new Date());

            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");

            t.connect("smtp.gmail.com", username, password);
            t.sendMessage(msg, msg.getAllRecipients());

            t.close();
        } catch (AddressException e) {

            Context.getCurrentLogger().log(Level.WARNING,
                    "AlertHandler : AddressException");

        } catch (MessagingException e) {

            Context.getCurrentLogger().log(Level.WARNING,
                    "AlertHandler : MessagingException ");
            e.printStackTrace();

        }

        return Filter.SKIP;

    }

}
