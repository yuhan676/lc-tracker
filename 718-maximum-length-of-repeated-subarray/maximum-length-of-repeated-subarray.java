class Solution {
    public int findLength(int[] nums1, int[] nums2) {
        int res = 0;
        int[][] dp = new int[nums1.length+1][nums2.length+1];
        //从i=1开始向前比对，所以如果i=0的话就无法向前，第一行和第一列全部初始化成0就好
        //但是因为默认就是0，所以没必要手动初始化
        for (int i = 1; i<= nums1.length;i++){
            for (int j =1; j<= nums2.length;j++){
                if (nums1[i-1] == nums2[j-1]){
                    dp[i][j] = dp[i-1][j-1] +1;
                }
                if (dp[i][j] > res) res = dp[i][j];
            }
        }
        return res;
        
    }
}