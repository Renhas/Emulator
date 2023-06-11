package emulator;

import com.fasterxml.jackson.core.JsonProcessingException;
import emulator.generators.ContainerGenerator;
import emulator.generators.ParameterGenerator;
import emulator.generators.TimeGenerator;
import emulator.exceptions.AttributeException;
import emulator.files_manager.FileWork;
import emulator.service.ManagerWork;
import org.json.simple.parser.ParseException;
import emulator.service.SensorsEmulator;
import emulator.settings.Settings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args)
            throws AttributeException, IOException, InterruptedException {
        LoadSettings();
        SensorsEmulator emulator = new SensorsEmulator(
                new ContainerGenerator(new ParameterGenerator(), new TimeGenerator()),
                new FileWork(Settings.results_file_name + ".json", new ManagerWork("http://localhost:8080/files")));
        emulator.emulate();
    }

    private static void LoadSettings() {
        try (FileReader reader = new FileReader("settings.json")) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            while (bufferedReader.ready()) {
                builder.append(bufferedReader.readLine()).append("\n");
            }
            Settings.fromJson(builder.toString());

        } catch (IOException | ParseException | AttributeException e) {
            e.printStackTrace();
        }
    }
}
