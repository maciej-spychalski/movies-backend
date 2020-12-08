package pl.asbt.movies.passwordgenerator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.asbt.movies.passwordgenerator.client.PasswordGeneratorClient;
import pl.asbt.movies.passwordgenerator.dto.GeneratedPasswordDto;

@Service
public class PasswordGeneratorService {

    @Autowired
    private PasswordGeneratorClient client;

    public GeneratedPasswordDto getPasswords() {
        return client.getPasswords();
    }
}
