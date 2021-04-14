package org.acme.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.jboss.logging.Logger;

import java.util.Map;

public class MessageDeserializer implements Deserializer<KafkaInFlightPayload> {

    public static final String BreadcrumbId = "breadcrumbId";

    private String breadcrumbId;
    private static final Logger log = Logger.getLogger(MessageDeserializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        if(configs.containsKey(BreadcrumbId))
            this.breadcrumbId = configs.get(BreadcrumbId).toString();
    }

    @Override
    public KafkaInFlightPayload deserialize(String s, byte[] bytes) {
        return getString(bytes);
    }

    @Override
    public KafkaInFlightPayload deserialize(String topic, Headers headers, byte[] data) {
        return getString(data);
    }

    @Override
    public void close() {

    }

    private KafkaInFlightPayload getString(byte[] bytes){
        KafkaInFlightPayload inFlightPayload = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            inFlightPayload = objectMapper.readValue(bytes, KafkaInFlightPayload.class);
        } catch (Exception ignored) {
            log.error(String.format("[breadcrumbId: %s] Error DeSerializing to KafkaInflightPayload. \nError details: %s", this.breadcrumbId, ignored.getMessage()));
        }
        return inFlightPayload;
    }


}

