package br.com.webwork.kafka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.webwork.kafka.consumer.Consumer;
import br.com.webwork.kafka.producer.Producer;
import br.com.webwork.kafka.utils.ConfigurationUtils;


@SpringBootApplication
public class MessengerKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessengerKafkaApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			
			
			new Producer().producerWithCallBack(ConfigurationUtils.producerConfiguration());
			
			new Consumer().consume(ConfigurationUtils.consumerConfiguration());
		};
	}
}