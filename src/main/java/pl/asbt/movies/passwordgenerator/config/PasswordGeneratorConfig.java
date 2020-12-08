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