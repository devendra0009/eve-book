package com.davendra.event_booking.common.security;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {

            response.setStatus(HttpServletResponse.SC_OK);

            filterChain.doFilter(request, response);

            return;
        }

        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {

            String token = authorization.substring(7);

            try {

                FirebaseToken decodedToken =
                        FirebaseAuth.getInstance().verifyIdToken(token);

                request.setAttribute("firebaseUid", decodedToken.getUid());
                request.setAttribute("email", decodedToken.getEmail());

            } catch (Exception e) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid Firebase Token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
