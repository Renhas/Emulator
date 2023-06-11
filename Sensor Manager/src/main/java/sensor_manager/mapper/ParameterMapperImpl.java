package sensor_manager.mapper;

import sensor_manager.dto.ParameterDto;
import sensor_manager.models.Parameter;
import sensor_manager.models.Sensor;

public class ParameterMapperImpl implements ParameterMapper {
    @Override
    public ParameterDto mapToDto(Sensor sensor) {
        if (sensor == null) return null;

        ParameterDto parameterDto = new ParameterDto();
        parameterDto.setId(sensor.getId().getParameterId());
        parameterDto.setName(sensor.getParameter().getName());
        parameterDto.setValue(sensor.getParameterValue());
        return parameterDto;
    }

    @Override
    public Parameter mapFromDtoToParameter(ParameterDto parameterDto) {
        if (parameterDto == null) return null;

        Parameter parameter = new Parameter();
        parameter.setId(parameterDto.getId());
        parameter.setName(parameterDto.getName());

        return parameter;
    }
}
