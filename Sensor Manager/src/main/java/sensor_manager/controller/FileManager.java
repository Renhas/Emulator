package sensor_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sensor_manager.exceptions.SaveException;
import sensor_manager.files.FileWork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileManager {
    private FileWork fileWork;
    @Autowired
    public FileManager(FileWork fileWork)
    {
        this.fileWork = fileWork;
    }
    @PostMapping("${app.sensor-manager-url}")
    public ResponseEntity<String> claimFile(@RequestParam("file") MultipartFile file) {
        try {
            fileWork.send(file);
        } catch (SaveException e) {
            return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
