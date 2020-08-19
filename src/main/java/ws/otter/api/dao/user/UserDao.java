package ws.otter.api.dao.user;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;
import ws.otter.api.dao.BaseDao;
import ws.otter.api.vo.user.*;
import ws.otter.constants.StatusCode;
import ws.otter.constants.UserStatus;
import ws.otter.model.user.UserEntity;
import ws.otter.util.Encrypt;
import ws.otter.util.jdbcRepackage.MapParam;
import ws.otter.web.JWT;
import ws.otter.web.ResponseHandler;
import ws.otter.web.WebInput;

@Repository
public class UserDao extends BaseDao {

    public Mono<ResponseHandler> UserSignUp(WebInput webInput, UserVo.SignUp signUpVo) {

        String sql = "INSERT INTO #table ( #accCol, #pwdCol, #nameCol ) VALUES ( :acc, :pwd, :name )";
        MapParam columns = new MapParam();
        columns.addValue("table", userPo.table());
        columns.addValue("accCol", userPo.acc);
        columns.addValue("pwdCol", userPo.pwd);
        columns.addValue("nameCol", userPo.name);
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

        String roleNameAs = "roleName";
        String sql = "SELECT user.#idCol, user.#accCol, user.#pwdCol, user.#nameCol, user.#roleCol, user.#statusCol, role.#roleNameCol AS #roleNameAs "
                + "FROM #userT user " + "INNER JOIN #roleT role ON user.#roleCol = role.#codeCol "
                + "WHERE user.#accCol = :acc";
        MapParam columns = new MapParam();
        columns.addValue("userT", userPo.table());
        columns.addValue("idCol", userPo.id);
        columns.addValue("accCol", userPo.acc);
        columns.addValue("pwdCol", userPo.pwd);
        columns.addValue("nameCol", userPo.name);
        columns.addValue("roleCol", userPo.roleCode);
        columns.addValue("statusCol", userPo.status);
        columns.addValue("roleT", rolePo.table());
        columns.addValue("codeCol", rolePo.code);
        columns.addValue("roleNameCol", rolePo.name);
        columns.addValue("roleNameAs", roleNameAs);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("acc", acc);

        try {
            List<Map<String, Object>> resultList = jdbc.queryMapList(sql, columns, params);
            // check sql result
            if (resultList == null || resultList.size() == 0) {
                return ResponseHandler.error(StatusCode.DATA_ERROR, null).toMono();
            }
            Map<String, Object> result = resultList.get(0);

            // check pwd
            String resultPwd = (String) result.get(userPo.pwd);
            if (!resultPwd.equals(Encrypt.sha3Encrypt(pwd))) {
                return ResponseHandler.error(StatusCode.DATA_ERROR, null).toMono();
            }

            // check account status
            String resultStatus = (String) result.get(userPo.status);
            if (!resultStatus.equals(UserStatus.Active.getStatus())) {
                return ResponseHandler.error(StatusCode.ACC_INACTIVE, null).toMono();
            }

            JWT.Payload payload = new JWT.Payload();
            payload.id = (Integer) result.get(userPo.id);
            payload.acc = (String) result.get(userPo.acc);
            payload.name = (String) result.get(userPo.name);
            payload.role = (String) result.get(userPo.roleCode);
            payload.roleName = (String) result.get(roleNameAs);

            String jwt = JWT.gen(payload);
            return ResponseHandler.ok().toMono(jwt);

        } catch (DataAccessException e) {
            errHandler.handle(e);
            return dbErrorHandler.handle(e);

        } catch (Exception e) {
            errHandler.handle(e);
            return ResponseHandler.error(StatusCode.SERVER_ERROR, null).toMono();
        }
    }

    public Mono<ResponseHandler> UserInfo(WebInput webInput, String acc) {

        String sql = "SELECT #accCol, #nameCol FROM #table WHERE #accCol=:acc";
        MapParam columns = new MapParam();
        columns.addValue("table", userPo.table());
        columns.addValue("accCol", userPo.acc);
        columns.addValue("nameCol", userPo.name);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("acc", acc);

        try {
            List<UserEntity> resultList = jdbc.queryList(sql, columns, params, (rowData, rowNum) -> {
                UserEntity userEntity = new UserEntity();
                userEntity.acc = rowData.getString(userPo.acc);
                userEntity.name = rowData.getString(userPo.name);
                return userEntity;
            });
            if (resultList.size() < 1) {
                return ResponseHandler.ok().toMono(resultList);
            }

            return ResponseHandler.ok().toMono(resultList.get(0));

        } catch (Exception e) {
            errHandler.handle(e);
            return dbErrorHandler.handle(e);
        }
    }

}