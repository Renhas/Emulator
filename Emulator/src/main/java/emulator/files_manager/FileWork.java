package emulator.files_manager;

import emulator.service.ManagerWork;
import emulator.settings.Settings;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class FileWork {
    String path;
    ManagerWork managerWork;
    public FileWork(String path, ManagerWork managerWork) {
        this.path = path;
        this.managerWork = managerWork;
    }

    public void save(String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(text);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File load() {
        return new File(path);
    }

    public void sendFile() throws IOException {
        File file = load();
        MultipartFile mpfile = new MockMultipartFile("file", file.getName(),
                "text/plain", IOUtils.toByteArray(new FileInputStream(file)));
        managerWork.send(file);
    }
}
