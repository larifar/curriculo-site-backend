package site.curriculos.web.controller;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import site.curriculos.configuration.SupabaseConfig;
import site.curriculos.security.SupabaseJwtValidator;

@Controller
@RequestMapping("/api")
public class ProfileController {
    private final SupabaseJwtValidator jwtValidator;
    private final SupabaseConfig config;
    private final RestTemplate restTemplate = new RestTemplate();


    public ProfileController(SupabaseJwtValidator jwtValidator, SupabaseConfig config) {
        this.jwtValidator = jwtValidator;
        this.config = config;
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String userId = jwtValidator.validate(token).getSubject();

            String url = config.getUrl() + "/rest/v1/profiles?id=eq." + userId;

            HttpHeaders headers = new HttpHeaders();
            headers.set("apikey", config.getServiceKey());
            headers.set("Authorization", "Bearer " + config.getServiceKey());

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou erro: " + e.getMessage());
        }
    }
}
