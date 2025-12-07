class Solution {
    int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
    int res = 0;
    int area = 0;
    public int maxAreaOfIsland(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        for (int i = 0; i<n;i++){
            for (int j=0;j<m;j++){
                if (grid[i][j]==1){
                    area = 0;
                    dfs(grid, i, j);
                }
            }
        }
        return res;
    }
    private void dfs(int[][] grid, int x, int y){
        grid[x][y] = 0;
        area ++;
        for (int i =0; i<4;i++){
            int nextX = x + dir[i][0];
            int nextY = y + dir[i][1];
            if (nextX<0|| nextY <0|| nextX >= grid.length || nextY>= grid[0].length){
                continue;
            }
            if (grid[nextX][nextY]==1){
                dfs(grid,nextX,nextY);
            }
        }
        res = Math.max(res,area);
    }
}