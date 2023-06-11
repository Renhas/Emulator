package emulator.generators;

import emulator.data.Container;
import emulator.settings.Settings;
import emulator.exceptions.AttributeException;

import java.util.ArrayList;
import java.util.List;

public class ContainerGenerator {

    ParameterGenerator parameterGenerator;
    TimeGenerator timeGenerator;
    public ContainerGenerator(ParameterGenerator parameterGenerator,
                              TimeGenerator timeGenerator) {
        this.parameterGenerator = parameterGenerator;
        this.timeGenerator = timeGenerator;
    }

    public List<Container> generateContainers() throws AttributeException {
        var count = Settings.number_of_containers;
        ArrayList<Container> containers = new ArrayList<>();
        for(int i = 1; i <= count; i++) {
            Container container = new Container(i, String.format("Контейнер #%d", i));
            oneContainer(container);
            containers.add(container);
        }
        return containers;
    }

    private void oneContainer(Container container) throws AttributeException {
        var steps_count = Settings.emulation_step_count;

        for(int i = 0; i < steps_count; i++) {
            var time = timeGenerator.generate(i);
            var parameters = parameterGenerator.generate();
            container.addParameters(time, parameters);
        }
    }
}
