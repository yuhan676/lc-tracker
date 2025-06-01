class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num: nums){
            map.put(num, map.getOrDefault(num,0) + 1);
        }
        //注意里面存的type事Map.Entry<Integer, Integer>
        //Map.Entry的方法： getKey(), getValue()
        //注意是小顶堆，a-b
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>((a,b) -> Integer.compare(a.getValue(), b.getValue()));
        //map.entrySet()返回的是一个key-value set
        for (Map.Entry<Integer, Integer> entry: map.entrySet()){
            minHeap.add(entry);
            while(minHeap.size()>k){
                minHeap.poll();
            }
        }
        int[] res = new int[k];
        //minheap最小的在前面，所以结果是从后向前填充
        for (int i=k-1; i>=0;i--){
            res[i] = minHeap.poll().getKey();
        }
        return res;


    }
}