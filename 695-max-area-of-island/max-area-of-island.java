class Solution {
    int[][] dir = {{1,0},{0,1},{-1,0},{0,-1}};
    int res = 0;
    int area = 0;
    public int maxAreaOfIsland(int[][] grid) {
        for (int i =0; i<grid.length;i++){
            for (int j=0; j<grid[0].length;j++){
                if (grid[i][j]==1){
                    area = 0;
                    bfs(grid,i,j);
                }
            }
        }
        return res;
    }
    private void bfs(int[][] grid, int x, int y){
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[] {x,y});
        grid[x][y] = 0;
        area ++;
        while(!que.isEmpty()){
            int[] currCor = que.poll();
            int currX = currCor[0];
            int currY = currCor[1];
            for (int i =0; i<4;i++){
                int nextX = currX + dir[i][0];
                int nextY = currY + dir[i][1];
                if (nextX <0 || nextY <0 || nextX >= grid.length || nextY >= grid[0].length) continue;
                if (grid[nextX][nextY]==1){
                    grid[nextX][nextY] = 0;
                    area ++;
                    que.offer(new int[] {nextX,nextY});
                }
            }
        }
        res = Math.max(res,area);
    }
}