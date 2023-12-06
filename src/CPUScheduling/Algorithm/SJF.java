package CPUScheduling.Algorithm;

import CPUScheduling.PCB;

import java.util.*;

public class SJF implements Runnable{
    private Scanner scanner = new Scanner(System.in);
    private List<PCB> PCBList = new ArrayList<>();//全局的控制进程信息
    private Queue<PCB> initQueue = new ArrayDeque<>();//初始化队列
    private Queue<PCB> readyQueue = new ArrayDeque<>();//就绪队列
    private PCB workingPCB;//正在处理机上的进程
    public int CPUTime = 0;//当前CPU时间

    Comparator<PCB> comparator = new Comparator<PCB>() {
        @Override
        public int compare(PCB o1, PCB o2) {
            return o1.getNeedTime() - o2.getNeedTime();
        }
    };

    public void initProcess(int num){
        for (int i=0;i<num;i++){
            System.out.println("请输入进程名，优先级，到达时间，服务时间");
            String processName =  scanner.next();
            int priority = scanner.nextInt();
            int comingTime = scanner.nextInt();
            int needTime = scanner.nextInt();
            PCBList.add(new PCB(processName,priority,comingTime,needTime,i));
            //readyQueue.add(new PCB(processName,priority,comingTime,needTime,i));
            initQueue.add(new PCB(processName,priority,comingTime,needTime,i));
        }
        //System.out.println(initQueue);
    }
    public void initProcess(Object [][] input){
        for (int i=0;i<input.length;i++){
            String processName = (String) input[i][0];
            int priority = Integer.parseInt((String) input[i][1]);
            int comingTime = Integer.parseInt((String) input[i][2]);
            int needTime = Integer.parseInt((String) input[i][3]);
            PCBList.add(new PCB(processName,priority,comingTime,needTime));
            initQueue.add(new PCB(processName,priority,comingTime,needTime,i));
        }
    }
    public boolean init_to_ready(){
        boolean flag = false;
        while (initQueue.peek()!=null && initQueue.peek().getComingTime() == CPUTime){
            readyQueue.add(initQueue.poll());
            sort();
            //System.out.println("更新了");
            flag = true;
        }
        sort();
        return flag;
    }
    public StringBuffer output;
    public void show() throws InterruptedException {
        output = new StringBuffer();
        output.append("当前执行短进程优先算法").append("\n");
        output.append("当前CPU时间:").append(CPUTime).append("\n");
        output.append("进程名  优先级  到达时间  剩余服务时间  状态").append("\n");
        System.out.println("CPUTIME:" + CPUTime);
        System.out.println("NAME  PRIORITY  COMINGTIME  NEEDTIME  STATE");
        for (int i = 0; i< PCBList.size(); i++){
            PCB temp = PCBList.get(i);
            String str = "";
            switch (temp.getState()){
                case 1:
                    str = "ready";
                    break;
                case 2:
                    str = "working";
                    break;
                case 3:
                    str = "finish";
                    break;
            }
            output.append(temp.getProcessName()).append("   ").
                    append(temp.getPriority()).append("   ").
                    append(temp.getComingTime()).append("   ").
                    append(temp.getNeedTime()).append("   ").append(str).append("\n");
            System.out.println(temp.getProcessName()+"   "+temp.getPriority()+"   "+temp.getComingTime()+"   "+temp.getNeedTime()+"   "+str);
        }
        Thread.sleep(1000);
    }

    public void simulate() throws InterruptedException {
        int count;
        //初始化
        while (!init_to_ready()){
            //System.out.println(111);
            show();
            CPUTime++;
        }
        //进入第一个进程
        while (!readyQueue.isEmpty()){
            if(readyQueue.peek().getComingTime() <= CPUTime){
                workingPCB = readyQueue.poll();
                int needTime = workingPCB.getNeedTime();
                count = workingPCB.getComingNumber();
                PCBList.get(count).setState(2);
                while (needTime > 0){
                    show();
                    needTime--;
                    CPUTime++;
                    init_to_ready();
                    PCBList.get(count).setNeedTime(needTime);
                }
                PCBList.get(count).setFinishTime(CPUTime);
                PCBList.get(count).setState(3);
            }
            else {
                show();
                CPUTime++;
                init_to_ready();
            }
        }
        show();
    }
    public double cycleTime(){
        double time=0;
        for (int i = 0; i<PCBList.size(); i++){
            time += PCBList.get(i).getFinishTime()-PCBList.get(i).getComingTime();
        }
        System.out.println(time/PCBList.size());
        output.append("平均周转时间为：").append(time/PCBList.size()).append("\n");
        return time;
    }

    public double weightedCycleTime(){
        double time=0;
        for (int i = 0; i<PCBList.size(); i++){
            time += (double) (PCBList.get(i).getFinishTime()-PCBList.get(i).getComingTime())/PCBList.get(i).getNT();
        }
        System.out.println(time/PCBList.size());
        output.append("平均带权周转时间为：").append(time/PCBList.size()).append("\n");
        return time;
    }
    public void sort(){
        List<PCB> list = new ArrayList<>();
        while (!readyQueue.isEmpty()){
            list.add(readyQueue.poll());
        }
        list.sort(comparator);
        readyQueue.addAll(list);
        //System.out.println(list);
    }
    public static void main(String[] args) throws InterruptedException {
        //假设到达时间顺序输入
        SJF rr = new SJF();
        rr.initProcess(1);
        rr.simulate();
        System.out.println(rr.cycleTime());
    }

    @Override
    public void run() {
        int count;
        //初始化
        while (!init_to_ready()){
            //System.out.println(111);
            try {
                show();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            CPUTime++;
        }
        //进入第一个进程
        while (!readyQueue.isEmpty()){
            if(readyQueue.peek().getComingTime() <= CPUTime){
                workingPCB = readyQueue.poll();
                int needTime = workingPCB.getNeedTime();
                count = workingPCB.getComingNumber();
                PCBList.get(count).setState(2);
                while (needTime > 0){
                    try {
                        show();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    needTime--;
                    CPUTime++;
                    init_to_ready();
                    PCBList.get(count).setNeedTime(needTime);
                }
                PCBList.get(count).setFinishTime(CPUTime);
                PCBList.get(count).setState(3);
            }
            else {
                try {
                    show();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                CPUTime++;
                init_to_ready();
            }
        }
        try {
            show();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        cycleTime();
        weightedCycleTime();
    }
}
