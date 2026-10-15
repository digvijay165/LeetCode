class Solution {
public:
    string maxSumOfSquares(int num, int sum) {
        if (sum < 1 || sum > 9LL * num) return "";

        string result;
        int nines = sum / 9;
        int remainder = sum % 9;

        result.append(nines, '9');
        if (remainder > 0) {
            result.push_back('0' + remainder);
        }
        result.append(num - result.size(), '0');

        return result;
    }
};