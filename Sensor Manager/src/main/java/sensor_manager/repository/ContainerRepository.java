package sensor_manager.repository;

import org.springframework.data.domain.Pageable;
import sensor_manager.models.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {
    List<Container> findAllBySensorsIdReadingTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

}
