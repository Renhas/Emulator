package emulator.settings;

import emulator.exceptions.AttributeException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParameterSettings {
    public final int id;
    public final String name;
    public final double avg;
    public final double std_dev;

    public ParameterSettings(int id, String name, double avg, double std_dev)
            throws AttributeException {
        if (id < 0) {
            throw new AttributeException("Id must be >= 0");
        }
        this.id = id;
        this.name = name;
        this.avg = avg;
        this.std_dev = std_dev;
    }

    public static ParameterSettings fromJson(String jsonString) throws ParseException, AttributeException {
        JSONParser jsonParser = new JSONParser();
        JSONObject root = (JSONObject) jsonParser.parse(jsonString);

        int id = ((Long) root.get("id")).intValue();
        String name = (String) root.get("name");
        double avg = (double) root.get("avg");
        double std_dev = (double) root.get("std_dev");
        return new ParameterSettings(id, name, avg, std_dev);
    }
}
