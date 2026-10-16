var lengthOfLongestSubstring = function(s) {
    const lastSeen = new Map();
    let left = 0;
    let longest = 0;

    for (let right = 0; right < s.length; right++) {
        const char = s[right];

        if (lastSeen.has(char) && lastSeen.get(char) >= left) {
            left = lastSeen.get(char) + 1;
        }

        lastSeen.set(char, right);
        longest = Math.max(longest, right - left + 1);
    }

    return longest;
};