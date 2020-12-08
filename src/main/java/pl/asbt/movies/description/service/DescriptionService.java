package pl.asbt.movies.description.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.asbt.movies.description.client.DescriptionClient;
import pl.asbt.movies.description.dto.DescriptionDto;

@Service
public class DescriptionService {

    @Autowired
    private DescriptionClient client;

    public DescriptionDto getDescription(String movieTitle) {
        return client.getDescription(movieTitle);
    }

}
