package org.apache.ibatis.lstest;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @program: mybatis
 * @author: lishuai
 * @create: 2019-06-21 10:27
 *
 *
 * 为什么直接中断线程没影响？？？
 */
public class TestMyThreadPoolExecutor {

    public static void main(String[] args) {
        ExecutorService defaultThreadPool = Executors.newFixedThreadPool(5);

        //ExecutorService defaultThreadPool = new ThreadPoolExecutor(3,6,0,TimeUnit.SECONDS,new LinkedBlockingQueue<>(4));

        List<Mythread> threads = new ArrayList<>(10);

        for (int  i = 0 ; i < 10; i++) {
            Mythread mythread = new Mythread(i);
            threads.add(mythread);
            defaultThreadPool.execute(mythread);
        }

        for (int i = 0; i < threads.size(); i++) {
            Mythread thread = threads.get(i);
           if (thread.index == 2) {
               thread.interrupt();
               System.out.println("中断index=2");
           }
        }

    }
}
