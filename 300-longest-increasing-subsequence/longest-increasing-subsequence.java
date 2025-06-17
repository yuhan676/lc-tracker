class Solution {
    public int lengthOfLIS(int[] nums) {
        List<Integer> sub = new ArrayList<>();
        for (int num: nums){
            int index = Collections.binarySearch(sub, num);
            //假如sub里没找到num，库函数返回值是一个负数，需要处理以后才能变成插入的index
            if (index<0) index = -(index+1);
            if (index < sub.size()){
                sub.set(index, num);
            }else{
                //假如sub中所有元素都比num小，那么index就会==sub.size()，也就是在最后一位加入
                sub.add(num);
            }
        }
        //sub的大小就是最长递增子序列的大小
        return sub.size();
    }

}