func pivotArray(nums []int, pivot int) []int {
	less, equal, greater := make([]int, 0), make([]int, 0), make([]int, 0)

	for _, num := range nums {
		if num < pivot {
			less = append(less, num)
		} else if num == pivot {
			equal = append(equal, num)
		} else {
			greater = append(greater, num)
		}
	}

	answer := append(less, equal...)
	answer = append(answer, greater...)
	return answer
}