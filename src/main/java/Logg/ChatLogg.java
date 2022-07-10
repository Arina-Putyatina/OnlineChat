package Logg;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ChatLogg {

    public static Logger createLogger(String fileName, String logName) {
        Logger logger = Logger.getLogger(logName);
        FileHandler fh;

        try {
            fh = new FileHandler(fileName, true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.setUseParentHandlers(false);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        return logger;
    }
}
