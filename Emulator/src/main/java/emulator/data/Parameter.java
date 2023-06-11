package emulator.data;

import emulator.exceptions.AttributeException;

public class Parameter {
    public final int id;
    public final String name;
    private double value;

    public Parameter(int id, String name, double value) throws AttributeException {
        if (id < 0) {
            throw new AttributeException("Id must be >= 0");
        }
        this.id = id;
        this.name = name;
        setValue(value);
    }

    public Parameter() throws AttributeException {
        this(0, "None", 0);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("#%d %s: %f ", id, name, value);
    }
}
