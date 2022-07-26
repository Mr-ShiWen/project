import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Median {
    //状态：大小顶堆的元素个数（扣除延迟删除部分）之差的绝对值不超过1、延迟删除的元素不是堆顶元素
    private PriorityQueue<Integer> small;//大顶堆，存放较小元素
    private PriorityQueue<Integer> large;//小顶堆，存放较大元素
    private HashMap<Integer, Integer> deleteMap;//存放延迟删除的元素
    private int smallSize; //小元素集个数(扣除延迟删除个数)
    private int largeSize; //大元素集个数(扣除延迟删除个数)

    public Median() {
        small = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        large = new PriorityQueue<>();

        deleteMap = new HashMap<>();
        smallSize = 0;
        largeSize = 0;
    }

    /**
     * 保证至少一个元素，才会调用这个方法
     *
     * @return
     */
    public double getMedia() {
        if (smallSize == 0) {
            return large.peek();
        } else if (largeSize == 0) {
            return small.peek();
        } else if (smallSize > largeSize) {
            return small.peek();
        } else if (largeSize > smallSize) {
            return large.peek();
        } else {
            return ((long) small.peek() + large.peek()) / 2.0;
        }
    }

    /**
     * 加入元素
     *
     * @param elem
     */
    public void addElem(int elem) {
        if (smallSize != 0 && elem <= small.peek()) {
            small.offer(elem);
            smallSize++;
        } else {
            large.add(elem);
            largeSize++;
        }
        balance();
    }

    public void deleteElem(int elem) {
        if (smallSize != 0 && elem == small.peek()) {
            small.poll();
            smallSize--;
            deleteInvalid(small);
        } else if (largeSize != 0 && elem == large.peek()) {
            large.poll();
            largeSize--;
            deleteInvalid(large);
        } else if (smallSize != 0 && elem < small.peek()) {
            deleteMap.put(elem, deleteMap.getOrDefault(elem, 0) + 1);
            smallSize--;
        } else {
            deleteMap.put(elem, deleteMap.getOrDefault(elem, 0) + 1);
            largeSize--;
        }
        balance();
    }

    /**
     * 平衡元素集，状态是：堆顶不会是延迟删除元素、小大元素集元素个数之差绝对值不超过2
     */
    private void balance() {
        if (smallSize - largeSize == 2) {
            large.offer(small.poll());
            smallSize--;
            largeSize++;
            deleteInvalid(small);
        } else if (largeSize - smallSize == 2) {
            small.offer(large.poll());
            smallSize++;
            largeSize--;
            deleteInvalid(large);
        }
    }

    /**
     * 删除延迟删除的元素，状态是堆顶弹出一个有效元素后
     *
     * @param queue
     */
    private void deleteInvalid(PriorityQueue<Integer> queue) {
        while (queue.size() != 0) {
            int elem = queue.peek();
            int num = deleteMap.getOrDefault(elem, 0);
            if (num == 0) {
                break;
            }
            for (int i = 0; i < num; i++) {
                queue.poll();
            }
            deleteMap.remove(elem);
        }
    }
}
