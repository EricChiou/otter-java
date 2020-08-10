package ws.otter.web;

import java.util.Base64;
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

@Component
public class JWT {

    public static class Payload {
        public Integer id; // user id
        public String acc; // user account
        public String name; // user name
        public String role; // user role
        public String roleName; // user role name
    }

    // payload json key
    private static final String USER_ID = "id"; // user id
    private static final String USER_ACC = "acc"; // user account
    private static final String USER_NAME = "name"; // user name
    private static final String USER_ROLE = "role"; // user role
    private static final String USER_ROLE_NAME = "roleName"; // user role name

    private static final SecretKey KEY = Keys.hmacShaKeyFor(JwtConfig.secret.getBytes());

    public static Payload setPayload(Integer id, String acc, String name, String role, String roleName) {

        Payload payload = new Payload();
        payload.id = id;
        payload.acc = acc;
        payload.name = name;
        payload.role = role;
        payload.roleName = roleName;

        return payload;
    }

    public static String gen(Payload payload) {

        Map<String, Object> claims = encodeClaim(payload);

        Date exp = new Date(System.currentTimeMillis() + (JwtConfig.exp * 1000));
        String jwt = Jwts.builder().setClaims(claims).setExpiration(exp).signWith(KEY).compact();
        String[] jwtSplit = jwt.split("\\.");
        String signature = jwtSplit[2];
        signature = new String(Base64.getEncoder().encode(signature.getBytes())); // encode to base64

        return jwtSplit[0] + "." + jwtSplit[1] + "." + signature;
    }

    public static Payload verify(String jwt) {

        try {
            String[] jwtSplit = jwt.split("\\.");
            String signature = jwtSplit[2];
            signature = new String(Base64.getDecoder().decode(signature.getBytes())); // decode signature
            jwt = jwtSplit[0] + "." + jwtSplit[1] + "." + signature;

            Claims claims = Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(jwt).getBody();

            return decodeClaim(claims);

        } catch (JwtException e) {
            return null;
        }

    }

    private static Map<String, Object> encodeClaim(Payload payload) {

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put(USER_ID, payload.id);
        claims.put(USER_ACC, payload.acc);
        claims.put(USER_NAME, payload.name);
        claims.put(USER_ROLE, payload.role);
        claims.put(USER_ROLE_NAME, payload.roleName);

        return claims;
    }

    private static Payload decodeClaim(Claims claims) {

        Payload payload = new Payload();
        payload.id = (Integer) claims.get(USER_ID);
        payload.acc = claims.get(USER_ACC).toString();
        payload.name = claims.get(USER_NAME).toString();
        payload.role = claims.get(USER_ROLE).toString();
        payload.roleName = claims.get(USER_ROLE_NAME).toString();

        return payload;
    }

}