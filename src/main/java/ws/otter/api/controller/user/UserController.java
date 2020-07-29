package ws.otter.api.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import ws.otter.api.controller.BaseController;
import ws.otter.api.url.UserUrl;
import ws.otter.api.vo.user.*;
import ws.otter.constants.StatusCode;
import ws.otter.util.Check;
import ws.otter.web.ResponseHandler;
import ws.otter.web.WebInput;

@RestController
public class UserController extends BaseController {

    @PutMapping(UserUrl.putUserSignUp)
    public Mono<ResponseHandler> UserSignUp(HttpServletRequest request,
            @RequestBody(required = true) UserVo.SignUp signUpVo) {

        WebInput webInput = new WebInput(request);

        Object[] params = new Object[] { signUpVo.acc, signUpVo.pwd, signUpVo.name };
        if (!Check.param(params)) {
            return ResponseHandler.error(StatusCode.FORMAT_ERROR, null).toMono();
        }

        return userService.UserSignUp(webInput, signUpVo);
    }

    @GetMapping(UserUrl.postUserSignIn)
    public Mono<ResponseHandler> UserSignIn(HttpServletRequest request, @RequestParam(required = true) String acc,
            @RequestParam(required = true) String pwd) {

        WebInput webInput = new WebInput(request);

        return userService.UserSignIn(webInput, acc, pwd);
    }

    @GetMapping(UserUrl.getUserInfo)
    public Mono<ResponseHandler> UserInfo(HttpServletRequest request, @PathVariable("acc") String acc) {

        WebInput webInput = new WebInput(request);

        return userService.UserInfo(webInput, acc);

    }

}