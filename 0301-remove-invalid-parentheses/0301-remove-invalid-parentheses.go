func removeInvalidParentheses(s string) []string {
    leftRemove, rightRemove := 0, 0

    for i := 0; i < len(s); i++ {
        if s[i] == '(' {
            leftRemove++
        } else if s[i] == ')' {
            if leftRemove > 0 {
                leftRemove--
            } else {
                rightRemove++
            }
        }
    }

    results := make(map[string]struct{})
    path := make([]byte, 0, len(s))

    var dfs func(int, int, int, int)
    dfs = func(i, balance, left, right int) {
        if i == len(s) {
            if balance == 0 && left == 0 && right == 0 {
                results[string(path)] = struct{}{}
            }
            return
        }

        ch := s[i]

        if ch == '(' {
            if left > 0 {
                dfs(i+1, balance, left-1, right) // Remove it
            }
            path = append(path, ch)
            dfs(i+1, balance+1, left, right) // Keep it
            path = path[:len(path)-1]
        } else if ch == ')' {
            if right > 0 {
                dfs(i+1, balance, left, right-1) // Remove it
            }
            if balance > 0 {
                path = append(path, ch)
                dfs(i+1, balance-1, left, right) // Keep it
                path = path[:len(path)-1]
            }
        } else {
            path = append(path, ch)
            dfs(i+1, balance, left, right)
            path = path[:len(path)-1]
        }
    }

    dfs(0, 0, leftRemove, rightRemove)

    answer := make([]string, 0, len(results))
    for value := range results {
        answer = append(answer, value)
    }
    return answer
}