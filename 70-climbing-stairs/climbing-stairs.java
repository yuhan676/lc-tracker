class Solution {
    public int climbStairs(int n) {
        int[] ways = new int[2];
        int temp;
        //站在第0格只有一种方式：不走
        ways[0] = 1;
        //站在第1格只有一种方式：从0爬一格
        ways[1] = 1;
        for (int i =2; i<=n; i++){
            temp = ways[0] + ways[1];
            ways[0] = ways[1];
            ways[1] = temp;
        }
        return ways[1];

    }
}