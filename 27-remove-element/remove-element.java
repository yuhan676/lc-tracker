class Solution {
    public int removeElement(int[] nums, int val) {
        //Index for iterating through nums
        int oldPointer = 0;
        //Index for pointing to the position where we are filling the numbers           
        //(only fill in the number if the number isn't val)
        int newPointer = 0;
        int n = nums.length;
        while (oldPointer < n && newPointer < n){
            if (nums[oldPointer] != val){
                nums[newPointer] = nums[oldPointer];
                newPointer ++;
            }
            oldPointer ++;
        }
        //here we don't return newPointer + 1, as if it was filled, newPointer would have already ++ed to the right number (the size)
        return newPointer;
    }
}