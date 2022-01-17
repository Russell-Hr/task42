package com.example.task42.cfg;
import com.example.task42.batch.CustomerItemProcessor;
import com.example.task42.batch.CustomerItemWriter;
import com.example.task42.entity.Customer;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.HibernateCursorItemReader;
import org.springframework.batch.item.database.builder.HibernateCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.internet.MimeMessage;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    private final String from = "Trantor.20202020@gmail.com";
    private final String password = "Trantor2020";

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public HibernateCursorItemReader<Customer> itemReader() {
        logger.info("Trying to read users which have balance less than 10.00$ from table: customers");
        return new HibernateCursorItemReaderBuilder<Customer>()
                .name("customerReader")
                .sessionFactory(sessionFactory)
                .queryString("from Customer where balance < 10")
                .build();
    }

    @Bean
    public ItemProcessor<Customer, MimeMessage> itemProcessor() {
        return new CustomerItemProcessor(from, password);
    }

    @Bean
    public ItemWriter<MimeMessage> itemWriter() {
        return new CustomerItemWriter(from);
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Customer, MimeMessage>chunk(10)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobBuilderFactory.get("firstBatchJob").flow(step1).end().build();
    }
}
