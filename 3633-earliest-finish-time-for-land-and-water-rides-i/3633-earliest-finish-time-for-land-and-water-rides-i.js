var earliestFinishTime = function(landStartTime, landDuration, waterStartTime, waterDuration) {
    let earliest = Infinity;

    for (let i = 0; i < landStartTime.length; i++) {
        const landFinish = landStartTime[i] + landDuration[i];

        for (let j = 0; j < waterStartTime.length; j++) {
            // Land first, then water
            earliest = Math.min(
                earliest,
                Math.max(landFinish, waterStartTime[j]) + waterDuration[j]
            );

            // Water first, then land
            const waterFinish = waterStartTime[j] + waterDuration[j];
            earliest = Math.min(
                earliest,
                Math.max(waterFinish, landStartTime[i]) + landDuration[i]
            );
        }
    }

    return earliest;
};