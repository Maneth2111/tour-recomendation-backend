package com.example.Tour_Recommendation.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class GoogleTokenVerifier {

    private final String clientId;

    public GoogleTokenVerifier(@Value("${google.client-id}") String clientId) {
        this.clientId = clientId;
    }

    public GoogleUserPayload verify(String idToken) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance()
            )
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (googleIdToken == null) {
                throw new RuntimeException("Invalid Google token");
            }

            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            String email = payload.getEmail();
            if (email == null || email.isBlank()) {
                throw new RuntimeException("Google account email is not available");
            }

            return new GoogleUserPayload(
                    payload.getSubject(),
                    email,
                    (String) payload.get("name"),
                    (String) payload.get("picture")
            );
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to verify Google token", ex);
            throw new RuntimeException("Failed to verify Google token");
        }
    }
}
