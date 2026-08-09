class Solution:
    def stoneGameII(self, piles: list[int]) -> int:
        n = len(piles)
        # suffix[i] stores the sum of piles from i to the end
        suffix = [0] * (n + 1)
        for i in range(n - 1, -1, -1):
            suffix[i] = suffix[i + 1] + piles[i]
            
        memo = {}
        
        def dp(i: int, M: int) -> int:
            if i + 2 * M >= n:
                return suffix[i]
            if (i, M) in memo:
                return memo[(i, M)]
            
            res = 0
            # Try all possible choices X from 1 to 2M
            for X in range(1, 2 * M + 1):
                # Maximize current score: total remaining minus opponent's optimal score from next state
                res = max(res, suffix[i] - dp(i + X, max(M, X)))
                
            memo[(i, M)] = res
            return res
            
        return dp(0, 1)