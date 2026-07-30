import collections

class Solution(object):
    def minimumPushes(self, word):
        """
        :type word: str
        :rtype: int
        """
        freqs = sorted(collections.Counter(word).values(), reverse=True)

        ans = 0
        for i, freq in enumerate(freqs):
            ans += freq * (i // 8 + 1)

        return ans