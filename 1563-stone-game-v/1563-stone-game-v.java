class Solution {
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;

        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stoneValue[i];
        }

        int[][] dp = new int[n][n];

        for (int len = 2; len <= n; len++) {
            for (int left = 0; left + len <= n; left++) {
                int right = left + len - 1;

                for (int mid = left; mid < right; mid++) {
                    int leftSum = prefix[mid + 1] - prefix[left];
                    int rightSum = prefix[right + 1] - prefix[mid + 1];

                    if (leftSum < rightSum) {
                        dp[left][right] = Math.max(
                            dp[left][right],
                            leftSum + dp[left][mid]
                        );
                    } 
                    else if (leftSum > rightSum) {
                        dp[left][right] = Math.max(
                            dp[left][right],
                            rightSum + dp[mid + 1][right]
                        );
                    } 
                    else {
                        dp[left][right] = Math.max(
                            dp[left][right],
                            leftSum + Math.max(
                                dp[left][mid],
                                dp[mid + 1][right]
                            )
                        );
                    }
                }
            }
        }

        return dp[0][n - 1];
    }
}