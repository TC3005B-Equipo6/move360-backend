package com.e6.infrastructure.infrastructure.config;

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
                InputStream serviceAccount =
                        getClass()
                                .getClassLoader()
                                .getResourceAsStream("firebase-service-account.json");

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