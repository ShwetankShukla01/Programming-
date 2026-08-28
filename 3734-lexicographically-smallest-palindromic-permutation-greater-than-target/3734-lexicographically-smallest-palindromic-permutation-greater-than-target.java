class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();
        int half = n / 2;

        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // A palindrome can have at most one odd-frequency character.
        int odd = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 != 0) {
                odd++;
            }
        }

        if (odd > 1) {
            return "";
        }

        // Frequency available for the first half.
        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        String targetHalf = target.substring(0, half);

        // --------------------------------------------------
        // 1. Try the exact target first half.
        // --------------------------------------------------
        int[] remaining = halfFreq.clone();
        boolean possible = true;

        for (int i = 0; i < half; i++) {

            int c = targetHalf.charAt(i) - 'a';

            if (remaining[c] == 0) {
                possible = false;
                break;
            }

            remaining[c]--;
        }

        if (possible) {

            String candidate =
                buildPalindrome(targetHalf, freq, n);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        // --------------------------------------------------
        // 2. Find the smallest first half greater than target.
        // --------------------------------------------------
        String nextHalf = findGreaterHalf(targetHalf, halfFreq);

        if (nextHalf == null) {
            return "";
        }

        return buildPalindrome(nextHalf, freq, n);
    }

    private String findGreaterHalf(String targetHalf, int[] freq) {

        int n = targetHalf.length();

        // Try changing the rightmost possible position.
        for (int pos = n - 1; pos >= 0; pos--) {

            int[] remaining = freq.clone();

            // Match the prefix before 'pos'.
            boolean possible = true;

            for (int i = 0; i < pos; i++) {

                int c = targetHalf.charAt(i) - 'a';

                if (remaining[c] == 0) {
                    possible = false;
                    break;
                }

                remaining[c]--;
            }

            if (!possible) {
                continue;
            }

            int targetChar = targetHalf.charAt(pos) - 'a';

            // Choose the smallest character greater than target[pos].
            for (int c = targetChar + 1; c < 26; c++) {

                if (remaining[c] == 0) {
                    continue;
                }

                StringBuilder result = new StringBuilder();

                // Prefix equal to target.
                result.append(targetHalf, 0, pos);

                // First character that makes it greater.
                result.append((char) ('a' + c));

                remaining[c]--;

                // Smallest possible suffix.
                for (int ch = 0; ch < 26; ch++) {
                    while (remaining[ch] > 0) {
                        result.append((char) ('a' + ch));
                        remaining[ch]--;
                    }
                }

                return result.toString();
            }
        }

        return null;
    }

    private String buildPalindrome(
            String firstHalf,
            int[] freq,
            int n) {

        StringBuilder result = new StringBuilder();

        // First half
        result.append(firstHalf);

        // Middle character
        if (n % 2 == 1) {

            for (int i = 0; i < 26; i++) {

                if (freq[i] % 2 != 0) {
                    result.append((char) ('a' + i));
                    break;
                }
            }
        }

        // Second half = reverse of first half
        for (int i = firstHalf.length() - 1; i >= 0; i--) {
            result.append(firstHalf.charAt(i));
        }

        return result.toString();
    }
}