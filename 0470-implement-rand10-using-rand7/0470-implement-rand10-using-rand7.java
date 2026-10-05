class Solution extends SolBase {
    
    public int rand10() {
      
        int column = rand7();
        int row = rand7();

        int val = (column) + (row - 1) * 7; 
        
        if (val <= 40) {
            return (val - 1) % 10 + 1; 
        } else {
            return rand10();  
        }
        
    }
}