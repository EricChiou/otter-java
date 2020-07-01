package ws.otter.api.controller.base;

import org.springframework.beans.factory.annotation.Autowired;

import ws.otter.api.service.UserService;

abstract public class BaseController {

    @Autowired
    protected UserService userService;

}
