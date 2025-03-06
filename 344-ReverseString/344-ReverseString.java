class Solution {
    public void reverseString(char[] s) {
        int i = 0;
        int j = s.length -1;
        while ( i < j){
            char temp1 = s[i];
            char temp2 = s[j];
            s[i] = temp2;
            s[j] = temp1;
            i++;
            j--;
        }
    }
}