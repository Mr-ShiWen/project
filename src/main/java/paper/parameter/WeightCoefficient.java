package paper.parameter;

public enum WeightCoefficient {
    a1(0.3f),
    a2(0.4f),
    a3(0.3f);

    private float coeff;

    public float getCoeff() {
        return coeff;
    }

    public void setCoeff(float coeff) {
        this.coeff = coeff;
    }

    WeightCoefficient(float coeff) {
        this.coeff = coeff;
    }
}
