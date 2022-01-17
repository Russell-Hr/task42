package com.example.task42.batch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
public class CustomerItemWriter implements ItemWriter<MimeMessage>{
    private static final Logger logger = LoggerFactory.getLogger(CustomerItemWriter.class);

    private String from;

    public CustomerItemWriter(String from) {
        this.from = from;
    }

    @Override
    public void write(List<? extends MimeMessage> list) throws Exception {
        for (MimeMessage message :
                list) {
            Session session = message.getSession();

            Transport transport = session.getTransport();
            InternetAddress addressFrom = new InternetAddress(from);

            message.setSender(addressFrom);

            transport.connect();
            Transport.send(message);
            transport.close();

            logger.info("Message has been sent");
        }
    }
}
