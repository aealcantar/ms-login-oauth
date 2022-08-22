package gob.mx.imss.mspad.oauth.util;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class IntentosException extends UsernameNotFoundException {
    public IntentosException(){
        super("Error username");

    }

    public IntentosException(String mensaje){
        super(mensaje);
    }
}
