package sensor_manager.controller;

import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sensor_manager.dto.WebAnswer;
import sensor_manager.models.Sensor;
import sensor_manager.authorization.Authorization;
import sensor_manager.services.SensorService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
public class WebApi {
    private final SensorService sensorService;
    private final Authorization authorization;

    public WebApi(SensorService sensorService,
                  Authorization authorization) {
        this.sensorService = sensorService;
        this.authorization = authorization;
    }

    @GetMapping("${app.web-api-url}")
    public ResponseEntity<?> getContainers(@RequestParam("time_after") int timeAfter,
                                                @RequestParam("time_to") int timeTo,
                                                @RequestParam("string_for_page") @Positive int string_to_page,
                                                @RequestHeader("Authorization") String token) {
        var auth = authorization.Authorize(token);
        if (!auth.isAuthorized()) {
            return new ResponseEntity<>(auth, HttpStatus.UNAUTHORIZED);
        }
        var startTime = sensorService.getFirstTime();
        startTime = startTime.plusSeconds(timeAfter);
        var endTime = startTime.plusSeconds(timeTo);
        int page = 0;
        var sensors = sensorService.getBetween(startTime, endTime, page, string_to_page);

        List<WebAnswer> answers = createAnswers(startTime, page, sensors);
        while(!sensors.isEmpty()) {
            page++;
            sensors = sensorService.getBetween(startTime, endTime, page, string_to_page);
            answers.addAll(createAnswers(startTime, page, sensors));
        }

        return new ResponseEntity<>(answers, HttpStatus.OK);
    }

    private List<WebAnswer> createAnswers(LocalDateTime startTime, int page, List<Sensor> sensors) {
        List<WebAnswer> answers = new ArrayList<>();

        for (var sensor: sensors) {
            var answerBuilder = WebAnswer.builder();
            answerBuilder.page(page + 1).container(sensor.getId().getContainerId().intValue());
            answerBuilder.time(difference(startTime, sensor.getId().getReadingTime()));
            answerBuilder.par_value(sensor.getParameterValue());
            answerBuilder.par_name(sensor.getParameter().getName());
            answers.add(answerBuilder.build());
        }
        return answers;
    }

    private double difference(LocalDateTime first, LocalDateTime second) {
        double years = first.until(second, ChronoUnit.YEARS);
        double months = first.until(second, ChronoUnit.MONTHS);
        double days = first.until(second, ChronoUnit.DAYS);
        double hours = first.until(second, ChronoUnit.HOURS);
        double minutes = first.until(second, ChronoUnit.MINUTES);
        double milli = first.until(second, ChronoUnit.MILLIS);
        return milli / 1000 + minutes * 60 + hours * 3600 +
                days * 86400 + months * 2592000 + years * 31104000;
    }
}
