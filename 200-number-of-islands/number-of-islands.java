class Solution {
    int count = 0;
    int[][] dir = new int[][] {{0,1},{1,0},{0,-1},{-1,0}};
    Queue<int[]> q = new LinkedList<>();
    public int numIslands(char[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int i =0; i< grid.length;i++){
            for (int j =0; j<grid[0].length;j++){
                if (grid[i][j] == '1' && !visited[i][j]){
                    count ++;
                    q.offer(new int[] {i,j});
                    visited[i][j] = true;
                    while (!q.isEmpty()){
                        int[] pos = q.poll();
                        int x = pos[0], y = pos[1];
                        for (int k = 0;k<4;k++){
                            int newX = x + dir[k][0];
                            int newY = y + dir[k][1];
                            if (newX <0 || newY <0 || newX >= grid.length || newY >= grid[0].length){
                                continue;
                            }
                            if (grid[newX][newY]=='1' && !visited[newX][newY]){
                                q.offer(new int[] {newX, newY});
                                visited[newX][newY] = true;
                            }
                        }
                    }
            }
        }
        
    }
    return count;
}
}