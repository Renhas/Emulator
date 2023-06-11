package sensor_manager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Containers_Parameters")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {
    @EmbeddedId
    private SensorId id;

    @ManyToOne
    @MapsId("containerId")
    @JoinColumn(name = "container_id")
    private Container container;
    @ManyToOne
    @MapsId("parameterId")
    @JoinColumn(name = "parameter_id")
    private Parameter parameter;
    @Column(nullable = false, name = "parameter_value")
    private Double parameterValue;
}
