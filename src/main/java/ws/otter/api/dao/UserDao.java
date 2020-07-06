package ws.otter.api.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;
import ws.otter.api.dao.base.BaseDao;
import ws.otter.api.vo.user.*;
import ws.otter.constants.StatusCode;
import ws.otter.model.user.UserEntity;
import ws.otter.util.Encrypt;
import ws.otter.web.JWT;
import ws.otter.web.ResponseHandler;
import ws.otter.web.WebInput;

@Repository
public class UserDao extends BaseDao {

    public Mono<ResponseHandler> UserSignUp(WebInput webInput, SignUpVo signUpVo) {

        String sql = "INSERT INTO !table ( !accCol, !pwdCol, !nameCol ) VALUES ( :acc, :pwd, :name )";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("table", userPo.table);
        params.put("accCol", userPo.acc);
        params.put("pwdCol", userPo.pwd);
        params.put("nameCol", userPo.name);
        params.put("acc", signUpVo.acc);
        params.put("pwd", Encrypt.sha3Encrypt(signUpVo.pwd));
        params.put("name", signUpVo.name);

        try {
            jdbc.update(sql, params);
        } catch (Exception e) {
            errHandler.handle(e, null);
            return dbErrorHandler.handle(e);
        }

        return Mono.just(ResponseHandler.ok().toMap(null));
    }

    public Mono<ResponseHandler> UserSignIn(WebInput webInput, String acc, String pwd) {

        String sql = "SELECT !idCol, !accCol, !nameCol, !roleCol FROM !table WHERE !accCol=:acc AND !pwdCol=:pwd";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("table", userPo.table);
        params.put("idCol", userPo.id);
        params.put("accCol", userPo.acc);
        params.put("pwdCol", userPo.pwd);
        params.put("nameCol", userPo.name);
        params.put("roleCol", userPo.roleCode);
        params.put("acc", acc);
        params.put("pwd", Encrypt.sha3Encrypt(pwd));

        try {
            List<Map<String, Object>> result = jdbc.queryForList(sql, params);
            if (result.size() < 1) {
                return Mono.just(ResponseHandler.error(StatusCode.DATA_ERROR, null));
            }

            Map<String, Object> userData = result.get(0);
            JWT payload = JWT.dbMap2Payload(userData);
            String jwt = JWT.gen(payload);
            return Mono.just(ResponseHandler.ok().toMap(jwt));

        } catch (Exception e) {
            errHandler.handle(e, null);
            return dbErrorHandler.handle(e);
        }
    }

    public Mono<ResponseHandler> UserInfo(WebInput webInput, String acc) {

        String sql = "SELECT !accCol, !nameCol FROM !table WHERE !accCol=:acc";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("table", userPo.table);
        params.put("accCol", userPo.acc);
        params.put("nameCol", userPo.name);
        params.put("acc", acc);

        try {
            List<Map<String, Object>> result = jdbc.queryForList(sql, params);
            if (result.size() < 1) {
                return Mono.just(ResponseHandler.ok().toMap(result));
            }

            Map<String, Object> userData = result.get(0);
            UserEntity entity = new UserEntity();
            entity.acc = (String) userData.get(userPo.acc);
            entity.name = (String) userData.get(userPo.name);
            return Mono.just(ResponseHandler.ok().toMap(entity));

        } catch (Exception e) {
            errHandler.handle(e, null);
            return dbErrorHandler.handle(e);
        }
    }

}