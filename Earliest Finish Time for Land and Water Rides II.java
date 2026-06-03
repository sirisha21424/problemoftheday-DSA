class Solution {

    public int earliestFinishTime(int[] landStartTime, int[] landDuration,
                                  int[] waterStartTime, int[] waterDuration) {

        int ans = Integer.MAX_VALUE;

        ans = Math.min(ans,
                solve(landStartTime, landDuration,
                      waterStartTime, waterDuration));

        ans = Math.min(ans,
                solve(waterStartTime, waterDuration,
                      landStartTime, landDuration));

        return ans;
    }

    private int solve(int[] start1, int[] dur1,
                      int[] start2, int[] dur2) {

        int m = start2.length;

        int[][] rides = new int[m][2];
        for (int i = 0; i < m; i++) {
            rides[i][0] = start2[i];
            rides[i][1] = dur2[i];
        }

        Arrays.sort(rides, (a, b) -> Integer.compare(a[0], b[0]));

        int[] starts = new int[m];
        int[] prefixMinDur = new int[m];
        int[] suffixMinFinish = new int[m];

        for (int i = 0; i < m; i++) {
            starts[i] = rides[i][0];
        }

        prefixMinDur[0] = rides[0][1];
        for (int i = 1; i < m; i++) {
            prefixMinDur[i] = Math.min(prefixMinDur[i - 1], rides[i][1]);
        }

        suffixMinFinish[m - 1] = rides[m - 1][0] + rides[m - 1][1];
        for (int i = m - 2; i >= 0; i--) {
            suffixMinFinish[i] = Math.min(
                    suffixMinFinish[i + 1],
                    rides[i][0] + rides[i][1]
            );
        }

        int res = Integer.MAX_VALUE;

        for (int i = 0; i < start1.length; i++) {

            int finish1 = start1[i] + dur1[i];

            int idx = upperBound(starts, finish1) - 1;

            if (idx >= 0) {
                res = Math.min(res,
                        finish1 + prefixMinDur[idx]);
            }

            if (idx + 1 < m) {
                res = Math.min(res,
                        suffixMinFinish[idx + 1]);
            }
        }

        return res;
    }

    private int upperBound(int[] arr, int target) {
        int l = 0, r = arr.length;

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (arr[mid] <= target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }
}
