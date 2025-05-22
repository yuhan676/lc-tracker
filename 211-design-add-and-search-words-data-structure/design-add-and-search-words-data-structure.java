class TrieNode{
    TrieNode[] arr;
    boolean end;

    TrieNode(){
        this.arr = new TrieNode[26];
        this.end = false;
    }
}
class WordDictionary {

    TrieNode root;

    public WordDictionary() {
        this.root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode curr = root;
        int c;
        for (int i =0; i<word.length();i++){
            c = word.charAt(i) - 'a';
            if (curr.arr[c] == null){
                curr.arr[c] = new TrieNode();
            }
            curr = curr.arr[c];
        }
        curr.end = true;
    }
    
    public boolean search(String word) {
        return dfs(word, 0, root);
    }

    public boolean dfs(String word, int index, TrieNode currNode){
        if (currNode == null) return false;
        if (index == word.length()) return currNode.end;

        if (word.charAt(index)=='.'){
            for (int i=0;i<26;i++){
                //这里注意要检查所有非空的子节点
                //只有子节点非空，而且后序遍历都返回true，才返回true；
                //我之前写成了if (currNode.arr[i] != null return dfs(word,index+1, currNode.arr[i])),但是那样就只遍历了第一个非空的子节点
                if (currNode.arr[i]!=null && dfs(word,index+1,currNode.arr[i])){
                    return true;
                }
            }
            return false;
        }
        else{
            int c = word.charAt(index) - 'a';
            return dfs(word,index+1,currNode.arr[c]);
        }
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */