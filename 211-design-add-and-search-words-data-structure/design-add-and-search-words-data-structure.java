class TrieNode{
    TrieNode[] arr;
    boolean isEnd;

    TrieNode(){
        this.arr = new TrieNode[26];
        this.isEnd = false;
    }
}
class WordDictionary {
    TrieNode root;

    public WordDictionary() {
        this.root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode curr = root;
        int index;
        for (int i =0; i< word.length();i++){
            index = word.charAt(i) - 'a';
            if (curr.arr[index] == null){
                curr.arr[index] = new TrieNode();
            }
            curr = curr.arr[index];
        }
        curr.isEnd = true;
    }
    
    public boolean search(String word) {
        return dfs(word, 0, root);
    }

    private boolean dfs(String word, int index, TrieNode node){
        if (node == null) return false;
        //这里不要写成if (index == word.length() && node.isEnd) return true;
        if (index == word.length()) return node.isEnd;
        if (word.charAt(index) == '.'){
            for (int i =0; i<26;i++){
                if (node.arr[i]!= null && dfs(word,index+1,node.arr[i])){
                    return true;
                }
            }
            //全部迭代完了但是都没找到合适的,就return false
            return false; 
        }else{
            return dfs(word,index +1, node.arr[word.charAt(index) -'a']);
        }
        
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */