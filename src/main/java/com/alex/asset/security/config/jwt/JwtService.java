package com.alex.asset.security.config.jwt;

import com.alex.asset.utils.DateService;
import com.alex.asset.utils.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {
    private final String TAG = "JWT SERVICE - ";

    public String extractUsername(String token) {
        log.info(TAG + "Extract username {} from token: ", token);
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.info(TAG + "Extract claim {} from token: ", token);
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(CustomPrincipal principal) {
        log.info(TAG + "Generate token for user {}", principal.getName());
        return generateToken(new HashMap<>(), principal);
    }

    public String generateToken(Map<String, Object> extraClaims, CustomPrincipal principal) {
        log.info(TAG + "Generate token for user {}", principal.getName());
        extraClaims.put("userUUID", principal.getUserId());
        extraClaims.put("email", principal.getName());
        return buildToken(extraClaims, principal.getName());
    }


    private String buildToken(Map<String, Object> extraClaims, String subject) {
        log.info(TAG + "Built token");
        return Jwts.builder().setClaims(extraClaims).setSubject(subject)
                .setIssuedAt(DateService.convertToDate(DateService.getDateNow()))
                .setExpiration(DateService.addFiveMinutesToDate(DateService.getDateNow()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        log.info(TAG + "Is token valid {}", token);
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        log.info(TAG + "Is token expired {}", token);
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        log.info(TAG + "Extract expiration from token {}", token);
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        log.info(TAG + "Extract claims from token {}", token);
        return Jwts.parserBuilder().setSigningKey(getSignInKey())
                .build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        log.info(TAG + "Get sign key");
        byte[] keyBytes = Decoders.BASE64.decode(Utils.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
