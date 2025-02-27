class Solution {
    public int[][] generateMatrix(int n) {
        int nums [] [] = new int [n] [n];
        int startX =0, startY = 0, loop = 1;
        int i,j;
        int count = 1, offset = 1;
        while (loop <= n/2) {
            for (j = startY;j < n-offset;j++){
                nums[startX][j] = count++;
            }
            for (i=startX; i < n-offset; i++){
                nums[i][j] = count++;
            }
            for (; j > startY; j--){
                nums[i][j] = count++;
            }
            for (; i> startX; i--){
                nums[i][j] = count ++;
            }
            startX ++;
            startY ++;
            loop ++;
            offset ++;
        }
        if (n%2 == 1){
            nums[startX][startY] = count;
        }
        return nums;
    }
}