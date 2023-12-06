package MemoryManagement.FIFO;

import java.util.List;

public class FIFO {
    private int capacity;
    private List<Integer> pageList;
    public int pageMissCount = 0;
    public FIFO(int capacity, List<Integer> list){
        this.pageList = list;
        this.capacity = capacity;
    }
    public String simulate(){
        FIFOCache fifo = new FIFOCache(capacity);
        StringBuilder output= new StringBuilder();
        for (int i=0;i<pageList.size();i++){
            output.append("ID=").append(i).append("访问=").append(pageList.get(i));
            boolean temp = fifo.put(pageList.get(i),1);
            if(!temp)//发生页面替换
            {
                output.append("   =>发生缺页，页面").append(fifo.head).append("被替换");
                pageMissCount++;
            }
            output.append("\n");
        }
        //System.out.println(output);
        return output.toString();
    }
    public double calculateMissRatio(){
        return  (double) (pageMissCount+capacity)/(double)pageList.size();
    }
}
