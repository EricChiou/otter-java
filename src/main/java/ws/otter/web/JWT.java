package ws.otter.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import ws.otter.config.JwtConfig;
import ws.otter.model.user.UserPo;

@Component
public class JWT {

    public Integer id; // user id
    public String acc; // user account
    public String name; // user name
    public String role; // user role

    private static final UserPo userPo = new UserPo();

    private static final String USER_ID = "id"; // user id
    private static final String USER_ACC = "acc"; // user account
    private static final String USER_NAME = "name"; // user name
    private static final String USER_ROLE = "role"; // user role

    private static final SecretKey KEY = Keys.hmacShaKeyFor(JwtConfig.secret.getBytes());

    public static JWT dbMap2Payload(Map<String, Object> userData) {
        return setPayload((Integer) userData.get(userPo.id), (String) userData.get(userPo.acc),
                (String) userData.get(userPo.name), (String) userData.get(userPo.roleCode));
    }

    public static JWT setPayload(Integer id, String acc, String name, String role) {

        JWT payload = new JWT();
        payload.id = id;
        payload.acc = acc;
        payload.name = name;
        payload.role = role;

        return payload;
    }

    public static String gen(JWT payload) {

        Map<String, Object> claims = encodeClaim(payload);

        Date exp = new Date(System.currentTimeMillis() + (JwtConfig.exp * 1000));
        String jwt = Jwts.builder().setClaims(claims).setExpiration(exp).signWith(KEY).compact();

        return jwt;

    }

    public static JWT verify(String jwt) {

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(jwt).getBody();

            return decodeClaim(claims);

        } catch (JwtException e) {
            return null;
        }

    }

    private static Map<String, Object> encodeClaim(JWT payload) {

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put(USER_ID, payload.id);
        claims.put(USER_ACC, payload.acc);
        claims.put(USER_NAME, payload.name);
        claims.put(USER_ROLE, payload.role);

        return claims;
    }

    private static JWT decodeClaim(Claims claims) {

        JWT payload = new JWT();
        payload.id = (Integer) claims.get(USER_ID);
        payload.acc = claims.get(USER_ACC).toString();
        payload.name = claims.get(USER_NAME).toString();
        payload.role = claims.get(USER_ROLE).toString();

        return payload;
    }

}