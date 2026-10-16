var isMatch = function(s, p) {
  const m = s.length;
  const n = p.length;
  const dp = Array.from({ length: m + 1 }, () => Array(n + 1).fill(false));

  dp[0][0] = true;

  // Handle patterns like a*, a*b*, and so on matching an empty string.
  for (let j = 2; j <= n; j++) {
    if (p[j - 1] === "*") {
      dp[0][j] = dp[0][j - 2];
    }
  }

  for (let i = 1; i <= m; i++) {
    for (let j = 1; j <= n; j++) {
      const patternChar = p[j - 1];

      if (patternChar === "." || patternChar === s[i - 1]) {
        dp[i][j] = dp[i - 1][j - 1];
      } else if (patternChar === "*") {
        // Use zero occurrences of the preceding pattern character.
        dp[i][j] = dp[i][j - 2];

        // Or use one more occurrence if that character matches.
        const precedingChar = p[j - 2];
        if (precedingChar === "." || precedingChar === s[i - 1]) {
          dp[i][j] = dp[i][j] || dp[i - 1][j];
        }
      }
    }
  }

  return dp[m][n];
};