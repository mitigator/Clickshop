package com.ecom.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "59c377cef1f8834d7af2ced652601eb631ae4f11b16c781eaa5391df425dd683503f4a84f36c581148d0ab66e2630c1d4aeb10584dc67b969f12e81bcd17683a022c231899dea3667eefa307981e771bc36d02dd647b0139036c7d51da42da8c129f66ef85690b1ce90eee6d4c29a2fd42ba27c8f27d11e92636601f3620d34cfc09e9e53a482dbb8eb3cf9778757fca946e46c4f5d6e4bbf421c8a6dbcee5bad954c7da6b7c7584cea07c8802b020262d5e58512ab982352e77f8c66df7e2e759fbb609fd8e56b8da42f01b7e52ca435e304b6d097a5417164b479de66840e6046762a8d7916e204620acb60d12a9a94ee02d68e140d19adec22de6ffbcecbb";

    private static final int TOKEN_VALIDITY = 3600 * 5;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
