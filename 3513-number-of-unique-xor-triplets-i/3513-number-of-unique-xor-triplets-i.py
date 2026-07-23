class Solution:
    def uniqueXorTriplets(self, nums):
        n = len(nums)

        if n < 3:
            return n

        return 1 << n.bit_length()