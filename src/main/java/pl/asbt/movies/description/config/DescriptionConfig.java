package pl.asbt.movies.description.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class DescriptionConfig {

    @Value("${omdb.api.endpoint}")
    private String descriptionEndpoint;

    @Value("${omdb.api.apikey}")
    private String descriptionApiKey;

    @Value("${omdb.api.type}")
    private String descriptionType;

    @Value("${omdb.api.plot}")
    private String descriptionPlot;

}
