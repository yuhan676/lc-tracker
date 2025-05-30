class Solution {
    Map<String, List<String>> neighbours = new HashMap<>();
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return 0;
        List<String> beginNeighbour = new LinkedList<>();
        for (String word: wordSet){
            if (isNeighbour(beginWord, word)){
                beginNeighbour.add(word);
            }
        }
        neighbours.put(beginWord, beginNeighbour);

        for (String word1: wordSet){
            List<String> wordNeighbours = new LinkedList<>();
            for (String word2: wordSet){
                if (isNeighbour(word1,word2)){
                    wordNeighbours.add(word2);
                }
            }
            neighbours.put(word1, wordNeighbours);
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
                for (String vocab: neighbours.get(curr)){
                    if (!visited.contains(vocab)){
                        que.offer(vocab);
                        visited.add(vocab);
                    }
                }
            }
            steps ++;
        }
        return 0;
    }

    public boolean isNeighbour(String s, String t){
        if (s.equals(t)) return false;
        if (s.length() != t.length()) return false;
        int diff = 0;
        for (int i=0; i< s.length();i++){
            if (s.charAt(i) != t.charAt(i)){
                diff ++;
                if (diff > 1) return false;
            }
        }
        if (diff == 1){
            return true;
        } 
        return false;
    }
}