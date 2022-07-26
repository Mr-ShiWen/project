package paper.entity;

public class Individual implements Comparable<Individual>{
    private int[] distribute;
    private double fitness;

    public int[] getDistribute() {
        return distribute;
    }

    public void setDistribute(int[] distribute) {
        this.distribute = distribute;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public Individual(int[] distribution) {
        this.distribute = distribution;
    }

    @Override
    public int compareTo(Individual o) {
        double result=this.fitness-o.getFitness();
        if(result==0) return 0;
        else if(result>0) return 1;
        else return -1;
    }
}
