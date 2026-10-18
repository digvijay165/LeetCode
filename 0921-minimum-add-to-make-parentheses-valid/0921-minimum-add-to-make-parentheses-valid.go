func minAddToMakeValid(s string) int {
    open, additions := 0, 0

    for _, ch := range s {
        if ch == '(' {
            open++
        } else if open > 0 {
            open--
        } else {
            additions++ // Add a '(' before this unmatched ')'
        }
    }

    return additions + open // Add ')' for each unmatched '('
}