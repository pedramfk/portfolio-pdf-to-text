package se.pedramfk.portfolio.pdftotext.server;

import java.net.InetAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties
public class AppServerConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Value("${application.server.address}")
    private InetAddress address;
    
    @Value("${application.server.port}") 
    private int port;

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        factory.setAddress(address);
        factory.setPort(port);
    }
    
}
