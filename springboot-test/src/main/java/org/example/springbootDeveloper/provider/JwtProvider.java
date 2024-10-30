package org.example.springbootDeveloper.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    // 환경 변수에 지정한 비밀키 값과 만료 시간을 가져옴
//    private final Key key;
//    private final int jwtExpirationMs;

    //    @Value("${jwt.secret}")
    private final Key key;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    //  토큰 만료 시간을 밀리초로 반환하는 메서드
    public int getExpiration() {
        return jwtExpirationMs;
    }

    public JwtProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") int jwtExpirationMs) {

        //  Base64로 인코딩된 비밀키를 디코딩하여 HMAC-SHA 알고리즘으로 암호화된 키 생성
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        //  환경 변수에서 가져온 만료 시간을 변수에 지정
        this.jwtExpirationMs = jwtExpirationMs;
    }

    /*
     * generateJwtToken
     * : JWT 생성 메서드
     * : 사용자 정보를 받아 JWT 토큰을 생성하는 메서드
     * @param : 사용자 식별자(JWT의 subject로 설정됨)
     * @return : 생성된 JWT 토큰 문자열
     * */
    public String generateJwtToken(String userId) {
        return Jwts.builder()
                .claim("userId", userId) // 클레임에 사용자 ID 저장 (사용자의 고유 ID)
                .setIssuedAt(new Date()) // 현재 시간을 기준으로 토큰 발행 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                // 현재 시간에 만료 시간을 더해 토큰 만료시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                // HMAC-SHA256 알고리즘으로 생성된 비밀키로 서명
                .compact();
        // JWT를 최종적으로 직렬화하여 문자열로 반환
    }

    /*
     * 이메일 검증용 JWT "생성" 메서드
     *
     * @param username - 사용자이름
     * @return 이메일 검증을 위한 JWT 토큰
     * */
    public String generateEmailValidToken(String username) {
        return Jwts.builder()
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000L * 60 * 5))) //  1초 * 60 * 5 = 5분
                //  현재 시간에 5분을 더해 토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /*
     * JWT에서 Bearer 접두사 제거
     *
     * @param bearerToken - 접두사가 포함된 JWT 문자열
     * @return Bearer이 제거된 JWT
     *
     * cf) Bearer : 소유자
     *       - 해당 토큰의 소유자에게 권한을 부여하다
     * */
    public String removeBearer(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid JWT token format");
        }
        return bearerToken.substring("Bearer ".length());
    }

    /*
     * JWT 유효성 검증 & 사용자 ID 추출
     *
     * @param token - JWT 토큰
     * @return 사용자 ID - 클레임에서 추출된 값
     * */
    public String getUserIdFromJwt(String token) {
        // JWT에서 클레임 정보를 추출 - claim 객체에 저장
        Claims claims = getClaims(token);

        // 클레임에서 userId 값을 문자열 형태로 반환
        return claims.get("userId", String.class);
    }

    /*
     * JWT 유혀성 검증
     *
     * @param token - JWT 토큰
     * @return 유효하면 true, 그렇지 않으면 false
     * */
    public boolean isValidToken(String token) {
        try {
            getClaims(token);   //  JWT 클레임을 가져오면서 유효성 검증
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * JWT 클레임 정보 가져오기
     *
     * @param token - JWT 토큰
     * @return 클레임 정보
     * */
    private Claims getClaims(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(key) //  JWT 파서에 서명에 사용된 비밀키 설정
                .build();

        //  JWT를 파싱하여 클레임 정보(body)를 반환
        return jwtParser.parseClaimsJws(token).getBody();
    }
}
