from bisect import bisect_right
from typing import List

class Solution:
    def earliestFinishTime(
        self,
        landStartTime: List[int],
        landDuration: List[int],
        waterStartTime: List[int],
        waterDuration: List[int],
    ) -> int:
        def prepare(starts, durations):
            rides = sorted(zip(starts, durations))
            sorted_starts = [start for start, _ in rides]

            prefix_min_duration = [float("inf")]
            for _, duration in rides:
                prefix_min_duration.append(
                    min(prefix_min_duration[-1], duration)
                )

            suffix_min_finish = [float("inf")] * (len(rides) + 1)
            for i in range(len(rides) - 1, -1, -1):
                start, duration = rides[i]
                suffix_min_finish[i] = min(
                    suffix_min_finish[i + 1], start + duration
                )

            return sorted_starts, prefix_min_duration, suffix_min_finish

        def second_ride_finish(first_finish, rides):
            starts, prefix_duration, suffix_finish = rides
            k = bisect_right(starts, first_finish)
            best = float("inf")

            if k > 0:
                best = first_finish + prefix_duration[k]
            if k < len(starts):
                best = min(best, suffix_finish[k])

            return best

        land = prepare(landStartTime, landDuration)
        water = prepare(waterStartTime, waterDuration)

        answer = float("inf")

        for start, duration in zip(landStartTime, landDuration):
            answer = min(answer, second_ride_finish(start + duration, water))

        for start, duration in zip(waterStartTime, waterDuration):
            answer = min(answer, second_ride_finish(start + duration, land))

        return answer