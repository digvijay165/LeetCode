var longestPalindrome = function(s) {
    let start = 0;
    let maxLength = 1;

    function expand(left, right) {
        while (
            left >= 0 &&
            right < s.length &&
            s[left] === s[right]
        ) {
            const length = right - left + 1;

            if (length > maxLength) {
                start = left;
                maxLength = length;
            }

            left--;
            right++;
        }
    }

    for (let i = 0; i < s.length; i++) {
        expand(i, i);       // Odd-length palindrome
        expand(i, i + 1);   // Even-length palindrome
    }

    return s.substring(start, start + maxLength);
};