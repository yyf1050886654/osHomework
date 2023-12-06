package MemoryManagement.FIFO;

import java.util.HashMap;
import java.util.LinkedList;

// 时间复杂度：get达到O(1)，set方法O(n)
// 空间复杂度：O(capacity)
public class FIFOCache {
    private LinkedList<Integer> mQueue;
    private HashMap<Integer, Integer> mMap;
    private int mCapacity;
    public int head;
    public FIFOCache(int capacity) {
        this.mCapacity = capacity;
        mQueue = new LinkedList<Integer>();
        mMap = new HashMap<Integer, Integer>();
    }
    public boolean put(int key, int value) {
        boolean num=true;
        Integer result = mMap.get(key);
        if(result != null) {
        } else {
            // 当发现队列里面不存在数据时
            if(mQueue.size() >= mCapacity) {
                // 如果队列大小超过了容量值，就需要把队头元素删掉
                head = mQueue.poll();
                System.out.println("已替换："+head);
                num = false;
                // 并且从hashMap里面抹掉
                mMap.remove(head);
            }
            mQueue.offer(key);
            mMap.put(key, value);
        }
        return num;
    }
}

