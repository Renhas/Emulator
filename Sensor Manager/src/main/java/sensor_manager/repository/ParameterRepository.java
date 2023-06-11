package sensor_manager.repository;

import sensor_manager.models.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository  extends JpaRepository<Parameter, Long> {
}
