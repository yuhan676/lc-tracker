class Solution {
    public String reorganizeString(String s) {
        int[] frequency = new int[26];
        for (char character: s.toCharArray()){
            frequency[character-'a'] ++;
        }
        //max heap
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a,b)->(b[1]-a[1]));
        for (int i=0;i<26;i++){
            if (frequency[i] != 0){
                maxHeap.add(new int[] {i,frequency[i]});
            }
        }
        int[] topElement = maxHeap.peek();
        if (topElement == null || topElement[1] > (s.length() + 1)/2) return "";
        StringBuilder sb = new StringBuilder();
        //只有堆里面有》=1个元素才能poll出两个元素来
        while (maxHeap.size()>1){
            int[] top1 = maxHeap.poll();
            int[] top2 = maxHeap.poll();
            //这里要把它从数字转换成char
            sb.append((char)(top1[0] + 'a'));
            sb.append((char)(top2[0] + 'a'));
            top1[1] --;
            top2[1] --;
            //只有余额大于0的时候才放回堆
            if (top1[1]>0){
                maxHeap.add(top1);
            }
            if (top2[1]>0){
                maxHeap.add(top2);
            }
        }
        if (maxHeap.isEmpty()){
            return sb.toString();
        }
        int[] lastEle = maxHeap.poll();
        //最后一个元素freq>1, 就不可能返回无重复的string
        if (lastEle[1]>1) return "";
        else{
            sb.append((char) (lastEle[0] + 'a'));
            return sb.toString();
        }
        
    }
}