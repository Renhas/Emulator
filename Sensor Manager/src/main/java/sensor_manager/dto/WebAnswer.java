package sensor_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebAnswer {
    private int page;
    private int container;
    private double time;
    private String par_name;
    private double par_value;
}
