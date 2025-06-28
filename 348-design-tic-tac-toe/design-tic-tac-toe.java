class TicTacToe {
    int player1;
    int player2;
    boolean gameOver;
    int[][] board;
    //hor, ver, dia, dia
    int[][][] allDir = new int[][][] {{{0,1}, {0,-1}}, {{1,0},{-1,0}},{{-1,-1},{1,1}},{{-1,1},{1,-1}}};
    int n;

    public TicTacToe(int n) {
        this.player1 = 1;
        this.player2 = 2;
        this.gameOver = false;
        this.board = new int[n][n];
        this.n = n;
    }
    
    public int move(int row, int col, int player) {
        if (board[row][col]!= 0){
            //it's not RunTimeError,
            throw new RuntimeException("Can't place here.");
        }else{
            board[row][col] = player;
            if (hasWon(player, row,col,board))return player;
            else return 0;
        }
    }

    public boolean hasWon(int player, int row, int col, int[][] board){
        for (int[][] dirPair: allDir){
            int count = 1;
            for (int[] dir: dirPair){
                int r = row + dir[0];
                int c = col + dir[1];
                while (r>= 0 && c >= 0 && r<n && c<n && board[r][c] == player){
                    count +=1;
                    r+= dir[0];
                    c+= dir[1];
                }
            }
            if(count>=n)return true;
        }
        return false;
    }
}

/**
 * Your TicTacToe object will be instantiated and called as such:
 * TicTacToe obj = new TicTacToe(n);
 * int param_1 = obj.move(row,col,player);
 */