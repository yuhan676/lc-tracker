class Solution {
    final String[] digitmap = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
    char[] path;
    List<String> res = new LinkedList<>();
    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0 ) return res;
        path = new char[digits.length()];
        backtracking(digits, 0);
        return res;
    }
    private void backtracking(String digits, int index){
        if (index == path.length){
            res.add(new String(path));
            return;
        }
        //这里应该把digitmap中取得string拿出来进行遍历
        String st = digitmap[digits.charAt(index) - '0'];
        for (char c: st.toCharArray()){
            path[index] = c;
            backtracking(digits, index +1);
        }
    }
}