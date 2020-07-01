package ws.otter.util.jdbcConvert;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class JdbcConvertResult {
    public String sql;
    public MapSqlParameterSource params;
}