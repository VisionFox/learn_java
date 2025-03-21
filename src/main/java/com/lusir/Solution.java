package com.lusir;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.stream.Collectors;

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

    // lc 84
    public int largestRectangleArea(int[] heights) {
        // 特殊处理
        if (heights == null || heights.length == 0) {
            return 0;
        }
        if (heights.length == 1) {
            return heights[0];
        }
        // 两边补0 为了清空栈
        int[] newHeights = new int[heights.length + 2];
        int newLen = newHeights.length;
        System.arraycopy(heights, 0, newHeights, 1, heights.length);

        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < newLen; i++) {
            // 单调栈 非递减
            while (!stack.isEmpty() && newHeights[i] < newHeights[stack.peek()]) {
                // stack pop 当前高度对应值-出栈的元素-高度
                int currHeight = newHeights[stack.pop()];
                // i为stack.pop 右侧 第一个比pop 小 的元素，
                // pop后的stack.peek 为 stack.pop 左侧 第一个比pop 小 的元素
                // 这时i和s.top() 分别代表 以该高度开始向右和向左第一个小于该高度的下标。
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
            if (flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0) && (i + 1 == flowerbed.length || flowerbed[i + 1] == 0)) {
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
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid + 1]) {
                right = mid;
            } else {
                // 注意这里加1
                left = mid + 1;
            }
        }
        return left;
    }

    public String largestNumber(int[] nums) {
        // 从结果来看，从左到右，我们需要两两组合最终都达到，左大到右小的结果
        // 左a,右b，我们还要看两两组合的效果，我们想要降的趋势，所以compare策略（小b+a比大a+b,此为升则全局小)
        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> (b + a).compareTo(a + b));

        for (int n : nums) {
            pq.offer(String.valueOf(n));
        }

        String ans = "";
        while (!pq.isEmpty()) {
            ans += pq.poll();
        }
        // 去掉前导0
        if (ans.charAt(0) == '0') {
            return "0";
        }
        return ans;
    }

    long pre = Long.MIN_VALUE;

    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }

        if (!isValidBST(root.left)) {
            return false;
        }

        if (root.val <= pre) {
            return false;
        }
        pre = root.val;
        return isValidBST(root.right);
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode cur = head;
        while (cur.next != null) {
            if (cur.val != cur.next.val) {
                cur = cur.next;
            } else {
                cur.next = cur.next.next;
            }
        }
        return head;
    }

    public String minWindow(String source, String target) {
        int idxSource = 0, idxTarget = 0, minLen = Integer.MAX_VALUE;
        int leftest = -1, right = 0;
        while (idxSource < source.length()) {
            if (source.charAt(idxSource) == target.charAt(idxTarget)) {
                // 找到了一个包含target的子串（右边界为idxSource）
                if (idxTarget == target.length() - 1) {
                    // 此时的right指向右边界
                    right = idxSource;
                    // 我们又顺着右边界反向遍历
                    // 直到找到（离右边界最近的）左边界
                    while (idxTarget >= 0) {
                        if (source.charAt(idxSource) == target.charAt(idxTarget)) {
                            idxTarget--;
                        }
                        idxSource--;
                    }
                    // 因为上面的反向遍历结束时idxSource指向了左边界的前一位
                    // ++idxSource之后此时idxSource就指向左边界
                    idxSource++;

                    if (right - idxSource + 1 < minLen) {
                        minLen = right - idxSource + 1;
                        leftest = idxSource;
                    }
                }
                idxTarget++;
            }
            idxSource++;
        }
        return leftest == -1 ? "" : source.substring(leftest, leftest + minLen);
    }

    int max = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        dfs(root);
        return max;
    }

    /**
     * 返回经过root的单边分支最大和， 即Math.max(root, root+left, root+right)
     *
     * @param root
     * @return
     */
    public int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        //计算左边分支最大值，左边分支如果为负数还不如不选择
        int leftMax = Math.max(0, dfs(root.left));
        //计算右边分支最大值，右边分支如果为负数还不如不选择
        int rightMax = Math.max(0, dfs(root.right));
        //left->root->right 作为路径与已经计算过历史最大值做比较
        max = Math.max(max, root.val + leftMax + rightMax);
        // 返回经过root的单边最大分支给当前root的父节点计算使用
        return root.val + Math.max(leftMax, rightMax);
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        Deque<Integer> deque = new LinkedList<>();
        int[] ans = new int[nums.length - k + 1];
        for (int idRight = 0, idxLeft = 1 - k; idRight < nums.length; idxLeft++, idRight++) {
            // 删除 deque 中对应的 nums[idxLeft-1]
            if (idxLeft > 0 && deque.peekFirst() == nums[idxLeft - 1]) {
                deque.removeFirst();
            }
            // 保持 deque 递减
            while (!deque.isEmpty() && deque.peekLast() < nums[idRight]) {
                deque.removeLast();
            }
            deque.addLast(nums[idRight]);
            // 记录窗口最大值
            if (idxLeft >= 0) {
                ans[idxLeft] = deque.peekFirst();
            }
        }
        return ans;
    }

    public boolean isCompleteTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean reachNull = false;
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur == null) {
                reachNull = true;
                continue;
            }

            if (reachNull) {
                return false;
            }

            queue.add(cur.left);
            queue.add(cur.right);
        }

        return true;
    }

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> ret = Arrays.stream(arr).boxed().collect(Collectors.toList());
        int n = ret.size();
        if (x <= ret.get(0)) {
            return ret.subList(0, k);
        } else if (ret.get(n - 1) <= x) {
            return ret.subList(n - k, n);
        } else {
            int index = Collections.binarySearch(ret, x);
            if (index < 0) index = -index - 1;
            int low = Math.max(0, index - k), high = Math.min(ret.size() - 1, index + k - 1);

            while (high - low > k - 1) {
                if ((x - ret.get(low)) <= (ret.get(high) - x)) high--;
                else if ((x - ret.get(low)) > (ret.get(high) - x)) low++;
                else System.out.println("unhandled case: " + low + " " + high);
            }
            return ret.subList(low, high + 1);
        }
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;

        if (key > root.val) root.right = deleteNode(root.right, key); // 去右子树删除

        else if (key < root.val) root.left = deleteNode(root.left, key);  // 去左子树删除

        else {  // 当前节点就是要删除的节点
            if (root.left == null) return root.right;      // 情况1，欲删除节点无左子
            else if (root.right == null) return root.left;  // 情况2，欲删除节点无右子
            else if (root.left != null && root.right != null) {  // 情况3，欲删除节点左右子都有
                TreeNode node = root.right;
                while (node.left != null)      // 寻找欲删除节点右子树的最左节点
                    node = node.left;

                node.left = root.left;     // 将欲删除节点的左子树成为其右子树的最左节点的左子树
                root = root.right;         // 欲删除节点的右子顶替其位置，节点被删除
            }
        }
        return root;
    }

    static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public int trapRainWater(int[][] height) {
        int m = height.length, n = height[0].length, res = 0;
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(p -> height[p[0]][p[1]]));
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            queue.offer(new int[]{i, 0});
            queue.offer(new int[]{i, n - 1});
            visited[i][0] = visited[i][n - 1] = true;
        }
        for (int j = 1; j < n - 1; j++) {
            queue.offer(new int[]{0, j});
            queue.offer(new int[]{m - 1, j});
            visited[0][j] = visited[m - 1][j] = true;
        }
        while (!queue.isEmpty()) {
            int[] p = queue.poll();
            int h = height[p[0]][p[1]];
            for (int[] d : DIRECTIONS) {
                int x = p[0] + d[0], y = p[1] + d[1];
                if (x < 0 || x >= m || y < 0 || y >= n || visited[x][y]) continue;
                if (h > height[x][y]) {
                    res += h - height[x][y];
                    height[x][y] = h;
                }
                queue.offer(new int[]{x, y});
                visited[x][y] = true;
            }
        }
        return res;
    }


    public int[][] spiralMatrixIII(int rows, int cols, int rStart, int cStart) {
        int[][] around = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int[][] ans = new int[rows * cols][2];

        int x = rStart, y = cStart, num = 1, direct = 0;
        int left = y - 1, right = y + 1, upper = x - 1, bottom = x + 1;

        while (num <= rows * cols) {
            // 符合要求的节点填入
            if (x >= 0 && x < rows && y >= 0 && y < cols) {
                ans[num - 1] = new int[]{x, y};
                num++;
            }

            // 调整方向和边界坐标
            if (direct == 0 && y == right) {
                // 向右
                direct++;
                right++;

            } else if (direct == 1 && x == bottom) {
                // 向下
                direct++;
                bottom++;
            } else if (direct == 2 && y == left) {
                // 向左
                direct++;
                left--;
            } else if (direct == 3 && x == upper) {
                // 向上
                direct = 0;
                upper--;
            }

            // 下一个节点的坐标
            x += around[direct][0];
            y += around[direct][1];
        }
        return ans;
    }

    public boolean isMatch(String source, String patten) {
        boolean[][] dp = new boolean[source.length() + 1][patten.length() + 1];

        // 初始化
        dp[0][0] = true;
        for (int j = 1; j <= patten.length(); j++) {
            // charAt(j - 1) ： j-1
            dp[0][j] = dp[0][j - 1] && patten.charAt(j - 1) == '*';
        }

        for (int i = 1; i <= source.length(); i++) {
            for (int j = 1; j <= patten.length(); j++) {
                if (source.charAt(i - 1) == patten.charAt(j - 1) || patten.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (patten.charAt(j - 1) == '*') {
                    // dp[i][j - 1] 表示 * 代表的是空字符，例如 ab, ab*
                    // dp[i - 1][j] 表示 * 代表的是非空字符，例如 abcd, ab*
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                }
            }
        }

        return dp[source.length()][patten.length()];
    }

    public Stack<Integer> stackSort(Stack<Integer> sourceStack) {
        Stack<Integer> tempStack = new Stack<>();
        while (!sourceStack.isEmpty()) {
            Integer peek = sourceStack.pop();
            while (!tempStack.isEmpty() && tempStack.peek() > peek) {
                Integer t = tempStack.pop();
                sourceStack.push(t);
            }
            tempStack.push(peek);
        }

        return tempStack;
    }

    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<AnnotatedNode> queue = new ArrayDeque<>();
        queue.add(new AnnotatedNode(root, 0, 0));
        int curDepth = 0, left = 0, ans = 0;

        while (!queue.isEmpty()) {
            AnnotatedNode curAnnotatedNode = queue.poll();
            if (curAnnotatedNode.node != null) {
                queue.offer(new AnnotatedNode(curAnnotatedNode.node.left, curAnnotatedNode.depth + 1, curAnnotatedNode.pos * 2));
                queue.offer(new AnnotatedNode(curAnnotatedNode.node.right, curAnnotatedNode.depth + 1, curAnnotatedNode.pos * 2));

                if (curDepth != curAnnotatedNode.depth) {
                    curDepth = curAnnotatedNode.depth;
                    left = curAnnotatedNode.pos;
                }
                ans = Math.max(ans, curAnnotatedNode.pos - left + 1);
            }
        }
        return ans;
    }

    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;

        int absSum = 0;
        for (int i : nums) {
            absSum += Math.abs(i);
        }

        // 提前校验，根据实际，根据01背包问题转换而来
        // 目标值大于absSum 或者 目标值+absSum为奇数的情况下 不符合条件
        if (Math.abs(target) > absSum || (absSum + target) % 2 != 0) {
            return 0;
        }

        // 问题转化
        return subSum(nums, (absSum + target) / 2);
    }

    private int subSum(int[] nums, int sumA) {
        // nums待选择的物品，sumA背包容量，求塞满背包的最多方案数
        int n = nums.length;
        int[][] dp = new int[n + 1][sumA + 1];

        //背包容量为0的情况下，只有什么都不装这一种方法
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= n; i++) {
            // 第i个物品的下标为i-1
            int num = nums[i - 1];
            for (int j = 0; j <= sumA; j++) {
                // 不把 nums[i] 算入子集（或者说不把这第 i 个物品装入背包）
                // 恰好装满背包的方法数就取决于上一个状态 dp[i-1][j]
                dp[i][j] = dp[i - 1][j];
                // 把 nums[i] 算入子集（或者说你把这第 i 个物品装入了背包）
                // 只要看前 i - 1 个物品有几种方法可以装满 j - nums[i-1] 的重量就行了
                // 但又因为 num 全为正数，且背包容量没有负数的情况，j - nums[i-1] 要 >= 0
                if (j >= num) {
                    dp[i][j] += dp[i - 1][j - num];
                }
            }
        }
        return dp[n][sumA];
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> ans = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    ans.add(1);
                } else {
                    Integer num1 = res.get(i - 1).get(j - 1);
                    Integer num2 = res.get(i - 1).get(j);
                    ans.add(num1 + num2);
                }
            }
            res.add(ans);
        }

        return res;
    }

    private boolean[][] visited;

    public boolean exist(char[][] board, String word) {
        int row = board.length;
        int col = board[0].length;
        if (row * col < word.length()) {
            return false;
        }

        visited = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (dfs(board, word, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] grid, String word, int index, int row, int col) {
        // 遍历完了
        if (index >= word.length()) {
            return true;
        }
        // 遍历无效
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            return false;
        }
        // 本次探索中 遍历过了
        if (visited[row][col]) {
            return false;
        }
        // 本次探索的值不满足 ！！！
        if (grid[row][col] != word.charAt(index)) {
            return false;
        }
        // 标记下本次满足要求的探索
        visited[row][col] = true;
        // 探索上下左右 看看是否有满足要求的值
        // 某个方向为false 即不满足 则找其他方向
        boolean flag1 = dfs(grid, word, index + 1, row + 1, col);
        if (flag1) {
            return true;
        }
        boolean flag2 = dfs(grid, word, index + 1, row - 1, col);
        if (flag2) {
            return true;
        }
        boolean flag3 = dfs(grid, word, index + 1, row, col + 1);
        if (flag3) {
            return true;
        }
        boolean flag4 = dfs(grid, word, index + 1, row, col - 1);
        if (flag4) {
            return true;
        }
        // 取消遍历标记
        visited[row][col] = false;
        return false;
    }

    public int numDecodings(String s) {
        if (s.charAt(0) == '0') {
            return 0;
        }

        if (s.length() == 1) {
            return 1;
        }


        int[] dp = new int[s.length()];
        dp[0] = 1;


        for (int i = 2; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                if (s.charAt(i - 1) == '1' || s.charAt(i - 1) == '2') {
                    dp[i] = dp[i - 2];
                } else {
                    return 0;
                }
            } else if (s.charAt(i - 1) == '1') {
                dp[i] = dp[i - 1] + dp[i - 2];
            } else if (s.charAt(i - 1) == '2' && s.charAt(i) >= '1' && s.charAt(i) <= '6') {
                dp[i] = dp[i - 1] + dp[i - 2];
            } else {
                return 0;
            }
        }
        return dp[s.length() - 1];
    }

    public boolean verifyPostorder(int[] postorder) {
        return recur(postorder, 0, postorder.length - 1);
    }

    private boolean recur(int[] postorder, int i, int j) {
        if (i >= j) {
            return true;
        }

        int flag = i;
        while (postorder[flag] < postorder[j]) {
            flag++;
        }

        int rightFirst = flag;

        while (postorder[flag] > postorder[j]) {
            flag++;
        }

        return flag == j && recur(postorder, i, rightFirst - 1) && recur(postorder, rightFirst, j - 1);
    }

    public boolean verifyPostorderV2(int[] postorder) {
        // 单调栈使用，单调递增的单调栈，里面存的是递增的右子树的节点
        // 遇到比栈顶小的，即为：局部左边子树第一个值，弹弹弹出，最终弹出为这两个局部树的root
        Stack<Integer> stack = new Stack<>();
        // 记录两个局部子树的根节点
        // 这里可以把postorder的最后一个元素root看成无穷大节点的左孩子
        int rootValue = Integer.MAX_VALUE;

        // 从后往前遍历 root -> right -> left
        for (int i = postorder.length - 1; i >= 0; i--) {
            // 由于rootValue存的是余下子树的root
            // 局部左子树元素必须要小于递增栈被peek访问的元素，否则就不是二叉搜索树
            if (postorder[i] > rootValue) {
                return false;
            }
            while (!stack.isEmpty() && postorder[i] < stack.peek()) {
                // 数组元素小于单调栈的元素了，表示往左子树走了，记录根节点
                // 找到这个左子树对应的根节点，之前右子树全部弹出，不再记录，因为不可能在往根节点的右子树走了
                rootValue = stack.pop();
            }
            stack.push(postorder[i]);
        }

        return true;
    }

    public int climbStairs(int n) {
        // 这个判断一定要有
        if (n <= 2) {
            return n;
        }

        int subOne = 1, subTwo = 1, ans = 0;
        for (int i = 2; i <= n; i++) {
            ans = subOne + subTwo;
            subTwo = subOne;
            subOne = ans;
        }

        return ans;
    }

    public int maxProfitV2(int[] prices) {
        int days = prices.length;
        int[][] dp = new int[days][2];
        // base
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < days; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            if (i == 1) {
                dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
            } else {
                dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]);
            }
        }
        return dp[days - 1][0];
    }

    public int maxProfit(int[] prices, int fee) {
        // -fee
        int buy = -prices[0] - fee;
        int sell = 0;
        for (int i = 1; i < prices.length; i++) {
            // -fee
            buy = Math.max(buy, sell - prices[i] - fee);
            sell = Math.max(sell, buy + prices[i]);
        }
        return sell;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        if (nums[0] > 0) {
            return Collections.emptyList();
        }

        List<List<Integer>> ans = new ArrayList<>();

        for (int aIdx = 0; aIdx < nums.length - 2; aIdx++) {
            if (nums[aIdx] > 0) {
                break;
            }

            // aIdx永远指向第一个不同数字，bIdx cIdx同样指向第一个不一样的数
            if (aIdx > 0 && nums[aIdx] == nums[aIdx - 1]) {
                continue;
            }

            int bIdx = aIdx + 1;
            int cIdx = nums.length - 1;

            while (bIdx < cIdx) {
                int sum = nums[aIdx] + nums[bIdx] + nums[cIdx];
                if (sum == 0) {
                    List<Integer> item = new ArrayList<>();
                    item.add(nums[aIdx]);
                    item.add(nums[bIdx]);
                    item.add(nums[cIdx]);

                    bIdx++;
                    while (bIdx < cIdx && nums[bIdx] == nums[bIdx - 1]) {
                        bIdx++;
                    }

                    cIdx--;
                    while (bIdx < cIdx && nums[cIdx] == nums[cIdx + 1]) {
                        cIdx--;
                    }
                } else if (sum < 0) {
                    bIdx++;
                    while (bIdx < cIdx && nums[bIdx] == nums[bIdx - 1]) {
                        bIdx++;
                    }
                } else {
                    cIdx--;
                    while (bIdx < cIdx && nums[cIdx] == nums[cIdx + 1]) {
                        cIdx--;
                    }
                }
            }
        }

        return ans;
    }

    public int lengthOfLongestSubstringV2(String s) {
        if (s == null) {
            return 0;
        }

        if (s.length() < 2) {
            return s.length();
        }

        HashMap<Character, Integer> charToCloseIdx = new HashMap<>();

        int ans = 0;
        int windowLeft = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (charToCloseIdx.containsKey(ch)) {
                windowLeft = Math.max(windowLeft, charToCloseIdx.get(ch) + 1);
            }
            charToCloseIdx.put(ch, i);
            ans = Math.max(ans, i - windowLeft + 1);
        }
        return ans;
    }

    // lc 159
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s.length() < 3) {
            return s.length();
        }

        int left = 0, right = 0, ans = 2;
        // K-V：K是对应字符，V是最后一次出现的位置
        // 为了多挪动几个left
        HashMap<Character, Integer> char2LastIdx = new HashMap<>();
        for (; right < s.length(); right++) {
            char2LastIdx.put(s.charAt(right), right);
            if (char2LastIdx.size() > 2) {
                // 反正只有2个元素，最小的肯定是：最早需要抛弃的
                int minCloseIdx = Collections.min(char2LastIdx.values());
                char2LastIdx.remove(s.charAt(minCloseIdx));
                left = minCloseIdx + 1;
            }
            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }

    public boolean checkInclusion(String s1, String s2) {
        int s1Len = s1.length();
        int s2Len = s2.length();
        if (s1Len < s2Len) {
            return false;
        }

        int[] cnt_1 = new int[26];
        int[] cnt_2 = new int[26];
        for (int i = 0; i < s1Len; i++) {
            cnt_1[s1.charAt(i) - 'a']++;
            cnt_2[s2.charAt(i) - 'a']++;
        }

        if (Arrays.equals(cnt_1, cnt_2)) {
            return true;
        }

        for (int i = s1Len; i < s2Len; i++) {
            cnt_2[s2.charAt(i) - 'a']++;
            cnt_2[s2.charAt(i - s1Len) - 'a']--;

            if (Arrays.equals(cnt_1, cnt_2)) {
                return true;
            }
        }

        return false;
    }

    /**
     * pre[j] - pre[i-1] = k
     * pre[j] - k = pre[i-1]
     */
    public int subarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0, 1);

        int ans = 0;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (prefixSumCount.containsKey(sum - k)) {
                ans += prefixSumCount.get(sum - k);
            }


            int cnt = prefixSumCount.getOrDefault(sum, 0);
            prefixSumCount.put(sum, cnt + 1);
        }

        return ans;
    }

    public boolean hasPath(int[][] maze, int[] start, int[] dest) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);
        visited[start[0]][start[1]] = true;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

        while (queue.size() > 0) {
            int[] pos = queue.poll();

            if (pos[0] == dest[0] && pos[1] == dest[1]) {
                return true;
            }

            for (int k = 0; k < dirs.length; ++k) {
                // 一直往某方向走
                int x = pos[0] + dirs[k][0];
                int y = pos[1] + dirs[k][1];
                while (x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] == 0) {
                    x += dirs[k][0];
                    y += dirs[k][1];
                }
                // 碰到墙壁就回退一下
                x -= dirs[k][0];
                y -= dirs[k][1];
                // 记录此方向走到的方向终点，并放入 queue
                if (!visited[x][y]) {
                    queue.add(new int[]{x, y});
                    visited[x][y] = true;
                }
            }
        }
        return false;
    }

    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int[] heights = new int[matrix[0].length];
        int maxArea = 0;

        for (int row = 0; row < matrix.length; row++) {
            //遍历每一列，更新高度
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] == '1') {
                    heights[col] += 1;
                } else {
                    heights[col] = 0;
                }
            }
            //调用上一题的解法，更新函数
            maxArea = Math.max(maxArea, largestRectangleAreaV2(heights));
        }

        return maxArea;
    }

    // lc 84
    public int largestRectangleAreaV2(int[] heights) {
        if (heights == null || heights.length < 1) {
            return 0;
        }
        // 单调递增，存下标
        Stack<Integer> incrStack = new Stack<>();
        // 首尾补0
        int[] newHeights = new int[heights.length + 2];
        System.arraycopy(heights, 0, newHeights, 1, heights.length);
        int ans = 0;

        for (int i = 0; i < newHeights.length; i++) {
            while (!incrStack.isEmpty() && newHeights[i] < newHeights[incrStack.peek()]) {
                int curIdx = incrStack.pop();
                int curHeight = newHeights[curIdx];

                int rightFirstMinIdx = i;
                int leftFirstMinIdx = incrStack.peek();

                int width = rightFirstMinIdx - 1 - leftFirstMinIdx;
                ans = Math.max(ans, curHeight * width);
            }
            incrStack.push(i);
        }
        return ans;
    }

    public void permuteUnique() {
        int[] arr = new int[]{1, 2, 3, 4};
        boolean[] used = new boolean[arr.length];
        permuteUniqueHelper(new ArrayList<>(), arr, new boolean[arr.length]);
    }

    private void permuteUniqueHelper(ArrayList<Integer> path, int[] arr, boolean[] used) {
        if (path.size() == 2) {
            System.out.println(path);
            return;
        }

    }

    public String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        char[] cs = s.toCharArray();
        boolean[][] dp = new boolean[cs.length][cs.length];
        for (int i = cs.length - 1; i >= 0; i--) {
            dp[i][i] = true;
            for (int j = cs.length - 1; j > i; j--) {
                if (j == i + 1) {
                    dp[i][j] = cs[j] == cs[i];
                } else {
                    dp[i][j] = cs[i] == cs[j] && dp[i + 1][j - 1];
                }
            }
        }

        int maxLen = -1;
        int flag1 = -1;
        int flag2 = -1;
        for (int i = 0; i < dp.length; i++) {
            for (int j = i; j < dp.length; j++) {
                if (dp[i][j] == true) {
                    int curLen = j - i + 1;
                    if (curLen > maxLen) {
                        flag1 = i;
                        flag2 = j;
                        maxLen = curLen;
                    }
                }
            }
        }

        return s.substring(flag1, flag2 + 1);
    }

    public boolean checkInclusionRe(String sub, String source) {
        // 滑动窗口匹配
        // 排除异常的边界情况，也限定了模式串的长度
        if (sub.length() > source.length()) {
            return false;
        }


        // 模式串的字典：可以看做一种频率分布
        int[] subCntArr = new int[26];
        // 动态更新的匹配窗口字典
        int[] sourceWindowCntArr = new int[26];

        int windowSize = sub.length();
        // 构建字典
        for (int i = 0; i < windowSize; i++) {
            subCntArr[sub.charAt(i) - 'a']++;
            sourceWindowCntArr[source.charAt(i) - 'a']++;
        }

        if (Arrays.equals(subCntArr, sourceWindowCntArr)) {
            return true;
        }

        // 对于每一轮滑窗查询，如果两个字典相等(频率分布一致)，则命中
        for (int i = sub.length(); i < source.length(); i++) {
            // 滑动
            sourceWindowCntArr[source.charAt(i) - 'a']++;
            sourceWindowCntArr[source.charAt(i - windowSize) - 'a']--;

            if (Arrays.equals(subCntArr, sourceWindowCntArr)) {
                return true;
            }
        }

        return false;
    }

    public List<List<Integer>> permute(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Collections.emptyList();
        }

        List<List<Integer>> ans = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        permuteHelper(nums, used, new ArrayList<>(), ans);
        return ans;
    }

    private void permuteHelper(int[] nums, boolean[] used, ArrayList<Integer> path, List<List<Integer>> ans) {
        if (path.size() == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }

            used[i] = true;
            path.add(nums[i]);

            permuteHelper(nums, used, path, ans);

            used[i] = false;
            path.remove(path.size() - 1);
        }
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Collections.emptyList();
        }

        // 元素要排序，主要是让相同的挨一起
        Arrays.sort(nums);

        List<List<Integer>> ans = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        permuteUniqueHelper(nums, used, new ArrayList<>(), ans);
        return ans;
    }

    private void permuteUniqueHelper(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> ans) {
        if (path.size() == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }

            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }

            path.add(nums[i]);
            used[i] = true;

            permuteUniqueHelper(nums, used, path, ans);

            path.remove(path.size() - 1);
            used[i] = false;
        }
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }

        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    // lc 43
    public String multiply(String num1, String num2) {
        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }


        int[] ans = new int[num2.length() + num1.length()];
        for (int i = num1.length() - 1; i >= 0; i--) {
            int n1 = num1.charAt(i) - '0';
            for (int j = num2.length() - 1; j >= 0; j--) {
                int n2 = num2.charAt(j) - '0';


                int sum = (ans[i + j + 1] + n1 * n2);
                ans[i + j + 1] = sum % 10;
                ans[i + j] += sum / 10;
            }
        }


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ans.length; i++) {
            // 因为 N位数 * M位数 : 结果最多为N+M位 ，或者M+N-1位，想象xxx*1 vs xxx*9
            if (i == 0 && ans[i] == 0) {
                continue;
            }
            sb.append(ans[i]);
        }
        return sb.toString();
    }

    public int compareVersion(String v1, String v2) {
        int i = 0, j = 0;
        int m = v1.length(), n = v2.length();
        while (i < m || j < n) {
            int num1 = 0, num2 = 0;

            while (i < m && v1.charAt(i) != '.') {
                num1 = num1 * 10 + v1.charAt(i) - '0';
                i++;
            }

            while (j < n && v2.charAt(j) != '.') {
                num2 = num2 * 10 + v2.charAt(j) - '0';
                j++;
            }

            if (num1 < num2) {
                return -1;
            } else if (num1 > num2) {
                return 1;
            }

            i++;
            j++;
        }
        return 0;
    }

    public int findMaxLength(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        int n = nums.length;

        // 0当作-1 1当作1
        // idx=0存0（保证01都出现）  idx=n存最后一个前缀和
        int[] prefixSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + ((nums[i - 1] == 0) ? -1 : 1);
        }

        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);

        for (int i = 1; i <= n; i++) {
            int curSum = prefixSum[i];

            if (map.containsKey(curSum)) {
                ans = Math.max(ans, i - map.get(curSum));
            }
            if (!map.containsKey(curSum)) {
                map.put(curSum, i);
            }
        }

        return ans;
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> num2Freq = new HashMap<>();
        for (int n : nums) {
            num2Freq.put(n, num2Freq.getOrDefault(n, 0) + 1);
        }

        // int[0] 数 int[1] 频率
        // ps: A.compareTo(B) 等价于 A-B 升序排序
        // 小顶堆
        PriorityQueue<int[]> pQ = new PriorityQueue<>((pair1, pair2) -> pair1[1] - pair2[1]);

        for (Map.Entry<Integer, Integer> entry : num2Freq.entrySet()) {
            Integer num = entry.getValue();
            Integer freq = entry.getValue();

            // 判断是否满了k个
            if (pQ.size() < k) {
                pQ.offer(new int[]{entry.getKey(), entry.getValue()});
            } else {
                if (pQ.peek() != null && pQ.peek()[1] < freq) {
                    pQ.poll();
                    pQ.offer(new int[]{entry.getKey(), entry.getValue()});
                }
            }
        }

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = pQ.poll()[0];
        }
        return res;
    }

    public List<Integer> findAnagrams(String s, String p) {
        if (p.length() > s.length()) {
            return Collections.emptyList();
        }

        int sSize = s.length();
        int pSize = p.length();
        int[] sWindowCnt = new int[26];
        int[] pCnt = new int[26];

        for (int i = 0; i < pSize; i++) {
            pCnt[p.charAt(i) - 'a']++;
            sWindowCnt[s.charAt(i) - 'a']++;
        }

        List<Integer> res = new ArrayList<>();
        if (Arrays.equals(pCnt, sWindowCnt)) {
            res.add(0);
        }

        for (int i = pSize; i < sSize; i++) {
            sWindowCnt[s.charAt(i) - 'a']++;
            sWindowCnt[s.charAt(i - pSize) - 'a']--;
            if (Arrays.equals(pCnt, sWindowCnt)) {
                res.add(i - pSize + 1);
            }
        }

        return res;
    }

    public List<Integer> findAnagramsV2(String s, String p) {
        int sLen = s.length(), pLen = p.length();

        if (sLen < pLen) {
            return new ArrayList<Integer>();
        }

        List<Integer> ans = new ArrayList<Integer>();
        int[] count = new int[26];
        for (int i = 0; i < pLen; ++i) {
            ++count[s.charAt(i) - 'a'];
            --count[p.charAt(i) - 'a'];
        }

        int differ = 0;
        for (int j = 0; j < 26; ++j) {
            if (count[j] != 0) {
                ++differ;
            }
        }

        if (differ == 0) {
            ans.add(0);
        }

        for (int i = 0; i < sLen - pLen; ++i) {
            // 窗口左边移动处理 缩减字符
            if (count[s.charAt(i) - 'a'] == 1) {  // 窗口中字母 s[i] 的数量与字符串 p 中的数量从不同变得相同
                --differ;
            } else if (count[s.charAt(i) - 'a'] == 0) {  // 窗口中字母 s[i] 的数量与字符串 p 中的数量从相同变得不同
                ++differ;
            }
            --count[s.charAt(i) - 'a'];
            // 窗口右边移动处理 新增字符
            if (count[s.charAt(i + pLen) - 'a'] == -1) {  // 窗口中字母 s[i+pLen] 的数量与字符串 p 中的数量从不同变得相同
                --differ;
            } else if (count[s.charAt(i + pLen) - 'a'] == 0) {  // 窗口中字母 s[i+pLen] 的数量与字符串 p 中的数量从相同变得不同
                ++differ;
            }
            ++count[s.charAt(i + pLen) - 'a'];

            if (differ == 0) {
                // ！！！此循环中的i代表上次窗口最新的left
                ans.add(i + 1);
            }
        }

        return ans;
    }

    public int minMeetingRooms(int[][] intervals, int n) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        // 按照会议开始时间来排序
        Arrays.sort(intervals, (O1, O2) -> O1[0] - O2[0]);
        // 会议结束时间的小顶堆，代表当前能同时开多少个会议 （存其结束时间）
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((O1, O2) -> O1 - O2);

        int res = 0;

        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            while (!priorityQueue.isEmpty() && start >= priorityQueue.peek()) {
                // 代表当前会议开始时间 大于等于 Queue中会议的结束时间
                // 可以在peek会议结束后再开
                // 删除掉已经结束的会议
                priorityQueue.poll();
            }
            // 将当前会议的结束时间加入Queue
            priorityQueue.offer(end);
            res = Math.max(res, priorityQueue.size());
        }
        return res;
    }

    public int maxEvents(int[][] events) {
        if (events == null || events.length == 0) {
            return 0;
        }

        Arrays.sort(events, (e1, e2) -> e1[0] - e2[0]);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> o1 - o2);
        int curDay = 1;
        int i = 0;
        int res = 0;
        while (i < events.length || !priorityQueue.isEmpty()) {
            while (events[i][0] == curDay) {
                priorityQueue.offer(events[i][1]);
                i++;
            }

            while (events[i][1] < priorityQueue.peek() && !priorityQueue.isEmpty()) {
                priorityQueue.poll();
            }

            if (!priorityQueue.isEmpty()) {
                priorityQueue.poll();
                res++;
            }

            curDay++;
        }

        return res;
    }

    public boolean canPartition(int[] nums) {
        int len = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if ((sum & 1) == 1) {
            return false;
        }

        int target = sum / 2;
        boolean[][] dp = new boolean[len][target + 1];

        // 初始化成为 true 虽然不符合状态定义，但是从状态转移来说是完全可以的
        dp[0][0] = true;

        if (nums[0] <= target) {
            dp[0][nums[0]] = true;
        }
        for (int i = 1; i < len; i++) {
            for (int j = 0; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];
                if (nums[i] <= j) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                }
            }

            // 由于状态转移方程的特殊性，提前结束，可以认为是剪枝操作
            if (dp[i][target]) {
                return true;
            }
        }
        return dp[len - 1][target];
    }

    // lc 505
    public int shortDistance(int[][] maze, int[] start, int[] end) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);

        int[][] mind = new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            Arrays.fill(mind[i], Integer.MAX_VALUE);
        }
        mind[start[0]][start[1]] = 0;
        int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int[] curNode = queue.poll();
            // 上下左右
            for (int[] dir : dirs) {
                int curX = curNode[0];
                int curY = curNode[1];
                int step = mind[curNode[0]][curNode[1]];

                // 每次都要一直走到头
                while (curX >= 0 && curY >= 0 &&
                        curX < maze.length && curY < maze[0].length &&
                        maze[curX][curY] == 0) {
                    curX += dir[0];
                    curY += dir[1];
                    step++;
                }
                // 碰壁返回一格
                curX -= dir[0];
                curY -= dir[1];
                step--;
                // 存最小值
                if (step < mind[curX][curY]) {
                    mind[curX][curY] = step;
                    queue.offer(new int[]{curX, curY});
                }
            }
        }

        if (mind[end[0]][end[1]] == Integer.MAX_VALUE) {
            return -1;
        }
        return mind[end[0]][end[1]];
    }

    // lc 567
    public boolean checkInclusionV2(String subS1, String testS2) {
        if (testS2.length() < subS1.length()) {
            return false;
        }

        int[] subS1Cnt = new int[26];
        int[] testS2Cnt = new int[26];
        for (int i = 0; i < subS1.length(); i++) {
            subS1Cnt[subS1.charAt(i) - 'a']++;
            testS2Cnt[testS2.charAt(i) - 'a']++;
        }

        if (Arrays.equals(subS1Cnt, testS2Cnt)) {
            return true;
        }

        for (int i = subS1.length(); i < testS2.length(); i++) {
            testS2Cnt[testS2.charAt(i - subS1.length()) - 'a']--;
            testS2Cnt[testS2.charAt(i) - 'a']++;

            if (Arrays.equals(subS1Cnt, testS2Cnt)) {
                return true;
            }
        }

        return false;
    }

    // lc 75
    public void sortColors(int[] nums) {
        int p0 = 0, p2 = nums.length - 1;
        for (int i = 0; i <= p2; i++) {
            while (p2 >= i && nums[i] == 2) {
                swap(nums, p2, i);
                p2--;
            }

            if (nums[i] == 0) {
                swap(nums, i, p0);
                p0++;
            }
        }
    }

    // lc 134
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = 0;
        int remainGas = 0;
        int startIdx = 0;

        for (int i = 0; i < gas.length; i++) {
            // 计算下一个点i+1的情况
            remainGas += gas[i] - cost[i];
            totalGas += gas[i] - cost[i];
            if (remainGas < 0) {
                // 剩油用完，代表start只能走到i，下一次只能从i+1开始
                startIdx = (i + 1) % gas.length;
                remainGas = 0;
                // totalGas无论正负不用补，走完可以看到是否欠油
                // 如果可以走完，油总量可以补完
            }
        }

        // 走完整个环的前提是gas的总量要大于cost的总量
        // 即整圈下来油足够
        if (totalGas < 0) {
            return -1;
        }

        return startIdx;
    }

    // lc 456
    public boolean find132pattern(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }

        Stack<Integer> stack = new Stack<>();
        int t2 = -1;
        for (int t1 = nums.length - 1; t1 >= 0; t1--) {
            if (t2 > -1 && nums[t2] > nums[t1]) {
                return true;
            }
            while (!stack.isEmpty() && nums[stack.peek()] < nums[t1]) {
                t2 = stack.pop();
            }
            stack.push(t1);
        }

        return false;
    }

    // lc 114. 二叉树展开为链表
    public void flatten(TreeNode root) {
        while (root != null) {
            //左子树为 null，直接考虑下一个节点
            if (root.left == null) {
                root = root.right;
            } else {
                // 找左子树最右边的节点
                TreeNode pre = root.left;
                while (pre.right != null) {
                    pre = pre.right;
                }
                //将原来的右子树接到左子树的最右边节点
                pre.right = root.right;
                // 将左子树插入到右子树的地方
                root.right = root.left;
                root.left = null;
                // 考虑下一个节点
                root = root.right;
            }
        }
    }

    // lc 55. 跳跃游戏
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int far = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (i <= far) {
                // 走得到则更新最远距离
                far = Math.max(far, nums[i] + i);
                if (far >= nums.length - 1) {
                    return true;
                }
            }
            // 走不到则没机会更新最远距离
        }
        return false;
    }

    // lc 665. 非递减数列
    public boolean checkPossibility(int[] nums) {
        if (nums == null || nums.length < 2) {
            return true;
        }

        boolean hasChance = nums[0] <= nums[1] ? true : false;

        for (int i = 1; i < nums.length - 1; i++) {
            if (nums[i + 1] < nums[i]) {
                if (hasChance) {
                    if (nums[i + 1] >= nums[i - 1]) {
                        nums[i] = nums[i + 1];
                    } else {
                        nums[i + 1] = nums[i];
                    }
                    // !!!
                    hasChance = false;
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    public int findKthLargestV2EZ(int[] nums, int k) {
        PriorityQueue<Integer> minHead = new PriorityQueue<>(nums.length, (a, b) -> a - b);
        for (int i = 0; i < nums.length; i++) {
            minHead.offer(nums[i]);
        }

        for (int i = 0; i < (nums.length - k); i++) {
            minHead.poll();
        }
        return minHead.peek();
    }
}


