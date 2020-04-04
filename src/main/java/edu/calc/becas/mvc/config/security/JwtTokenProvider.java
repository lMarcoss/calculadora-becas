package edu.calc.becas.mvc.config.security;

import edu.calc.becas.mseguridad.login.model.UserLogin;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

/**
 * @author Marcos Santiago Leonardo
 * Meltsan Solutions
 * Description:
 * Date: 4/16/19
 */
@Component
public class JwtTokenProvider {

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:28800000}")
    private long validityInMilliseconds = 28800000; // 1h

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(@Qualifier("userCustomDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserLogin userLogin) {


        Claims claims = Jwts.claims().setSubject(userLogin.getUsername());
        //Claims claims = Jwts.claims().set;
        claims.put("roles", Collections.singletonList(userLogin.getRol()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return "Bearer " + Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .claim("username", userLogin.getUsername())
                .claim("esAlumno", userLogin.isEsAlumno())
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = getClaims(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            LOG.error(e.getMessage());
            LOG.error("Expired or invalid JWT token");
            return false;

        }
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }

    public UserLogin getUserByToken(HttpServletRequest request) {
        UserLogin userRequest = new UserLogin();
        Jws<Claims> claims = getClaims(resolveToken(request));
        String username = claims.getBody().get("username") != null ? claims.getBody().get("username").toString() : null;
        String isAlumno = claims.getBody().get("esAlumno") != null ? claims.getBody().get("esAlumno").toString() : null;
        ArrayList<String> roles = claims.getBody().get("roles") != null ? (ArrayList<String>) claims.getBody().get("roles") : null;
        userRequest.setRol(roles.get(0));
        userRequest.setUsername(username);
        if (isAlumno != null) {
            userRequest.setEsAlumno(Boolean.parseBoolean(isAlumno));
        }
        return userRequest;
    }
}
