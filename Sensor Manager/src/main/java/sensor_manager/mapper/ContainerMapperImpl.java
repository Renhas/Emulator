package sensor_manager.mapper;

import sensor_manager.dto.ContainerDto;
import sensor_manager.dto.ParameterDto;
import sensor_manager.models.Container;
import sensor_manager.models.Sensor;
import sensor_manager.models.SensorId;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class ContainerMapperImpl implements ContainerMapper{

    @Override
    public ContainerDto mapToDto(Container container) {
        if (container == null) return null;

        var containerDto = new ContainerDto();
        containerDto.setId(container.getId());
        containerDto.setName(container.getName());


        Map<LocalDateTime, List<ParameterDto>> parameters = new HashMap<>();
        for (var sensor: container.getSensors()) {
            var time = sensor.getId().getReadingTime();
            var parameterDto = Mappers.PARAMETERS_MAPPER.mapToDto(sensor);
            if (parameters.containsKey(time)) {
                parameters.get(time).add(parameterDto);
            } else {
                List<ParameterDto> tempList = new ArrayList<>();
                tempList.add(parameterDto);
                parameters.put(time, tempList);
            }
        }

        return containerDto;
    }

    @Override
    public Container mapFromDtoToContainer(ContainerDto containerDto) {
        if (containerDto == null) return null;

        Container container = new Container();
        container.setId(containerDto.getId());
        container.setName(containerDto.getName());

        return container;

    }

    @Override
    public List<Sensor> mapFromDtoToSensor(ContainerDto containerDto) {
        if (containerDto == null) return null;

        ArrayList<Sensor> sensors = new ArrayList<>();
        var id = containerDto.getId();
        var container = mapFromDtoToContainer(containerDto);
        for (var item: containerDto.getParameters().entrySet()) {
            var time = item.getKey();
            for (var parameter : item.getValue()) {
                Sensor sensor = new Sensor();
                sensor.setId(new SensorId(id, parameter.getId(), time));
                sensor.setParameterValue(parameter.getValue());
                sensor.setContainer(container);
                var parameterDb = Mappers.PARAMETERS_MAPPER.mapFromDtoToParameter(parameter);
                sensor.setParameter(parameterDb);
                sensors.add(sensor);
            }
        }
        return sensors;
    }
}
