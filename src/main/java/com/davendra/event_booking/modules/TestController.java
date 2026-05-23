package com.davendra.event_booking.modules;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class TestController {

    @GetMapping("/api/test")
    public String test(HttpServletRequest request) {

        String uid = (String) request.getAttribute("firebaseUid");
        String email = (String) request.getAttribute("email");

        return "Authenticated user: " + email + " uid: " + uid;
    }
}