package org.acme.kafka.utility;

public enum InFlightConstants {
    PartitionKey("PK"),
    BreadcrumbId("BId"),
    PartitionNumber("PN");



    private final String shortName;

    @Override
    public String toString(){ return this.shortName; }

    InFlightConstants(String shortName) { this.shortName = shortName; }
}
