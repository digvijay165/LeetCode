import "strings"

func removeOuterParentheses(s string) string {
    var result strings.Builder
    depth := 0

    for i := 0; i < len(s); i++ {
        if s[i] == '(' {
            if depth > 0 {
                result.WriteByte('(')
            }
            depth++
        } else {
            depth--
            if depth > 0 {
                result.WriteByte(')')
            }
        }
    }

    return result.String()
}