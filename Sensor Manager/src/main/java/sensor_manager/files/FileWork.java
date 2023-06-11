package sensor_manager.files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sensor_manager.dto.ContainerDto;
import sensor_manager.dto.ParameterDto;
import sensor_manager.exceptions.SaveException;
import sensor_manager.mapper.Mappers;
import sensor_manager.models.Container;
import sensor_manager.models.Sensor;
import sensor_manager.services.KafkaService;

import java.util.List;

@Component
public class FileWork {

    private final KafkaService kafkaService;
    private final FileParser fileParser;

    @Autowired
    public FileWork(KafkaService kafkaService,
                    FileParser fileParser) {
        this.kafkaService = kafkaService;
        this.fileParser = fileParser;
    }
    public void send(MultipartFile file) throws SaveException {
        List<ContainerDto> containers = fileParser.parseFile(file);
        if (containers == null) {
            throw new SaveException("Unable to save file");
        }
        sendContainers(containers);
    }


    private void sendContainers(List<ContainerDto> containers) {
        for (var container: containers) {
            sendContainer(container);
        }
    }

    private void sendContainer(ContainerDto containerDto) {
        Container container = Mappers.CONTAINER_MAPPER.mapFromDtoToContainer(containerDto);
        kafkaService.sendContainer(container);

        for (var item: containerDto.getParameters().entrySet()) {
            sendParameters(item.getValue());
        }
        sendSensors(Mappers.CONTAINER_MAPPER.mapFromDtoToSensor(containerDto));
    }

    private void sendParameters(List<ParameterDto> parameters) {
        for (var parameterDto: parameters) {
            var parameter = Mappers.PARAMETERS_MAPPER.mapFromDtoToParameter(parameterDto);
            kafkaService.sendParameter(parameter);
        }
    }
    private void sendSensors(List<Sensor> sensors) {
        for(var sensor: sensors) {
            kafkaService.sendSensor(sensor);
        }
    }
}
