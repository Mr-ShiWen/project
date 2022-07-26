package paper.parameter;


public enum TurnoverRatio {
    A(0.3f),
    B(0.5f),
    C(0.2f);

    private float rate;

    TurnoverRatio(float rate) {
        this.rate=rate;
    }

    public float getRate() {
        return rate;
    }
}
