package br.com.webwork.kafka.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer{
	
	final Logger logger = LoggerFactory.getLogger(Producer.class);

    public static final String topic = "first_topic";
    public static final String key = "id_";

    public void producerWithKeys(final Properties properties) throws ExecutionException, InterruptedException {

//      Create producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);


        for(int i = 0; i<10; i++) {

//          Create a producer record
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key+ Integer.toString(i), "Message " +i+ " was sent via application Java :)");

            logger.info("Key " + key);

            //      Send data - asynchronous
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception exception) {

                    if (exception == null) {
                        logger.info("Message received from topic. \n" +
                                "Topic: " + recordMetadata.topic() + "\n" +
                                "Partition: " + recordMetadata.partition() + "\n" +
                                "Timestamp: " + recordMetadata.timestamp()
                        );
                    } else {
                        logger.error("Error while producing message." + exception);
                    }
                }
            });
        }

//      Flush data
        producer.flush();

//      Flush and close producer
        producer.close();
    }

    public void producerWithCallBack(final Properties properties){

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for(int i = 0; i<10; i++) {

                ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, "Message " +i+ " sent via application Java :)");

                producer.send(record, new Callback() {
                    public void onCompletion(RecordMetadata recordMetadata, Exception exception) {

                        if (exception == null) {
                            logger.info("Message receive from topic. \n" +
                                    "Topic: " + recordMetadata.topic() + "\n" +
                                    "Partition: " + recordMetadata.partition() + "\n" +
                                    "Timestamp: " + recordMetadata.timestamp()
                            );
                        } else {
                            logger.error("Error while producing message." + exception);
                        }
                    }
                });
        }

        producer.flush();

        producer.close();
    }
}
