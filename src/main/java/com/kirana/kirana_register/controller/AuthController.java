package com.kirana.kirana_register.controller;

import com.kirana.kirana_register.dto.request.LoginRequest;
import com.kirana.kirana_register.enums.Roles;
import com.kirana.kirana_register.security.UserPrincipal;
import com.kirana.kirana_register.security.jwt.JwtUtil;
import com.kirana.kirana_register.security.refresh.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * STAFF / ADMIN login only
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getPhoneNumber(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid credentials"));
        }

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        // 🚫 Explicitly block CUSTOMER login
        if (principal.getRole().equals(Roles.CUSTOMER.name())) {
            return ResponseEntity.status(403)
                    .body(Map.of("error", "Customers are not allowed to login"));
        }

        // 🔐 Generate tokens
        String accessToken = jwtUtil.generateAccessToken(principal);

        String refreshToken = refreshTokenService.createSession(
                principal.getUserId(),
                principal.getUsername(), // phone number
                principal.getRole()
        );

        return ResponseEntity.ok(
                Map.of(
                        "accessToken", accessToken,
                        "refreshToken", refreshToken,
                        "role", principal.getRole()
                )
        );
    }

    /**
     * Refresh access token (ROTATION ENABLED)
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {

        String refreshToken = body.get("refreshToken");

        if (refreshToken == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Refresh token is required"));
        }

        Map<String, Object> session =
                refreshTokenService.getSession(refreshToken);

        if (session == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid or expired refresh token"));
        }

        // 🔁 ROTATE refresh token
        refreshTokenService.deleteSession(refreshToken);

        String newRefreshToken = refreshTokenService.createSession(
                (String) session.get("userId"),
                (String) session.get("phone"),
                (String) session.get("role")
        );

        // Build lightweight principal for JWT
        UserPrincipal principal = UserPrincipal.fromSession(
                (String) session.get("userId"),
                (String) session.get("phone"),
                (String) session.get("role")
        );

        String newAccessToken = jwtUtil.generateAccessToken(principal);

        return ResponseEntity.ok(
                Map.of(
                        "accessToken", newAccessToken,
                        "refreshToken", newRefreshToken
                )
        );
    }

    /**
     * Logout (invalidate refresh session)
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> body) {

        String refreshToken = body.get("refreshToken");

        if (refreshToken != null) {
            refreshTokenService.deleteSession(refreshToken);
        }

        return ResponseEntity.ok(
                Map.of("message", "Logged out successfully")
        );
    }
}
