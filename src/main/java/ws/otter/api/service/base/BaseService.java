package ws.otter.api.service.base;

import org.springframework.beans.factory.annotation.Autowired;

import ws.otter.api.dao.UserDao;
import ws.otter.util.ErrorHandler;

abstract public class BaseService {

    protected ErrorHandler errHandler = new ErrorHandler();

    @Autowired
    protected UserDao userDao;

}