package ws.otter.api.url;

import org.springframework.stereotype.Component;

@Component
public class UserUrl {

    public static final String putUserSignUp = "/user/signUp";
    public static final String postUserSignIn = "/user/signIn";
    public static final String getUserInfo = "/user/info/{acc}";

}