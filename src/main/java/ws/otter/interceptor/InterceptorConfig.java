package ws.otter.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ws.otter.api.path.UserPath;
import ws.otter.interceptor.aclInterceptor.AclInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private String[] include;
    private String[] exclude;

    @Autowired
    private AclInterceptor aclInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // Auth Interceptor
        include = new String[] { "/**" };
        exclude = new String[] { "/", UserPath.postUserSignIn, UserPath.putUserSignUp };
        registry.addInterceptor(aclInterceptor).addPathPatterns(include).excludePathPatterns(exclude);

    }
}