package com.project.jwt.service;

import com.project.jwt.DTO.RequestAuthParams;
import com.project.jwt.DTO.RequestLoginParams;
import com.project.jwt.entity.Role;
import com.project.jwt.entity.User;
import com.project.jwt.repository.RoleRepository;
import com.project.jwt.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       JwtEncoder jwtEncoder,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> login(RequestLoginParams request) {
        User user = userRepository.findByUserName(request.userName());
        if (user == null || !user.credentialsTest(request.password(), passwordEncoder)) {
            return ResponseEntity.status(401).body(Map.of("error-msg","User already exists"));
        }
        Instant now = Instant.now();
        long expires = 10L;

        String scope = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .subject(user.getUserName())
                .expiresAt(now.plusSeconds(expires))
                .claim("scope", scope)
                .issuedAt(now).build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.status(200).body(Map.of("token", jwtValue));
    }

    public ResponseEntity<?> register (RequestAuthParams request) {
        if (userRepository.existsByUserName(request.userName())) {
            return ResponseEntity.status(401).body(Map.of("error-msg","User already exists"));
        }
        Instant now = Instant.now();
        long expires = 10L;

        User user = new User();
        Role role = roleRepository.findByName(Role.Values.COMMON.name());

        user.setUserName(request.userName());
        user.setAge(request.age());
        user.setHeight(request.height());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(role));

        userRepository.save(user);

        String scope = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getUserName())
                .expiresAt(now.plusSeconds(expires))
                .claim("scope", scope)
                .issuedAt(now).build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.status(200).body(Map.of("token", jwtValue));
    }
}