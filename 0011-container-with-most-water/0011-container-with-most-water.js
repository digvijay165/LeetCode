var maxArea = function(height) {
  let left = 0;
  let right = height.length - 1;
  let maxWater = 0;

  while (left < right) {
    const width = right - left;
    const currentArea = width * Math.min(height[left], height[right]);
    maxWater = Math.max(maxWater, currentArea);

    // The shorter line limits the water, so move that side inward.
    if (height[left] < height[right]) {
      left++;
    } else {
      right--;
    }
  }

  return maxWater;
};