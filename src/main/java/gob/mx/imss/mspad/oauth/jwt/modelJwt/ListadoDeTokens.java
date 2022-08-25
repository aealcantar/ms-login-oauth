package gob.mx.imss.mspad.oauth.jwt.modelJwt;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ListadoDeTokens {

    public static final Map<String, RefreshToken> LISTA_DE_SESIONES = new HashMap<>();

    public static RefreshToken get(String token) {

        return LISTA_DE_SESIONES.get(token);

    }

    public static void set(String token, RefreshToken refreshToken) {

        LISTA_DE_SESIONES.put(token, refreshToken);

    }

    public static void remove(String token) {

        LISTA_DE_SESIONES.remove(token);

    }

}
