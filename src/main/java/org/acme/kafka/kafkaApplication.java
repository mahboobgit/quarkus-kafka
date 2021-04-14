package org.acme.kafka;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain(name="kafkaApplication")
public class kafkaApplication {
    public static void main(String ...args){
        Quarkus.run(KafkaServiceIntegration.class, args);
    }


    public static class KafkaServiceIntegration implements QuarkusApplication {


        @Override
        public int run(String... args) throws Exception {
            Quarkus.waitForExit();
            return 0;
        }
    }
}
