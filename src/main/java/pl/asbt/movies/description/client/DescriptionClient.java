package pl.asbt.movies.description.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.asbt.movies.description.config.DescriptionConfig;
import pl.asbt.movies.description.dto.DescriptionDto;

import java.net.URI;

@Component
public class DescriptionClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DescriptionClient.class);

    @Autowired
    private DescriptionConfig config;

    @Autowired
    private RestTemplate restTemplate;

    public DescriptionDto getDescription(String movieTitle) {

        URI url = UriComponentsBuilder.fromHttpUrl(config.getDescriptionEndpoint())
                .queryParam("apikey", config.getDescriptionApiKey())
                .queryParam("type", config.getDescriptionType())
                .queryParam("plot", config.getDescriptionPlot())
                .queryParam("t", movieTitle).build().encode().toUri();

        try {
            DescriptionDto descriptionDto = restTemplate.getForObject(url, DescriptionDto.class);
            return descriptionDto;
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new DescriptionDto();
        }

    }
}
