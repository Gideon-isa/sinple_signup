package com.gideon.jwt_1.services.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.PrivateKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "fc318a158ff7d0facbee85f32b510c543adaed341be06006d8758f9e205a2c70";

    // public String generateToken() {
    //     return Jwts.claims()
    // }

    /**
     *
     * @return a key
     * @throws WeakKeyException
     */
    private SecretKey getSignInKey() {
      Decoder<String, byte[]> base  = Decoders.BASE64;
       byte[] keyByte = new byte[0];

        try {
            keyByte = base.decode(SECRET_KEY);
        } catch (DecodingException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }


        return Keys.hmacShaKeyFor(keyByte);

    }

    /**
     * A parser for parsing token
     *
     * @return a JwtParser
     */
    private JwtParser createParser() {
        Key key = null;
        try {
            key = getSignInKey();
        } catch (WeakKeyException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build();

    }


    /**
     * For extracting all claims
     * @param token
     * @return a Jws<Claims>
     */
    private Jws<Claims> extractClaims(String token) throws Exception{
        return createParser()
                .parseClaimsJws(token);
    }


    /**
     * To extract the payload of a token
     * @param token
     * @return Claims
     */
    private Claims extractPayloadBody(String token) {
        Claims claims = null;
        try {
            claims = extractClaims(token).getBody();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return claims;
    }

    /**
     * To extract the signature of a token
     * @param token
     * @return String
     */
    public String extractSignature(String token) {
        String signature = "";
        try {
            signature = extractClaims(token).getSignature();
        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
        }
        return signature;
    }

    /**
     * extracts the username
     * @param token
     * @return String
     */
    public String extractUsername(String token) {
        return  extractPayloadBody(token).getSubject();
    }

    public String generateToken(String email) {
        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*30))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }



}
