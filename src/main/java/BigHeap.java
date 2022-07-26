import java.util.ArrayList;

public class BigHeap {
    ArrayList<Integer> nodes;
    int size;

    public int getMax() {//保证调用的时候size大于0
        return nodes.get(0);
    }

    public int pop() {//保证调用的时候size大于0
        int ans = nodes.get(0);
        //把最后一个元素放到第一个来
        if (size == 1) {
            nodes.remove(0);
            size--;
            System.out.println("取出元素为:" + ans);
            System.out.println("大顶堆:" + nodes);
            System.out.println();
            return ans;
        }
        nodes.set(0, nodes.get(size - 1));
        nodes.remove(size - 1);
        size--;
        //下沉
        int p = 0;
        int pNum = nodes.get(0);
        if (ans == 79 && size == 23) {
            System.out.println("---------------------------------------------------------------------------------------");
        }
        while (true) {
            if (ans == 79 && size == 23) {
                System.out.println("进入一趟，p为:" + p + " pNum为:" + pNum);
            }
            if (2 * p + 2 < size) {
                int left = nodes.get(2 * p + 1);
                int right = nodes.get(2 * p + 2);
                if (pNum >= left && pNum >= right) break;
                if (left >= right) {
                    nodes.set(p, left);
                    nodes.set(2 * p + 1, pNum);
                    p = 2 * p + 1;
                } else {
                    nodes.set(p, right);
                    nodes.set(2 * p + 2, pNum);
                    p = 2 * p + 2;
                }
            } else if (2 * p + 1 < size) {
                int left = nodes.get(2 * p + 1);
                if (pNum >= left) break;
                nodes.set(p, left);
                nodes.set(2 * p + 1, pNum);
                p = 2 * p + 1;
            } else {
                break;
            }
        }
        if (ans == 79 && size == 23) {
            System.out.println("---------------------------------------------------------------------------------------");
        }
        System.out.println("取出元素为:" + ans);
        System.out.println("大顶堆:" + nodes);
        System.out.println();
        return ans;
    }

    public void push(int elem) {
        nodes.add(elem);
        size++;
        int p = size - 1;
        int pNum = elem;
        while (true) {
            if (p == 0) break;
            int parent = p / 2;
            if (p % 2 == 0) parent -= 1;
            int parentNum = nodes.get(parent);
            if (pNum <= parentNum) break;
            nodes.set(parent, pNum);
            nodes.set(p, parentNum);
            p = parent;
        }
        System.out.println("插入元素:" + elem);
        System.out.println("大顶堆:" + nodes);
        System.out.println();
    }

    public BigHeap() {
        nodes = new ArrayList<>();
        size = 0;
    }
}
