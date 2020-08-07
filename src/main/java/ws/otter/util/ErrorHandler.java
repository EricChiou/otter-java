package ws.otter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandler {

    protected static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    public void handle(Exception e) {

        logger.error(e.getMessage());
    }

    public void handle(Exception e, String msg) {

        if (msg != null && !"".equals(msg)) {
            logger.error(msg);

        } else {
            logger.error(e.getMessage());
        }
    }

}