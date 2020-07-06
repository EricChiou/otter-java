package ws.otter.util.jdbcConvert;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcConvert {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public int update(String sql, Map<String, Object> params) throws DataAccessException {

        JdbcConvertResult convertResult = convert(sql, params);

        return jdbc.update(convertResult.sql, convertResult.params);
    }

    public List<Map<String, Object>> queryForList(String sql, Map<String, Object> params) throws DataAccessException {

        JdbcConvertResult convertResult = convert(sql, params);

        return jdbc.queryForList(convertResult.sql, convertResult.params);
    }

    private JdbcConvertResult convert(String sql, Map<String, Object> params) {

        String newSql = "";
        for (Integer i = 0; i < sql.length(); i++) {
            Character c = sql.charAt(i);
            if (c == '!') {
                String key = getKey(sql, i + 1);
                String keyVal = (String) params.get(key);
                if (keyVal != null && !"".equals(keyVal)) {
                    newSql += keyVal;
                } else {
                    newSql += key;
                }
                i += key.length();

            } else {
                newSql += c;
            }

        }

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        for (String key : params.keySet()) {
            if (newSql.indexOf(":" + key) > 0) {
                paramSource.addValue(key, params.get(key));
            }
        }

        JdbcConvertResult convertResult = new JdbcConvertResult();
        convertResult.sql = newSql;
        convertResult.params = paramSource;

        return convertResult;
    }

    private String getKey(String sql, Integer beginIndex) {

        for (Integer i = beginIndex; i < sql.length(); i++) {
            Character c = sql.charAt(i);
            if (c == ' ' || c == '=' || c == ',' || c == ';' || c == '\'' || c == '"' || c == '(' || c == ')'
                    || c == '\n' || c == '*' || c == '&' || c == '|' || c == '%') {
                return sql.substring(beginIndex, i);
            }
        }

        return sql.substring(beginIndex);
    }

}