package org.apache.ibatis.lstest;

/**
 * @program: mybatis
 * @author: lishuai
 * @create: 2019-06-21 10:28
 */
public class Mythread extends Thread {

    public int index;


    public Mythread(int index) {
        this.index = index;
    }
    @Override
    public void run() {

//测试中断
//        if (index == 2) {
//            System.out.println("中断开始"+Thread.currentThread().getName()+" : "+index);
//            Thread.currentThread().interrupt();
//            System.out.println("中断结束"+Thread.currentThread().getName()+" : "+index);
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        } else {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }



            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("111111111111111111111111111111");
            }
        System.out.println(Thread.currentThread().getName()+" : "+index);

//测试抛出异常后，线程池会新建线程
//       if (index == 2) {
//            int num = index / 0;
//       }





    }
}
