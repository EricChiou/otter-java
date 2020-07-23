package ws.otter.api.dao;

import org.springframework.beans.factory.annotation.Autowired;

import ws.otter.model.user.UserPo;
import ws.otter.util.DbErrorHandler;
import ws.otter.util.ErrorHandler;
import ws.otter.util.JdbcConvert;

abstract public class BaseDao {

    @Autowired
    protected JdbcConvert jdbc;

    @Autowired
    protected ErrorHandler errHandler;

    @Autowired
    protected DbErrorHandler dbErrorHandler;

    @Autowired
    protected UserPo userPo;

}