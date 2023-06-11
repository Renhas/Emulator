package emulator.settings;

import emulator.exceptions.AttributeException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Settings {
    public static String results_file_name;
    public static int number_of_containers;
    public static LocalDateTime start_time;
    public static double max_shift;
    public static double step_size;
    public static int emulation_step_count;
    public static int interval;
    public static ArrayList<ParameterSettings> parameters;

    public static void fromJson(String jsonString) throws ParseException, AttributeException {
        JSONParser jsonParser = new JSONParser();
        JSONObject root = (JSONObject) jsonParser.parse(jsonString);

        setFromJson(root);

        parameters = new ArrayList<ParameterSettings>();
        JSONArray parameters = (JSONArray) root.get("parameters");
        for (var object: parameters) {
            addParameter((JSONObject) object);
        }

    }

    private static void addParameter(JSONObject object) throws ParseException, AttributeException {
        var parameter = ParameterSettings.fromJson(object.toJSONString());
        parameters.add(parameter);
    }

    private static void setFromJson(JSONObject root) {
        results_file_name = (String) root.get("results_file_name");
        number_of_containers = ((Long) root.get("number_of_containers")).intValue();
        start_time = LocalDateTime.parse((String)root.get("start_time"));
        max_shift = (double) root.get("max_shift");
        step_size = (double) root.get("step_size");
        emulation_step_count = ((Long) root.get("emulation_step_count")).intValue();
        interval = ((Long) root.get("interval")).intValue();
    }
}
