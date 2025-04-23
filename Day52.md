## km101. 孤岛的总面积
* https://kamacoder.com/problempage.php?pid=1173
* 文章：https://www.programmercarl.com/kamacoder/0101.%E5%AD%A4%E5%B2%9B%E7%9A%84%E6%80%BB%E9%9D%A2%E7%A7%AF.html#%E6%80%9D%E8%B7%AF
* 视频：https://www.bilibili.com/video/BV1mmZJYRESc?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### dfs
* 需要注意的地方：第一个岛屿格子也需要检查是不是处在边界（马上判断这个格子符不符合孤岛的标准）
```java
import java.util.*;

public class Main{
    static int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
    //ans累计计算孤岛面积
    static int ans = 0;
    //记录当前岛屿面积
    static int currA = 0;
    //记录当前岛屿是不是孤岛
    static boolean isIso = true;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                grid[i][j] = sc.nextInt();
            }
        }

        boolean[][] visited = new boolean[n][m];

        for (int i =0;i<n;i++){
            for(int j=0; j<m;j++){
                if (!visited[i][j] && grid[i][j]==1){
                    //标记访问
                    visited[i][j] = true;
                    //默认是孤岛
                    isIso = true;
                    //检查第一个格子是不是孤岛
                    if (i==0||j==0||i==n-1||j==m-1){
                        isIso = false;
                    }
                    //增加currA到1（我们刚刚遍历了一个这个岛屿上的格子）
                    currA = 1;
                    //递归遍历该岛所有格子，标记访问，确认该岛是不是孤岛
                    dfs(grid,visited,i,j);
                    if (isIso){
                        //如果遍历完了还认为它是孤岛，那就把面积累加到ans
                        ans += currA;
                    }
                }
            }
        }
        System.out.print(ans);

    }
    public static void dfs(int[][] grid, boolean[][] visited, int x, int y){
        for (int i=0; i<4;i++){
            int nextX = x + dir[i][0];
            int nextY = y + dir[i][1];

            if (nextX <0 || nextY <0 || nextX>=grid.length || nextY >= grid[0].length){
                continue;
            }
            if (!visited[nextX][nextY] && grid[nextX][nextY]==1){
                if (nextX == 0 || nextY==0 || nextX == grid.length-1 || nextY == grid[0].length -1){
                    //如果找到相邻的格子，但是和处在grid边界，说明不是孤岛
                    isIso = false;
                }
                visited[nextX][nextY] = true;
                currA++;
                dfs(grid,visited,nextX,nextY);
            }
        }
    }
}
```
### bfs解法
```java
import java.util.*;

public class Main{
    static int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
    static int ans = 0;
    static int currA = 0;
    static boolean isIso = true;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                grid[i][j] = sc.nextInt();
            }
        }

        boolean[][] visited = new boolean[n][m];

        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                isIso = true;
                currA =1;
                if (!visited[i][j] && grid[i][j]==1){
                    if (i==0||j==0||i==n-1||j==m-1){
                        isIso=false;
                    }
                    bfs(grid,visited,i,j);
                    if (isIso){
                        ans += currA;
                    }
                }
            }
        }
        System.out.print(ans);



    }
    public static void bfs (int[][] grid, boolean[][] visited, int x, int y){
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[] {x,y});
        //记得bfs一定是一入队就标记访问
        visited[x][y] = true;
        while (!que.isEmpty()){
            int[] currXY = que.poll();
            int currX = currXY[0];
            int currY = currXY[1];
            for (int i=0;i<4;i++){
                int nextX = currX + dir[i][0];
                int nextY = currY + dir[i][1];

                if (nextX < 0 || nextY <0 || nextX >= grid.length || nextY >= grid[0].length){
                    continue;
                }
                if (!visited[nextX][nextY] && grid[nextX][nextY]==1){
                    if (nextX == 0 || nextY ==0 || nextX == grid.length-1 || nextY == grid[0].length-1){
                        isIso = false;
                    }
                    que.add(new int[] {nextX,nextY});
                    visited[nextX][nextY]=true;
                    currA ++;
                }
            }

        }
    }
}
```
## km102. 沉没孤岛
* https://kamacoder.com/problempage.php?pid=1174
* 文章：https://www.programmercarl.com/kamacoder/0102.%E6%B2%89%E6%B2%A1%E5%AD%A4%E5%B2%9B.html#%E6%80%9D%E8%B7%AF
* 视频：https://www.bilibili.com/video/BV1fjdWYyEGu?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### dfs
* 我的方法是把当前岛屿的格子都存在一个queue里
* 如果最后决定当前岛屿是孤岛，就一个一个把grid里的格子沉没
* 总体思路和前面的岛屿问题非常像,我就只改了关键逻辑
```java
import java.util.*;

public class Main {
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static Queue<int[]> currIsland = new LinkedList<>();
    static boolean isIso = true;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        boolean[][] visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!visited[i][j] && grid[i][j] == 1) {
                    //标记访问
                    visited[i][j] = true;
                    //默认是孤岛
                    isIso = true;
                    currIsland.clear();
                    //检查第一个格子是不是孤岛
                    if (i == 0 || j == 0 || i == n - 1 || j == m - 1) {
                        isIso = false;
                    }
                    currIsland.add(new int[] {i, j});
                    dfs(grid, visited, i, j);
                    if (isIso) {
                        while (!currIsland.isEmpty()) {
                            int[] coor = currIsland.poll();
                            int a = coor[0];
                            int b = coor[1];
                            grid[a][b] = 0;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void dfs(int[][] grid, boolean[][] visited, int x, int y) {
        for (int i = 0; i < 4; i++) {
            int nextX = x + dir[i][0];
            int nextY = y + dir[i][1];

            if (nextX < 0 || nextY < 0 || nextX >= grid.length || nextY >= grid[0].length) {
                continue;
            }
            if (!visited[nextX][nextY] && grid[nextX][nextY] == 1) {
                if (nextX == 0
                        || nextY == 0
                        || nextX == grid.length - 1
                        || nextY == grid[0].length - 1) {
                    //如果找到相邻的格子，但是和处在grid边界，说明不是孤岛
                    isIso = false;
                }
                visited[nextX][nextY] = true;
                currIsland.add(new int[] {nextX, nextY});
                dfs(grid, visited, nextX, nextY);
            }
        }
    }
}
```
### bfs
```java
import java.util.*;

public class Main{
    static int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
    static Queue<int[]> currIsland= new LinkedList<>();
    static boolean isIso = true;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                grid[i][j] = sc.nextInt();
            }
        }

        boolean[][] visited = new boolean[n][m];

        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                
                if (!visited[i][j] && grid[i][j]==1){
                    isIso = true;
                    currIsland.clear();
                    currIsland.add(new int[] {i,j});
                    if (i==0||j==0||i==n-1||j==m-1){
                        isIso=false;
                    }
                    bfs(grid,visited,i,j);
                    if (isIso){
                        while(!currIsland.isEmpty()){
                            int[] coor = currIsland.poll();
                            int a = coor[0];
                            int b = coor[1];
                            grid[a][b] = 0;
                        }
                    }
                }
            }
        }
        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
               System.out.print(grid[i][j]+" ");
            }
            System.out.println();
        }


    }
    public static void bfs (int[][] grid, boolean[][] visited, int x, int y){
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[] {x,y});
        //记得bfs一定是一入队就标记访问
        visited[x][y] = true;
        while (!que.isEmpty()){
            int[] currXY = que.poll();
            int currX = currXY[0];
            int currY = currXY[1];
            for (int i=0;i<4;i++){
                int nextX = currX + dir[i][0];
                int nextY = currY + dir[i][1];

                if (nextX < 0 || nextY <0 || nextX >= grid.length || nextY >= grid[0].length){
                    continue;
                }
                if (!visited[nextX][nextY] && grid[nextX][nextY]==1){
                    if (nextX == 0 || nextY ==0 || nextX == grid.length-1 || nextY == grid[0].length-1){
                        isIso = false;
                    }
                    que.add(new int[] {nextX,nextY});
                    visited[nextX][nextY]=true;
                    currIsland.add(new int[] {nextX,nextY});
                }
            }

        }
    }
}
```
## km103. 水流问题
* https://kamacoder.com/problempage.php?pid=1175
* 文章：https://www.programmercarl.com/kamacoder/0103.%E6%B0%B4%E6%B5%81%E9%97%AE%E9%A2%98.html#%E6%80%9D%E8%B7%AF
* 视频：https://www.bilibili.com/video/BV1WNoEYrEio?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 这一题也是dfs和bfs都可以解决的
### dfs暴力解法（可能超时）
* 从每一个格子出发去寻找水能留到哪里，能流到的格子用一个visited[][] (boolean) 来储存
* 另外写一个reachedBorders来检查visited[][]，包不包括能再border的格子
* 时间复杂度：O(n^2*m^2), 因为遍历所有格子需要O(m*n),每个格子构建visited又需要（m*n)
* 空间复杂度
* <img width="806" alt="Screenshot 2025-04-23 at 19 29 40" src="https://github.com/user-attachments/assets/e89b178d-1f6d-4b37-bd20-0db8f18cd414" />
```java

import java.util.*;
import java.util.*;

public class Main{

    static int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
    static Queue<int[]> ans = new LinkedList<>();
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];

        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                grid[i][j] = sc.nextInt();
            }
        }
        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                boolean[][] visited = new boolean[n][m];
                //这里记得一定要标记当前格子为visited
                visited[i][j] = true;
                dfs(grid,visited,i,j);
                if (reachedBorders(visited)){
                    ans.add(new int[] {i,j});
                }
            }
        }
        while (!ans.isEmpty()){
            int[] coor = ans.poll();
            System.out.println(coor[0] + " " + coor[1]);
        }
    }

    public static boolean reachedBorders (boolean[][] visited){
        boolean B1 = false;
        boolean B2 = false;
        for (int i=0;i<visited.length;i++){
            if (visited[i][0]){
                B1 = true;
                break;
            }
        }
        if (!B1){
            for (int j=0;j<visited[0].length;j++){
            if (visited[0][j]){
                B1 = true;
                break;
            }
            }
        }
        for (int i=0;i<visited.length;i++){
            if (visited[i][visited[0].length-1]){
                B2=true;
                break;
            }
        }
        if (!B2){
            for (int j=0;j<visited[0].length;j++){
            if (visited[visited.length-1][j]){
                B2=true;
                break;
            }
        }
        }
        if (B1 && B2) return true;
        else return false;
    }
    public static void dfs (int[][]grid, boolean[][]visited, int x, int y){
        for (int i=0;i<4;i++){
            int nextX = x + dir[i][0];
            int nextY = y + dir[i][1];
            if (nextX<0|| nextY<0 || nextX >= grid.length || nextY >= grid[0].length){
                continue;
            }
            if (!visited[nextX][nextY] && grid[nextX][nextY]<= grid[x][y]){
                visited[nextX][nextY] = true;
                dfs(grid,visited,nextX,nextY);
            }
        }
    }
}
```
### 优化以后dfs
* dfs方程改成查找“能流到当前格子的临近格子”
* 从border1和2开始出发寻找，生成两个visited【】【】
* 对比两个visited，如果两个表格同时含有同一个格子，说明这个格子可以流水到border1和2
```java

```

