package ws.otter.interceptor.aclInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ws.otter.config.JwtConfig;
import ws.otter.constants.StatusCode;
import ws.otter.web.JWT;
import ws.otter.web.ResponseHandler;

@Component
public class AclInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String header = request.getHeader(JwtConfig.header);
        if (header == null || !header.startsWith(JwtConfig.prefix)) {
            ResponseHandler.setHttpServletResponse(response, StatusCode.TOKEN_ERROR);
            return false;
        }

        String jwt = header.substring(JwtConfig.prefix.length());
        JWT payload = JWT.verify(jwt);
        if (payload == null) {
            ResponseHandler.setHttpServletResponse(response, StatusCode.TOKEN_ERROR);
            return false;
        }

        request.setAttribute("payload", JWT.verify(jwt));

        return true;
    }

}