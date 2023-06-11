package sensor_manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import sensor_manager.config.KafkaConfigProducer;
import sensor_manager.config.KafkaProperties;
import sensor_manager.models.Container;
import sensor_manager.models.Parameter;
import sensor_manager.models.Sensor;

@Service
public class KafkaService {
    private KafkaConfigProducer configProducer;
    private KafkaProperties properties;

    @Autowired
    public KafkaService(KafkaConfigProducer configProducer,
                        KafkaProperties properties) {
        this.configProducer = configProducer;
        this.properties = properties;
    }

    public void sendContainer(Container container) {
        var template = configProducer.containerKafkaTemplate();
        template.send(properties.getContainerTopic(),
                String.format("%d#%s", container.getId(), container.getName()),
                container);
    }

    public void sendParameter(Parameter parameter) {
        var template = configProducer.parameterKafkaTemplate();
        template.send(properties.getParameterTopic(),
                String.format("%d#%s", parameter.getId(), parameter.getName()),
                parameter);
    }

    public void sendSensor(Sensor sensor) {
        var template = configProducer.sensorKafkaTemplate();
        template.send(properties.getSensorTopic(),
                String.format("%d#%d", sensor.getId().getContainerId(), sensor.getId().getParameterId()),
                sensor);
    }


}
