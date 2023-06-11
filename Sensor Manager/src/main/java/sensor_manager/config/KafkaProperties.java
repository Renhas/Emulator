package sensor_manager.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@ConfigurationProperties("kafka")
@Data
@NoArgsConstructor
public class KafkaProperties {

    private String bootstrapServers;
    private String groupId;
    private String sensorTopic;
    private String containerTopic;
    private String parameterTopic;
}
