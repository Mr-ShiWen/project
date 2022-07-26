public class RLEIterator {
    private int[] encoding;
    private int p;//p指向一个偶数index，remain表示该index对应的数字剩余数目，remain<=encoding[p]
    private int remain;

    public RLEIterator(int[] encoding) {
        this.encoding=encoding;
        p=0;
        remain=encoding[0];
    }

    public int next(int n) {
        int t=p;
        int sum=remain;
        while (sum<n&&t+2<encoding.length){
            t+=2;
            sum+=encoding[t];
        }
        if(sum<n){//不够
            p=encoding.length-2;
            remain=0;
            return -1;
        }else {//够
            p=t;
            remain=sum-n;
            return encoding[p+1];
        }
    }

}
