package org.example.security.oauth2;

import org.example.smart_delivery.entity.User;
import java.util.Map;

public interface OAuth2Strategy {
    User extractUser(Map<String,Object> attributes);
    String getProviderName();
}
