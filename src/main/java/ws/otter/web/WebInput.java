package ws.otter.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import ws.otter.util.ErrorHandler;

public class WebInput {

    public HttpServletRequest request;
    public JWT.Payload payload;

    @Autowired
    protected ErrorHandler errHandler;

    public WebInput(HttpServletRequest request) {
        try {
            this.request = request;
            this.payload = (JWT.Payload) request.getAttribute("payload");

        } catch (Exception e) {
            errHandler.handle(e, "WebInput:");
        }
    }

}