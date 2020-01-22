package walking_web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import walking_web.repositories.ShoeRepository;

@SpringBootApplication
@ComponentScan
public class WalkingWebbApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalkingWebbApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ShoeRepository repository) {
        return (args) -> {
            repository.findAll();

        };

    }
}
