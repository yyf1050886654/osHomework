package CPUScheduling;

public class PCB {

    private String processName;
    private int priority;
    private int comingTime;
    private int needTime;
    private int state =1;
    private int comingNumber;
    private int finishTime;
    private double responseRatio;
    private double NeedTime;

    public double getNT(){
        return this.NeedTime;
    }

    public double getResponseRatio() {
        return responseRatio;
    }

    public void setResponseRatio(double responseRatio) {
        this.responseRatio = responseRatio;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public PCB(String processName, int priority, int comingTime, int needTime) {
        this.processName = processName;
        this.priority = priority;
        this.comingTime = comingTime;
        this.needTime = needTime;
        this.NeedTime = needTime;
    }

    public PCB(String processName, int priority, int comingTime, int needTime, int comingNumber) {
        this.processName = processName;
        this.priority = priority;
        this.comingTime = comingTime;
        this.needTime = needTime;
        this.comingNumber = comingNumber;
        this.NeedTime = needTime;
    }

    public int getComingNumber() {
        return comingNumber;
    }

    public void setComingNumber(int comingNumber) {
        this.comingNumber = comingNumber;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getComingTime() {
        return comingTime;
    }

    public void setComingTime(int comingTime) {
        this.comingTime = comingTime;
    }

    public int getNeedTime() {
        return needTime;
    }

    public void setNeedTime(int needTime) {
        this.needTime = needTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "PCB{" +
                ", processName='" + processName + '\'' +
                ", priority=" + priority +
                ", comingTime=" + comingTime +
                ", needTime=" + needTime +
                ", state=" + state +
                ", comingNumber=" + comingNumber +
                '}';
    }
}
