func minSumSquareDiff(nums1 []int, nums2 []int, k1 int, k2 int) int64 {
    maxDiff := 0
    for i := range nums1 {
        diff := nums1[i] - nums2[i]
        if diff < 0 {
            diff = -diff
        }
        if diff > maxDiff {
            maxDiff = diff
        }
    }

    freq := make([]int64, maxDiff+1)
    for i := range nums1 {
        diff := nums1[i] - nums2[i]
        if diff < 0 {
            diff = -diff
        }
        freq[diff]++
    }

    ops := int64(k1) + int64(k2)

    for d := maxDiff; d > 0 && ops > 0; d-- {
        reduce := freq[d]
        if reduce > ops {
            reduce = ops
        }

        freq[d] -= reduce
        freq[d-1] += reduce
        ops -= reduce
    }

    var answer int64
    for d, count := range freq {
        answer += count * int64(d) * int64(d)
    }

    return answer
}