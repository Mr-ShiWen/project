package paper;

import paper.entity.Cargo;
import paper.entity.Individual;
import paper.geneticOperator.Crossover;
import paper.parameter.Location;
import paper.parameter.Parameter;
import paper.util.FitnessUtil;

import java.util.*;

public class StorageAllocation {

    public List<Individual> getAllocations(List<Cargo> list) {
        List<Integer> freeLocation = getFreeLocation();
        //获取初始化种群,设置适应值
        List<Individual> population = getInitPopulation(list,Parameter.populationSize, list.size(), freeLocation);
        //迭代
        int count = 0;
        while (count < Parameter.maxGenTime && isQualified(list, population)) {
            //交叉变异
            cross_variation(list,population);
        }

        return null;
    }

    /**
     * 交叉变异，融合了小生境技术
     * @param list
     * @param population
     */
    private void cross_variation(List<Cargo> list,List<Individual> population){
        int c=10;
        int w=10;

        Random random=new Random();
        double allFitness=0;
        double maxFitness=0;
        double avgFitness=0;
        for (Individual individual : population) {
            allFitness+=individual.getFitness();
            maxFitness=Math.max(maxFitness,individual.getFitness());
        }
        avgFitness=allFitness/population.size();

        for (int i = 0; i < population.size() / 2; i++) {
            //轮盘赌算法选择p1
            Individual p1=getIndividualIndex_Roulette(population,allFitness,random);
            //轮盘赌选取c个个体，取最靠近p1的个体为p2
            Individual p2=getIndividualIndex_Roulette(population,allFitness,random);
            for (int j = 0; j < c; j++) {
                Individual p=getIndividualIndex_Roulette(population,allFitness,random);
                if(p!=p1&&getDistanceOfIndividual(p1,p)<getDistanceOfIndividual(p1,p2)){
                    p2=p;
                }
            }
            //自适应交叉
            double pc= Crossover.getProbability(maxFitness,avgFitness,Math.max(p1.getFitness(),p2.getFitness()));
            if(random.nextDouble()<=pc){
                Individual[] result = Crossover.crossOver(p1, p2);
                p1=result[0];
                p2=result[1];
            }
            //自适应

        }
    }


    /**
     * 计算变异概率
     * @param maxFitness 种群最大适应度
     * @param avgFitness 种群平均适应度
     * @param fitness 待变异个体的适应度
     * @return
     */
    private double getVariationProbability(double maxFitness, double avgFitness, double fitness){
        if(fitness<avgFitness) return Parameter.pm1;
        double pm1=Parameter.pm1;
        double pm2=Parameter.pm2;
        return pm1-(pm1-pm2)*(fitness-avgFitness)/(maxFitness-avgFitness);
    }

    private double getDistanceOfIndividual(Individual o1, Individual o2){
        int sum=0;
        int[] distribute1 = o1.getDistribute();
        int[] distribute2 = o2.getDistribute();
        for (int i = 0; i < distribute1.length; i++) {
            int d=distribute1[i]-distribute2[i];
            sum+=d*d;
        }
        return Math.sqrt(sum);
    }

    /**
     * 轮盘赌算法选择个体
     * @param population
     * @param allFitness
     * @param random
     * @return
     */
    private Individual getIndividualIndex_Roulette(List<Individual> population, double allFitness, Random random){
        double need=allFitness*random.nextDouble();
        int index=-1;
        int fitness=0;
        while (fitness<need&&index<population.size()-1){
            index++;
            fitness+=population.get(index).getFitness();
        }
        if(index==-1) return population.get(random.nextInt(population.size()));
        return population.get(index);
    }

    /**
     * 种群适应度是否达到要求
     *
     * @param list
     * @param population
     * @return
     */
    private boolean isQualified(List<Cargo> list, List<Individual> population) {
        for (Individual individual : population) {
            if (individual.getFitness() < Parameter.TerminationFitness) return false;
        }
        return true;
    }

    /**
     * 获取编号对应的坐标，三个方向坐标从0开始
     *
     * @param number
     * @return
     */
    private int[] getCoordinate(int number) {
        int[] ans = Location.map.getOrDefault(number, null);
        if (ans != null) return ans;

        int row = Location.row;
        int col = Location.col;
        int lay = Location.lay;

        int count_Row = number / (col * lay);//排数
        int remain = number % (col * lay);
        if (remain == 0) {
            ans[0] = count_Row - 1;
            ans[1] = col - 1;
            ans[2] = lay - 1;
        } else {
            int count_col = remain / lay;//列数
            int count_lay = remain % lay; //层数
            if (count_lay == 0) {
                ans[0] = count_Row;
                ans[1] = count_col - 1;
                ans[2] = lay - 1;
            } else {
                ans[0] = count_Row;
                ans[1] = count_col;
                ans[2] = count_lay - 1;
            }
        }
        Location.map.put(number, ans);
        return ans;
    }


    /**
     * 获取初始种群
     *
     * @param populationSize   种群大小
     * @param individualLength 个体长度
     * @param freeLocation     空闲库位
     * @return
     */
    private List<Individual> getInitPopulation(List<Cargo> list,int populationSize, int individualLength, List<Integer> freeLocation) {
        if (individualLength > freeLocation.size()) return null; //null表示种群初始化失败

        List<Individual> ans = new ArrayList<>();
        while (ans.size() < populationSize) {//产生一个个体
            int[] distribute = new int[individualLength];

            boolean[] used = new boolean[freeLocation.size()];//记录使用情况，以做去重处理
            Random random = new Random();

            int locSize = freeLocation.size();

            for (int i = 0; i < distribute.length; i++) {
                int index = random.nextInt(locSize);
                while (used[index]) {
                    index = random.nextInt(locSize);
                }
                distribute[i] = freeLocation.get(index);
                used[index] = true;
            }

            Individual individual=new Individual(distribute);
            individual.setFitness(FitnessUtil.getFitnessValue(list,individual));//设置个体适应度
            ans.add(individual);
        }
        return ans;
    }

    /**
     * 返回空闲库位，每个库位用一个编号表示，编号从1开始
     *
     * @return
     */
    private List<Integer> getFreeLocation() {
        int row = Location.row;
        int col = Location.col;
        int lay = Location.lay;

        int count = row * col * lay;
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= col; i++) {
            list.add(i);
        }
        return list;
    }

}
