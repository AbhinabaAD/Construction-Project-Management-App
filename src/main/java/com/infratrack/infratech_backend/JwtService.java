
package com.infratrack.infratech_backend;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${infratrack.jwt.secret}")
    private String secret;

    public String generateToken(String userId, String email, String role) {
        long now = Instant.now().getEpochSecond();
        long expiry = now + 86400; // 24 hours

        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payload = "{\"sub\":\"" + escape(userId)
                + "\",\"email\":\"" + escape(email)
                + "\",\"role\":\"" + escape(role)
                + "\",\"iat\":" + now
                + ",\"exp\":" + expiry + "}";

        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

        String unsigned = encoder.encodeToString(
                header.getBytes(StandardCharsets.UTF_8))
                + "."
                + encoder.encodeToString(
                        payload.getBytes(StandardCharsets.UTF_8));

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"));

            byte[] signature = mac.doFinal(
                    unsigned.getBytes(StandardCharsets.UTF_8));

            return unsigned + "." + encoder.encodeToString(signature);
        } catch (Exception e) {
            throw new IllegalStateException("Could not generate JWT", e);
        }
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

    
