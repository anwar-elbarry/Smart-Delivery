package org.example.security.oauth2;

import org.example.smart_delivery.entity.User;
import org.example.smart_delivery.entity.enums.Provider;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FacebookOAuth2Strategy implements OAuth2Strategy{
    @Override
    public User extractUser(Map<String, Object> attributes) {
        User user = new User();
        // Facebook Attributes
        user.setEmail((String) attributes.get("email"));
        user.setProviderId((String) attributes.get("id")); // Facebook's unique ID

        // Handle Name (Facebook sends "John Doe", not separate first/last by default)
        String fullName = (String) attributes.get("name");
        if (fullName != null) {
            String[] parts = fullName.split(" ", 2);
            user.setNom(parts[0]);
            user.setPrenom(parts.length > 1 ? parts[1] : "");
        }

        return user;
    }

    @Override
    public String getProviderName() {
        return Provider.FACEBOOK.name();
    }
}
