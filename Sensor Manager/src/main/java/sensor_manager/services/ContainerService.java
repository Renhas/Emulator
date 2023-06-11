package sensor_manager.services;

import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import sensor_manager.exceptions.SaveException;
import sensor_manager.models.Container;
import sensor_manager.repository.ContainerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContainerService {
    private ContainerRepository containerRepository;

    @Autowired
    public ContainerService(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    public void save(Container container) throws SaveException {
        var existed = containerRepository.findById(container.getId());
        if (existed.isEmpty()) {
            containerRepository.saveAndFlush(container);
        } else {
            throw new SaveException("Container already exists!");
        }
    }

    public List<Container> loadAll() {
        return containerRepository.findAll();
    }

    public List<Container> loadPage(@Positive int page, @Positive int size) {
        Pageable pageable = PageRequest.of(page, size);
        return containerRepository.findAll(pageable).getContent();
    }

    public List<Container> loadPageBetween(LocalDateTime start, LocalDateTime end,
                                           @Positive int page, @Positive int size) {
        Pageable pageable = PageRequest.of(page, size);
        return containerRepository.findAllBySensorsIdReadingTimeBetween(start, end, pageable);

    }

}
