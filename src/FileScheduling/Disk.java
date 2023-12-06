package FileScheduling;

import java.util.*;

public class Disk {
    private int diskSize = 500;//磁盘的盘块数量
    private List<DiskBlock> diskBlockList = new ArrayList<>();
    private Queue<DiskBlock> emptyBlockQueue = new ArrayDeque<>();
    private List<DiskBlock> fullBlockList = new ArrayList<>();
    public List<DiskBlock> indexBlockList = new ArrayList<>();
    Iterator<DiskBlock> iterator;

    public Disk(){
        for(int i=0;i<diskSize;i++){
            DiskBlock diskBlock = new DiskBlock(0,0,i);
            //创建500个磁盘块
            diskBlockList.add(diskBlock);
            emptyBlockQueue.add(diskBlock);
        }
    }
    public List<Integer> requestIndexBlock(int indexSum){
        if(!emptyBlockQueue.isEmpty()){
            if(emptyBlockQueue.size()-1 < indexSum){
                System.out.println("错误：空闲块少于请求块");
                return null;
            }
            else {
                int diskNum = emptyBlockQueue.poll().getDiskNum();
                diskBlockList.get(diskNum).setSTATE(2);//作为索引块
                List<Integer> list = requestBlock(indexSum);
                List<DiskBlock> diskBlocks = new ArrayList<>();
                for (Integer integer : list) {
                    diskBlocks.add(diskBlockList.get(integer));
                }
                diskBlockList.get(diskNum).setBlockIndex(diskBlocks);//赋予这个块队列
                indexBlockList.add(diskBlockList.get(diskNum));
                System.out.println("索引盘块："+diskNum);
                list.add(diskNum);
                return list;
            }

        }else {
            System.out.println("错误：没有空闲块");
            return null;//请求失败，没有空闲块
        }
    }
    //需要多少块
    public List<Integer> requestBlock(int requestNum){
        List<Integer> output = new ArrayList<>();
        for(int i=0;i<requestNum;i++){
            if(!emptyBlockQueue.isEmpty()){
                int diskNum = emptyBlockQueue.poll().getDiskNum();
                diskBlockList.get(diskNum).setSTATE(1);//作为存储块
                output.add(diskNum);
                System.out.println("存储盘块："+diskNum);
            }
        }
        return output;
    }
    public int getIndexBlockListId(DiskBlock indexBlock){
        for (int i=0;i<indexBlockList.size();i++){
            if(indexBlockList.get(i).getDiskNum() == indexBlock.getDiskNum()){
                return i;
            }
        }
        return -1;
    }
    public List<DiskBlock> release(DiskBlock indexBlock){
        List<DiskBlock> list = indexBlock.getBlockIndex();
        for (int i=0;i<list.size();i++){
            int num = list.get(i).getDiskNum();
            diskBlockList.get(num).setSTATE(0);
            emptyBlockQueue.add(diskBlockList.get(num));
            System.out.println("回收存储盘块："+num);
        }
        diskBlockList.get(indexBlock.getDiskNum()).setSTATE(0);
        emptyBlockQueue.add(diskBlockList.get(indexBlock.getDiskNum()));
        /*for (int i=0;i<indexBlockList.size();i++){
            if(indexBlockList.get(i).getDiskNum() == indexBlock.getDiskNum()){
                indexBlockList.remove(i);
            }
        }*/
        System.out.println("回收索引盘块："+indexBlock.getDiskNum());
        list.add(indexBlock);
        return list;
    }

    public static void main(String[] args) {
        Disk disk = new Disk();
        Random random = new Random();
        for (int i=0;i<50;i++){
            int randomNum = random.nextInt(8)+2;
            System.out.println("文件："+(i+1)+"------随机数为："+randomNum+"---------------------");
            disk.requestIndexBlock(randomNum);
        }
        System.out.println(disk.indexBlockList.size()+"\n\n\n\n");
        int count=0;
        for (int i=0;i<50;i+=2){
            System.out.println("文件："+(i+1)+"-------------------------");
            int temp = disk.getIndexBlockListId(disk.indexBlockList.get(count));
            disk.release(disk.indexBlockList.get(temp));
            disk.indexBlockList.remove(temp);
            count++;
        }for (int i=0;i<30;i++){
            int randomNum = random.nextInt(8)+2;
            System.out.println("文件："+(i+1)+"------随机数为："+randomNum+"---------------------");
            System.out.println(disk.requestIndexBlock(randomNum));
        }
        System.out.println(disk.indexBlockList.size()+"\n\n\n\n");


    }
}
