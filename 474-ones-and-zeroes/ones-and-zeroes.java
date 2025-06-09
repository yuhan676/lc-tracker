class Solution {
    Map<String, Integer> memo = new HashMap<>();

    public int findMaxForm(String[] strs, int m, int n) {
        return(largestSubSet(strs,0,m,n));
    }

    public int largestSubSet(String[] strs, int index, int remaining0, int remaining1){
        String key = index + "," + remaining0 + "," + remaining1;
        if (memo.containsKey(key)) return memo.get(key);
        //终止条件：遍历到最后一个物品以后了，没有任何0或者1可以加入，只能返回0=subset大小
        if (index >= strs.length) return 0;

        int[] count = countZeroOne(strs[index]);
        int zerocount = count[0];
        int onecount = count[1];

        
        int take = 0;
        int notTake = largestSubSet(strs,index+1,remaining0,remaining1);
        if (remaining0 >= zerocount && remaining1 >= onecount){
            //注意take得increment一个1
            take = 1 + largestSubSet(strs,index+1,remaining0-zerocount,remaining1-onecount);
        }
        int maxSubset = Math.max(take,notTake);
        memo.put(key,maxSubset);
        return maxSubset;
    }

    private int[] countZeroOne(String s){
        int zeros = 0;
        int ones = 0;
        for (char c: s.toCharArray()){
            if (c=='0') zeros++;
            else ones++;
        }
        return new int[] {zeros, ones};
    }
}