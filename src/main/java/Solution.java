import sun.plugin.javascript.navig.LinkArray;

import java.util.*;

public class Solution {

    public int smallestRangeII(int[] nums, int k) {
        Arrays.sort(nums);
        int n=nums.length;
        int ans=Integer.MAX_VALUE;
        for (int i = 0; i < n-1; i++) {
            int min=Math.min(nums[0]+k,nums[i+1]-k);
            int max=Math.max(nums[i]+k,nums[n-1]-k);
            ans=max-min<ans?max-min:ans;
        }
        ans=nums[n-1]-nums[0]<ans?nums[n-1]-nums[0]:ans;
        return ans;
    }





}
