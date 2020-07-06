package ws.otter.api.dao.base;

import org.springframework.beans.factory.annotation.Autowired;

import ws.otter.model.user.UserPo;
import ws.otter.util.DbErrorHandler;
import ws.otter.util.ErrorHandler;
import ws.otter.util.jdbcConvert.JdbcConvert;

abstract public class BaseDao {

    @Autowired
    protected JdbcConvert jdbc;

    protected DbErrorHandler dbErrorHandler = new DbErrorHandler();

    // protected UserEntity userEntity;
    protected UserPo userPo;

    protected ErrorHandler errHandler;

}