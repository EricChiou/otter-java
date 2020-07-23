package ws.otter.api.service;

import org.springframework.beans.factory.annotation.Autowired;

import ws.otter.api.dao.user.UserDao;
import ws.otter.util.ErrorHandler;

abstract public class BaseService {

    @Autowired
    protected ErrorHandler errHandler;

    @Autowired
    protected UserDao userDao;

}