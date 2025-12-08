class Solution {
    int[][] dir = {{1,0}, {0,1}, {-1,0}, {0,-1}};
    Map<Integer, Integer> islandIdToArea = new HashMap<>();

    public int largestIsland(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int islandId = 2;

        for (int i=0; i<n;i++){
            for (int j=0;j<m;j++){
                if (grid[i][j] == 1){
                    int area = dfs(grid,i,j,islandId);
                    islandIdToArea.put(islandId, area);
                    islandId ++;
                }
            }
        }

        int maxArea = 0;
        for(int i=0;i<n;i++){
            for (int j =0; j<m;j++){
                if (grid[i][j] == 0){
                    Set<Integer> seen = new HashSet<>();
                    int area = 1;
                    for (int[] d: dir){
                        int ni = i + d[0];
                        int nj = j + d[1];
                        if(ni >= 0 && nj >= 0 && ni < n && nj < m && grid[ni][nj]>1){
                            seen.add(grid[ni][nj]);
                        }
                    }    
                    for (int id: seen){
                        area += islandIdToArea.get(id);
                    }
                    maxArea = Math.max(maxArea,area);
                }
            }
        }
        if (maxArea == 0){
            for (int a: islandIdToArea.values()){
                maxArea = Math.max(maxArea,a);
            }
        }
        return maxArea ;
    }

    public int dfs (int[][] grid, int x, int y, int islandId){
        if (x < 0 || y < 0 || x >=grid.length|| y >=grid[0].length || grid[x][y] != 1) return 0;
        grid[x][y] = islandId;
        int area = 1;
        area += dfs(grid,x+1,y, islandId);
        area += dfs(grid,x-1,y, islandId);
        area += dfs(grid,x,y+1, islandId);
        area += dfs(grid,x,y-1, islandId);

        return area;
    }
}
