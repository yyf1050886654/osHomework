package MemoryManagement.LRU;

import java.util.List;

public class LRU {
    private int capacity;
    private List<Integer> pageList;
    public int pageMissCount = 0;

    public LRU(int capacity, List<Integer> list){
        this.pageList = list;
        this.capacity = capacity;
    }
    public String simulate(){
        LRUCache lruCache = new LRUCache(capacity);
        StringBuilder output= new StringBuilder();
        for (int i=0;i<pageList.size();i++){
            output.append("ID=").append(i).append("访问=").append(pageList.get(i));
            boolean temp = lruCache.put(pageList.get(i),1);
            if(!temp)//发生页面替换
            {
                output.append("   =>发生缺页，页面").append(lruCache.head).append("被替换");
                pageMissCount++;
            }
            output.append("\n");
        }
        //System.out.println(output);
        return output.toString();
    }
    public double calculateMissRatio(){
        System.out.println(pageMissCount+"    "+capacity+"       "+pageList.size());
        return  (double) (pageMissCount+capacity)/(double)pageList.size();
    }
}
