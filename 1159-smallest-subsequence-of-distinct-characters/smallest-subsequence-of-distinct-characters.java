class Solution {
    public String smallestSubsequence(String s) {
        int len = s. length();
        int[] last = new int[26];
        int[] seen = new int[26];
        Arrays.fill(last, -1);
        for (int i =0; i< len; i++){
            last[s.charAt(i) - 'a'] = i;
        }
        Stack<Character> st = new Stack<>();
        for (int i = 0; i<len;i++){
            char c = s.charAt(i);
            System.out.println("new char: " + c);
            if (seen[c - 'a'] > 0) {
                continue;
            }
            while (!st.isEmpty() && (int )st.peek() > (int) c && i < last[st.peek() - 'a']){
                seen[st.pop() - 'a'] --;
            }
            st.push(c);
            seen[c - 'a'] ++;
        }
        StringBuilder sb = new StringBuilder();
        while (!st.isEmpty()){
            sb.append(st.pop());
        }
        return sb.reverse().toString();
    }
}