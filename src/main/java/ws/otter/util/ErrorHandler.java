package ws.otter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandler {

    protected static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    public void handle(Exception e) {

        System.out.println(e.getMessage());
        logger.error(e.getMessage());
    }

    public void handle(Exception e, String msg) {

        System.out.println(msg);
        System.out.println(e.getMessage());
        logger.error(msg);
        logger.error(e.getMessage());
    }

}