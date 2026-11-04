class Solution {
    public int maxIceCream(int[] costs, int coins) {
        Arrays.sort(costs);
        int maxIceCreamBars = 0;

        for (int cost : costs) {
            if (cost <= coins) {
                maxIceCreamBars++;
                coins -= cost;
            } else {
                break;
            }
        }

        return maxIceCreamBars;
    }
}