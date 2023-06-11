package emulator.generators;

import emulator.settings.Settings;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TimeGenerator {
    Random rand;

    public TimeGenerator(Random rand) {
        this.rand = rand;
    }

    public TimeGenerator() {
        this.rand = new Random();
    }

    public LocalDateTime generate(int i) {
        var time = Settings.start_time;
        var sec_to_nano = TimeUnit.SECONDS.toNanos(1);
        var step = i * Settings.step_size;
        step += rand.nextDouble(Settings.max_shift);
        return time.plusNanos(Math.round(step * sec_to_nano));
    }
}
