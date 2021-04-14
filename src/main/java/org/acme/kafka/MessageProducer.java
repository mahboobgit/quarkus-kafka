package org.acme.kafka;

import java.time.Duration;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

/**
 * A bean producing random prices every 5 seconds.
 * The prices are written to a Kafka topic (prices). The Kafka configuration is specified in the application configuration.
 */
@ApplicationScoped
public class MessageProducer {

    private Random random = new Random();

    @Outgoing("generate-appmail")
    public Multi<KafkaInFlightPayload> generate() {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(5))
                .onOverflow().drop()
                .map(tick -> {
                    KafkaInFlightPayload payload = new KafkaInFlightPayload();
                    ObjectMapper objectMapper = new ObjectMapper();
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("Body",  String.format("%s. %s", random.nextInt(100), "Hello this is a test mail."));
                    try {
                        payload.setPayload(objectMapper.writeValueAsString(node));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    payload.addToHeader("mail-to", String.format("Mahboob-%s", random.nextInt(100)), true);
                    payload.addToHeader("partition", String.format("%s", random.nextInt(100)), true);

                    return payload;
                });
    }

}
