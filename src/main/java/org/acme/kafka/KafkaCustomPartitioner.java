//package org.acme.kafka;
//
//import org.acme.kafka.utility.InFlightConstants;
//import org.acme.kafka.utility.UtilityKafkaPartition;
//import org.acme.kafka.utility.UtilityLog;
//import org.apache.kafka.clients.producer.Partitioner;
//import org.apache.kafka.common.Cluster;
//import org.jboss.logging.Logger;
//
//import java.util.Map;
//
//public class KafkaCustomPartitioner implements Partitioner {
//
//    private static final Logger log = Logger.getLogger(KafkaCustomPartitioner.class);
//
//    private String partitionKey;
//    private String breadcrumbId;
//
//
//    @Override
//    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
//
//        try {
//
//            int partitionNumber;
//            int numberOfPartition = cluster.availablePartitionsForTopic(topic).size();
//            partitionNumber = UtilityKafkaPartition.getPartitionNumber(this.partitionKey, numberOfPartition);
//            log.warn(UtilityLog.formatMessage(String.format("Kafka producer topic: %s [Partition: %s] for Id: %s", topic, partitionNumber, this.partitionKey), this.breadcrumbId));
//            return partitionNumber;
//
//        }catch (Exception ignored) {
//            log.fatal(UtilityLog.formatMessage(String.format("Could not calculate Partition Number based on the PartitionKey: %s", this.partitionKey), this.breadcrumbId), ignored);
//            return 0;
//        }
//    }
//
//    @Override
//    public void configure(Map<String, ?> configs) {
//        if(configs.containsKey(InFlightConstants.PartitionKey.toString()))
//            this.partitionKey = configs.get(InFlightConstants.PartitionKey.toString()).toString();
//
//        if(configs.containsKey(InFlightConstants.BreadcrumbId.toString()))
//            this.breadcrumbId = configs.get(InFlightConstants.BreadcrumbId.toString()).toString();
//
//    }
//
//    @Override
//    public void close() { }
//
//}
//
