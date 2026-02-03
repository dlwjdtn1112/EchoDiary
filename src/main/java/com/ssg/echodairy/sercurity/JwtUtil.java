package com.ssg.echodairy.sercurity;

import com.ssg.echodairy.domain.Client;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil {

    private static final long EXP = 1000 * 60 * 60; // 1시간

    // 256bit 이상
    private static final String SECRET =
            "echodairy-secret-key-echodairy-secret-key";

    private final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(Client client) {

        System.out.println("🎟️ [JWT 생성] 사용자 아이디: " + client.getLoginId());
        return Jwts.builder()
                .setSubject(client.getLoginId())
                .claim("role", client.getRole())
                .claim("nickname", client.getNickname())
                .claim("userId", client.getUserId())

                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXP)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
