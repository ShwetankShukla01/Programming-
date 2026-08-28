class Solution {

    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();

        int[] count = new int[26];

        // Characters available from s
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        // Start by assuming we match target completely.
        // Subtract characters used by target.
        for (char c : target.toCharArray()) {
            count[c - 'a']--;
        }

        // Try to make the string greater at the rightmost possible position.
        for (int i = n - 1; i >= 0; i--) {

            // Put target[i] back into the available characters.
            int current = target.charAt(i) - 'a';
            count[current]++;

            // Check whether target[0..i-1] can be formed from s.
            boolean possible = true;

            for (int c = 0; c < 26; c++) {
                if (count[c] < 0) {
                    possible = false;
                    break;
                }
            }

            if (!possible) {
                continue;
            }

            // Find the smallest character greater than target[i].
            for (int c = current + 1; c < 26; c++) {

                if (count[c] == 0) {
                    continue;
                }

                StringBuilder ans = new StringBuilder();

                // Keep prefix equal to target.
                ans.append(target, 0, i);

                // Make the string strictly greater.
                ans.append((char) ('a' + c));
                count[c]--;

                // Fill the rest with the smallest characters.
                for (int ch = 0; ch < 26; ch++) {
                    while (count[ch] > 0) {
                        ans.append((char) ('a' + ch));
                        count[ch]--;
                    }
                }

                return ans.toString();
            }
        }

        return "";
    }
}