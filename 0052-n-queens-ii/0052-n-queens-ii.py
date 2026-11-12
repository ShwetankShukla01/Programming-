class Solution:
    def totalNQueens(self, n: int) -> int:
        ld = [False] * (2 * n - 1)
        rd = [False] * (2 * n - 1)
        col = [False] * n

        ans = 0

        def placeQueen(row):
            nonlocal ans

            if row == n:
                ans += 1
                return

            for j in range(n):
                leftDiagonal = n - 1 + row - j
                rightDiagonal = row + j

                if col[j] or ld[leftDiagonal] or rd[rightDiagonal]:
                    continue

                col[j] = True
                ld[leftDiagonal] = True
                rd[rightDiagonal] = True

                placeQueen(row + 1)

                col[j] = False
                ld[leftDiagonal] = False
                rd[rightDiagonal] = False

        placeQueen(0)

        return ans