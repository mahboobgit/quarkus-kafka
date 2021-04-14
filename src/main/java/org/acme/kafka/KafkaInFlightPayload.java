package org.acme.kafka;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.HashMap;
import java.util.Map;

@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KafkaInFlightPayload {

    public static final String LongDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private Map<String, Object> headers = new HashMap<>();


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LongDateFormat)
    private String expireOn;


    private Object payload; //This field will hold the main txn payload ( like Withdraw, Deposit ect)
    private Object compensatingPayload; //Will hold the txn payload for reversal of the main txn.


    public String getExpireOn() { return expireOn; }
    public void setExpireOn(String expireOn) { this.expireOn = expireOn; }


    public Map<String, Object> getHeaders() { return headers; }
    public KafkaInFlightPayload setHeaders(Map<String, Object> headers) { this.headers = headers;  return this;}

    public Object findHeader(String key){ return headers.getOrDefault(key, null); }
    public KafkaInFlightPayload addToHeader(String key, Object value, boolean overrideIfPresent){
        if(overrideIfPresent){
            headers.remove(key);
            headers.put(key, value);
        }
        else {
            if (!headers.containsKey(key)){
                headers.put(key, value);
            }
        }
        return this;
    }

    public Object getPayload() { return payload; }
    public void setPayload(Object payload) { this.payload = payload; }

    public Object getCompensatingPayload() { return compensatingPayload; }
    public KafkaInFlightPayload setCompensatingPayload(Object compensatingPayload) {  this.compensatingPayload = compensatingPayload; return this; }

    @Override
    public String toString()
    {
        return "KafkaInFlightPayload {"
                + "payload={" + ((payload != null) ? payload.toString() : "") + "},"
                + "compensatingPayload={" + ((compensatingPayload != null) ? compensatingPayload.toString() : "") + "}"
                +"}";
    }

}


