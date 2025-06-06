class Solution {
    List<List<String>> res = new LinkedList<>();
    List<String> path = new LinkedList<>();

    public List<List<String>> partition(String s) {
        backtrack(s,0);
        return res;

    }
    private void backtrack(String s, int startIndex){
        if (startIndex == s.length()){
            res.add(new LinkedList<>(path));
            return;
        }
        for (int i = startIndex; i <s.length();i++){
            String sbs = s.substring(startIndex, i+1);
            if (isPalindrome(sbs)){
                path.add(sbs);
                backtrack(s,i+1);
                path.remove(path.size()-1);
            }
        }
        
    }

    private boolean isPalindrome(String s){
        for (int i = 0; i< s.length()/2;i++){
            if (s.charAt(i) != s.charAt(s.length()-1-i)) return false;
        }
        return true;
    }
}