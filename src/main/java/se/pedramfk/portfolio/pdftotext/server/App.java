package se.pedramfk.portfolio.pdftotext.server;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.env.Environment;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(AppServerConfig.class)
public class App {

    private static final Logger LOGGER = LogManager.getLogger(App.class);

    public final static void main(String[] args) {

        LOGGER.info("starting application");
        SpringApplication app = new SpringApplication(App.class);
        app.run(args);
        
    }

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            java.util.stream.Stream.of(environment.getDefaultProfiles()).forEach(p -> { LOGGER.trace(p); });
        };
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
        return args -> {
            java.util.stream.Stream.of(applicationContext.getBeanDefinitionNames()).forEach(p -> { LOGGER.trace(p); });
        };
    }

}