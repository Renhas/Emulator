package sensor_manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sensor_manager.exceptions.SaveException;
import sensor_manager.models.Parameter;
import sensor_manager.repository.ParameterRepository;

@Service
public class ParameterService {
    private final ParameterRepository parameterRepository;

    @Autowired
    public ParameterService(ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }

    public void save(Parameter parameter) throws SaveException {
        var existed = parameterRepository.findById(parameter.getId());
        if (existed.isEmpty()) {
            parameterRepository.saveAndFlush(parameter);
        } else {
            throw new SaveException("Parameter already exists!");
        }
    }
}
