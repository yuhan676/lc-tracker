class Solution {
    public int racecar(int target) {
        Queue<int[]> que = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        //pos, speed
        que.add(new int[] {0,1});
        visited.add("0,1");
        int steps = 0;
        while (!que.isEmpty()){
            int sz = que.size();
            for (int i = 0; i< sz;i++){
                int[] currStatus = que.poll();
                int pos = currStatus[0];
                int speed = currStatus[1];

                if (pos == target) return steps;

                //Accelerate
                int accpos = pos + speed;
                int accspeed = speed*2;
                int[] nextStatus = new int[] {accpos,accspeed};
                String nextStatusS = accpos + "," + accspeed;
                if (Math.abs(accpos) <= Math.abs(target *2)&& !visited.contains(nextStatusS)){
                    visited.add(nextStatusS);
                    que.offer(nextStatus);
                }

                //reverse
                int revSpeed;
                if (speed <0) revSpeed = 1;
                else revSpeed = -1;
                nextStatus = new int[] {pos,revSpeed};
                nextStatusS = pos + "," + revSpeed;
                if (!visited.contains(nextStatusS)){
                    visited.add(nextStatusS);
                    que.offer(nextStatus);
                }
            }
            //这一层的所有步骤都走了
                steps++;
        }
        //应该不会走到这一步
        return -1;
    }
}