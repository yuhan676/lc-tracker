class Solution {
    public int lengthOfLongestSubstring(String s) {
        int maxCount = 0;
        int count = 0;
        int l =0;
        Map<Character, Integer> map = new HashMap<>();
        for (int r=0; r<s.length();r++){
            if (!map.containsKey(s.charAt(r))){
            }
            else if(map.get(s.charAt(r)) >= l){
                l = map.get(s.charAt(r)) + 1;
            }
            map.put(s.charAt(r),r);
            maxCount = Math.max(maxCount, r - l + 1);
        }
        return maxCount;
    }
}