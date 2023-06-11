package sensor_manager.config;

import lombok.NoArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import sensor_manager.models.Container;
import sensor_manager.models.Parameter;
import sensor_manager.models.Sensor;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@NoArgsConstructor
public class KafkaConfigProducer {

    private KafkaProperties properties;

    @Autowired
    public KafkaConfigProducer(KafkaProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ProducerFactory<String, Container> containerProducerFactory() {
        var props = propsGenerate();

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Container> containerKafkaTemplate() {
        return new KafkaTemplate<>(containerProducerFactory());
    }

    @Bean
    public ProducerFactory<String, Parameter> parameterProducerFactory() {
        var props = propsGenerate();

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Parameter> parameterKafkaTemplate() {
        return new KafkaTemplate<>(parameterProducerFactory());
    }

    @Bean
    public ProducerFactory<String, Sensor> sensorProducerFactory() {
        var props = propsGenerate();

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Sensor> sensorKafkaTemplate() {
        return new KafkaTemplate<>(sensorProducerFactory());
    }

    private Map<String, Object> propsGenerate() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

}
