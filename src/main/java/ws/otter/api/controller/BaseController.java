package ws.otter.api.controller;

import org.springframework.beans.factory.annotation.Autowired;

import ws.otter.api.service.user.UserService;
import ws.otter.util.ErrorHandler;

abstract public class BaseController {

    @Autowired
    protected ErrorHandler errHandler;

    @Autowired
    protected UserService userService;

}
