package sensor_manager.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerDto {
    @Positive
    private Long id;
    @NotBlank
    private String name;
    private Map<LocalDateTime, List<ParameterDto>> parameters;

}
