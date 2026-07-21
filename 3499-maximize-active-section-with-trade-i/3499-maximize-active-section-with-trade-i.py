import itertools

class Solution(object):
    def maxActiveSectionsAfterTrade(self, s):
        zeroGroups = [len(list(g)) for c, g in itertools.groupby(s) if c == '0']

        max_sum = 0
        for i in range(len(zeroGroups) - 1):
            max_sum = max(max_sum, zeroGroups[i] + zeroGroups[i + 1])

        return s.count('1') + max_sum