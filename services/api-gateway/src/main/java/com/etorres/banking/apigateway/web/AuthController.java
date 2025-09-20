package com.etorres.banking.apigateway.web;

import com.etorres.banking.apigateway.dto.AuthenticationRequest;
import com.etorres.banking.apigateway.dto.AuthenticationResponse;
import com.etorres.banking.apigateway.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ReactiveUserDetailsService userDetailsService;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthenticationResponse>> login(@RequestBody AuthenticationRequest request) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()))
                .flatMap(authentication -> userDetailsService.findByUsername(authentication.getName()))
                .map(userDetails -> {
                    String token = jwtUtil.generateToken(userDetails);
                    return ResponseEntity.ok(new AuthenticationResponse(token));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
