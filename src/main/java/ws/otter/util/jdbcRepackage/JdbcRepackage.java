package ws.otter.util.jdbcRepackage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcRepackage {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public int update(String sql, MapParam po, MapSqlParameterSource params) throws DataAccessException {

        String convertSql = convertPo2Sql(sql, po);

        return jdbc.update(convertSql, params);
    }

    public Map<String, Object> queryMap(String sql, MapParam po, MapSqlParameterSource params)
            throws DataAccessException {

        String convertSql = convertPo2Sql(sql, po);

        return jdbc.queryForMap(convertSql, params);
    }

    public <T> List<T> queryList(String sql, MapParam po, RowMapper<T> rowMapper) throws DataAccessException {

        String convertSql = convertPo2Sql(sql, po);

        return jdbc.query(convertSql, rowMapper);
    }

    public <T> List<T> queryList(String sql, MapParam po, MapSqlParameterSource params, RowMapper<T> rowMapper)
            throws DataAccessException {

        String convertSql = convertPo2Sql(sql, po);

        return jdbc.query(convertSql, params, rowMapper);
    }

    public List<Map<String, Object>> queryMapList(String sql, MapParam po, MapSqlParameterSource params)
            throws DataAccessException {

        String convertSql = convertPo2Sql(sql, po);

        return jdbc.queryForList(convertSql, params);
    }

    private String convertPo2Sql(String sql, MapParam po) {

        String convertSql = "";
        for (Integer i = 0; i < sql.length(); i++) {
            Character c = sql.charAt(i);
            if (c == '#') {
                String key = getKey(sql, i + 1);
                String val = po.get(key) == null ? "null" : po.get(key).toString();
                if (val != null && !"".equals(val)) {
                    convertSql += val;
                } else {
                    convertSql += key;
                }

                i += key.length();

            } else {
                convertSql += c;
            }
        }

        return convertSql;
    }

    private String getKey(String sql, Integer beginIndex) {

        for (Integer i = beginIndex; i < sql.length(); i++) {
            Character c = sql.charAt(i);
            if (c == ' ' || c == '=' || c == ',' || c == ';' || c == '\'' || c == '"' || c == '(' || c == ')'
                    || c == '{' || c == '}' || c == '\n' || c == '\r' || c == '*' || c == '&' || c == '|' || c == '%') {
                return sql.substring(beginIndex, i);
            }
        }

        return sql.substring(beginIndex);
    }

}