package ws.otter.api.dao.user;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;
import ws.otter.api.dao.BaseDao;
import ws.otter.api.vo.user.*;
import ws.otter.constants.StatusCode;
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
        columns.addValue("table", userPo.table);
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

        String sql = "SELECT #idCol, #accCol, #nameCol, #roleCol FROM #table WHERE #accCol=:acc AND #pwdCol=:pwd";
        MapParam columns = new MapParam();
        columns.addValue("table", userPo.table);
        columns.addValue("idCol", userPo.id);
        columns.addValue("accCol", userPo.acc);
        columns.addValue("pwdCol", userPo.pwd);
        columns.addValue("nameCol", userPo.name);
        columns.addValue("roleCol", userPo.roleCode);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("acc", acc);
        params.addValue("pwd", Encrypt.sha3Encrypt(pwd));

        try {
            List<JWT> resultList = jdbc.queryList(sql, columns, params, (rowData, rowNum) -> {
                JWT jwt = new JWT();
                jwt.id = (Integer) rowData.getInt(userPo.id);
                jwt.acc = rowData.getString(userPo.acc);
                jwt.name = rowData.getString(userPo.name);
                jwt.role = rowData.getString(userPo.roleCode);
                return jwt;
            });
            if (resultList == null || resultList.size() == 0) {
                return ResponseHandler.error(StatusCode.DATA_ERROR, null).toMono();
            }

            String jwt = JWT.gen(resultList.get(0));
            return ResponseHandler.ok().toMono(jwt);

        } catch (Exception e) {
            errHandler.handle(e);
            return dbErrorHandler.handle(e);
        }
    }

    public Mono<ResponseHandler> UserInfo(WebInput webInput, String acc) {

        String sql = "SELECT #accCol, #nameCol FROM #table WHERE #accCol=:acc";
        MapParam columns = new MapParam();
        columns.addValue("table", userPo.table);
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