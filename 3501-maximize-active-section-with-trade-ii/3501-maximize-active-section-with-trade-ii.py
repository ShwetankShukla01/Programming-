import math


class Group(object):
    def __init__(self, start, length):
        self.start = start
        self.length = length


class SparseTable(object):
    def __init__(self, nums):
        self.n = len(nums)

        if self.n == 0:
            self.st = []
            return

        levels = int(math.log(self.n, 2)) + 2

        self.st = [[0] * (self.n + 1) for _ in range(levels)]
        self.st[0] = list(nums)

        i = 1
        while (1 << i) <= self.n:
            j = 0
            while j + (1 << i) <= self.n:
                self.st[i][j] = max(
                    self.st[i - 1][j],
                    self.st[i - 1][j + (1 << (i - 1))]
                )
                j += 1
            i += 1

    def query(self, l, r):
        length = r - l + 1
        i = int(math.log(length, 2))
        return max(self.st[i][l], self.st[i][r - (1 << i) + 1])


class Solution(object):
    def maxActiveSectionsAfterTrade(self, s, queries):
        ones = s.count('1')
        zeroGroups, zeroGroupIndex = self._getZeroGroups(s)

        if not zeroGroups:
            return [ones] * len(queries)

        st = SparseTable(self._getZeroMergeLengths(zeroGroups))

        def getMaxActiveSections(l, r):
            if zeroGroupIndex[l] == -1:
                left = -1
            else:
                g = zeroGroups[zeroGroupIndex[l]]
                left = g.length - (l - g.start)

            if zeroGroupIndex[r] == -1:
                right = -1
            else:
                g = zeroGroups[zeroGroupIndex[r]]
                right = r - g.start + 1

            if s[r] == '1':
                endIndex = zeroGroupIndex[r]
            else:
                endIndex = zeroGroupIndex[r] - 1

            startAdjacentGroupIndex, endAdjacentGroupIndex = \
                self._mapToAdjacentGroupIndices(
                    zeroGroupIndex[l] + 1,
                    endIndex
                )

            activeSections = ones

            if (s[l] == '0' and s[r] == '0' and
                    zeroGroupIndex[l] + 1 == zeroGroupIndex[r]):
                activeSections = max(activeSections, ones + left + right)

            elif startAdjacentGroupIndex <= endAdjacentGroupIndex:
                activeSections = max(
                    activeSections,
                    ones + st.query(startAdjacentGroupIndex,
                                    endAdjacentGroupIndex)
                )

            if s[l] == '0':
                limit = zeroGroupIndex[r] if s[r] == '1' else zeroGroupIndex[r] - 1
                if zeroGroupIndex[l] + 1 <= limit:
                    activeSections = max(
                        activeSections,
                        ones + left +
                        zeroGroups[zeroGroupIndex[l] + 1].length
                    )

            if (s[r] == '0' and
                    zeroGroupIndex[l] < zeroGroupIndex[r] - 1):
                activeSections = max(
                    activeSections,
                    ones + right +
                    zeroGroups[zeroGroupIndex[r] - 1].length
                )

            return activeSections

        ans = []
        for l, r in queries:
            ans.append(getMaxActiveSections(l, r))
        return ans

    def _getZeroGroups(self, s):
        zeroGroups = []
        zeroGroupIndex = []

        for i in range(len(s)):
            if s[i] == '0':
                if i > 0 and s[i - 1] == '0':
                    zeroGroups[-1].length += 1
                else:
                    zeroGroups.append(Group(i, 1))
            zeroGroupIndex.append(len(zeroGroups) - 1)

        return zeroGroups, zeroGroupIndex

    def _getZeroMergeLengths(self, zeroGroups):
        res = []
        for i in range(len(zeroGroups) - 1):
            res.append(
                zeroGroups[i].length + zeroGroups[i + 1].length
            )
        return res

    def _mapToAdjacentGroupIndices(self, startGroupIndex, endGroupIndex):
        return startGroupIndex, endGroupIndex - 1