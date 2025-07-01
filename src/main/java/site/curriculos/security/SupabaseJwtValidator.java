package site.curriculos.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;
import site.curriculos.configuration.SupabaseConfig;

@Component
public class SupabaseJwtValidator {
    private final SupabaseConfig config;
    private JWTVerifier verifier;

    public SupabaseJwtValidator(SupabaseConfig config) {
        this.config = config;
        Algorithm algorithm = Algorithm.HMAC256(config.getJwtSecret());
        this.verifier = JWT.require(algorithm)
                .withIssuer(config.getIssuer())
                .build();
    }

    public DecodedJWT validate(String token){
        return verifier.verify(token);
    }
}
