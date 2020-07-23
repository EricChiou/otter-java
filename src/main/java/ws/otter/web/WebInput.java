package ws.otter.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class WebInput {

    public HttpServletRequest request;
    public JWT payload;

    public WebInput(HttpServletRequest request) {
        this.request = request;
        this.payload = (JWT) request.getAttribute("payload");
    }

}