import "strconv"

type wavinessState struct {
    ways int64
    sum  int64
}

func totalWaviness(num1 int64, num2 int64) int64 {
    return wavinessUpTo(num2) - wavinessUpTo(num1-1)
}

func wavinessUpTo(n int64) int64 {
    if n < 0 {
        return 0
    }

    digits := strconv.FormatInt(n, 10)

    var memo [20][2][11][11]wavinessState
    var seen [20][2][11][11]bool

    var dfs func(pos, prev2, prev1, tight int) wavinessState
    dfs = func(pos, prev2, prev1, tight int) wavinessState {
        if pos == len(digits) {
            return wavinessState{ways: 1}
        }

        if seen[pos][tight][prev2][prev1] {
            return memo[pos][tight][prev2][prev1]
        }
        seen[pos][tight][prev2][prev1] = true

        limit := 9
        if tight == 1 {
            limit = int(digits[pos] - '0')
        }

        var result wavinessState

        for d := 0; d <= limit; d++ {
            nextTight := 0
            if tight == 1 && d == limit {
                nextTight = 1
            }

            nextPrev2, nextPrev1 := prev2, prev1
            extra := int64(0)

            if prev1 == 10 { // Still processing leading zeros
                if d != 0 {
                    nextPrev1 = d
                }
            } else if prev2 == 10 { // Only one significant digit so far
                nextPrev2 = prev1
                nextPrev1 = d
            } else {
                if (prev1 > prev2 && prev1 > d) ||
                    (prev1 < prev2 && prev1 < d) {
                    extra = 1
                }
                nextPrev2 = prev1
                nextPrev1 = d
            }

            child := dfs(pos+1, nextPrev2, nextPrev1, nextTight)
            result.ways += child.ways
            result.sum += child.sum + extra*child.ways
        }

        memo[pos][tight][prev2][prev1] = result
        return result
    }

    return dfs(0, 10, 10, 1).sum
}