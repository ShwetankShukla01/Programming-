import collections

class Solution(object):

    def __init__(self):
        self.MAX = 10 ** 6 + 1

    def smallestPalindrome(self, s, k):
        count = collections.Counter(s)

        if not self._isPalindromePossible(count):
            return ""

        halfCount, midLetter = self._getHalfCountAndMidLetter(count)

        totalPerm = self._calculateTotalPermutations(halfCount)

        if k > totalPerm:
            return ""

        leftHalf = self._generateLeftHalf(halfCount, k)

        return "".join(leftHalf) + midLetter + "".join(reversed(leftHalf))

    def _isPalindromePossible(self, count):
        oddCount = 0
        for freq in count.values():
            if freq % 2 == 1:
                oddCount += 1
        return oddCount <= 1

    def _getHalfCountAndMidLetter(self, count):
        halfCount = [0] * 26
        midLetter = ""

        for c, freq in count.items():
            halfCount[ord(c) - ord('a')] = freq // 2
            if freq % 2 == 1:
                midLetter = c

        return halfCount, midLetter

    def _calculateTotalPermutations(self, halfCount):
        return self._countArrangements(halfCount)

    def _generateLeftHalf(self, halfCount, k):
        halfLen = sum(halfCount)
        left = []

        for _ in xrange(halfLen):
            for i in xrange(26):
                if halfCount[i] == 0:
                    continue

                halfCount[i] -= 1
                arrangements = self._countArrangements(halfCount)

                if arrangements >= k:
                    left.append(chr(i + ord('a')))
                    break
                else:
                    k -= arrangements
                    halfCount[i] += 1

        return left

    def _countArrangements(self, count):
        total = sum(count)
        res = 1

        for freq in count:
            res *= self._nCk(total, freq)

            if res >= self.MAX:
                return self.MAX

            total -= freq

        return res

    def _nCk(self, n, k):
        if k < 0 or k > n:
            return 0

        k = min(k, n - k)
        res = 1

        for i in xrange(1, k + 1):
            res = res * (n - i + 1) // i

            if res >= self.MAX:
                return self.MAX

        return res
    