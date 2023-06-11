package emulator.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import emulator.exceptions.AttributeException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Container implements Serializable {
    public final int id;
    public final String name;
    private final Map<LocalDateTime, ArrayList<Parameter>> parameters;

    public Container(int id, String name) throws AttributeException{
        if (id < 0) {
            throw new AttributeException("Id must be >= 0");
        }
        this.id = id;
        this.name = name;
        this.parameters = new HashMap<LocalDateTime, ArrayList<Parameter>>();
    }

    public Container() throws AttributeException {
        this(0, "None");
    }

    public Map<LocalDateTime, ArrayList<Parameter>> getParameters() {
        return Map.copyOf(parameters);
    }

    public void addParameters(LocalDateTime dateTime, ArrayList<Parameter> parameters) {
        if (this.parameters.containsKey(dateTime)) {
            this.parameters.get(dateTime).addAll(parameters);
        } else {
            this.parameters.put(dateTime, parameters);
        }
    }

    public void addParameters(LocalDateTime dateTime, Parameter parameter) {
        addParameters(dateTime,
                new ArrayList<Parameter>(Collections.singletonList(parameter)));
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

    public static String toJson(List<Container> containers) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(containers);
    }

    public static Container fromJson(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return mapper.readValue(jsonString, Container.class);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("#%d %s\n", id, name));
        builder.append("Parameters:\n");
        for (var item: parameters.entrySet()) {
            var key = item.getKey();
            var value = item.getValue();
            builder.append(key);
            builder.append(": ");
            builder.append(value);
        }
        return builder.toString();
    }
}
