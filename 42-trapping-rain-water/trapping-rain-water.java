class Solution {
    public int trap(int[] height) {
        //The idea is for each index, record the largest left and right element's index
        int n = height.length;
        if (n==1) return 0;
        int[] left = new int[n];
        int[] right = new int[n];

        //这里记录的是index不是高度
        left[0] = 0;
        right[n-1] = n-1;
        int maxIndex = 0;
        for (int i = 1; i<n;i++){
            left[i] = maxIndex;
            if (height[i] >height[maxIndex]){
                maxIndex = i;
            }
        }
        maxIndex = n-1;
        for (int i = n-2; i>= 0;i--){
            right[i] = maxIndex;
            if (height[i] > height[maxIndex]){
                maxIndex = i;
            }
        }

        int res = 0;

        for (int i =0;i<n;i++){
            if (height[left[i]] > height[i] && height[right[i]] >height[i]){
                //记得这里要减去当前高度
                res += Math.min(height[left[i]],height[right[i]]) - height[i];
            }
        }
        return res;
    }
}