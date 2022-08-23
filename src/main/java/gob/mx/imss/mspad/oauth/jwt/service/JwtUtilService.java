package gob.mx.imss.mspad.oauth.jwt.service;

import gob.mx.imss.mspad.oauth.api.AplicacionController;
import gob.mx.imss.mspad.oauth.dao.UsuarioRepository;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtilService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AplicacionController.class);
    @Autowired
    UsuarioRepository usuarioRepository;

    // pruebaEflofe => [Base64] => cHJ1ZWJhRWZsb2Zl
    private static final String JWT_SECRET_KEY = "cHJ1ZWJhRWZsb2Zl";

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 1000; // 8 Horas // 5 minutos

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

    public String generateToken(UserDetails userDetails) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        String rol = String.valueOf(userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0));
        claims.put("rol", rol);
        return createToken(claims, userDetails.getUsername());
    }



    private String createToken(Map<String, Object> claims, String subject) throws Exception {
        UsuarioEntity usuarioEntity = usuarioRepository.findByNumMatricula(Long.valueOf(subject));
        String rol = usuarioEntity.getNomUsuario();
        if (rol != null) {
            if (usuarioEntity.getIndNumIntentos() >= 3 || !usuarioEntity.getIndActivo().booleanValue()) {
                throw new Exception(Constants.USUARIO_BLOQUEADO);
            }
            if (usuarioEntity != null && usuarioEntity.getIndNumIntentos() <= 3) {
                int numIntentos = usuarioEntity.getIndNumIntentos().intValue();
                usuarioEntity.setIndNumIntentos((long) numIntentos);
                usuarioRepository.update3Reintentos(Long.valueOf(numIntentos) + 1, usuarioEntity.getId());
            }
            if (usuarioEntity.getIndNumIntentos() == 3) {
                usuarioRepository.updateActivoInactivoUSer(false, usuarioEntity.getId());
                throw new Exception(Constants.USUARIO_BLOQUEADO);
            }
            return Jwts
                    .builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                    .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                    .compact();
        }
        return null;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
