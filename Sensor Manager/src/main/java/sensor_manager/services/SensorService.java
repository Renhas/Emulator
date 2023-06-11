package sensor_manager.services;

import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sensor_manager.exceptions.SaveException;
import sensor_manager.models.Sensor;
import sensor_manager.repository.SensorRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class SensorService {
    private SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public void save(Sensor sensor) throws SaveException {
        var existed = sensorRepository.findById(sensor.getId());
        if (existed.isEmpty()) {
            sensorRepository.saveAndFlush(sensor);
        } else {
            throw new SaveException("Sensor already exists!");
        }
    }

    public LocalDateTime getFirstTime() {
        return sensorRepository.findFirstOrderByIdReadingTime();
    }

    public List<Sensor> getBetween(LocalDateTime startTime, LocalDateTime endTime,
                                   @Positive int page, @Positive int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return sensorRepository.findAllByIdReadingTimeBetween(startTime, endTime, pageable);
    }
}
