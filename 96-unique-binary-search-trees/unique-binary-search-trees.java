class Solution {
    Map<Integer, Integer> memo = new HashMap<>();

    public int numTrees(int n) {
        return getBstNum(n);
    }

    private int getBstNum(int n){
        if (n == 0 || n ==1) return 1;
        if (memo.containsKey(n)) return memo.get(n);
        int res = 0;
        //遍历所有头节点可能性
        for (int i =1; i<= n;i++){
            //每个头节点的可能性 = 左边数字排布组合数 * 右边数字排布组合数
            int left = getBstNum(i-1);
            int right = getBstNum(n-i);
            //累加上左右组合数的乘积
            res += left * right;
        }
        memo.put(n, res);
        return res;
    }

}