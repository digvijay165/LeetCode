var reverse = function(x) {
    const INT_MAX = 2147483647;
    const INT_MIN = -2147483648;
    let reversed = 0;

    while (x !== 0) {
        const digit = x % 10;
        x = Math.trunc(x / 10);

        if (
            reversed > 214748364 ||
            (reversed === 214748364 && digit > 7) ||
            reversed < -214748364 ||
            (reversed === -214748364 && digit < -8)
        ) {
            return 0;
        }

        reversed = reversed * 10 + digit;
    }

    return reversed;
};