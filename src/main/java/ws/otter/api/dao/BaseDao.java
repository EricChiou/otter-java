package ws.otter.api.dao;

import org.springframework.beans.factory.annotation.Autowired;

import ws.otter.model.role.RolePo;
import ws.otter.model.user.UserPo;
import ws.otter.util.DbErrorHandler;
import ws.otter.util.ErrorHandler;
import ws.otter.util.jdbcRepackage.JdbcRepackage;

abstract public class BaseDao {

    @Autowired
    protected JdbcRepackage jdbc;

    @Autowired
    protected ErrorHandler errHandler;

    @Autowired
    protected DbErrorHandler dbErrorHandler;

    @Autowired
    protected UserPo userPo;

    @Autowired
    protected RolePo rolePo;

}