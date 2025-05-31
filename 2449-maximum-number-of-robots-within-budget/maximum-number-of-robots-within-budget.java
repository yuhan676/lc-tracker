class Solution {
    public int maximumRobots(int[] chargeTimes, int[] runningCosts, long budget) {
        //一共有n个robot可供选择
        int n = chargeTimes.length;
        //滑动窗口+treemap
        //storing chargeTimes, frequency
        //因为我们要维护一个窗口内的最大chargetime
        TreeMap<Integer, Integer> map = new TreeMap<>();
        long rcsum = 0;
        int left = 0, maxRobots = 0;
        for (int right = 0; right <n; right ++){
            //get the new Element's chargetime & running cost
            int ct = chargeTimes[right];
            int rc = runningCosts[right];

            //add it to the sliding window
            //frequency ++
            map.put(ct, map.getOrDefault(ct,0) + 1);
            //rcsum also increment
            rcsum += rc;
            int windowSize = right - left + 1;
            //treemap 特有的lastkey()得到最大的key
            //O(logn)
            long totalCost = map.lastKey() + windowSize * rcsum;
            //如果超了budget就要从左边缩小window
            while (totalCost > budget){
                int leftCt = chargeTimes[left];
                int leftRc = runningCosts[left];

                //从窗口中删除left
                //之前已经放进去过，就不要get or default 了
                //frequency --
                map.put(leftCt, map.get(leftCt)-1);
                //清零了就删除
                if (map.get(leftCt)==0){
                    map.remove(leftCt);
                }
                rcsum -= leftRc;
                left ++;

                windowSize = right - left + 1;
                if (!map.isEmpty()){
                   totalCost = map.lastKey() + windowSize * rcsum;
                }else{
                    totalCost = 0;
                }
                
            }
            maxRobots = Math.max(maxRobots, windowSize);
        }
        return maxRobots;
    }
}