package pack.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "mySecretKey123456789012345678901234567890"; // ✅ 최소 32바이트 필요

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // ✅ SecretKey 반환
    }

    // ✅ 최신 방식으로 JWT 생성
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", email); // ✅ setSubject() 대신 claims 사용

        return Jwts.builder()
                .claims(claims) // ✅ Map을 사용하여 claims 설정
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1시간 유효
                .signWith(getSigningKey()) // ✅ Key를 SecretKey로 변경
                .compact();
    }

    // ✅ JWT에서 이메일(사용자명) 추출
    public String extractUsername(String token) {
        return extractAllClaims(token).get("sub", String.class); // ✅ subject에서 이메일 가져오기
    }

    // ✅ JWT에서 모든 Claims 추출
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // ✅ SecretKey 사용
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ✅ JWT가 만료되었는지 확인
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // ✅ JWT 유효성 검사
    public boolean validateToken(String token, String email) {
        return (extractUsername(token).equals(email) && !isTokenExpired(token));
    }
}

