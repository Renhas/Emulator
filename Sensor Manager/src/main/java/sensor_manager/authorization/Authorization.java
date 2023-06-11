package sensor_manager.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import sensor_manager.config.AppConfig;

@Component
public class Authorization {
    private AppConfig appConfig;

    @Autowired
    public Authorization(AppConfig appConfig) {
        this.appConfig = appConfig;
    }
    public AuthorizationStatus Authorize(String token) {
        if (!token.equals(appConfig.getAccessToken())) {
            return new AuthorizationStatus(HttpStatus.UNAUTHORIZED.value(), "Wrong token!", false);

        }
        return new AuthorizationStatus(HttpStatus.OK.value(), "Access granted!", true);
    }
}
