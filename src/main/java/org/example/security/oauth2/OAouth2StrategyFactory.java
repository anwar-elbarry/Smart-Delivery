package org.example.security.oauth2;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OAouth2StrategyFactory {
    private final List<OAuth2Strategy> strategies ;
    private  Map<String,OAuth2Strategy> strategyMap ;

    @PostConstruct
    public void init(){
        strategyMap = strategies.stream().collect(Collectors.toMap(OAuth2Strategy::getProviderName, Function.identity()));
    }

    public OAuth2Strategy getStrategy(String registrationId) {
        return strategyMap.get(registrationId.toUpperCase());
    }
}
