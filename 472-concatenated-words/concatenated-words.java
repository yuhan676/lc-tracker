class Solution {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> res = new LinkedList<>();
        //一个优化：从短的word开始放进trie，比较合理,所以排序从短到长
        Arrays.sort(words,(a,b) -> a.length() - b.length());
        Trie trie = new Trie();
        for (String word:words){
            if (trie.canBeConcat(word, 0,0)){
                res.add(word);
            }else{
                //只有非concat可以放进trie，可以节省一些空间和时间开销
                trie.addWord(word);
            }
        }
        return res;
    }
}

class TrieNode{
    TrieNode[] children;
    boolean isEnd;

    TrieNode(){
        this.children = new TrieNode[26];
        this.isEnd = false;
    }
}

class Trie{
    TrieNode root;
    Trie(){
        this.root = new TrieNode();
    }
    public void addWord(String s){
        TrieNode curr = root;
        for (char c: s.toCharArray()){
            if (curr.children[c-'a'] == null) curr.children[c-'a'] = new TrieNode();
            curr = curr.children[c-'a'];
        }
        curr.isEnd = true;
    }
    //检查一个string可不可以用trie中的word组成
    //从startindex开始检查，并记录至今为止找到的合法word的数量foundWordCount
    public boolean canBeConcat(String s, int startIndex, int foundWordCount){
        TrieNode curr = root;
        for (int i = startIndex; i<s.length();i++){
            int charIndex = s.charAt(i) - 'a';
            //找不到下一个字母，说明这个s并不是concat word
            if (curr.children[charIndex]==null) return false;
            //找到了下一个字母，往下走一格
            curr = curr.children[charIndex];
            //假如当前这个位置是一个词的结尾
            if (curr.isEnd){
                //假如刚好遍历到了s的末尾了，就得检查我们至今为止发现了多少个valid word
                if (i == s.length()-1){
                    //假如之前已经找到1个，现在又遇到一个validword，说明至少是两个word平凑成s
                    //假如之前找到0个，说明这个词只找到一个valid word，不合法，不是concat word
                    return foundWordCount>=1;
                }
                //检查这个validword后面，从index i+1开始的地方剩余的子串能不能be concat
                //假如可以，说明前后半段都是合法word，就说明s是concat
                if (canBeConcat(s,i+1, foundWordCount+1)) return true;
            }
        }
        //全部遍历完s中的字母，没能半途中return true，只好返回false
        return false;
    }
}