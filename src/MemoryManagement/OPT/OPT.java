package MemoryManagement.OPT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OPT {
    private int capacity;
    private List<Integer> pageList;
    public int pageMissCount = 0;
    private int[] data;
    public OPT(int capacity, List<Integer> list){
        this.pageList = list;
        this.capacity = capacity;
        listToArr();
    }
    public void listToArr(){
        data = new int[pageList.size()];
        for(int i=0;i<pageList.size();i++){
            data[i] = pageList.get(i);
        }
    }
    public String simulate(){
        List<Integer> list = new ArrayList<>(); //正在使用的页 (2)->(2,3)->(2,3)->(2,3,1)
        StringBuilder output= new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int n = data[i];
            if (list.contains(n)) {
                output.append("ID=").append(i).append("访问=").append(pageList.get(i)).append("\n");
                //没有缺页
                System.out.println(list);
                continue;
            }
            if (list.size() < capacity) {
                output.append("ID=").append(i).append("访问=").append(pageList.get(i)).append("\n");
                //缺页了但是不用换
                list.add(n);
            } else {
                output.append("ID=").append(i).append("访问=").append(pageList.get(i));
                //缺页但是要换
                int[] pages = Arrays.copyOfRange(data, i, data.length);
                int[] distance = opt(pages, list);
                list.set(minIndex(distance), n);
                output.append("   =>发生缺页，页框").append(minIndex(distance)+1).append("的页被替换").append("\n");
            }
            pageMissCount++;
            System.out.println(list);
        }
        return output.toString();
    }
    public static void main(String[] args) {

    }
    public double calculateMissRatio(){
        return  (double) pageMissCount/(double)pageList.size();
    }
    /**
     * 比较几个距离哪个更远
     *
     * @param nums
     * @return 返回索引值
     */
    private static int minIndex(int... nums) {
        int min = nums[0];
        int index = 0;
        for (int i = 1; i < nums.length; i++) {
            if (min < nums[i]) {
                min = nums[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * 计算页内每页距离现在多长时间后再次访问
     *
     * @param pages 还未加载的页
     * @return
     */
    private static int[] opt(int[] pages, List<Integer> list) {
        int[] distance = new int[list.size()];
        for (int i = 0; i < distance.length; i++) {
            distance[i] = indexOf(pages, list.get(i));
        }
        return distance;
    }

    private static int indexOf(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }

        return Integer.MAX_VALUE;
    }
}
