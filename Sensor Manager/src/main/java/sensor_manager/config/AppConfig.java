package sensor_manager.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app")
@Data
@NoArgsConstructor
public class AppConfig {

    private String webApiUrl;
    private String sensorManagerUrl;
    private String accessToken;
}
