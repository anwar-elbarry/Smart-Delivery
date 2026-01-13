package org.example.smart_delivery.service.authService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.security.jwt.JwtService;
import org.example.security.service.TokenService;
import org.example.smart_delivery.dto.request.AuthRequest;
import org.example.smart_delivery.dto.request.RegisterRequest;
import org.example.smart_delivery.dto.response.AuthResponse;
import org.example.smart_delivery.dto.response.UserRespDTO;
import org.example.smart_delivery.entity.Role;
import org.example.smart_delivery.entity.User;
import org.example.smart_delivery.entity.enums.Provider;
import org.example.smart_delivery.exception.ResourceNotFoundException;
import org.example.smart_delivery.mapper.response.UserRespMapper;
import org.example.smart_delivery.repository.RoleRepository;
import org.example.smart_delivery.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;
    private final UserRespMapper userRespMapper;

    public AuthResponse login(AuthRequest request) throws UsernameNotFoundException{

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User user = userRepository.findUserByusername(request.username()).orElseThrow(
                () -> new UsernameNotFoundException(request.username()));

        UserRespDTO userRespDTO = userRespMapper.toRespDto(user);
        Map<String,String> tokens = generateTokens(user);

        return AuthResponse.builder()
                .accessToken(tokens.get("accessToken"))
                .refreshToken(tokens.get("refreshToken"))
                .user(userRespDTO)
                .build();
    }

    public AuthResponse register(RegisterRequest request) throws RuntimeException{
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username is already taken");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email is already in use");
        }

        Role role= roleRepository.findById(request.roleId()).orElseThrow(
                () -> new ResourceNotFoundException("role",request.roleId())
        );

        // Créer un nouvel utilisateur
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nom(request.nom())
                .prenom(request.prenom())
                .telephone(request.telephone())
                .adress(request.adresse())
                .role(role)
                .provider(Provider.LOCAL)
                .enable(true)
                .build();

        User savedUser =  userRepository.save(user);
        UserRespDTO userRespDTO = userRespMapper.toRespDto(savedUser);
         Map<String,String> tokens = generateTokens(user);
        return AuthResponse.builder()
                .accessToken(tokens.get("accessToken"))
                .refreshToken(tokens.get("refreshToken"))
                .user(userRespDTO)
                .build();
    }

    public Map<String, String> refreshToken(String refreshToken) {
        String username = jwtService.extractAllClaims(refreshToken).getSubject();
        String storedRefreshToken = tokenService.getRefreshToken(username);

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new SecurityException("Invalid refresh token");
        }

        User user = userRepository.findUserByusername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Generate new tokens
        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        return Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken
        );
    }

    public void logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String username = jwtService.extractAllClaims(token).getSubject();
                long expiration = jwtService.getExpirationTime(token) - System.currentTimeMillis();
                if (expiration > 0) {
                    tokenService.blacklistToken(token, expiration / 1000);
                }
                // Delete refresh token
                tokenService.deleteRefreshToken(username);
                SecurityContextHolder.clearContext();
            } catch (Exception e) {
                throw new SecurityException("Error during logout: " + e.getMessage());
            }
        }
    }

   public Map<String, String> generateTokens(User user){
       String accessToken = jwtService.generateAccessToken(user);
       String refreshToken = jwtService.generateRefreshToken(user);

       Map<String, String> tokens = new HashMap<>();
       tokens.put("accessToken", accessToken);
       tokens.put("refreshToken", refreshToken);
       return tokens;
   }

}
