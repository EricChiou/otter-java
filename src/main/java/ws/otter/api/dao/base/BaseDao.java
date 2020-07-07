package ws.otter.api.dao.base;

import org.springframework.beans.factory.annotation.Autowired;

import ws.otter.model.user.UserPo;
import ws.otter.util.DbErrorHandler;
import ws.otter.util.ErrorHandler;
import ws.otter.util.JdbcConvert;

abstract public class BaseDao {

    @Autowired
    protected JdbcConvert jdbc;

    protected DbErrorHandler dbErrorHandler = new DbErrorHandler();

    protected UserPo userPo = new UserPo();

    protected ErrorHandler errHandler = new ErrorHandler();

}