package sensor_manager.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sensor_manager.models.Sensor;
import sensor_manager.models.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, SensorId> {

    @Query("select s.id.readingTime from Sensor s order by s.id.readingTime asc limit 1")
    LocalDateTime findFirstOrderByIdReadingTime();

    List<Sensor> findAllByIdReadingTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
}
