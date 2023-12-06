package FileScheduling;

import java.util.ArrayList;
import java.util.List;

public class DiskBlock {
    private int STATE;//表示该磁盘块是作为存储单元还是索引块
    private final int EMPTYSTATE = 0;
    private final int LOADSTATE = 1;
    private final int INDEXSTATE = 2;
    private int diskNum=-1;//该块是磁盘的哪一块
    private double capacity = 2;//2kb
    private double load;//当前装了多少
    private final int indexLength = 1000;//相当于一个块可以装1000条索引
    private int indexNum;
    private List<DiskBlock> blockIndex;//索引
    public DiskBlock(int state,double load,int diskNum){
        //将这个磁盘划为索引还是存储块
        this.STATE = state;
        this.load = load;
        this.diskNum = diskNum;
        if(STATE == INDEXSTATE){
            blockIndex = new ArrayList<>();
        }else if(STATE == EMPTYSTATE){
            this.load = 0;
        }
    }
    public boolean isFull(){
        return load == capacity;
    }
    public boolean isEmpty(){
        return load == 0;
    }

    public int getSTATE() {
        return STATE;
    }

    public List<DiskBlock> getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(List<DiskBlock> blockIndex) {
        this.blockIndex = blockIndex;
    }

    public void setSTATE(int STATE) {
        if(STATE == INDEXSTATE){
            blockIndex = new ArrayList<>();
        }
        this.STATE = STATE;
    }

    public int getDiskNum() {
        return diskNum;
    }

    public void setDiskNum(int diskNum) {
        this.diskNum = diskNum;
    }

    public int getIndexLength() {
        return indexLength;
    }
}
