package paper.entity;

public class Cargo {
    private CargoType type;
    private int weight;

    public CargoType getType() {
        return type;
    }

    public void setType(CargoType type) {
        this.type = type;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Cargo(CargoType type, int count) {
        this.type = type;
        this.weight = count;
    }
}
