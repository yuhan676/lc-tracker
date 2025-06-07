class Solution {
    List<String> res = new LinkedList<>();

    public List<String> restoreIpAddresses(String s) {
        if (s.length() > 12 || s.length() <4) return res;
        StringBuilder sb = new StringBuilder(s);
        backtracking(sb,0,0);
        return res;
    }

    private void backtracking(StringBuilder s, int start, int dotCount){
        if (dotCount == 3){
            if (isValid(s,start,s.length()-1)){
                res.add(s.toString());
            }
            return;
        }
        for(int i = start; i<s.length();i++){
            if (isValid(s,start,i)){
                s.insert(i+1, ".");
                backtracking(s, i+2, dotCount +1);
                s.deleteCharAt(i+1);
            }
        }
    }


    private boolean isValid(StringBuilder s, int startIndex, int endIndex){
        if (startIndex > endIndex) return false;
        if (endIndex - startIndex > 2) return false;
        //注意这里的判断不是==0
        if (s.charAt(startIndex) == '0' && startIndex != endIndex) return false;
        int num = 0;
        for (int i = startIndex;i <= endIndex;i++){
            num = num*10 + s.charAt(i) - '0';
        }
        if (num > 255) return false;
        return true;
    }
}