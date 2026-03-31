package br.com.coreto;

import br.com.coreto.infrastructure.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class CoretoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoretoApplication.class, args);
    }
}
