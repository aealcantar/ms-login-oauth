package gob.mx.imss.mspad.oauth.jwt.modelJwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accessToken;
    private String tokenType = "Bearer";
    private String refreshToken;


}
