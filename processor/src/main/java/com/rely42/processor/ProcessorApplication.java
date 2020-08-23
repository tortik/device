package com.rely42.processor;

import com.rely42.processor.adapter.db.DBConfiguration;
import com.rely42.processor.adapter.jms.JmsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@Import(value = {
		JmsConfiguration.class,
		DBConfiguration.class
})
@EnableJms
public class ProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessorApplication.class, args);
	}

}
