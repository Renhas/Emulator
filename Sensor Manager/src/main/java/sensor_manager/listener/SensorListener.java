package sensor_manager.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sensor_manager.exceptions.SaveException;
import sensor_manager.models.Sensor;
import sensor_manager.services.SensorService;

@Component
public class SensorListener {
    private final SensorService sensorService;

    @Autowired
    public SensorListener(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @KafkaListener(containerFactory = "sensorKafkaListenerContainerFactory", topics = "${kafka.sensor-topic}")
    public void onMessage(Sensor sensor) {
        try {
            sensorService.save(sensor);
        } catch(SaveException e) {
            System.out.println(e.getMessage());
        }
    }
}
