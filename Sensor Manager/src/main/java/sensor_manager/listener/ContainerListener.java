package sensor_manager.listener;

import sensor_manager.exceptions.SaveException;
import sensor_manager.models.Container;
import sensor_manager.services.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ContainerListener {

    private final ContainerService containerService;

    @Autowired
    public ContainerListener(ContainerService containerService) {
        this.containerService = containerService;
    }

    @KafkaListener(containerFactory = "containerKafkaListenerContainerFactory", topics = "${kafka.container-topic}")
    public void onMessage(Container container) {
        try {
            containerService.save(container);
        } catch (SaveException e) {
            System.out.println(e.getMessage());
        }
    }
}
