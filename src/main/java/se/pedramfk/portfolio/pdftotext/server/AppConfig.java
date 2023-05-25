package se.pedramfk.portfolio.pdftotext.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;


@Data
@Component
@ConfigurationProperties
@PropertySource("classpath:application.properties")
public class AppConfig {

    //@Value("${application.logging.level}") 
    //private String loggingLevel;
    
    @Value("${application.name}") 
    private String name;

}
