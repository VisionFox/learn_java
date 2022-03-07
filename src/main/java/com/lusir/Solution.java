package com.lusir;

import com.alibaba.fastjson.JSON;

import java.util.*;

public class Solution {
    public int searchInsert(int[] nums, int target) {
        int head = 0, tail = nums.length - 1;
        if (nums[head] > target) {
            return head;
        }
        if (nums[tail] < target) {
            return tail + 1;
        }

        while (head < tail) {
            if (nums[head] == target) {
                return head;
            }
            if (nums[tail] == target) {
                return tail;
            }

            int center = (head + tail) / 2;
            int centerNum = nums[center];
            if (centerNum == target) {
                return center;
            }
            if (centerNum < target) {
                head = center + 1;
            }
            if (centerNum > target) {
                tail = center - 1;
            }
        }

        if (nums[head] > target) {
            return head;
        }

        if (nums[head] < target) {
            return head + 1;
        }

        return 0;
    }

    public double findMedianSortedArrays(int[] A, int[] B) {
        int m = A.length, n = B.length;
        int len = n + m;
        int aStart = 0, bStart = 0;
        int left = 0, right = 0;
        for (int i = 0; i <= len / 2; i++) {
            left = right;
            if (aStart < m && (bStart >= n || A[aStart] < B[bStart])) {
                right = A[aStart++];
            } else {
                right = B[bStart++];
            }
        }
        // len为奇数时中位数为len/2向下取整；len为偶数时中位数为len/2-1、len/2
        if ((len & 1) == 1) {
            return right;
        } else {
            return (left + right) / 2.0;
        }
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        // 区间 [a, b) 包含 k 个待反转元素
        ListNode a = head, b = head;
        for (int i = 0; i < k; i++) {
            // 不足 k 个，不需要反转
            if (b == null) {
                return head;
            }
            b = b.next;
        }
        // 反转前 k 个元素
        ListNode newHead = reverse(a, b);
        // 递归反转后续链表并连接起来
        a.next = reverseKGroup(b, k);
        return newHead;
    }

    ListNode reverse(ListNode a) {
        ListNode pre = null, cur = a, next = a;
        while (cur != null) {
            next = cur.next;
            // 逐个结点反转
            cur.next = pre;
            // 更新指针位置
            pre = cur;
            cur = next;
        }
        // 返回反转后的头结点
        return pre;
    }

    ListNode reverse(ListNode a, ListNode b) {
        // 反转区间 [a, b) 的元素，注意是左闭右开
        // pre为b
        ListNode pre = b, cur = a, next = a;
        // while 终止的条件改一下就行了
        while (cur != b) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int n = prices.length;
        int[][][] dp = new int[n][2][2];
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                dp[i][0][1] = -prices[i];
                continue;
            }
            dp[i][0][0] = Math.max(dp[i - 1][0][0], dp[i - 1][0][1] + prices[i]);
            dp[i][0][1] = Math.max(dp[i - 1][0][0] - prices[i], dp[i - 1][0][1]);
            dp[i][1][0] = Math.max(dp[i - 1][1][0], dp[i - 1][1][1] + prices[i]);
            dp[i][1][1] = Math.max(dp[i - 1][0][0] - prices[i], dp[i - 1][1][1]);
        }
        return Math.max(dp[n - 1][1][0], dp[n - 1][0][0]);
    }

    private int maxNum(int... nums) {
        int max = Integer.MIN_VALUE;
        for (int tmp : nums) {
            max = (tmp > max) ? tmp : max;
        }
        return max;
    }

    int[][] memo;

    public int superEggDrop(int K, int N) {
        return dropEgg(N, K);
    }

    private int dropEggV3(int eggNum, int floor) {
        for (int i = 0; i <= eggNum; i++) {
            for (int j = 0; j <= floor; j++) {
                if (i == 0 || j == 0) {
                    memo[i][j] = 0;
                } else if (i == 1) {
                    memo[i][j] = j;
                } else if (j == 1) {
                    memo[i][j] = 1;
                } else {
                    memo[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        for (int eggTmp = 2; eggTmp <= eggNum; eggTmp++) {
            for (int floorTmp = 2; floorTmp <= floor; floorTmp++) {
                for (int randFloor = 2; randFloor <= floorTmp; randFloor++) {
                    int broken = 1 + memo[eggTmp - 1][randFloor - 1];
                    int notBroken = 1 + memo[eggTmp][floorTmp - randFloor];
                    int mostBad = Math.max(broken, notBroken);
                    memo[eggTmp][floorTmp] = Math.min(mostBad, memo[eggTmp][floorTmp]);
                }
            }
        }

        return memo[eggNum][floor];
    }

    private int dropEggV2(int floor, int eggNum) {
        if (floor == 0) {
            return 0;
        }

        if (eggNum == 1 || floor == 1) {
            return floor;
        }

        int res = Integer.MAX_VALUE;
        // 每层楼扔一下取min
        for (int randFloorDrop = 1; randFloorDrop <= floor; randFloorDrop++) {
            int broken = 1 + dropEgg(randFloorDrop - 1, eggNum - 1);
            int notBroken = 1 + dropEgg(floor - randFloorDrop, eggNum);
            // 最差情况
            int mostBad = Math.max(broken, notBroken);
            // 选最优
            res = Math.min(mostBad, res);
        }
        return res;
    }

    private int dropEgg(int floor, int eggNum) {
        if (floor == 0 || eggNum == 0) {
            return 0;
        }

        if (eggNum == 1 || floor == 1) {
            return floor;
        }

        int res = Integer.MAX_VALUE;
        // 每层楼扔一下取min
        for (int randFloorDrop = 2; randFloorDrop <= floor; randFloorDrop++) {
            int broken = 1 + dropEgg(randFloorDrop - 1, eggNum - 1);
            int notBroken = 1 + dropEgg(floor - randFloorDrop, eggNum);
            // 最差情况
            int mostBad = Math.max(broken, notBroken);
            // 选最优
            res = Math.min(mostBad, res);
        }
        return res;
    }

    public int singleNumber(int[] nums) {
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            res = nums[i] ^ res;
        }
        return res;
    }

    public void merge(int[] A, int m, int[] B, int n) {
        int aPoint = m - 1;
        int bPoint = n - 1;
        int resPoint = m + n - 1;

        while (aPoint >= 0 && bPoint >= 0) {
            if (A[aPoint] > B[bPoint]) {
                A[resPoint--] = A[aPoint--];
            } else {
                A[resPoint--] = B[bPoint--];
            }
        }

        while (aPoint >= 0) {
            A[resPoint--] = A[aPoint--];
        }

        while (bPoint >= 0) {
            A[resPoint--] = B[bPoint--];
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode flagHead = new ListNode(-1);
        ListNode flagTmp = flagHead;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                flagTmp.next = l1;
                l1 = l1.next;
            } else {
                flagTmp.next = l2;
                l2 = l2.next;
            }

            flagTmp = flagTmp.next;
        }

        if (l1 != null) {
            flagTmp.next = l1;
        }

        if (l2 != null) {
            flagTmp.next = l2;
        }

        return flagHead.next;
    }

    public int majorityElement(int[] nums) {
        int flag = nums[0], count = 0;

        for (int num : nums) {
            if (num == flag) {
                count++;
            } else {
                count--;
            }

            if (count == 0) {
                flag = num;
                count++;
            }
        }
        return flag;
    }

//    public List<Integer> majorityElement(int[] nums) {
//        ArrayList res = new ArrayList();
//        if (nums == null || nums.length == 0) {
//            return res;
//        }
//        // 大于n/3证明最多有两人符合条件
//        int flag_1 = nums[0], flag_2 = nums[0];
//        int count_1 = 0, count_2 = 0;
//        for (int num : nums) {
//            //投A
//            if (flag_1 == num) {
//                count_1++;
//                continue;
//            }
//            //投B
//            if (flag_2 == num) {
//                count_2++;
//                continue;
//            }
//            //此时当前值和AB都不等，检查是否有票数减为0的情况，如果为0，则更新候选人
//            if (count_1 == 0) {
//                flag_1 = num;
//                count_1++;
//                continue;
//            }
//
//            if (count_2 == 0) {
//                flag_2 = num;
//                count_2++;
//                continue;
//            }
//            //若此时两个候选人的票数都不为0，且当前元素不投AB，那么A,B对应的票数都要--;
//            count_1--;
//            count_2--;
//        }
//
//        //上一轮遍历找出了两个候选人，但是这两个候选人是否均满足票数大于N/3仍然没法确定，需要重新遍历，确定票数
//        count_1 = 0;
//        count_2 = 0;
//        for (int num : nums) {
//            if (num == flag_1) {
//                count_1++;
//            } else if (num == flag_2) {
//                count_2++;
//            }
//        }
//
//        if (count_1 > nums.length / 3) {
//            res.add(flag_1);
//        }
//        if (count_2 > nums.length / 3) {
//            res.add(flag_2);
//        }
//        return res;
//    }

//    public boolean isPalindrome(String s) {
//        if (s == null) {
//            return false;
//        } else if (s.length() == 0) {
//            return true;
//        }
//
//        int head = 0, tail = s.length() - 1;
//
//        while (head < tail) {
//            //跳过非法字符串
//            if (!isNum(s.charAt(head)) && !isLetter(s.charAt(head))) {
//                head++;
//                continue;
//            }
//
//            if (!isNum(s.charAt(tail)) && !isLetter(s.charAt(tail))) {
//                tail--;
//                continue;
//            }
//
//            if (isNum(s.charAt(head))) {
//                if (s.charAt(head) != s.charAt(tail)) {
//                    return false;
//                }
//            } else if (isLetter(s.charAt(head))) {
//                char headChar = Character.toLowerCase(s.charAt(head));
//                char tailChar = Character.toLowerCase(s.charAt(tail));
//                if (headChar != tailChar) {
//                    return false;
//                }
//            }
//            head++;
//            tail--;
//        }
//
//        return true;
//    }

    private boolean isNum(char c) {
        return (c >= '0' && c <= '9') ? true : false;
    }

    private boolean isLetter(char c) {
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) ? true : false;
    }


    //判断是否为回文串
    private boolean isPalindrome(String s) {
        int head = 0, tail = s.length() - 1;
        while (head < tail) {
            if (s.charAt(head) != s.charAt(tail)) {
                return false;
            }
            head++;
            tail--;
        }
        return true;
    }


    public int[] buildMaxHeap(int arr[]) {
        for (int i = (arr.length - 1 - 1) / 2; i >= 0; i--) {
            adjustUpToDown(arr, i, arr.length);
        }
        return arr;
    }

    private void adjustUpToDown(int[] arr, int k, int len) {
        //temp一直为加入的初始最顶的变量
        int temp = arr[k];
        //沿节点较大的子节点向下调整
        //i为初始化为左孩子节点
        //<len-1,确保i最终也为左孩子
        for (int i = 2 * k + 1; i < len - 1; i = 2 * i + 1) {
            // i变为左右孩子节点中最大节点坐标
            if (i < len && arr[i] < arr[i + 1]) {
                i++;
            }
            // temp大于（上一次i的）左右两子节点就退出
            if (temp >= arr[i]) {
                break;
            } else if (temp < arr[i]) {
                //temp 和（上一次i的）左右两子节点最大的那一个交换位置，其实就是把temp调下去
                arr[k] = arr[i];
                //修改k值，以便继续向下调整，其实k就是下次i的父节点
                k = i;
            }
        }
        //被调整的结点的值放人最终位置
        arr[k] = temp;
    }

    private int[] headSort(int[] arr) {
        arr = buildMaxHeap(arr);
        //初始建堆，array[0]为第一趟值最大的元素
        for (int i = arr.length - 1; i > 1; i--) {
            //将堆顶元素和堆低元素交换，即得到当前最大元素正确的排序位置
            int t = arr[0];
            arr[0] = arr[i];
            //整理，将剩余的元素整理成堆
            adjustUpToDown(arr, 0, i);
        }
        return arr;
    }

    private int[] deleteMax(int[] arr) {
        //将堆的最后一个元素与堆顶元素交换，堆底元素值设为-99999
        arr[0] = arr[arr.length - 1];
        arr[arr.length - 1] = Integer.MIN_VALUE;
        //对此时的根节点进行向下调整
        adjustUpToDown(arr, 0, arr.length);
        return arr;
    }

    public int findKthLargest(int[] nums, int k) {
        int headSize = nums.length;
        buildMaxHeap(nums);
        for (int i = nums.length - 1; i >= nums.length - 1 - k; --i) {
            swap(nums, 0, i);
            --headSize;
            adjustUpToDown(nums, 0, headSize);
        }
        return nums[0];
    }

    public int maxArea(int[] height) {
        int i = 0;
        int j = height.length - 1;
        int res = 0;

        while (i < j) {
            int minLen = (height[i] > height[j]) ? height[j] : height[i];
            int area = (j - i) * minLen;
            res = (area > res) ? area : res;
            if (height[i] > height[j]) {
                j--;
            } else {
                i++;
            }
        }

        return res;
    }

    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int i = 0;
        int j = 1;

        while (j < nums.length) {
            if (nums[i] == nums[j]) {
                j++;
            } else {
                nums[i + 1] = nums[j];
                i++;
            }

        }
        return i + 1;
    }

    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        //dp i 存储第i个位置 最大连续和
        dp[0] = nums[0];
        for (int i = 1; i < n; i++) {
            // 第i个位置最大连续和 ：当前nums[i] 或者 i-1位置的最大和加上当前位置的值
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
        }

        int max = dp[0];
        for (int item : dp) {
            max = Math.max(item, max);
        }
        return max;
    }

    public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }

        if (x == 0.0) {
            return 0;
        }

        return n > 0 ? quickMul(x, n) : 1.0 / quickMul(x, -n);
    }

    private double quickMul(double x, int N) {
        if (N == 0) {
            return 1.0;
        }
        double half = quickMul(x, N / 2);
        return (N % 2 == 0) ? half * half : half * half * x;
    }

    public boolean isValid(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        if (n % 2 == 1) {
            return false;
        }

        Deque<Character> dueue = new LinkedList<Character>();
        Map<Character, Character> pairs = new HashMap<Character, Character>();
        pairs.put(')', '(');
        pairs.put('}', '{');
        pairs.put(']', '[');

        for (int i = 0; i < n; i++) {
            char ch = arr[i];
            if (pairs.containsKey(ch)) {
                if (dueue.isEmpty() || !dueue.peek().equals(pairs.get(ch))) {
                    return false;
                }
                dueue.pop();
            } else {
                dueue.push(ch);
            }
        }

        return dueue.isEmpty();
    }

    public int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() < 2) {
            return 1;
        }

        // etc "abcabcbb"
        int[] tmp = new int[128];
        int maxLen = 0;
        for (int i = 0, j = 0; j < s.length(); j++) {
            // 放在这是错的 因为第0个导致i永远挪到j那 导致结果永远为1
            //tmp[s.charAt(j)] = j;
            i = Math.max(i, tmp[s.charAt(j)]);
            maxLen = Math.max(maxLen, j - i + 1);
            // 这里要加1：因为i要走到s[j]对应的下一个！！！！！！而不是当前 ；不加1的话
            tmp[s.charAt(j)] = j + 1;
        }
        return maxLen;
    }

//    public int findKthLargest(int[] nums, int k) {
//        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
//    }

    private int quickSelect(int[] arr, int left, int right, int index) {
        int q = randomPartition(arr, left, right);
        if (index == q) {
            return arr[q];
        } else {
            return (index < q) ? quickSelect(arr, left, q - 1, index) : quickSelect(arr, q + 1, right, index);
        }
    }

    // 随机主元
    private int randomPartition(int[] arr, int left, int right) {
        Random random = new Random();
        int randomIndex = random.nextInt(right - left + 1) + left;
        swap(arr, randomIndex, right);
        return partition(arr, left, right);
    }

    // 快排
    private int partition(int[] arr, int left, int right) {
        int base = arr[right];
        int i = left, j = right - 1;
        while (i < j) {
            while (i < j && arr[i] < base) {
                i++;
            }
            while (i < j && arr[j] >= base) {
                j--;
            }
            // 麻的 居然是漏了这个判断
            if (i < j) {
                swap(arr, i, j);
            }
        }
        // 麻的 居然还有这个判断，相碰的值可能比base小
        if (arr[i] < base) {
            i++;
        }
        swap(arr, i, right);
        return i;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    public int longestConsecutive(int[] nums) {
        HashSet<Integer> numSet = new HashSet<Integer>();
        for (int n : nums) {
            numSet.add(n);
        }

        int longest = 0;
        for (Integer num : numSet) {
            //判断为连续数字的第一个数
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentLen = 1;
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentLen++;
                }
                longest = Math.max(longest, currentLen);
            }
        }
        return longest;
    }

//    public int lengthOfLIS(int[] nums) {
//        int n = nums.length;
//        if (n <= 1) {
//            return n;
//        }
//
//        int[] dp = new int[n];
//        int res = 1;
//        for (int i = 0; i < n; i++) {
//            // 此时必须前提 res = 1，要不然数字相同进入不了if 答案就为0了
//            dp[i] = 1;
//            for (int j = 0; j < i; j++) {
//                if (nums[j] < nums[i]) {
//                    dp[i] = Math.max(dp[i], dp[j] + 1);
//                    res = Math.max(dp[i], res);
//                }
//            }
//        }
//        return res;
//    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (n <= 0) {
            return head;
        }
        ListNode dummy = new ListNode(0, head);

        ListNode a = dummy, b = head;
        while (n != 0) {
            b = b.next;
            n--;
        }
        while (b.next != null) {
            a = a.next;
            b = b.next;
        }
        ListNode tmp = a.next.next;
        a.next.next = null;
        a.next = tmp;
        return dummy.next;
    }


//    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
//        int m = nums1.length;
//        int n = nums2.length;
//        int left = (m + n + 1) / 2;
//        int right = (m + n + 2) / 2;
//        //中位数 = nums（left + right）/2
//        return (findKth(nums1, 0, nums2, 0, left) + findKth(nums1, 0, nums2, 0, right)) / 2.0;
//    }

    private int findKth(int[] nums1, int i, int[] nums2, int j, int k) {
        //若nums1为空（或是说其中数字全被淘汰了）
        //在nums2中找第k个元素，此时nums2起始位置是j，所以是j+k-1
        if (i >= nums1.length) {
            return nums2[j + k - 1];
        }
        //nums2同理
        if (j >= nums2.length) {
            return nums1[i + k - 1];
        }

        //递归出口
        if (k == 1) {
            return Math.min(nums1[i], nums2[j]);
        }
        //这两个数组的第K/2小的数字，若不足k/2个数字则赋值整型最大值，以便淘汰另一数组的前k/2个数字
        int midVal1 = (i + k / 2 - 1 < nums1.length) ? nums1[i + k / 2 - 1] : Integer.MAX_VALUE;
        int midVal2 = (j + k / 2 - 1 < nums2.length) ? nums2[j + k / 2 - 1] : Integer.MAX_VALUE;
        //二分法核心部分
        if (midVal1 < midVal2) {
            return findKth(nums1, i + k / 2, nums2, j, k - k / 2);
        } else {
            return findKth(nums1, i, nums2, j + k / 2, k - k / 2);
        }
    }

    public boolean isSymmetric(TreeNode root) {
        return check(root.left, root.right);
        // return check(root, root);
    }

    private boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }

        return p.val == q.val && check(p.left, q.right) && check(p.right, q.left);
    }

    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] numsDummy = new int[n + 2];
        numsDummy[0] = 1;
        numsDummy[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            numsDummy[i + 1] = nums[i];
        }

        int[][] dp = new int[numsDummy.length][numsDummy.length];
        // 没定义初始状态，可能是由于数组的初始值为0
        for (int start = numsDummy.length - 3; start >= 0; start--) {
            for (int end = start + 2; end < numsDummy.length; end++) {
                for (int k = start + 1; k < end; k++) {
                    int coins = numsDummy[start] * numsDummy[k] * numsDummy[end];
                    int sum = dp[start][k] + coins + dp[k][end];
                    dp[start][end] = Math.max(dp[start][end], sum);
                }

            }
        }
        return dp[0][numsDummy.length - 1];
    }

    public int coinChange(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[max];
        // 因为要求min所有初始要设置max值
        Arrays.fill(dp, max);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                // 设置进入
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public int[] dailyTemperatures(int[] T) {
        int n = T.length;
        int[] res = new int[n];
        Arrays.fill(res, 0);
        if (n <= 1) {
            return res;
        }
        // stack记录单调递减温度的下标值
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < n; i++) {
            int temperature = T[i];
            while (!stack.isEmpty() && temperature > T[stack.peek()]) {
                int tmp = stack.pop();
                res[tmp] = i - tmp;
            }
            stack.push(i);
        }
        return res;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        return root;
    }

    public int maxProduct(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return nums[0];
        }

        int[] minDP = new int[n];
        int[] maxDP = new int[n];

        int max = nums[0];
        minDP[0] = maxDP[0] = nums[0];

        for (int i = 1; i < n; i++) {
            maxDP[i] = Math.max(nums[i], Math.max(maxDP[i - 1] * nums[i], minDP[i - 1] * nums[i]));
            minDP[i] = Math.min(nums[i], Math.min(minDP[i - 1] * nums[i], maxDP[i - 1] * nums[i]));
            max = Math.max(max, maxDP[i]);
        }
        return max;
    }

    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return heights[0];
        }

        int[] newHeights = new int[n + 2];
        int newLen = newHeights.length;
        System.arraycopy(heights, 0, newHeights, 1, n);
        int maxArea = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < newLen; i++) {
            // 单调栈 非递减
            while (!stack.isEmpty() && newHeights[i] < newHeights[stack.peek()]) {
                // stack pop 当前高度对应值
                int currHeight = newHeights[stack.pop()];
                // i为stack.pop 右侧 第一个比pop 小 的元素，pop后的stack.peek 为 stack.pop 左侧 第一个比pop 小 的元素
                // 这时i和s.top()分别代表以该高度开始向右和向左第一个小于该高度的下标。
                int width = i - stack.peek() - 1;
                maxArea = Math.max(maxArea, width * currHeight);
            }
            stack.push(i);
        }
        return maxArea;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode tmpA = headA;
        ListNode tmpB = headB;

        // 交换头节点后即使不相交 第二遍后都同时会到达null然后退出
        while (tmpA != tmpB) {
            tmpA = (tmpA != null) ? tmpA.next : headB;
            tmpB = (tmpB != null) ? tmpB.next : headA;
        }

        return tmpA;
    }


    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                maxArea = Math.max(maxArea, dfsSearch(grid, row, col));
            }
        }
        return maxArea;
    }

    private int dfsSearch(int[][] grid, int row, int col) {
        if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && grid[row][col] == 1) {
            // 记得置零
            grid[row][col] = 0;
            return 1 + dfsSearch(grid, row - 1, col) + dfsSearch(grid, row + 1, col) + dfsSearch(grid, row, col - 1) + dfsSearch(grid, row, col + 1);
        } else {
            return 0;
        }
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (root == null) {
            return res;
        }
        // 按层从左到右存node
        Queue<TreeNode> nodeQueue = new LinkedList<TreeNode>();
        nodeQueue.offer(root);
        boolean isToRightLevel = true;
        while (!nodeQueue.isEmpty()) {
            Deque<Integer> levelList = new LinkedList<Integer>();
            // 把每一层的node拿出来，看看方向存进双端队列
            int size = nodeQueue.size();
            for (int i = 0; i < size; i++) {
                TreeNode curNode = nodeQueue.poll();
                if (isToRightLevel) {
                    levelList.offerLast(curNode.val);
                } else {
                    levelList.offerFirst(curNode.val);
                }
                if (curNode.left != null) {
                    nodeQueue.offer(curNode.left);
                }
                if (curNode.right != null) {
                    nodeQueue.offer(curNode.right);
                }
            }
            isToRightLevel = !isToRightLevel;
            res.add(new ArrayList<Integer>(levelList));
        }
        return res;
    }

    public int countSubstrings(String s) {
        char[] strArr = s.toCharArray();
        int n = strArr.length;
        boolean[][] dp = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    dp[i][j] = true;
                }
                if (j == (i + 1) && strArr[i] == strArr[j]) {
                    dp[i][j] = true;
                }
                //  dp[i + 1] 就是因为 i+1，才让i从n-1 to 0 遍历
                if (j > (i + 1) && strArr[i] == strArr[j] && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                }
            }
        }

        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (dp[i][j]) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public int maxEnvelopes(int[][] envelopes) {
        int n = envelopes.length;
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0];
            }
        });

        int[] heights = new int[n];
        for (int i = 0; i < n; i++) {
            heights[i] = envelopes[i][1];
        }

        return lengthOfLIS(heights);
    }

    private int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int max = 0;
        for (int a : dp) {
            max = Math.max(a, max);
        }
        return max;
    }

    public int[][] merge(int[][] intervals) {
        // 先按照区间起始位置排序
        Arrays.sort(intervals, (v1, v2) -> v1[0] - v2[0]);
        // 遍历区间
        int[][] res = new int[intervals.length][2];
        int idx = -1;
        for (int[] interval : intervals) {
            // 如果结果数组是空的，或者当前区间的起始位置 > 结果数组中最后区间的终止位置，
            // 则不合并，直接将当前区间加入结果数组。
            if (idx == -1 || interval[0] > res[idx][1]) {
                res[++idx] = interval;
            } else {
                // 反之将当前区间合并至结果数组的最后区间
                res[idx][1] = Math.max(res[idx][1], interval[1]);
            }
        }
        return Arrays.copyOf(res, idx + 1);
    }

    public boolean canAttendMeetings(int[][] intervals) {
        // 将区间按照会议开始实现升序排序
        Arrays.sort(intervals, (v1, v2) -> v1[0] - v2[0]);
        // 遍历会议，如果下一个会议在前一个会议结束之前就开始了，返回 false。
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) {
                return false;
            }
        }
        return true;
    }

    public int minMeetingRooms(Interval[] intervals) {
        Arrays.sort(intervals, (a, b) -> a.start - b.start);
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> a.compareTo(b));
        int room = 0;
        for (Interval interval : intervals) {
            minHeap.offer(interval.end);
            if (interval.start < minHeap.peek()) {
                room++;
            } else {
                minHeap.poll();
            }
        }
        return room;
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            prefix = lcp(prefix, strs[i]);
            if (prefix.equals("")) {
                break;
            }
        }
        return prefix;
    }

    private String lcp(String str1, String str2) {
        int n = Math.min(str1.length(), str2.length());
        String res = "";
        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                break;
            }
            res += str1.charAt(i);
        }
        return res;
    }

    public int cuttingRope(int n) {
        int[] dp = new int[n + 1];
        dp[2] = 1;
        for (int i = 3; i < n + 1; i++) {
            for (int j = 2; j < i; j++) {
                dp[i] = Math.max(dp[i], Math.max(j * (i - j), j * dp[i - j]));
                dp[i] %= 1000000007;
            }
        }
        return dp[n];
    }

    public int minArray(int[] numbers) {
        if (numbers.length == 0) {
            return 0;
        } else if (numbers.length == 1) {
            return numbers[0];
        }

        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (numbers[mid] < numbers[right]) {
                right = mid;
            } else if (numbers[mid] > numbers[right]) {
                // ??
                left = mid + 1;
            } else {
                // 为嘛right就减少1
                right -= 1;
            }
        }
        // 神奇就选left
        return numbers[left];
    }

    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] leftMul = new int[n];
        leftMul[0] = 1;
        for (int i = 1; i < n; i++) {
            leftMul[i] = nums[i - 1] * leftMul[i - 1];
        }

        int[] ans = new int[n];
        int rightMuls = 1;
        for (int i = n - 1; i >= 0; i--) {
            ans[i] = leftMul[i] * rightMuls;
            rightMuls *= nums[i];
        }
        return ans;
    }

    private int SEG_COUNT = 4;
    private List<String> res = new ArrayList<>();
    private int[] segments = new int[SEG_COUNT];

    public List<String> restoreIpAddresses(String s) {
        dfs(s, 0, 0);
        return res;
    }

    private void dfs(String s, int segId, int segStart) {
        // 如果找到了 4 段 IP 地址并且遍历完了字符串，那么就是一种答案
        if (segId == SEG_COUNT) {
            if (segStart == s.length()) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < SEG_COUNT; i++) {
                    sb.append(segments[i]);
                    if (i != SEG_COUNT - 1) {
                        sb.append(".");
                    }
                }
                res.add(sb.toString());
            }
            return;
        }

        // 如果还没有找到 4 段 IP 地址就已经遍历完了字符串，那么提前回溯
        if (segStart == s.length()) {
            return;
        }

        // 由于不能有前导零，如果当前数字为 0，那么这一段 IP 地址只能为 0
        if (s.charAt(segStart) == '0') {
            segments[segId] = 0;
            dfs(s, segId + 1, segStart + 1);
        }

        // 一般情况，枚举每一种可能性并递归
        int addr = 0;
        for (int segEnd = segStart; segEnd < s.length(); segEnd++) {
            addr = addr * 10 + (s.charAt(segEnd) - '0');
            if (addr > 0 && addr <= 255) {
                segments[segId] = addr;
                //自己大意了
                //dfs(s, segId + 1, segStart + 1);
                dfs(s, segId + 1, segEnd + 1);
            } else {
                break;
            }
        }
    }

    public String decodeString(String s) {
        Integer multi = 0;
        StringBuilder res = new StringBuilder();
        LinkedList<Integer> stack_multi = new LinkedList<>();
        LinkedList<String> stack_res = new LinkedList<>();
        for (char c : s.toCharArray()) {
            if (c == '[') {
                stack_multi.addLast(multi);
                stack_res.addLast(res.toString());
                multi = 0;
                res = new StringBuilder();
            } else if (c == ']') {
                StringBuilder tmp = new StringBuilder();
                int curMulti = stack_multi.removeLast();
                for (int i = 0; i < curMulti; i++) {
                    tmp.append(res);
                }
                res = new StringBuilder(stack_res.removeLast() + tmp);
            } else if (c >= '0' && c <= '9') {
                multi *= 10;
                multi += (c - '0');
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }

    public int firstMissingPositive(int[] nums) {
        int len = nums.length;

        Set<Integer> hashSet = new HashSet<>();
        for (int num : nums) {
            hashSet.add(num);
        }

        for (int i = 1; i <= len; i++) {
            if (!hashSet.contains(i)) {
                return i;
            }
        }

        return len + 1;
    }

    public int singleNonDuplicate(int[] nums) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (mid % 2 == 1) {
                mid--;
            }
            if (nums[mid] == nums[mid + 1]) {
                low = mid + 2;
            } else {
                high = mid;
            }
        }
        return nums[low];
    }

    public String addStrings(String num1, String num2) {
        String res = "";
        int carry = 0;
        int i = num1.length() - 1;
        int j = num2.length() - 1;

        while (i >= 0 || j >= 0 || carry > 0) {
            int x = (i >= 0) ? num1.charAt(i) - '0' : 0;
            int y = (j >= 0) ? num2.charAt(j) - '0' : 0;
            int temp = x + y + carry;
            carry = temp / 10;
            res += temp % 10;
            // 不要漏
            i--;
            j--;
        }
        // 记得翻转字符串
        return new StringBuffer(res).reverse().toString();
    }


//    public int majorityElement(int[] nums) {
//        int n = nums.length;
//        if (n == 1) {
//            return nums[0];
//        }
//
//        int candNum = nums[0];
//        int count = 1;
//        for (int i = 1; i < n; i++) {
//            if (nums[i] == candNum) {
//                count++;
//            } else if (count > 0) {
//                count--;
//            } else {
//                candNum = nums[i];
//                count = 1;
//            }
//        }
//        return candNum;
//    }

//    public int maxProfit(int[] prices) {
//        int n = prices.length;
//        if (n <= 1) {
//            return 0;
//        }
//        int[][] dp = new int[n][3];
//        //0.不持股且当天没卖出,定义其最大收益dp[i][0];
//        //1.持股,定义其最大收益dp[i][1]；
//        //2.不持股且当天卖出了，定义其最大收益dp[i][2]；
//        dp[0][0] = 0;
//        dp[0][1] = -prices[0];
//        dp[0][2] = 0;
//
//        for (int i = 1; i < n; i++) {
//            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2]);
//            dp[i][1] = Math.max(dp[i - 1][0] - prices[i], dp[i - 1][1]);//有冷冻期
//            dp[i][2] = dp[i - 1][1] + prices[i];
//        }
//
//        return Math.max(dp[n - 1][0], dp[n - 1][2]);
//    }
//
//    private void swap(int[] arr, int i, int j) {
//        // 异或满足交换律，并且a^a^b=b;
//        arr[i] = arr[i] ^ arr[j];
//        arr[j] = arr[i] ^ arr[j];
//        arr[i] = arr[i] ^ arr[j];
//    }

    public int test(String str) {
        int len = str.length();
        String maxStr = str;
        String minStr = str;
        for (int i = 1; i < len; i++) {
            String tmp = str.substring(i) + str.substring(0, i);
            maxStr = max(maxStr, tmp);
            minStr = min(minStr, tmp);
        }
        return Integer.parseInt(minStr, 2) + Integer.parseInt(maxStr, 2);
    }

    private String max(String str1, String str2) {
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) > str2.charAt(i)) {
                return str1;
            }
        }
        return str2;
    }

    private String min(String str1, String str2) {
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) > str2.charAt(i)) {
                return str2;
            }
        }
        return str1;
    }

    public void test2() {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            String str = scanner.next();
            int num1 = Integer.valueOf(str.split(" ")[0]);
            int num2 = Integer.valueOf(str.split(" ")[1]);
            System.out.println(num1 + num2);
        }
        scanner.close();
    }

    public int test3() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.valueOf(scanner.next());
        int res = 0;
        for (int i = 0; i < n; i++) {
            String str = scanner.next();
            String[] intStr = str.split(" ");
            for (String s : intStr) {
                res += Integer.valueOf(s);
            }
        }
        return res;
    }

    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int indexG = 0, indexS = 0;

        while (indexG < g.length && indexS < s.length) {
            if (g[indexG] <= s[indexS]) {
                indexG++;
            }
            indexS++;
        }
        return indexG;
    }

    public int candy(int[] ratings) {
        int[] res = new int[ratings.length];
        Arrays.fill(res, 1);
        int len = ratings.length;

        // left -> right
        for (int i = 1; i < len; i++) {
            if (ratings[i] > ratings[i - 1]) {
                res[i] = res[i - 1] + 1;
            }
        }

        // right -> left
        for (int i = len - 1; i > 0; i--) {
            if (ratings[i - 1] > ratings[i]) {
                res[i - 1] = Math.max(res[i - 1], res[i] + 1);
            }
        }

        int cnt = 0;
        for (int i : res) {
            cnt += i;
        }
        return cnt;
    }

    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        System.out.println(JSON.toJSONString(intervals));
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        System.out.println(JSON.toJSONString(intervals));

        int removeCnt = 0, tailFlag = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < tailFlag) {
                removeCnt++;
            } else {
                tailFlag = intervals[i][1];
            }
        }

        return removeCnt;
    }

    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (flowerbed == null || flowerbed.length == 0) {
            return false;
        }

        int cnt = 0;
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 0 &&
                    (i == 0 || flowerbed[i - 1] == 0) &&
                    (i + 1 == flowerbed.length || flowerbed[i + 1] == 0)
            ) {
                cnt++;
                flowerbed[i] = 1;
            }
        }

        return cnt >= n;
    }

    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (a, b) -> a[1] - b[1]);
        int cnt = 1, rightFlag = points[0][1];
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > rightFlag) {
                rightFlag = points[i][1];
                cnt++;
            }
        }
        return cnt;
    }

    public int[][] reconstructQueue(int[][] people) {
        System.out.println(JSON.toJSONString(people));
        Arrays.sort(people, (a, b) -> (a[0] != b[0] ? b[0] - a[0] : a[1] - b[1]));
        System.out.println(JSON.toJSONString(people));
        Arrays.sort(people, (a, b) -> (a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]));
        System.out.println(JSON.toJSONString(people));


        List<int[]> ans = new LinkedList<>();
        for (int[] p : people) {
            ans.add(p[1], p);
        }

        return ans.toArray(new int[people.length][]);
    }

    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum > target) {
                right--;
            } else {
                left++;
            }
        }
        return null;
    }

    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int i = 0;

        for (int p : pushed) {
            stack.push(p);
            while (!stack.isEmpty() && stack.peek() == popped[i]) {
                stack.pop();
                i++;
            }
        }

        return stack.isEmpty();
    }

    public String removeKdigits(String nums, int k) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums.length(); i++) {
            int num = nums.charAt(i) - '0';
            // 是while 循环
            while (k > 0 && !stack.isEmpty() && stack.peek() > num) {
                stack.pop();
                k--;
            }
            stack.push(num);
        }

        while (k > 0 && !stack.isEmpty()) {
            stack.pop();
            k--;
        }

        if (stack.isEmpty()) {
            return "0";
        }

        String ans = "";
        // 记得顺序
        // 记得跳过无效的高位0
        Integer[] l = stack.toArray(new Integer[stack.size()]);
        boolean leadingZero = true;
        for (int n : l) {
            if (leadingZero && n == 0) {
                continue;
            }
            leadingZero = false;
            ans += n;
        }

        if (ans == "") {
            return "0";
        }
        return ans;
    }

    public String smallestSubsequence(String s) {
        boolean[] existStack = new boolean[26];
        int[] charCnt = new int[26];
        for (int i = 0; i < s.length(); i++) {
            charCnt[s.charAt(i) - 'a']++;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            // stack 中没有的char可以 尝试 加进来 (标记唯一性)
            if (!existStack[ch - 'a']) {
                // 尝试保持单调递增 - 维持单调递增
                while (sb.length() > 0 && sb.charAt(sb.length() - 1) > ch) {
                    // 此位置后还有的（后续还能让栈保持单调递增的就pop）
                    if (charCnt[sb.charAt(sb.length() - 1) - 'a'] > 0) {
                        existStack[sb.charAt(sb.length() - 1) - 'a'] = false;
                        sb.deleteCharAt(sb.length() - 1);
                    } else {
                        break;
                    }
                }
                existStack[ch - 'a'] = true;
                sb.append(ch);
            }
            // source 字符串中 char 的计数减1
            charCnt[ch - 'a']--;
        }

        return sb.toString();
    }

    public int lastRemaining(int n, int m) {
        int x = 0;
        for (int i = 2; i <= n; i++) {
            x = (x + m) % i;
        }
        return x;
    }

    public int findPeakElement(int[] nums) {
        int left=0,right=nums.length-1;
        while (left<right){
            int mid = left + (right - left) / 2;
            if (nums[mid]>nums[mid+1]){
                right = mid;
            }else {
                // 注意这里加1
                left=mid+1;
            }
        }
        return left;
    }
}
