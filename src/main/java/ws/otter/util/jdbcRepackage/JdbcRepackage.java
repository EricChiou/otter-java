package ws.otter.util.jdbcRepackage;

import java.util.Arrays;
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

    private static final String SpecificChar = "\"':.,;(){}[]&|=+-*%/\\<>^";
    private static final Boolean[] specificChar = new Boolean[128];

    static {
        Arrays.fill(specificChar, false);
        for (Character c : SpecificChar.toCharArray()) {
            specificChar[c] = true;
        }
    }

    public int update(String sql, MapParam po, MapSqlParameterSource params) throws DataAccessException {

        String convertSql = convertSql(sql, po);

        return jdbc.update(convertSql, params);
    }

    public Map<String, Object> queryMap(String sql, MapParam po, MapSqlParameterSource params)
            throws DataAccessException {

        String convertSql = convertSql(sql, po);

        return jdbc.queryForMap(convertSql, params);
    }

    public <T> List<T> queryList(String sql, MapParam po, RowMapper<T> rowMapper) throws DataAccessException {

        String convertSql = convertSql(sql, po);

        return jdbc.query(convertSql, rowMapper);
    }

    public <T> List<T> queryList(String sql, MapParam po, MapSqlParameterSource params, RowMapper<T> rowMapper)
            throws DataAccessException {

        String convertSql = convertSql(sql, po);

        return jdbc.query(convertSql, params, rowMapper);
    }

    public List<Map<String, Object>> queryMapList(String sql, MapParam po, MapSqlParameterSource params)
            throws DataAccessException {

        String convertSql = convertSql(sql, po);

        return jdbc.queryForList(convertSql, params);
    }

    private String convertSql(String sql, MapParam po) {

        String convertSql = "";
        Integer preIndex = 0;
        for (Integer i = 0; i < sql.length() - 1; i++) {
            Character c = sql.charAt(i);
            if (c == '#') {
                String key = getKey(sql, i + 1);
                String val = po.get(key);
                if (val != null && !val.isEmpty()) {
                    convertSql += sql.substring(preIndex, i) + val;
                    i += key.length();
                    preIndex = i + 1;
                }
            }
        }
        convertSql += sql.substring(preIndex);

        return convertSql;
    }

    private String getKey(String sql, Integer beginIndex) {

        for (Integer i = beginIndex; i < sql.length(); i++) {
            Character c = sql.charAt(i);
            if (isSpecificChar(c)) {
                return sql.substring(beginIndex, i);
            }
        }

        return sql.substring(beginIndex);
    }

    private Boolean isSpecificChar(Character c) {
        try {
            return (c < 128 && specificChar[c]) || Character.isWhitespace(c);

        } catch (Exception e) {
            return false;
        }
    }

}