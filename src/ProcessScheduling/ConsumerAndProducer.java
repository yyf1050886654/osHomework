package ProcessScheduling;

import java.util.concurrent.Semaphore;

public class ConsumerAndProducer {
 
    private static Integer count = 0;
    //创建三个信号量
    final Semaphore notFull = new Semaphore(10);
    //notEmpty的入参为0表示，第一次进行acquire就会被阻塞，直到进行了release操作，释放了一个许可
    final Semaphore notEmpty = new Semaphore(0);
    final Semaphore mutex = new Semaphore(1);
    public String output = "";
    public StringBuffer outputbuff = new StringBuffer();
    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    notFull.acquire();
                    mutex.acquire();
                    count++;
                    outputbuff.append(Thread.currentThread().getName()).append("      生产者生产，目前总共有").append(count).append("\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mutex.release();
                    notEmpty.release();
                }
            }
        }
    }
    class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    notEmpty.acquire();
                    mutex.acquire();
                    count--;
                    outputbuff.append(Thread.currentThread().getName()).append("       消费者消费，目前总共有").append(count).append("\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mutex.release();
                    notFull.release();
                }
            }
        }
    }
}
