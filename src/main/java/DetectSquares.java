import java.util.*;

public class DetectSquares {
    private List<Integer>[] x_axis;
//    private Set<Integer>[] y_axis;
    private int[][] buckets;

    public DetectSquares() {
        x_axis=new ArrayList[1001];
//        y_axis=new HashSet[1001];
        buckets=new int[1001][1001];
    }

    public void add(int[] point) {
        int x=point[0];
        int y=point[1];
        if(x_axis[x]==null) x_axis[x]=new ArrayList<>();
        x_axis[x].add(y);

//        if(y_axis[y]==null) y_axis[y]=new HashSet<>();
//        y_axis[y].put(x,y_axis[y].getOrDefault(x,0)+1);
        buckets[x][y]++;
    }

    public int count(int[] point) {
        int x=point[0];
        int y=point[1];
        //x、y轴的点
        List<Integer> xList=x_axis[x];
        if(xList==null) return 0;

        int count=0;
        for (Integer yy : xList) {
            if(yy==y) continue;
            int len=Math.abs(yy-y);

            int left=x-len;
            if(left>=0){
                int leftC=buckets[left][y];
                int diagC=buckets[left][yy];
                count+=leftC*diagC;
            }
            int right=x+len;
            if(right<=1000){
                int rightC=buckets[right][y];
                int diagC=buckets[right][yy];
                count+=rightC*diagC;
            }
        }
        return count;
    }
}
