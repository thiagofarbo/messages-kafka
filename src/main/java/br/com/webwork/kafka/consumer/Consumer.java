package br.com.webwork.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {
	
	final Logger logger = LoggerFactory.getLogger(Consumer.class);
	
    public static final String bootStrapServer = "127.0.0.1:9092";
    public static final String topic = "first_topic";
    public static final String key = "id_";
    public static final String idGroup = "my-application-group";


    public void consume(final Properties properties) throws ExecutionException, InterruptedException {

//      Create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

//      Subscribe consumer to our topic(s).
        consumer.subscribe(Arrays.asList(topic));

//      Poll for new data

        while(true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            
            for(ConsumerRecord record: records) {
            	logger.info("Key: "+ record.key() + ", ValueThiago: " + record.value());
            	logger.info("Partition: "+ record.partition() + ", Offset: " + record.offset());
            }
        }
     }
}
