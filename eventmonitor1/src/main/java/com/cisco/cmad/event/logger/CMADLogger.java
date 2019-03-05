package com.cisco.cmad.event.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CMADLogger {

    private static Logger logger = LoggerFactory.getLogger(CMADLogger.class);
    private static StringBuilder sb = new StringBuilder();

    public static void logInfo(String className, String msg) {
        sb.append("ClassName:").append(className).append(" - Message: ").append(msg);
        logger.info(sb.toString());
        sb.setLength(0);
    }

    public static void logDebug(String className, String msg) {
        sb.append("ClassName:").append(className).append(" - Message: ").append(msg);
        logger.info(sb.toString());
        sb.setLength(0);
    }
    
    public static void logError(String className, String msg) {
        sb.append("ClassName:").append(className).append(" - Message: ").append(msg);
        logger.error(msg);
    }

}
