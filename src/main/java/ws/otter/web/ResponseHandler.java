package ws.otter.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;

import reactor.core.publisher.Mono;
import ws.otter.constants.StatusCode;

public class ResponseHandler {
    @JsonProperty("status")
    private String status;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("data")
    private Object data;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("trace")
    private Object trace;

    @JsonIgnore
    private HttpStatus httpStatus;

    public static ResponseHandler ok() {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.status = StatusCode.OK.getCode();
        responseHandler.httpStatus = HttpStatus.OK;

        return responseHandler;
    }

    public static ResponseHandler error(StatusCode statusCode, Object trace) {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.status = statusCode.getCode();
        responseHandler.trace = trace;
        responseHandler.httpStatus = HttpStatus.OK;

        return responseHandler;
    }

    public static void setHttpServletResponse(HttpServletResponse response, StatusCode statusCode) throws IOException {

        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(error(StatusCode.TOKEN_ERROR, null).toStr(null));
        response.getWriter().flush();
        response.getWriter().close();
    }

    @JsonIgnore
    public Mono<ResponseHandler> toMono() {
        return Mono.just(this);
    }

    @JsonIgnore
    public Mono<ResponseHandler> toMono(Object data) {
        this.data = data;

        return Mono.just(this);
    }

    @JsonIgnore
    public ResponseHandler toMap(Object data) {
        this.data = data;

        return this;
    }

    @JsonIgnore
    public String toStr(Object data) {
        ObjectMapper om = new ObjectMapper();

        String json;
        try {
            this.data = data;
            json = om.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            json = "{\"status\": " + StatusCode.PARSE_ERROR.getCode() + "}";
        }

        return json;
    }
}