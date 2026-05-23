package com.davendra.event_booking;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.InputStream;

@SpringBootApplication
@EnableScheduling
public class EventBookingApplication {

    public static void main(String[] args) {
//		try {
//
//			InputStream serviceAccount = EventBookingApplication.class
//					.getClassLoader()
//					.getResourceAsStream("firebase/firebase_config.json");
//
//			if (serviceAccount == null) {
//				throw new RuntimeException("Firebase service account file not found");
//			}
//
//			FirebaseOptions options = FirebaseOptions.builder()
//					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
//					.build();
//
//			if (FirebaseApp.getApps().isEmpty()) {
//				FirebaseApp.initializeApp(options);
//			}

        SpringApplication.run(EventBookingApplication.class, args);

//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    }

}
