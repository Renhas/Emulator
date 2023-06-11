package sensor_manager.files;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sensor_manager.dto.ContainerDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class FileParser {

    public List<ContainerDto> parseFile(MultipartFile file) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            StringBuilder builder = new StringBuilder();
            while(reader.ready()) {
                builder.append(reader.readLine()).append('\n');
            }
            return parseJson(builder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ContainerDto> parseJson(String jsonString) throws JsonProcessingException, JSONException {
        List<ContainerDto> containers = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        JSONArray array = new JSONArray(jsonString);
        for(int i = 0; i < array.length(); i++) {
            ContainerDto containerDto = mapper.readValue(array.get(i).toString(), ContainerDto.class);
            containers.add(containerDto);
        }
        return containers;
    }
}
