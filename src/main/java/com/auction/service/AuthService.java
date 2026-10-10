package com.auction.service;

import com.auction.dto.request.LoginRequest;
import com.auction.dto.request.RegisterRequest;
import com.auction.dto.response.JwtTokenResponse;
import com.auction.dto.response.UserResponse;
import com.auction.entity.User;
import com.auction.entity.UserRole;
import com.auction.exception.UnauthorizedException;
import com.auction.exception.UserAlreadyExistsException;
import com.auction.repository.UserRepository;
import com.auction.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public JwtTokenResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email is already registered: " + request.getEmail());
        }

        UserRole role = UserRole.BUYER;
        if (request.getRole() != null && request.getRole().equalsIgnoreCase("SELLER")) {
            role = UserRole.SELLER;
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .role(role)
                .isVerified(false)
                .isActive(true)
                .build();

        user = userRepository.save(user);

        String token = tokenProvider.generateTokenForUsername(user.getUsername());

        return JwtTokenResponse.builder()
                .accessToken(token)
                .refreshToken(tokenProvider.generateTokenForUsername(user.getUsername()))
                .expiresIn(86400000L)
                .user(mapToUserResponse(user))
                .build();
    }

    public JwtTokenResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsernameOrEmail())
                .or(() -> userRepository.findByEmail(request.getUsernameOrEmail()))
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String token = tokenProvider.generateTokenForUsername(user.getUsername());

        return JwtTokenResponse.builder()
                .accessToken(token)
                .refreshToken(tokenProvider.generateTokenForUsername(user.getUsername()))
                .expiresIn(86400000L)
                .user(mapToUserResponse(user))
                .build();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name())
                .isVerified(user.getIsVerified())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
