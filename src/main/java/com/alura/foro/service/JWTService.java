package com.alura.foro.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alura.foro.modelo.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer("foro_alura")
                .withSubject(usuario.getEmail())
                .withClaim("id", usuario.getId())
                .withExpiresAt(generarTiempoExpiracion())
                .sign(algorithm);

            return token;
        } catch (JWTCreationException e){
            throw new RuntimeException(e);
        }
    }

    private Instant generarTiempoExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }
    
    
    private DecodedJWT decodificarToken(String token){

        DecodedJWT tokenDecodificado;

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verificadorDeToken = JWT.require(algorithm)
                .withIssuer("foro_alura")
                .build();
            
            tokenDecodificado = verificadorDeToken.verify(token);
            return tokenDecodificado;

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido o expirado!");
        }
    }

    
    public String getSubject(String token){
        DecodedJWT tokenDecodificado = decodificarToken(token);
        return tokenDecodificado.getSubject();
    }
    
    public boolean verificarToken(String token){
        String email = getSubject(token);
        if(email == null || email.isBlank()){
            return false;
        }
        return true;    
    }

    
}
