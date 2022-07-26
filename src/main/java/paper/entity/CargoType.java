package paper.entity;

public enum CargoType {
    A(1),
    B(2),
    C(3);

    private int type;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    CargoType(int type) {
        this.type = type;
    }
}
