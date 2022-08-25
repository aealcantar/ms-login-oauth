package gob.mx.imss.mspad.oauth.jwt.modelJwt;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    private String token;

    private Instant expiryDate;

    private UserDetails userDetails;
    //getters and setters
}