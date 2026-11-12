class Solution:
    def solveNQueens(self,n:int)->List[List[str]]:
        ans=[]
        tans=[['.' for _ in range(n)] for _ in range(n)]
        ld=[False]*(2*n-1)
        rd=[False]*(2*n-1)
        col=[False]*n

        def placequeen(i):
            if i==n:
                ans.append([''.join(row) for row in tans])
                return
            for j in range(n):
                if col[j] or ld[n-1+i-j] or rd[i+j]:
                    continue
                col[j]=True
                ld[n-1+i-j]=True
                rd[i+j]=True
                tans[i][j]='Q'
                placequeen(i+1)
                col[j]=False
                ld[n-1+i-j]=False
                rd[i+j]=False
                tans[i][j]='.'

        placequeen(0)
        return ans