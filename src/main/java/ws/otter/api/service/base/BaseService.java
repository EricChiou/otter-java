package ws.otter.api.service.base;

import org.springframework.beans.factory.annotation.Autowired;

import ws.otter.api.dao.UserDao;

abstract public class BaseService {

    @Autowired
    protected UserDao userDao;

}