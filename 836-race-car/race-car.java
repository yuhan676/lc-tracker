class Solution {
    //走到target（key），最少需要memo.get(target)步
    Map<Integer, Integer> memo = new HashMap<>();

    public int racecar(int target) {
        return dp(target);
    }

    private int dp(int target){
        if (memo.containsKey(target)) return memo.get(target);
        //计算最少需要的步数k，所以走k步A的话，target<=distance <=target*2 -1
        int k = (int)Math.ceil(Math.log(target+1)/Math.log(2));
        //走了k步acceleration以后覆盖的距离
        int distPostK =(1<<k) -1;
        //第一种情况：走k步acceleration可以直接到target, target == 2^k
        //直接return步数k
        if (distPostK== target) return k;
        //第二种情况，走k步超过了target，这个时候走一步reverse，然后往回走(distPostk - target)步
        int res = k + 1 + dp(distPostK-target);
        //第三种情况（未到达target先reverse），走k-1步，reverse，走几步，reverse，再走几步
        //不知道往回走几步才是最优的，最少往回走0步，最多往回走k-1步，所以一个一个遍历
        for (int i = 0; i<k-1;i++){
            int forDist = (1<<(k-1)) -1;
            int backDist = (1<<i) -1;
            //向前走k-1, reverse一步，向后走i步，reverse一步，加上剩下路程的最小步数
            int stepsNeeded = k-1 + 1 + i + 1 + dp(target-forDist+backDist);
            //记得只有找到更小步数的时候才更新
            res = Math.min(res, stepsNeeded);
        }
        memo.put(target,res);
        return res;
    }
}