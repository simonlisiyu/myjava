package com.lsy.other;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

/**
 * Created by lisiyu on 2019/8/16.
 */
public class ConcurrentFormat    {



    public static void main(String[] args) {
        try {
            format(new String[]{"123","456","789"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 用多线程去处理"123"，"456"，“789”这个三个字符串，
     * 然后以"147"，"258"，"369"这种形式去输出，请写出代码实现 
     *
     * @param numbers
     * @return
     */
    public static String[] format(final String[] numbers) throws Exception {
        // 存储执行结果的List
        List<FutureTask<String>> results = new ArrayList<FutureTask<String>>();
        final CountDownLatch latch = new CountDownLatch(numbers.length);
        for (int i = 0; i < numbers.length; i++) {
            final int finalI = i;
            FormatThread formatThread = new FormatThread(numbers,finalI,latch);
            FutureTask<String> faeature = new FutureTask<String>(formatThread);
            new Thread(faeature).start();//注意启动方式，FutureTask将被作为Runnable被线程执行
            results.add(faeature);
        }
        //主线程
        latch.await();//阻塞当前线程直到latch中数值为零才执行
        for (FutureTask<String> futureTask : results) {
            System.out.println(futureTask.get());
        }
        return null;
    }

    public static class FormatThread implements Callable<String> {
        String[] numbers;
        Integer index;
        CountDownLatch countDownLatch;

        public FormatThread(){}

        public FormatThread(String[] numbers, Integer index, CountDownLatch latch){
            this.numbers = numbers;
            this.index = index;
            this.countDownLatch = latch;
        }

//        @Override
        public String call() throws Exception {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(numbers[0].toCharArray()[index]);
                stringBuilder.append(numbers[1].toCharArray()[index]);
                stringBuilder.append(numbers[2].toCharArray()[index]);
                return stringBuilder.toString();
            } finally {
                countDownLatch.countDown();
            }
        }
    }

}
