package sensor_manager.config;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import sensor_manager.models.Container;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;
import sensor_manager.models.Parameter;
import sensor_manager.models.Sensor;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@NoArgsConstructor
public class KafkaConfigConsumer {
    private KafkaProperties properties;

    @Autowired
    public KafkaConfigConsumer(KafkaProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ConsumerFactory<String, Container> containerConsumerFactory() {
        var props = propsGenerate();

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(Container.class));
    }

    @Bean("containerKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Container> containerKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Container> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(containerConsumerFactory());
        factory.getContainerProperties().setAckMode(AckMode.RECORD);
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(1000L, 5)));

        return factory;
    }

    @Bean
    public ConsumerFactory<String, Sensor> sensorConsumerFactory() {
        var props = propsGenerate();

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(Sensor.class));
    }

    @Bean("sensorKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Sensor> sensorKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Sensor> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(sensorConsumerFactory());
        factory.getContainerProperties().setAckMode(AckMode.RECORD);
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(1000L, 5)));

        return factory;
    }

    @Bean
    public ConsumerFactory<String, Parameter> parameterConsumerFactory() {
        var props = propsGenerate();

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(Parameter.class));
    }

    @Bean("parameterKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Parameter> parameterKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Parameter> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(parameterConsumerFactory());
        factory.getContainerProperties().setAckMode(AckMode.RECORD);
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(1000L, 5)));

        return factory;
    }

    private Map<String, Object> propsGenerate() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return props;
    }

}
