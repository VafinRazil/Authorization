package com.command.project.security.util;

import com.command.project.security.configurations.JwtTokenConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtTokenUtils {

    private final JwtTokenConfig jwtTokenConfig;

    @Autowired
    public JwtTokenUtils(
            JwtTokenConfig jwtTokenConfig
    ){
        this.jwtTokenConfig = jwtTokenConfig;
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", roles);
        final Date issuedDatetime = new Date();
        final Date expiredDatetime = new Date(issuedDatetime.getTime() + jwtTokenConfig.getLifetime().toMillis());
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDatetime)
                .setExpiration(expiredDatetime)
                .signWith(SignatureAlgorithm.HS256, jwtTokenConfig.getSecret())
                .compact();
    }

    public String getUsername(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(String token){
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts
                .parser()
                .parseClaimsJwt(token)
                .getBody();
    }
}
