class Solution {
    public int getMaxLen(int[] nums) {
        int maxPos = 0;
        int posLen = 0;
        int negLen = 0;

        for (int num:nums){
            if (num>0){
                posLen +=1;
                negLen = negLen == 0 ? 0: negLen+1;
            }
            else if (num == 0){
                posLen = 0;
                negLen = 0;
            }else{
                int temp = posLen;
                posLen = negLen == 0? 0: negLen +1;
                negLen = temp +1;
            }
            maxPos = Math.max(maxPos, posLen);
        }
        return maxPos;
    }
}