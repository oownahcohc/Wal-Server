package server.wal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@EnableFeignClients
@EnableJpaAuditing
@EnableSwagger2
@SpringBootApplication
public class WalApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalApplication.class, args);
    }

}
