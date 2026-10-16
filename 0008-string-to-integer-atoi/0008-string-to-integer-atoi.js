var myAtoi = function(s) {
    let i = 0;

    while (i < s.length && s[i] === " ") {
        i++;
    }

    let sign = 1;
    if (s[i] === "+" || s[i] === "-") {
        if (s[i] === "-") sign = -1;
        i++;
    }

    const limit = sign === 1 ? 2147483647 : 2147483648;
    let value = 0;

    while (i < s.length) {
        const digit = s.charCodeAt(i) - 48;
        if (digit < 0 || digit > 9) break;

        if (value > Math.floor((limit - digit) / 10)) {
            return sign === 1 ? 2147483647 : -2147483648;
        }

        value = value * 10 + digit;
        i++;
    }

    return sign * value;
};