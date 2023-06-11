package emulator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import emulator.data.Container;
import emulator.generators.ContainerGenerator;
import emulator.settings.Settings;
import emulator.exceptions.AttributeException;
import emulator.files_manager.FileWork;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SensorsEmulator {
    private ContainerGenerator containerGenerator;
    private FileWork fileWork;
    public SensorsEmulator(ContainerGenerator containerGenerator,
                           FileWork fileWork) {
        this.containerGenerator = containerGenerator;
        this.fileWork = fileWork;
    }
    public void emulate()
            throws AttributeException, IOException, InterruptedException {
        var containers = containerGenerator.generateContainers();
        generateFiles(containers);
    }

    private void generateFiles(List<Container> containers)
            throws IOException, AttributeException, InterruptedException {
        var time = Settings.start_time;
        var max_time = findMaxTime(containers);
        while (time.isBefore(max_time)) {
            var end_time = time.plusSeconds(Settings.interval);
            formFile(formContainers(containers, time, end_time));
            time = end_time;
        }
    }

    private LocalDateTime findMaxTime(List<Container> containers) {
        LocalDateTime max = LocalDateTime.MIN;
        for (var container: containers) {
            var times = container.getParameters().keySet();
            var max_time = times.stream().max(LocalDateTime::compareTo).get();
            if (max.isBefore(max_time)) {
                max = max_time;
            }
        }
        return max;
    }

    private void formFile(List<Container> containers) throws IOException, InterruptedException {
        String jsonString = Container.toJson(containers);
        fileWork.save(jsonString);
        fileWork.sendFile();
        Thread.sleep(Settings.interval * 1000L);
    }



    private ArrayList<Container> formContainers(List<Container> containers,
                                                       LocalDateTime start_time,
                                                       LocalDateTime end_time)
            throws AttributeException {
        ArrayList<Container> result = new ArrayList<>();

        for (var container: containers) {
            result.add(formContainer(container, start_time, end_time));
        }
        return result;
    }

    private Container formContainer(Container container, LocalDateTime start_time,
                                           LocalDateTime end_time)
            throws AttributeException {
        Container result = new Container(container.id, container.name);
        for (var item: container.getParameters().entrySet()) {
            var key = item.getKey();
            var value = item.getValue();
            if (key.isBefore(end_time) && key.isAfter(start_time)) {
                result.addParameters(key, value);
            }
        }
        return result;
    }


}
