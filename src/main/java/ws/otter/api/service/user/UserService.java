package ws.otter.api.service.user;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import ws.otter.api.service.BaseService;
import ws.otter.api.vo.user.*;
import ws.otter.web.ResponseHandler;
import ws.otter.web.WebInput;

@Service
public class UserService extends BaseService {

    public Mono<ResponseHandler> UserSignUp(WebInput webInput, UserVo.SignUp signUpVo) {
        return userDao.UserSignUp(webInput, signUpVo);
    }

    public Mono<ResponseHandler> UserSignIn(WebInput webInput, String acc, String pwd) {
        return userDao.UserSignIn(webInput, acc, pwd);
    }

    public Mono<ResponseHandler> UserInfo(WebInput webInput, String acc) {
        return userDao.UserInfo(webInput, acc);
    }

}