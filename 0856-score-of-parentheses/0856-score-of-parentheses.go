func scoreOfParentheses(s string) int {
    depth, score := 0, 0

    for i := 0; i < len(s); i++ {
        if s[i] == '(' {
            depth++
        } else {
            depth--
            // A primitive "()" scores 1, doubled for each outer pair.
            if i > 0 && s[i-1] == '(' {
                score += 1 << depth
            }
        }
    }

    return score
}