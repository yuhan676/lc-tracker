class Solution {
    public long appealSum(String s) {
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen,-1);
        long res = 0;
        for (int i = 0; i<s.length();i++){
            char c = s.charAt(i);
            int contribution = i - lastSeen[c-'a'];
            int subStringStartI = s.length()-i;
            lastSeen[c-'a'] = i;
            res += contribution * (long)subStringStartI;
        }
        return res;
    }
}