package CPUScheduling.Algorithm;

import CPUScheduling.PCB;

import java.util.*;

public class FCFS implements Runnable{
    private Scanner scanner = new Scanner(System.in);
    private List<PCB> PCBList = new ArrayList<>();
    private Queue<PCB> readyQueue = new ArrayDeque<>();
    private PCB workingPCB;
    private int CPUTime = 0;

    public void initProcess(int num){
        for (int i=0;i<num;i++){
            System.out.println("请输入进程名，优先级，到达时间，服务时间");
            String processName =  scanner.next();
            int priority = scanner.nextInt();
            int comingTime = scanner.nextInt();
            int needTime = scanner.nextInt();
            PCBList.add(new PCB(processName,priority,comingTime,needTime));
            readyQueue.add(new PCB(processName,priority,comingTime,needTime));
        }
    }

    public void initProcess(Object [][] input){
        for (int i=0;i<input.length;i++){
            String processName = (String) input[i][0];
            int priority = Integer.parseInt((String) input[i][1]);
            int comingTime = Integer.parseInt((String) input[i][2]);
            int needTime = Integer.parseInt((String) input[i][3]);
            PCBList.add(new PCB(processName,priority,comingTime,needTime));
            readyQueue.add(new PCB(processName,priority,comingTime,needTime));
        }
    }
    @Override
    public void run() {
        int count=0;
        while (!readyQueue.isEmpty()){
            if(readyQueue.peek().getComingTime() <= CPUTime){
                workingPCB = readyQueue.poll();
                int needTime = workingPCB.getNeedTime();
                PCBList.get(count).setState(2);
                while (needTime > 0){
                    try {
                        show();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    needTime--;
                    CPUTime++;
                    PCBList.get(count).setNeedTime(needTime);
                }
                PCBList.get(count).setState(3);
                PCBList.get(count).setFinishTime(CPUTime);
                count++;
            }
            else {
                try {
                    show();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                CPUTime++;
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
    public void simulate() throws InterruptedException {
        int count=0;
        while (!readyQueue.isEmpty()){
            if(readyQueue.peek().getComingTime() <= CPUTime){
                workingPCB = readyQueue.poll();
                int needTime = workingPCB.getNeedTime();
                PCBList.get(count).setState(2);
                while (needTime > 0){
                    show();
                    needTime--;
                    CPUTime++;
                    PCBList.get(count).setNeedTime(needTime);
                }
                PCBList.get(count).setState(3);
                PCBList.get(count).setFinishTime(CPUTime);
                count++;
            }
            else {
                show();
                CPUTime++;
            }
        }
        show();
        cycleTime();
        weightedCycleTime();

    }
    public StringBuffer output;
    public void show() throws InterruptedException {
        output = new StringBuffer();
        output.append("当前执行先来先服务算法").append("\n");
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
    public double cycleTime(){
        double time=0;
        for (int i = 0; i<PCBList.size(); i++){
            System.out.println(PCBList.get(i).getFinishTime());
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
    public static void main(String[] args) throws InterruptedException {
        //假设到达时间顺序输入
        FCFS fcfs = new FCFS();
        fcfs.initProcess(2);
        fcfs.simulate();
        System.out.println(fcfs.output);
    }
}
