package gob.mx.imss.mspad.oauth.jwt.service;

import gob.mx.imss.mspad.oauth.api.AplicacionController;
import gob.mx.imss.mspad.oauth.dao.UsuarioRepository;
import gob.mx.imss.mspad.oauth.jwt.exception.JwtException;
import gob.mx.imss.mspad.oauth.jwt.exception.TokenRefreshException;
import gob.mx.imss.mspad.oauth.jwt.modelJwt.ListadoDeTokens;
import gob.mx.imss.mspad.oauth.jwt.modelJwt.RefreshToken;
import gob.mx.imss.mspad.oauth.model.entity.UsuarioEntity;
import gob.mx.imss.mspad.oauth.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtilService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AplicacionController.class);
    @Autowired
    UsuarioRepository usuarioRepository;


    private static final String JWT_SECRET_KEY = "cHJ1ZWJhRWZsb2Zl";

    public static final long JWT_ACCES_TOKEN_VALIDITY = 7200000;//7200000 = 2hrs
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 21600000;//21600000 = 6hrs

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) throws JwtException {
        Map<String, Object> claims = new HashMap<>();
        String rol = String.valueOf(userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0));
        claims.put("rol", rol);
        return createToken(claims, userDetails.getUsername());
    }


    private String createToken(Map<String, Object> claims, String subject) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByNumMatricula(Long.valueOf(subject));
        String rol = usuarioEntity.getNomUsuario();
        if (rol != null) {
            if (Boolean.FALSE.equals(usuarioEntity.getIndActivo())) {
                throw new JwtException(Constants.USUARIO_INACTIVO);
            }
            if (usuarioEntity.getIndNumIntentos() < 3) {

                usuarioRepository.update3Reintentos(0L, usuarioEntity.getId());
            }
            if (usuarioEntity.getIndNumIntentos() >= 3) {
                throw new JwtException(Constants.USUARIO_BLOQUEADO);
            }
            return Jwts
                    .builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCES_TOKEN_VALIDITY))
                    .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                    .compact();
        }
        return null;
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public RefreshToken createRefreshToken(UserDetails userDetails) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().plusMillis(JWT_REFRESH_TOKEN_VALIDITY));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUserDetails(userDetails);
        ListadoDeTokens.set(refreshToken.getToken(), refreshToken);
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return Optional.ofNullable(ListadoDeTokens.get(token));
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            ListadoDeTokens.remove(token.getToken());
            throw new TokenRefreshException(token.getToken(), Constants.REFRESH_TOKEN_EXPIRED);
        }
        return token;
    }

}
