package org.acme.kafka;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.reactive.messaging.annotations.Broadcast;

import java.util.Date;
import java.util.Map;

/**
 * A bean consuming data from the "appmail" Kafka topic and applying some conversion.
 * The result is pushed to the "my-data-stream" stream which is an in-memory stream.
 */
@ApplicationScoped
public class MessageConsumer {


    // Consume from the `appmail` channel and produce to the `my-data-stream` channel
    @Incoming("appmail")
    @Outgoing("my-data-stream")
    // Send to all subscribers
    @Broadcast
    // Acknowledge the messages before calling this method.
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public KafkaInFlightPayload process(Message<KafkaInFlightPayload> kafkaInFlightMessage) {

        KafkaInFlightPayload kafkaInFlightPayload = kafkaInFlightMessage.getPayload();

        Map<String, Object> headers = kafkaInFlightPayload.getHeaders();
        headers.put("AddedOn", new Date());

        kafkaInFlightPayload.setHeaders(headers);

        if (Integer.parseInt(String.valueOf( headers.get("partition"))) % 2 == 0  )
            kafkaInFlightMessage.nack(new Exception("Number is divisible by 2!!"));
        else
            kafkaInFlightMessage.ack();

        return kafkaInFlightPayload;
    }

}
