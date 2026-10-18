func leftRightDifference(nums []int) []int {
    total := 0
    for _, num := range nums {
        total += num
    }

    answer := make([]int, len(nums))
    leftSum := 0

    for i, num := range nums {
        rightSum := total - leftSum - num
        diff := leftSum - rightSum
        if diff < 0 {
            diff = -diff
        }
        answer[i] = diff
        leftSum += num
    }

    return answer
}