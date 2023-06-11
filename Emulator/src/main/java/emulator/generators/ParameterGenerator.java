package emulator.generators;

import emulator.data.Parameter;
import emulator.settings.Settings;
import emulator.exceptions.AttributeException;

import java.util.ArrayList;
import java.util.Random;

public class ParameterGenerator {

    Random rand;
    public ParameterGenerator(Random rand) {
        this.rand = rand;
    }
    public ParameterGenerator() {
        rand = new Random();
    }
    public ArrayList<Parameter> generate() throws AttributeException {
        ArrayList<Parameter> parameters = new ArrayList<>();
        for (var setting: Settings.parameters) {
            var value = setting.avg + rand.nextDouble(-setting.std_dev, setting.std_dev);
            Parameter parameter = new Parameter(setting.id, setting.name, value);
            parameters.add(parameter);
        }
        return parameters;
    }
}
