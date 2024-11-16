package com.ecommerce.liven.ecommerce_backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ecommerce.liven.ecommerce_backend.model.UsuarioLocal;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String key;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expireTime}")
    private int expireTime;
    private Algorithm algorithm;

    private static final String USERNAME_KEY = "USERNAME";

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(key);
    }

    public String generateJWT(UsuarioLocal user) {
        return JWT.create()
                .withClaim(USERNAME_KEY, user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expireTime)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getUseEmail(String token){
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }
}
