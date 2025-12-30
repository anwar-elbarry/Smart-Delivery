package org.example.security.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.security.jwt.JwtService;
import org.example.smart_delivery.dto.request.UserDTO;
import org.example.smart_delivery.dto.response.UserRespDTO;
import org.example.smart_delivery.entity.Role;
import org.example.smart_delivery.entity.User;
import org.example.smart_delivery.entity.enums.Provider;
import org.example.smart_delivery.repository.RoleRepository;
import org.example.smart_delivery.repository.UserRepository;
import org.example.smart_delivery.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
@AllArgsConstructor
public class MultiProviderSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;
    private final JwtService jwtService;
    private final OAouth2StrategyFactory oAouth2StrategyFactory;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String registrationId = token.getAuthorizedClientRegistrationId();

        OAuth2Strategy oAuth2Strategy = oAouth2StrategyFactory.getStrategy(registrationId);
        if (oAuth2Strategy == null) {
            throw new RuntimeException("No OAuth2 strategy found for provider: " + registrationId);
        }

        User user = oAuth2Strategy.extractUser(token.getPrincipal().getAttributes());
        try {
            // Try to get existing user
            User userDb = userRepository.findUserByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException("user not found with email :"+user.getEmail()));

            // Generate token with user's role
            String accessToken = jwtService.generateAccessToken(userDb);
            String refreshToken = jwtService.generateAccessToken(userDb);
            sendTokenResponse(response, accessToken,refreshToken);

        } catch (Exception e) {
            // If user doesn't exist, create a new one with CLIENT role
            try {
                // Get CLIENT role ID
                Role clientRole = roleRepository.findRoleByName("CLIENT").orElseThrow(() -> new RuntimeException("role not found with name : CLIENT "));

                // Create new user
                UserDTO newUser = UserDTO.builder()
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .nom(user.getNom())
                        .prenom(user.getPrenom())
                        .roleId(clientRole.getId())
                        .provider(Provider.valueOf(oAuth2Strategy.getProviderName()))
                        .providerId(user.getProviderId())
                        .enable(true)
                        .build();

                UserRespDTO createdUser = userService.create(newUser);
                User entityUser = userRepository.findUserByEmail(createdUser.getEmail()).orElseThrow(() -> new RuntimeException("user not found with email :"+createdUser.getEmail()));

                String accessToken = jwtService.generateAccessToken(entityUser);
                String refreshToken = jwtService.generateRefreshToken(entityUser);
                sendTokenResponse(response, accessToken,refreshToken);

            } catch (Exception ex) {
                // Handle any errors during user creation
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                String jsonFormat = String.format("{\"error\": \"Failed to create user\",\"message\" : \"%S\"}",ex.getMessage());
                response.getWriter().write(jsonFormat);
                response.getWriter().flush();
            }
        }
    }

    private void sendTokenResponse(HttpServletResponse response, String accessToken,String refreshToken) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = String.format("{\"Access Token\": \"%s\" ,\"Access Token\": \"%s\", \"message\": \"Login Successful\"}", accessToken,refreshToken);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

}
