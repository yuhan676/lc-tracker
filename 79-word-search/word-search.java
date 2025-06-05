class Solution {

    final int[][] dir= {{1,0}, {0,1},{-1,0},{0,-1}};

    public boolean exist(char[][] board, String word) {
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int i=0;i <board.length;i++){
            for (int j=0; j<board[0].length;j++){
                if (board[i][j] == word.charAt(0)){
                    if (dfs(board,visited,word,0,i,j)) return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, boolean[][] visited, String word, int index, int x, int y){
        if (index == word.length()) return true;
        //判断直接在这里判断
        //visited，越界，是否当前wordindex仍然合法
        if (x < 0 || y <0 || x >= board.length || y >= board[0].length || word.charAt(index) != board[x][y]|| visited[x][y]) return false;

        visited[x][y] = true;

        for (int i =0; i<4;i++){
            int newX = x + dir[i][0];
            int newY = y + dir[i][1];
            //这里要传递下一层的boolean值回去
            if (dfs(board,visited,word,index+1,newX,newY)){
                return true;
            }
        }
        visited[x][y] = false;
        //所有方向试过都走不通才return false
        return false;

    }
}