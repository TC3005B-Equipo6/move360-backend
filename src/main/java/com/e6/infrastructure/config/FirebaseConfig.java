package com.e6.infrastructure.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import io.quarkus.runtime.Startup;

import java.io.InputStream;

@Singleton
@Startup
public class FirebaseConfig {


    @PostConstruct
    void init() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                String credentialsPath = System.getenv("FIREBASE_CREDENTIALS_PATH");

                InputStream serviceAccount = (credentialsPath != null)
                        ? new java.io.FileInputStream(credentialsPath)
                        : getClass().getClassLoader()
                        .getResourceAsStream("firebase-service-account.json");

                assert serviceAccount != null;
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando Firebase", e);
        }
    }
}