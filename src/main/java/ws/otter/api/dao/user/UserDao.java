package ws.otter.api.dao.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;
import ws.otter.api.dao.BaseDao;
import ws.otter.api.vo.user.*;
import ws.otter.constants.StatusCode;
import ws.otter.model.user.UserEntity;
import ws.otter.util.Encrypt;
import ws.otter.web.JWT;
import ws.otter.web.ResponseHandler;
import ws.otter.web.WebInput;

@Repository
public class UserDao extends BaseDao {

    public Mono<ResponseHandler> UserSignUp(WebInput webInput, UserVo.SignUp signUpVo) {

        String sql = "INSERT INTO #table ( #accCol, #pwdCol, #nameCol ) VALUES ( :acc, :pwd, :name )";
        Map<String, String> columns = new HashMap<String, String>();
        columns.put("table", userPo.table);
        columns.put("accCol", userPo.acc);
        columns.put("pwdCol", userPo.pwd);
        columns.put("nameCol", userPo.name);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("acc", signUpVo.acc);
        params.addValue("pwd", Encrypt.sha3Encrypt(signUpVo.pwd));
        params.addValue("name", signUpVo.name);

        try {
            jdbc.update(sql, columns, params);
        } catch (Exception e) {
            errHandler.handle(e);
            return dbErrorHandler.handle(e);
        }

        return ResponseHandler.ok().toMono();
    }

    public Mono<ResponseHandler> UserSignIn(WebInput webInput, String acc, String pwd) {

        String sql = "SELECT #idCol, #accCol, #nameCol, #roleCol FROM #table WHERE #accCol=:acc AND #pwdCol=:pwd";
        Map<String, String> columns = new HashMap<String, String>();
        columns.put("table", userPo.table);
        columns.put("idCol", userPo.id);
        columns.put("accCol", userPo.acc);
        columns.put("pwdCol", userPo.pwd);
        columns.put("nameCol", userPo.name);
        columns.put("roleCol", userPo.roleCode);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("acc", acc);
        params.addValue("pwd", Encrypt.sha3Encrypt(pwd));

        try {
            Map<String, Object> result = jdbc.query(sql, columns, params);
            if (result == null) {
                return ResponseHandler.error(StatusCode.DATA_ERROR, null).toMono();
            }

            JWT payload = new JWT();
            payload.id = (Integer) result.get(userPo.id);
            payload.acc = (String) result.get(userPo.acc);
            payload.name = (String) result.get(userPo.name);
            payload.role = (String) result.get(userPo.roleCode);
            String jwt = JWT.gen(payload);
            return ResponseHandler.ok().toMono(jwt);

        } catch (Exception e) {
            errHandler.handle(e);
            return dbErrorHandler.handle(e);
        }
    }

    public Mono<ResponseHandler> UserInfo(WebInput webInput, String acc) {

        String sql = "SELECT #accCol, #nameCol FROM #table WHERE #accCol=:acc";
        Map<String, String> columns = new HashMap<String, String>();
        columns.put("table", userPo.table);
        columns.put("accCol", userPo.acc);
        columns.put("nameCol", userPo.name);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("acc", acc);

        try {
            List<Map<String, Object>> resultList = jdbc.queryList(sql, columns, params);
            if (resultList.size() < 1) {
                return ResponseHandler.ok().toMono(resultList);
            }

            Map<String, Object> result = resultList.get(0);
            UserEntity entity = new UserEntity();
            entity.acc = (String) result.get(userPo.acc);
            entity.name = (String) result.get(userPo.name);

            return ResponseHandler.ok().toMono(entity);

        } catch (Exception e) {
            errHandler.handle(e);
            return dbErrorHandler.handle(e);
        }
    }

}