package sensor_manager.mapper;

public class Mappers {

    private Mappers() {

    }

    public static final ContainerMapper CONTAINER_MAPPER = new ContainerMapperImpl();
    public static final ParameterMapper PARAMETERS_MAPPER = new ParameterMapperImpl();

}
