package ws.otter.api.controller.base;

import org.springframework.beans.factory.annotation.Autowired;

import ws.otter.api.service.UserService;
import ws.otter.util.ErrorHandler;

abstract public class BaseController {

    protected ErrorHandler errHandler = new ErrorHandler();

    @Autowired
    protected UserService userService;

}
