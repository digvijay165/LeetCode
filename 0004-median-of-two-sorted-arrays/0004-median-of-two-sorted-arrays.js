var findMedianSortedArrays = function(nums1, nums2) {
    let a = nums1;
    let b = nums2;

    if (a.length > b.length) {
        [a, b] = [b, a];
    }

    const m = a.length;
    const n = b.length;
    const half = Math.floor((m + n + 1) / 2);
    let low = 0;
    let high = m;

    while (low <= high) {
        const i = Math.floor((low + high) / 2);
        const j = half - i;

        const leftA = i === 0 ? -Infinity : a[i - 1];
        const rightA = i === m ? Infinity : a[i];
        const leftB = j === 0 ? -Infinity : b[j - 1];
        const rightB = j === n ? Infinity : b[j];

        if (leftA <= rightB && leftB <= rightA) {
            if ((m + n) % 2 === 1) {
                return Math.max(leftA, leftB);
            }
            return (Math.max(leftA, leftB) + Math.min(rightA, rightB)) / 2;
        }

        if (leftA > rightB) {
            high = i - 1;
        } else {
            low = i + 1;
        }
    }
};