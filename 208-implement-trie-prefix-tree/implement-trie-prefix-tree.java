class TrieNode{
    TrieNode [] arr;
    boolean end;
    TrieNode(){
        arr = new TrieNode[26];
        //end初始化为false
        this.end = false;
    }
}
class Trie {
    //每个trie都会有一个root节点
    public TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode curr = root;
        //c指代当前character的index（0-25）
        int c;
        for (int i=0; i<word.length();i++){
            c = word.charAt(i) - 'a';
            //如果之前没有储存过这个就新加一个trienode
            if (curr.arr[c]==null){
                curr.arr[c] = new TrieNode();
            }
            curr = curr.arr[c];
        }
        //全部遍历完毕，标记end
        curr.end = true;
    }
    
    public boolean search(String word) {
        TrieNode curr = root;
        int c;
        for (int i =0; i<word.length();i++){
            c = word.charAt(i) - 'a';
            if (curr.arr[c]==null) return false;
            curr = curr.arr[c];
        }
        //到底了，但是要检查word的最后一个字母是不是储存的字符串的end
        return curr.end;
    }
    
    public boolean startsWith(String prefix) {
        TrieNode curr = root;
        int c;
        for (int i =0; i<prefix.length();i++){
            c = prefix.charAt(i) - 'a';
            if (curr.arr[c]==null) return false;
            curr = curr.arr[c];
        }
        //prefix只要能全部遍历完都非null就可以顺理成章地返回true，不用检查是不是end
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */