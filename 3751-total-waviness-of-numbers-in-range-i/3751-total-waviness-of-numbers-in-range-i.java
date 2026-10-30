class Solution {

    public int totalWaviness(int num1, int num2) {

        int ans = 0;

        for (int x = num1; x <= num2; x++) {

            String s = String.valueOf(x);

            // First and last digits cannot be wavy
            for (int i = 1; i < s.length() - 1; i++) {

                char previous = s.charAt(i - 1);
                char current = s.charAt(i);
                char next = s.charAt(i + 1);

                // Check if current digit is a peak
                boolean isPeak =
                    current > previous && current > next;

                // Check if current digit is a valley
                boolean isValley =
                    current < previous && current < next;

                if (isPeak || isValley) {
                    ans++;
                }
            }
        }

        return ans;
    }
}