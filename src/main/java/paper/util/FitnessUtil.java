package paper.util;

import paper.entity.Cargo;
import paper.entity.CargoType;
import paper.entity.Individual;
import paper.parameter.Location;
import paper.parameter.WeightCoefficient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FitnessUtil {
    private static int g0 = -1;
    private static int distributeLength = -1;

    /**
     * 计算个体适应度值
     *
     * @param individual
     * @return
     */
    public static double getFitnessValue(List<Cargo> list, Individual individual) {
        int[] distribute=individual.getDistribute();
        int[][] coordinates = new int[distribute.length][];
        for (int i = 0; i < distribute.length; i++) {
            coordinates[i] = getCoordinate(distribute[i]);
        }

        double ans = WeightCoefficient.a1.getCoeff() * fitness_f1(coordinates) +
                WeightCoefficient.a2.getCoeff() * fitness_f2(list, coordinates) +
                WeightCoefficient.a3.getCoeff() * fitness_f3(list, coordinates);

        if (distributeLength != distribute.length) {
            g0 = getG0(distribute.length);
            distributeLength = distribute.length;
        }

        return g0 - ans;
    }

    /**
     * 计算进出货物时间
     *
     * @param coordinates
     * @return
     */
    private static int fitness_f1(int[][] coordinates) {
        Map<Integer, List<int[]>> map = new HashMap<>();//记录堆垛机到任务列表的映射
        for (int[] coordinate : coordinates) {
            int stacker = coordinate[0] / 2;
            List<int[]> workList = map.getOrDefault(stacker, null);
            if (workList == null) workList = new ArrayList<>();
            workList.add(coordinate);
            map.put(stacker, workList);
        }

        int ans = 0;
        for (List<int[]> workList : map.values()) {//计算每个堆垛机用时
            int time = 0;
            for (int[] coordinate : workList) {
                time += Math.max(coordinate[1], coordinate[2]);
            }
            ans = time > ans ? time : ans;
        }
        return ans;
    }

    /**
     * 计算货架重心
     *
     * @param list
     * @param coordinates
     * @return
     */
    private static double fitness_f2(List<Cargo> list, int[][] coordinates) {
        int weightHeight = 0;
        int weight = 0;

        for (int i = 0; i < coordinates.length; i++) {
            Cargo cargo = list.get(i);
            int[] coordinate = coordinates[i];
            weightHeight += cargo.getWeight() * coordinate[2];
            weight += cargo.getWeight();
        }

        if (weight == 0) return 0;
        return weightHeight / weight;
    }

    /**
     * 计算同类货物距离
     *
     * @param list
     * @param coordinates
     * @return
     */
    private static double fitness_f3(List<Cargo> list, int[][] coordinates) {
        Map<CargoType, List<Integer>> map = new HashMap<>();//记录<货物种类，index列表>之间的映射
        for (int i = 0; i < list.size(); i++) {
            CargoType type = list.get(i).getType();
            List<Integer> indexList = map.getOrDefault(type, null);
            if (indexList == null) indexList = new ArrayList<>();
            indexList.add(i);
            map.put(type, indexList);
        }

        //计算中心距离
        double ans = 0;

        for (List<Integer> indexList : map.values()) {
            //计算中心点
            int row_avg = 0;
            int col_avg = 0;
            int lay_avg = 0;
            for (Integer index : indexList) {
                int[] coordinate = coordinates[index];
                row_avg += coordinate[0];
                col_avg += coordinate[1];
                lay_avg += coordinate[2];
            }
            row_avg /= indexList.size();
            col_avg /= indexList.size();
            lay_avg /= indexList.size();

            //计算中心距离
            double center_dist = 0;
            for (Integer index : indexList) {
                int[] coordinate = coordinates[index];
                int x = coordinate[0] - row_avg;
                int y = coordinate[1] - col_avg;
                int z = coordinate[2] - lay_avg;
                center_dist += Math.sqrt(x * x + y * y + z * z);
            }

            ans += center_dist;
        }

        return ans;
    }

    /**
     * 获取编号对应的坐标，三个方向坐标从0开始
     *
     * @param number
     * @return
     */
    private static int[] getCoordinate(int number) {
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
     * 计算g0，是最大的进出货时间、最高的重心以及最大的同类货物距离之和。用于个体适应值计算。
     *
     * @param individualLength
     * @return
     */
    private static int getG0(int individualLength) {
        int row = Location.row;
        int col = Location.col;
        int lay = Location.lay;

        int maxTime = individualLength * Math.max(col, lay);//最大进出货时间
        int maxBaryCenter = individualLength * lay;//最大重心
        int maxDis = (int) Math.sqrt(row * row + col * col + lay * lay) * individualLength;//最大同类货物距离

        return maxTime + maxBaryCenter + maxDis;
    }
}
