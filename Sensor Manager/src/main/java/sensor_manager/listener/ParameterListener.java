package sensor_manager.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sensor_manager.exceptions.SaveException;
import sensor_manager.models.Parameter;
import sensor_manager.services.ParameterService;

@Component
public class ParameterListener {
    private final ParameterService parameterService;

    @Autowired
    public ParameterListener(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    @KafkaListener(containerFactory = "parameterKafkaListenerContainerFactory", topics = "${kafka.parameter-topic}")
    public void onMessage(Parameter parameter) {
        try {
            parameterService.save(parameter);
        } catch(SaveException e) {
            System.out.println(e.getMessage());
        }
    }
}
