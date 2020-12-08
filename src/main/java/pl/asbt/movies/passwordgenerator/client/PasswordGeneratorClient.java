package pl.asbt.movies.passwordgenerator.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.asbt.movies.passwordgenerator.config.PasswordGeneratorConfig;
import pl.asbt.movies.passwordgenerator.dto.GeneratedPasswordDto;

import java.net.URI;

@Component
public class PasswordGeneratorClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordGeneratorClient.class);

    @Autowired
    private PasswordGeneratorConfig config;

    @Autowired
    private RestTemplate restTemplate;

    public GeneratedPasswordDto getPasswords() {
        URI url = UriComponentsBuilder.fromHttpUrl(config.getPasswordGeneratorApiEndpoint())
                .queryParam("apikey", config.getPasswordGeneratorApiKey())
                .queryParam("limit", config.getPasswordGeneratorLimit())
                .queryParam("length", config.getPasswordGeneratorLength())
                .queryParam("num", config.getPasswordGeneratorNum())
                .queryParam("symbols", config.getPasswordGeneratorSymbols())
                .queryParam("upper", config.getPasswordGeneratorUpper()).build().encode().toUri();

        try {
            GeneratedPasswordDto generatedPasswordDto = restTemplate.getForObject(url, GeneratedPasswordDto.class);
            return generatedPasswordDto;
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new GeneratedPasswordDto();
        }
    }
}
