package Models;

import java.util.Arrays;

public class Laptop extends ElectronicDevice{

    private String[] Ports;

    public Laptop(String brand, String model, String[] ports) {
        super(brand, model);
        Ports = ports;
    }

    @Override
    public String toString() {
        return "Models.Laptop{" +
                "Ports=" + Arrays.toString(Ports) +
                '}';
    }
}
