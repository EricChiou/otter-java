package ws.otter.util;

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

    public int update(String sql, Map<String, String> po, MapSqlParameterSource params) throws DataAccessException {

        String convertSql = convertPo2Sql(sql, po);

        return jdbc.update(convertSql, params);
    }

    public List<Map<String, Object>> queryForList(String sql, Map<String, String> po, MapSqlParameterSource params)
            throws DataAccessException {

        String convertSql = convertPo2Sql(sql, po);

        return jdbc.queryForList(convertSql, params);
    }

    private String convertPo2Sql(String sql, Map<String, String> po) {

        String convertSql = "";
        for (Integer i = 0; i < sql.length(); i++) {
            Character c = sql.charAt(i);
            if (c == '#') {
                String key = getKey(sql, i + 1);
                String val = po.get(key);
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