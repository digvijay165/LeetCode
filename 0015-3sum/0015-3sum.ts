
function threeSum(nums: number[]): number[][] {
    nums.sort((a, b) => a - b);

    const result: number[][] = [];
    const n = nums.length;

    for (let i = 0; i < n - 2; i++) {
        // Skip duplicate first elements
        if (i > 0 && nums[i] === nums[i - 1]) {
            continue;
        }

        // Since the array is sorted, no further triplet can sum to zero
        if (nums[i] > 0) {
            break;
        }

        let left = i + 1;
        let right = n - 1;

        while (left < right) {
            const sum = nums[i] + nums[left] + nums[right];

            if (sum === 0) {
                result.push([nums[i], nums[left], nums[right]]);

                left++;
                right--;

                // Skip duplicate second elements
                while (left < right && nums[left] === nums[left - 1]) {
                    left++;
                }

                // Skip duplicate third elements
                while (left < right && nums[right] === nums[right + 1]) {
                    right--;
                }
            } else if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }
    }

    return result;
};
