package com.rely42.device;

import com.rely42.device.adapter.jms.JmsConfiguration;
import com.rely42.device.core.sender.SenderConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {
		JmsConfiguration.class,
		SenderConfiguration.class,
})
@EnableAutoConfiguration
public class DeviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceApplication.class, args);
	}

}
