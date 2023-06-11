package sensor_manager.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorId implements Serializable {
    @Column(nullable = false, name = "container_id")
    private Long containerId;
    @Column(nullable = false, name = "parameter_id")
    private Long parameterId;
    @Column(nullable = false, name = "reading_time")
    private LocalDateTime readingTime;
}
