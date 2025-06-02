class Solution {
    //course, pre
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //prerequisite -> list of courses
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] inDegree = new int[numCourses];

        //构建图，统计每个course的入度（pre -> course, 这算一条入度）
        for(int[] edge: prerequisites){
            graph.computeIfAbsent(edge[1], k -> new LinkedList<>()).add(edge[0]);
            inDegree[edge[0]]++;
        }

        //遍历所有入度为0的course，也就是说他们没有prerequisite就能上
        //想象遍历过程就是完成一门课，假如过了，那就把它放进visited
        //同时要记录我们已经上过的课程的数量
        //如果有环，那我们就永远没法上到一些课，最后上了的课程总数就小于总课程数量
        //因为从所有没有prerequisite的课程开始遍历，所以不用担心死循环
        //bfs用队列来进行遍历，先把这些课程都入队
        Queue<Integer> que = new LinkedList<>();
        for (int i = 0; i<numCourses; i++){
            if (inDegree[i] == 0){
                que.add(i);
            }
        }
        //上过的课的数量
        int courseCount = 0;
        while(!que.isEmpty()){
            int course = que.poll();
            courseCount++;
            for (int neighbor: graph.getOrDefault(course, new ArrayList<>())){
                //我们正在上这个课，上了这个就可以消除一个neighbor的入度
                inDegree[neighbor] --;
                if (inDegree[neighbor] == 0){
                    que.offer(neighbor);
                }
            }
        }
        return courseCount == numCourses;
    }
}