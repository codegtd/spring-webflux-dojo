package academy.devdojo.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
public class AppDriver {

    public static void main(String[] args) {
        System.out.println(
                PasswordEncoderFactories
                        .createDelegatingPasswordEncoder()
                        .encode("devdojo"));
        SpringApplication.run(AppDriver.class ,args);
    }

}
