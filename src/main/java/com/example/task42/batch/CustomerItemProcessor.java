package com.example.task42.batch;

import com.example.task42.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class CustomerItemProcessor implements ItemProcessor<Customer, MimeMessage>{
    private static final Logger logger = LoggerFactory.getLogger(CustomerItemProcessor.class);

    private String from;
    private String password;

    public CustomerItemProcessor(String from, String password) {
        this.from = from;
        this.password = password;
    }

    @Override
    public MimeMessage process(Customer customer) throws Exception {
        String to = customer.getEmail();
        String subject = "Notification";
        String msg = "You don't have enough money to transaction. Your balance: " + customer.getBalance();

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        MimeMessage message = new MimeMessage(session);
        message.setSubject(subject);
        message.setContent(msg, "text/plain");
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        logger.info("Message to {} has been prepared", customer.getEmail());

        return message;
    }
}
