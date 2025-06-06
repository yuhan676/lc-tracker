class Solution {

    final int[][] dir = {{1,0}, {0,1},{-1,0},{0,-1}};
    public boolean exist(char[][] board, String word) {
        boolean[][] visited = new boolean[board.length][board[0].length];
        char[] path = new char[word.length()];
        for (int i=0; i<board.length;i++){
            for (int j=0; j<board[0].length;j++){
                if (board[i][j] == word.charAt(0)){
                    if (dfs(board,word,path,visited,i,j,0)) return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, String word,char[] path, boolean[][] visited, int x, int y, int index){
        if (word.charAt(index) != board[x][y]){
            return false;
        }
        if (index == word.length()-1){
            return true;
        }
        path[index] = board[x][y];
        visited[x][y] = true;
        for(int[] d: dir){
            int newX = x + d[0];
            int newY = y + d[1];
            if (newX < 0 || newY <0 || newX >= board.length || newY >=board[0].length || visited[newX][newY]){
                continue;
            }
            if (dfs(board,word,path,visited,newX,newY,index+1)) return true;
        }
        visited[x][y] = false;
        return false;
    }
}