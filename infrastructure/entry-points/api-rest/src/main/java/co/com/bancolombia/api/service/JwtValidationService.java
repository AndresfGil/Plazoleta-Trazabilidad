package co.com.bancolombia.api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



import javax.crypto.SecretKey;

@Service
public class JwtValidationService {

    @Value("${jwt.secret}")
    private String secret;

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String getRolFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.get("rol", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String getCorreoFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public Long getIdUsuarioFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.get("idUsuario", Long.class);
        } catch (Exception e) {
            return null;
        }
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
