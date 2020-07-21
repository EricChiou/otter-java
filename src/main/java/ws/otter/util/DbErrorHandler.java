package ws.otter.util;

import reactor.core.publisher.Mono;
import ws.otter.constants.StatusCode;
import ws.otter.web.ResponseHandler;

public class DbErrorHandler {

    public Mono<ResponseHandler> handle(Exception e) {
        String msg = e.getMessage();
        if (e.getMessage().indexOf("Duplicate") > 0) {
            return ResponseHandler.error(StatusCode.DUPLICATE, msg).toMono();
        }

        return ResponseHandler.error(StatusCode.DB_ERROR, msg).toMono();
    }

}