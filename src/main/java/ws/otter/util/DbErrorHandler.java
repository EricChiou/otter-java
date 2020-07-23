package ws.otter.util;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import ws.otter.constants.StatusCode;
import ws.otter.web.ResponseHandler;

@Component
public class DbErrorHandler {

    public Mono<ResponseHandler> handle(Exception e) {
        String msg = e.getMessage();
        if (e.getMessage().indexOf("Duplicate") > 0) {
            return ResponseHandler.error(StatusCode.DUPLICATE, msg).toMono();
        }

        return ResponseHandler.error(StatusCode.DB_ERROR, msg).toMono();
    }

}