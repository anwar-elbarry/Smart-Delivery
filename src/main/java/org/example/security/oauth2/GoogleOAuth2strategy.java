package org.example.security.oauth2;

import org.example.smart_delivery.entity.User;
import org.example.smart_delivery.entity.enums.Provider;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class GoogleOAuth2strategy implements OAuth2Strategy {

    @Override
    public User extractUser(Map<String,Object> attributes) {
        User user = new User();
        // Google Attributes
        user.setEmail((String) attributes.get("email"));
        user.setNom((String) attributes.get("given_name"));
        user.setPrenom((String) attributes.get("family_name"));
        user.setUsername((String) attributes.get("given_name"));
        user.setProviderId((String) attributes.get("sub"));
        return user;
    }

    @Override
    public String getProviderName() {
        return Provider.GOOGLE.name();
    }

}
