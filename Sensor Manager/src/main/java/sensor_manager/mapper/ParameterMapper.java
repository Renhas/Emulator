package sensor_manager.mapper;

import sensor_manager.dto.ParameterDto;
import sensor_manager.models.Parameter;
import sensor_manager.models.Sensor;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper
@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG
)
public interface ParameterMapper {

    ParameterDto mapToDto(Sensor sensor);
    Parameter mapFromDtoToParameter(ParameterDto parameterDto);

}
