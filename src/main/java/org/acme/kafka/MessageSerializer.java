package org.acme.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.jboss.logging.Logger;

import java.util.Map;

public class MessageSerializer implements Serializer<KafkaInFlightPayload> {

    public static final String BreadcrumbId = "breadcrumbId";

    private String breadcrumbId;
    private static final Logger log = Logger.getLogger(MessageSerializer.class);


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        if(configs.containsKey(BreadcrumbId.toString())) {
            this.breadcrumbId = configs.get(BreadcrumbId.toString()).toString();
        }
    }

    @Override
    public byte[] serialize(String s, KafkaInFlightPayload kafkaInFlightPayload) {
        return getBytes(kafkaInFlightPayload);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, KafkaInFlightPayload data) {
        return getBytes(data);
    }

    @Override
    public void close() {

    }

    private byte[] getBytes(KafkaInFlightPayload payload){
        byte[] bytes = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String str = objectMapper.writeValueAsString(payload);
            bytes = str.getBytes();
        } catch (Exception ignored) {
            log.error(String.format("[breadcrumbId: %s] Error Serializing KafkaInflightPayload. \nError details: %s", this.breadcrumbId, ignored.getMessage()));
        }
        return bytes;
    }
}

