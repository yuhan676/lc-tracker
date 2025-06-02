class Solution {
    //[course, prerequisite]
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        constructGraph(prerequisites, graph);
        //0 -> unvisited, 1 -> visiting, 2 -> visited
        int[] visited = new int[numCourses];
        for (int i =0; i<numCourses; i++){
            if (hasCycle(i, graph, visited)) return false;
        }
        return true;
    }

    private void constructGraph(int[][] prerequisites, Map<Integer,List<Integer>> graph){
        for (int[] vertice: prerequisites){
            int course= vertice[0];
            int prere= vertice[1];
            graph.computeIfAbsent(prere, k->new LinkedList<>()).add(course);
        }
    }

    private boolean hasCycle(int course, Map<Integer,List<Integer>> graph, int[] visited){
        //递归边界
        if (visited[course] ==1) return true;//访问过程中遇到正在访问的节点，说明成环
        if (visited[course] == 2) return false;//已经访问，返回
        visited[course] = 1;
        for (int neighbour: graph.getOrDefault(course, new LinkedList<>())){
            if (hasCycle(neighbour,graph,visited)) return true;
        }
        //现在已经遍历完所有当前课程的后置课程，就是访问结束了
        visited[course] = 2;
        return false;
    }
}