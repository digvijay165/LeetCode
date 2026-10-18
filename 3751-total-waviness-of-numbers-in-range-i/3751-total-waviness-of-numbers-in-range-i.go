import "strconv"

func totalWaviness(num1 int, num2 int) int {
    total := 0

    for n := num1; n <= num2; n++ {
        digits := strconv.Itoa(n)

        for i := 1; i < len(digits)-1; i++ {
            left := digits[i-1]
            middle := digits[i]
            right := digits[i+1]

            if (middle > left && middle > right) ||
                (middle < left && middle < right) {
                total++
            }
        }
    }

    return total
}