package com.lusir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class MeoGo {
    private static void testWeightedRand_V1() throws Exception {
        int[] input = {4, 1, 3, 2};
        WeightedRand_V1 rand = new WeightedRand_V1(input);
        int[] result = new int[input.length];
        int runCount = 1000 * input.length;
        for (int i = 0; i < runCount; i++) {
            int next = rand.next();
            if (next < 0 || next > input.length) {
                throw new Exception("unexpected rand value: " + next);
            }
            result[next]++;
        }

        int sum = 0;
        for (int j : input) {
            sum += j;
        }

        for (int i = 0; i < input.length; i++) {
            double realP = result[i] / (double) runCount;
            double expectedP = input[i] / (double) sum;
            if (realP - expectedP > 0.1 || realP - expectedP < -0.1) {
                throw new Exception("unexpected probability " + realP + " for value " + i + ", expected is " + expectedP);
            }
        }
    }


    private static void testWeightedRand_V2() throws Exception {
        int[] input = {4, 1, 3, 2};
        WeightedRand_V2 rand = new WeightedRand_V2(input);
        int[] result = new int[input.length];
        int runCount = 1000 * input.length;
        for (int i = 0; i < runCount; i++) {
            int next = rand.next();
            if (next < 0 || next > input.length) {
                throw new Exception("unexpected rand value: " + next);
            }
            result[next]++;
        }

        int sum = 0;
        for (int j : input) {
            sum += j;
        }

        for (int i = 0; i < input.length; i++) {
            double realP = result[i] / (double) runCount;
            double expectedP = input[i] / (double) sum;
            if (realP - expectedP > 0.1 || realP - expectedP < -0.1) {
                throw new Exception("unexpected probability " + realP + " for value " + i + ", expected is " + expectedP);
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("Testing Q1...");
            testWeightedRand_V1();
            System.out.println("PASSED!");
        } catch (Exception e) {
            System.out.println("FAILED: " + e.toString());
        }
        try {
            System.out.println("Testing Q2...");
            testWeightedRand_V2();
            System.out.println("PASSED!");
        } catch (Exception e) {
            System.out.println("FAILED: " + e.toString());
        }
        try {
            System.out.println("Testing Q3...");
            testParallelTaskExecutor();
            System.out.println("PASSED!");
        } catch (Exception e) {
            System.out.println("FAILED: " + e.toString());
        }
    }


    /**
     * 系统开始执行时间
     */
    private static long startTime;

    /**
     * 创建一个虚拟的任务
     *
     * @param wait        指定任务耗时, 毫秒
     * @param shouldThrow 指定任务是否抛出异常, 如果为 true, 任务将抛出一个 Exception
     * @return 返回一个虚拟任务, 在开始执行后大约 wait 毫秒后执行完毕
     */
    private static Callable<Integer> createCallable(long wait, boolean shouldThrow) {
        return () -> {
            Thread.sleep(wait);
            long costTime = (System.currentTimeMillis() - startTime);
            // 以 100 为单位向下取整, 屏蔽精度对结果的影响.
            long expectedCostTime = costTime - (costTime % 100);
            if (shouldThrow) {
                throw new Exception(Long.toString(expectedCostTime));
            }
            return (int) expectedCostTime;
        };
    }

    private static void testParallelTaskExecutor() throws Exception {
        ParallelTaskExecutor pool = new ParallelTaskExecutor(2);
        List<String> expectedResults = Arrays.asList(
                "RETURN: 100",
                "RETURN: 200",
                "THROW: java.lang.Exception: 300",
                "RETURN: 400",
                "RETURN: 600",
                "RETURN: 700"
        );

        List<String> actualResults = new ArrayList<>();

        startTime = System.currentTimeMillis();
        List<Future<Integer>> futureList = new ArrayList<>();
        futureList.add(pool.submit(createCallable(101, false)));
        futureList.add(pool.submit(createCallable(202, false)));
        futureList.add(pool.submit(createCallable(203, true)));
        futureList.add(pool.submit(createCallable(204, false)));
        futureList.add(pool.submit(createCallable(305, false)));

        for (Future<Integer> future : futureList) {
            try {
                Integer v = future.get();
                actualResults.add("RETURN: " + v.toString());
            } catch (Exception e) {
                actualResults.add("THROW: " + e.getMessage());
            }
        }

        Future<Integer> anotherTask = pool.submit(createCallable(106, false));
        try {
            actualResults.add("RETURN: " + anotherTask.get().toString());
        } catch (Exception e) {
            actualResults.add("THROW: " + e.getMessage());
        }

        for (int i = 0; i < expectedResults.size(); i++) {
            if (!expectedResults.get(i).equals(actualResults.get(i))) {
                throw new Exception(
                        String.format("Unexpected \"%s\" for task %d, expected is \"%s\"",
                                actualResults.get(i), i, expectedResults.get(i))
                );
            }
        }
    }
}


