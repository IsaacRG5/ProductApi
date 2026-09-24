package com.example.productapi.controller;

import com.example.productapi.Security.jwtService;
import com.example.productapi.dto.Request.LoginRequestDto;
import com.example.productapi.dto.Request.LogoutRequest;
import com.example.productapi.dto.Request.RefreshRequetsDto;
import com.example.productapi.dto.Response.AuthResponse;
import com.example.productapi.exception.InvalidRefreshTokenException;
import com.example.productapi.model.RefreshToken;
import com.example.productapi.service.RefreshService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Authentication", description = "Endpoints for user authentication and JWT token management")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final jwtService jwtService;
    private final RefreshService RefreshService;
    private final UserDetailsService userDetailsService;

    @Value("${security.jwt.access-token-expiration-ms}")
    private long accessTokenExpirationMs;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequestDto request) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(), request.password()
        ));

        UserDetails User = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(issueTokens(User));
    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequetsDto request) {
        RefreshToken current = RefreshService.validate(request.refreshToken());
        RefreshService.revoke(current);
        UserDetails user = userDetailsService.loadUserByUsername(current.getUsername());

        return ResponseEntity.ok(issueTokens(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @Valid @RequestBody LogoutRequest request,
            @AuthenticationPrincipal UserDetails authenticatedUser) {

        RefreshToken refreshToken = RefreshService.validate(request.refreshToken());

        if (!refreshToken.getUsername().equals(authenticatedUser.getUsername())) {
            throw new InvalidRefreshTokenException("Este refresh token no pertenece al usuario autenticado");
        }

        RefreshService.revoke(refreshToken);
        return ResponseEntity.noContent().build();
    }


    private AuthResponse issueTokens(UserDetails user) {

        // Genera un Access Token JWT para autenticar las peticiones del usuario.
        String accessToken = jwtService.generateAccessToken(user);

        // Genera y guarda un Refresh Token asociado al usuario.
        RefreshToken refreshToken = RefreshService.create(user.getUsername());

        // Construye la respuesta que contiene los tokens y la información de expiración.
        return new AuthResponse(
                accessToken,                    // Token de acceso para consumir los endpoints protegidos.
                refreshToken.getToken(),        // Token utilizado para solicitar un nuevo Access Token.
                "Bearer",                       // Tipo de autenticación utilizado en el encabezado Authorization.
                accessTokenExpirationMs / 1000  // Convierte la expiración de milisegundos a segundos.
        );
    }

}          // Tipo de autenticación utilizado en el encabezado