package project.adp.voting_system_server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@Configuration
@Component
@ConfigurationProperties("twilio")
@Data
public class TwilioConfig {
    private String AccoundSid;
    private String AuthToken;
    private String PhoneNumber;
}
