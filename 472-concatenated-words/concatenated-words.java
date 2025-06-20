class Solution {
    Map<String, Boolean> memo = new HashMap<>();

    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        //这里学习一下，把string[]加入set的快捷方式:Arrays.asList()
        Set<String> set= new HashSet<>(Arrays.asList(words));

        List<String> res = new LinkedList<>();
        for (String word:words){
            //避免suffix==word本身
            set.remove(word);
            if (isConcatenated(word, set)){
                res.add(word);
            }
            //把word加回来
            set.add(word);
        }
        return res;
    }

    private boolean isConcatenated(String s, Set<String> set){
        if (memo.containsKey(s)) return memo.get(s);
        //这里注意如果写成(int i =1; i<=s.length()-2;i++)就会遇到s.length()==2无法拆分的edgecase
        for (int i =1; i<=s.length();i++){
            String prefix = s.substring(0,i);
            String suffix = s.substring(i);
            if (set.contains(prefix) && (set.contains(suffix) || isConcatenated(suffix,set))){
                memo.put(s,true);
                return true;
            }
        }
        memo.put(s,false);
        return false;
    }
}