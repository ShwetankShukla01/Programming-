class Solution:
    def smallestTrimmedNumbers(self, nums: List[str], queries: List[List[int]]) -> List[int]:
        max_trim = max(queries, key=lambda x: x[1])[1]
        # radix sort
        l = [[[x,i] for i,x in enumerate(nums)]]
        for i in range(max_trim):
            l.append(self.counting_sort(l[i], i+1))
        # get answer
        ans = []
        for k, trim in queries:
            ans.append(l[trim][k-1][1])
        return ans

    def counting_sort(self, lst: List[int], z: int) -> None:
        counts = [0] * 10

        for elem in lst:
            digit = ord(elem[0][-z])-48
            counts[digit] += 1

        starting_index = 0
        for i, count in enumerate(counts):
            counts[i] = starting_index
            starting_index += count

        sorted_lst = [0] * len(lst)
        for elem in lst:
            digit = ord(elem[0][-z])-48
            sorted_lst[counts[digit]] = elem
            counts[digit] += 1

        return sorted_lst