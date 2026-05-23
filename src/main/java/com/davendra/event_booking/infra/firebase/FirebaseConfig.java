package com.davendra.event_booking.infra.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Configuration
public class FirebaseConfig {

//    @PostConstruct
//    public void initialize() throws IOException {
//
//        InputStream serviceAccount =
//                getClass().getClassLoader()
//                        .getResourceAsStream("firebase/firebase_config.json");
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        if (FirebaseApp.getApps().isEmpty()) {
//            FirebaseApp.initializeApp(options);
//        }
//    }

//
//    @PostConstruct
//    public void initialize() {
//
//        try {
//
//            // this is necessary to create firebase instance to call it from backend
//
//            InputStream serviceAccount = getClass()
//                    .getClassLoader()
//                    .getResourceAsStream("firebase/firebase_config.json");
//
//            if (serviceAccount == null) {
//                throw new RuntimeException("Firebase service account file not found");
//            }
//
//            FirebaseOptions options = FirebaseOptions.builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .build();
//
//            if (FirebaseApp.getApps().isEmpty()) {
//                FirebaseApp.initializeApp(options);
//            }
//
//            System.out.println("Firebase initialized successfully");
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to initialize Firebase", e);
//        }
//    }


    @PostConstruct
    public void initialize() {

        try {

            // Read Base64 encoded Firebase config from ENV
            String firebaseConfigBase64 =
                    System.getenv("FIREBASE_CONFIG_BASE64");

            if (firebaseConfigBase64 == null ||
                    firebaseConfigBase64.isBlank()) {

                throw new RuntimeException(
                        "FIREBASE_CONFIG_BASE64 env variable is missing"
                );
            }

            // Decode Base64 string
            byte[] decodedBytes =
                    Base64.getDecoder().decode(firebaseConfigBase64);

            // Convert to InputStream
            InputStream serviceAccount =
                    new ByteArrayInputStream(decodedBytes);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(
                            GoogleCredentials.fromStream(serviceAccount)
                    )
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            System.out.println("Firebase initialized successfully");

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to initialize Firebase",
                    e
            );
        }
    }
}