func minInsertions(s string) int {
    insertions, need := 0, 0

    for i := 0; i < len(s); i++ {
        if s[i] == '(' {
            // Each '(' needs two consecutive ')'.
            if need%2 == 1 {
                insertions++
                need--
            }
            need += 2
        } else {
            need--
            if need < 0 {
                // Insert '(' before this unmatched ')'.
                insertions++
                need = 1
            }
        }
    }

    return insertions + need
}