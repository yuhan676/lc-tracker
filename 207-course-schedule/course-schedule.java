class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //mapping: pre-requisite -> list of courses
        Map<Integer,List<Integer>> graph = new HashMap<>();
        int[] indegree = new int[numCourses];
        int courseCount = 0;

        for (int[] edge: prerequisites){
            //adding current course to the value list of prerequisite
            graph.computeIfAbsent(edge[1], k-> new LinkedList<>()).add(edge[0]);
            indegree[edge[0]] ++;
        }

        Queue<Integer> que = new LinkedList<>();
        for (int i =0; i< numCourses;i++){
            if (indegree[i] == 0){
                que.offer(i);
            }
        }
        while (!que.isEmpty()){
            int cur = que.poll();
            courseCount ++;
            for (int course: graph.getOrDefault(cur, new LinkedList<>())){
                indegree[course] --;
                if (indegree[course]==0){
                    que.offer(course);
                }
            }
        }
        return courseCount == numCourses;
    }
}