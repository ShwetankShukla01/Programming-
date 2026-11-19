class Solution:
    def grayCode(self, n: int) -> List[int]:
        # Calculate the number of Gray Code values
        size = 1 << n

        # Create a list to store the result
        res = []

        # Generate Gray Codes using XOR and right shift
        for i in range(size):
            res.append(i ^ (i >> 1))

        # Return the generated sequence
        return res