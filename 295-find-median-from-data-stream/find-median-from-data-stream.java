class MedianFinder {
    PriorityQueue<Integer> minHeap;
    PriorityQueue<Integer> maxHeap;

    public MedianFinder() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>((a,b) -> Integer.compare(b,a));
    }
    
    public void addNum(int num) {
        maxHeap.add(num);
        minHeap.add(maxHeap.poll());
        while(minHeap.size()>maxHeap.size()){
            int e = minHeap.poll();
            maxHeap.add(e);
        }
    }
    
    public double findMedian() {
        //大顶堆比小顶堆多一个，
        //大顶堆的堆顶就是median
        if (maxHeap.size()>minHeap.size()){
            return (double) maxHeap.peek();
        }else{
            //两个堆一样大
            double e1 = maxHeap.peek();
            double e2 = minHeap.peek();
            return (e1 + e2)/2.0;
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */