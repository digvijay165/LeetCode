func checkValidString(s string) bool {
    minOpen, maxOpen := 0, 0

    for _, ch := range s {
        switch ch {
        case '(':
            minOpen++
            maxOpen++
        case ')':
            minOpen--
            maxOpen--
        case '*':
            minOpen-- // Treat * as ')' or empty
            maxOpen++ // Treat * as '('
        }

        if maxOpen < 0 {
            return false
        }
        if minOpen < 0 {
            minOpen = 0
        }
    }

    return minOpen == 0
}