package paper.geneticOperator;

import paper.entity.Individual;
import paper.parameter.Parameter;

public class Crossover {

    /**
     * 计算交叉概率
     * @param maxFitness 种群最大适应度
     * @param avgFitness 种群平均适应度
     * @param fitness 待交叉两个个体中的较大适应度
     * @return
     */
    public static double getProbability(double maxFitness, double avgFitness, double fitness){
        if(fitness<avgFitness) return Parameter.pc1;
        double pc1=Parameter.pc1;
        double pc2=Parameter.pc2;
        return pc1-(pc1-pc2)*(fitness-avgFitness)/(maxFitness-avgFitness);
    }

    /**
     * 自适应
     * @param o1
     * @param o2
     * @return
     */
    public static Individual[] crossOver(Individual o1, Individual o2){
        return null;
    }
}
