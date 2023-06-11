package sensor_manager.mapper;

import sensor_manager.dto.ContainerDto;
import sensor_manager.models.Container;
import sensor_manager.models.Sensor;
import org.mapstruct.*;

import java.util.List;

@Mapper
@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG
)
public interface ContainerMapper {

    ContainerDto mapToDto(Container container);

    Container mapFromDtoToContainer(ContainerDto containerDto);

    List<Sensor> mapFromDtoToSensor(ContainerDto containerDto);
}
