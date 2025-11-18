class Solution {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer,Integer> sumToCount = new HashMap<>();
        int res = 0;
        for (int i =0; i< nums1.length;i++){
            for (int j=0; j<nums2.length;j++){
                int sum12 = nums1[i] + nums2[j];
                sumToCount.put(sum12, sumToCount.getOrDefault(sum12,0) + 1);
            }
        }
        for (int k =0;k<nums3.length;k++){
            for (int l=0;l<nums4.length;l++){
                int comp34 = 0 - nums3[k] - nums4[l];
                if (sumToCount.containsKey(comp34)){
                    res += sumToCount.get(comp34);
                }
            }
        }
        return res;
    }
}