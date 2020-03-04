package se.iths.anton.myservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class SetupDatabase {
    @Bean
    void initDatabase(UsersRepository repository) {

    }
    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
