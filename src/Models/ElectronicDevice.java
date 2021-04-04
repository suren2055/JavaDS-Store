package Models;

public class ElectronicDevice extends Product implements IDevice {

    private String Model;
    private String Brand;

    public ElectronicDevice(String model, String brand) {
        Model = model;
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public String getBrand() {
        return Brand;
    }

    public void print() {

        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Models.ElectronicDevice{" +
                "Model='" + Model + '\'' +
                ", Brand='" + Brand + '\'' +
                '}'+"-"+super.toString();
    }

}
