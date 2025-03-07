class Solution {
    public String reverseWords(String s) {
        String [] splitS = s.trim().split ("\\s+");//can also use " "
        StringBuffer res = new StringBuffer();

        for (int i = splitS.length-1; i >=1; i--){
            res.append(splitS[i]);
            res.append(" ");
        }
        res.append(splitS[0]);
        String result = res.toString();

        return result;
    }
}