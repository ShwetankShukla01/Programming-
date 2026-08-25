class Solution {
    public int missingMultiple(int[] nums, int k) {
        // Use a boolean array as a presence set for values in nums.
        // Values are assumed to be in the range [0, 100].
        boolean[] present = new boolean[101];

        // Mark each number in nums as present.
        for (int num : nums) {
            present[num] = true;
        }

        // Check multiples of k in increasing order: k, 2k, 3k, ...
        for (int i = 1;; ++i) {
            int multiple = k * i;

            // Return the first multiple that is either out of the array's
            // range (and therefore cannot appear in nums) or not present.
            if (multiple >= present.length || !present[multiple]) {
                return multiple;
            }
        }
    }
}