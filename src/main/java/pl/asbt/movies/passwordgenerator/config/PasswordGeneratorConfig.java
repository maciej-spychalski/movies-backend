package pl.asbt.movies.passwordgenerator.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PasswordGeneratorConfig {

    @Value("${password.generator.endpoint}")
    private String passwordGeneratorApiEndpoint;

    @Value("${password.generator.apikey}")
    private String passwordGeneratorApiKey;

    @Value("${password.generator.limit}")
    private String passwordGeneratorLimit;

    @Value("${password.generator.length}")
    private String passwordGeneratorLength;

    @Value("${password.generator.num}")
    private String passwordGeneratorNum;

    @Value("${password.generator.symbols}")
    private String passwordGeneratorSymbols;

    @Value("${password.generator.upper}")
    private String passwordGeneratorUpper;


}

/*
password.generator.ednpoint=https://api.happi.dev/v1/generate-password
password.generator.apikey=0707dc9G4ktUWqO8ewGHnAfv3yYOMNt8xz6A9NaeTdDS71A2PBYaKoT0
password.generator.limit=1
password.generator.length=10
password.generator.num=true
password.generator.symbols=true
password.generator.upper=true
*/