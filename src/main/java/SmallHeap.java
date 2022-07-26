import java.util.ArrayList;

public class SmallHeap {
    ArrayList<Integer> nodes;
    int size;

    public int getMin(){//保证调用的时候size大于0
        return  nodes.get(0);
    }

    public int pop() {//保证调用的时候size大于0
        int ans=nodes.get(0);
        if(size==1){
            nodes.remove(0);
            size--;
            return ans;
        }
        //把最后一个放到首位置
        nodes.set(0,nodes.get(size-1));
        nodes.remove(size-1);
        size--;

        int p=0;
        int pNum=nodes.get(0);
        while (true){
            if(2*p+2<size){//有左右孩子
                int left=nodes.get(2*p+1);
                int right=nodes.get(2*p+2);
                if(pNum<=left&&pNum<=right) break;
                if(left<=right){
                    nodes.set(p,left);
                    nodes.set(2*p+1,pNum);
                    p=2*p+1;
                }else {
                    nodes.set(p,right);
                    nodes.set(2*p+2,pNum);
                    p=2*p+2;
                }
            }else if(2*p+1<size){//只有左孩子
                int left=nodes.get(2*p+1);
                if(pNum<=left) break;
                nodes.set(p,left);
                nodes.set(2*p+1,pNum);
                p=2*p+1;
            }else {//无孩子
                break;
            }
        }
        return ans;
    }

    public void push(int elem){
        nodes.add(elem);
        size++;
        int p=size-1;
        int pNum=elem;
        while (true){
           if(p==0) break;
           int parent=p/2;
           if(p%2==0) parent-=1;
           int parentNum=nodes.get(parent);
           if(parentNum<=pNum) break;
           nodes.set(p,parentNum);
           nodes.set(parent,pNum);
           p=parent;
        }
    }

    public SmallHeap(){
        nodes=new ArrayList<>();
        size=0;
    }
}
