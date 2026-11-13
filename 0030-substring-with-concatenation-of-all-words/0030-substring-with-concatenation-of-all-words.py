from collections import deque
from typing import List

class Solution:
    def findSubstring(self, s: str, words: List[str]) -> List[int]:

        dit = {}
        n_word = len(words[0])
        lst_len = len(words)
        ans = []

        if n_word * lst_len > len(s):
            return []

        for word in words:
            dit[word] = dit.get(word, 0) + 1

        for offset in range(n_word):

            compare = {word: 0 for word in dit}
            q = deque()
            left = offset

            for right in range(offset, len(s) - n_word + 1, n_word):

                x = s[right:right+n_word]
                q.append(x)

                if x in dit:
                    compare[x] += 1

                if len(q) > lst_len:
                    z = q.popleft()

                    if z in dit:
                        compare[z] -= 1

                    left += n_word

                if len(q) == lst_len and dit == compare:
                    ans.append(left)

        return ans