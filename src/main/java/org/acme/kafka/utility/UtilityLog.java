package org.acme.kafka.utility;

public class UtilityLog {


    public static final String BreadcrumbId = "breadcrumbId";

    /***
     * Returns the formatted string to be logged. Modify the below string to add additional information.
     * @param message Actual message to be logged
     * @param breadcrumbId BreadcrumbId with which all the log messages will be co-related together.
     * @return formatted log string
     */
    public static String formatMessage(String message, String breadcrumbId) {
        return String.format("[%s: %s] %s", BreadcrumbId, breadcrumbId, message);
    }

}
