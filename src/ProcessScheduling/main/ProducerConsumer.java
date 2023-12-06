package ProcessScheduling.main;

import ProcessScheduling.frame.MyFrame;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ProducerConsumer extends Thread{
    
    /**
    * @Fields MAX : 缓冲区的数量上限
    */ 
    private int MAX = 1;
    
    /**
    * @Fields lock : Lock对象，为临界区加锁和释放
    */ 
    private final Lock lock = new ReentrantLock();
    
    /**
    * @Fields full : 缓冲池内满缓冲区
    */ 
    private final Condition full = lock.newCondition();
    
    /**
    * @Fields empty : 缓冲区内空缓冲区
    */ 
    private final Condition empty = lock.newCondition();
    
    /**
    * @Fields numbers : 生产者队列待生产的产品数
    */ 
    private int numbers=100;

    public ProducerConsumer() {
        for(int i=0;i<numbers;i++) {
        	MyFrame.proList.add(new String(String.valueOf(i)));
        }
    }


    @Override
    public void run() {
    	new Producer().start();
        new Consumer().start();
        new Producer().start();
        new Consumer().start();
    }
   
    public synchronized int getNumbers() {
		return numbers;
	}

	
	public synchronized int getMAX() {
		return MAX;
	}

	public synchronized void setMAX(int mAX) {
		MAX = mAX;
	}

	
	public synchronized void lessNumber() {
		this.numbers=this.numbers-1;
	}

	/**
    * @ClassName: Producer
    * @Description: 生产者进程
    * @author OuO
    * @date 2020年5月1日 下午2:15:39
    */ 
    class Producer extends Thread {
		
    	/* (non-Javadoc)
    	* Title: run 
    	* Description: 重写run方法
    	* @see java.lang.Thread#run()
    	*/ 
    	@Override
        public void run() {
            while (getNumbers()>=1) {
            	//加锁
                lock.lock();
                try {
                	//线程停止3秒是为了让程序走的慢一些，不至于一眨眼就结束了
                	sleep(3000);
                   if (MyFrame.comList.size() == getMAX()) {
                	   //若公共缓冲池已满，将当前进程加入等待队列
                	   //直到被唤醒：full.signal
                        util.showInfo("警告:Full!\n生产者受阻\n");
                        MyFrame.setFull(true);
                        //刷新画面
                        MyFrame.centerPanel.repaint();
                        //当前进程进入等待队列
                        full.await();
                    }
                   
                    String str = MyFrame.proList.removeLast();
                    if (MyFrame.comList.add(str)) {
                    	//若object对象成功添加进队列comList
                    	//说明生产者进程成功生产一个产品并送入公共缓冲池
                    	util.showInfo("生产者: "+str+"\n");
                    	MyFrame.setEmpty(false);
                        MyFrame.centerPanel.repaint();
                        //唤醒等待的消费者进程
                        empty.signal();
                    }
                } catch (InterruptedException ie) {
                	//捕获到异常说明进程被异常中断
                	util.showInfo("生产异常终止!\n");
                } finally {
                	//次数减一
                	lessNumber();
                	//释放锁
                    lock.unlock();
                    try {
                    	//线程随机暂停几秒是为了让生产者和消费者之间的运行产生冲突
                    	//从而达到演示的目的
						sleep(new Random().nextInt(10000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                }
            }
        }
    }

    /**
    * @ClassName: Consumer
    * @Description: 消费者进程
    * @author OuO
    * @date 2020年5月1日 下午2:35:56
    */ 
    class Consumer extends Thread {
    	
    	/* (non-Javadoc)
    	* Title: run
    	* Description: 重写run方法
    	* @see java.lang.Thread#run()
    	*/ 
    	@Override
        public void run() {
            while (!(getNumbers()==0 && MyFrame.comList.size()==0)) {
            	//加锁
                lock.lock();
                try {
                	//线程停止2秒是为了让程序走的慢一些，不至于一眨眼就结束了
                	sleep(2000);
                    if (MyFrame.comList.size() == 0) {
                    	//若comList长度为0说明公共缓冲池里没有没有缓冲区可用
                    	//即缓冲区为空，没有产品可取
                    	util.showInfo("警告: Empty!\n消费者受阻!\n");
                    	MyFrame.setEmpty(true);
                    	//刷新画面
                    	MyFrame.centerPanel.repaint();
                    	
                        //将当前进程放入等待队列直到被唤醒：empty.signal()
                        empty.await();
                    }
                    
                	//消费者进程成功将产品从缓冲池取出
                    String str = MyFrame.comList.removeLast();
                    if(MyFrame.conList.add(str)) {
                    	util.showInfo("消费者: " + str+"\n");
                    	MyFrame.setFull(false);
                    	 MyFrame.centerPanel.repaint();
                         //唤醒在等待的生产者进程
                         full.signal();
                    }
                } catch (InterruptedException ie) {
                	util.showInfo("消费异常终止!\n");
                } finally {
                	//释放锁
                    lock.unlock();
                    try {
                    	//线程随机暂停几秒是为了让生产者和消费者之间的运行产生冲突
                    	//从而达到演示的目的
						sleep(new Random().nextInt(8000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                }
            }
            util.showInfo("结束");
        }
    }   
}
