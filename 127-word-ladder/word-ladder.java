class Solution {
    Map<String, List<String>> allCombo = new HashMap<>();

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        //SC:n
        int L = beginWord.length();
        //remove all words that don't have the right length, cuz we won't do length check later 
        for (String word: wordList){
            if (word.length() == L){
                wordSet.add(word);
            }
        }
        if (!wordSet.contains(endWord)) return 0;
        //SC: N * L entry
        //TC: n * L ^ 2
        for (String word: wordSet){
            for (int i=0; i<L;i++){
                //注意substring没有大写
                //each pattern's concatination is O(L)
                String pattern = word.substring(0,i) + "*" + word.substring(i+1);
                //注意这里是先computeifabsent再add
                allCombo.computeIfAbsent(pattern, k -> new ArrayList<String>()).add(word);
            }
        }
        Set<String> visited = new HashSet<>();
        int steps = 1;
        Queue<String> que = new LinkedList<>();
        que.offer(beginWord);
        visited.add(beginWord);

        while(!que.isEmpty()){
            int sz = que.size();
            for (int i=0; i<sz;i++){
                String curr = que.poll();
                if (curr.equals(endWord)) return steps;
                for (int j =0; j<L;j++){
                    String pattern = curr.substring(0,j) + "*" + curr.substring(j+1);
                    //这里用Collections.emptyList（）比new ArrayList()节省空间
                    for (String word: allCombo.getOrDefault(pattern, Collections.emptyList())){
                        if (!visited.contains(word)){
                            que.offer(word);
                            visited.add(word);
                        }
                    }
                }
            }
            steps ++;
        }
        return 0;
    }
}