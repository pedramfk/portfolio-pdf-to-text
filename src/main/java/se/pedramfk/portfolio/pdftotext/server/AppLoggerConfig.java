package se.pedramfk.portfolio.pdftotext.server;

//import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties
public class AppLoggerConfig {

    //@Value("${application.logging.level}")
    //private String level;
    
}
