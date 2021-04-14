package org.acme.kafka.utility;

import org.jboss.logging.Logger;

public class UtilityKafkaPartition {

    private static final Logger log = Logger.getLogger(UtilityKafkaPartition.class);
    public static final String KafkaBootUpError = "KAFKA BOOT ERROR.";

    public static int getPartitionNumber(String id, long numberOfPartition){
        int partitionId = 0;
        try {
            //id = id.replaceFirst("^0+", "");
            long longId = Long.parseLong(id);
            long remainder = longId % numberOfPartition;
            partitionId = Math.toIntExact(remainder);
        }
        catch(Exception ignored){
            log.error(String.format("%s Fail to generate partition number. Defaulting it to partition 0. Error: %s", KafkaBootUpError, ignored.getMessage()));
        }
        return partitionId;
    }
}
