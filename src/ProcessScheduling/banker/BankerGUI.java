package ProcessScheduling.banker;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;
import GUI.welcomePage;


public class BankerGUI extends JFrame implements ActionListener {
    private JTable max,need,allocation;
    private JTableHeader maxHead,needHead,allocationHead,availableHead;
    private int processNum=8,sourceNum;
    private JTextField jtf1,jtf2,jtf3;
    private JLabel l1,l2,l3;
    private JLabel jlb1,jlb2,jlb3,jlb4;
    private JButton jbt1,jbt2,jbt3,jbt4;
    private JPanel jp1,jp2,jp3;
    private JTextArea jta1;
    private JScrollPane jScrollPane;
    private JButton rtb;

    private Banker b = new Banker();

    private String[] sourceName = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"};
    private int Max[][], Allocation[][],Need[][],Available[],Request[],Work[],Finish[],Security[];

    public static void main(String[] args) {
        new BankerGUI();
    }
    public BankerGUI(){
        l1 = new JLabel("Max");
        l2 = new JLabel("Allocation");
        l3 = new JLabel("Need");

        l1.setBounds(150+200+50,30,150,30);
        l2.setBounds(150+200+150+30+50,30,150,30);
        l3.setBounds(150+200+300+30+30+50,30,150,30);

        jbt4 = new JButton("重置");
        jlb1 = new JLabel("请输入资源数量：");
        jlb2 = new JLabel("各资源数量：");
        jbt1 = new JButton("确定");
        jbt1.addActionListener(this);
        jbt4.addActionListener(this);

        jp1 = new JPanel();
        jtf1 = new JTextField(10);
        jp1.add(jlb1);
        jp1.add(jtf1);
        jp1.setBounds(0,230,300,30);
        this.add(jp1);

        jp2 = new JPanel();
        jtf2 = new JTextField(10);
        jp2.add(jlb2);
        jp2.add(jtf2);
        jp2.setBounds(0,260,300,30);
        this.add(jp2);

        jbt1.setBounds(0,290,100,30);
        this.add(jbt1);

        jta1 = new JTextArea(300,300);
        jta1.setEditable(false);

        this.add(jta1);

        jbt2 = new JButton("输入完成");
        jbt2.addActionListener(this);
        jbt2.setBounds(100,290,100,30);
        jbt4.setBounds(200,290,100,30);
        this.add(jbt2);
        this.add(jbt4);

        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(jta1);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setBounds(10,10,300,200);
        this.add(jScrollPane);

        jbt3 = new JButton("请求分配");
        jbt3.addActionListener(this);
        jbt3.setBounds(500,270,100,30);
        this.add(jbt3);


        jtf3 = new JTextField(10);
        jlb4 = new JLabel("请输入申请资源（最后一位表示进程号）：");
        jp3 = new JPanel();
        jp3.add(jlb4);
        jp3.add(jtf3);
        jp3.setBounds(350,230,500,30);
        this.add(jp3);

        this.add(l1);
        this.add(l2);
        this.add(l3);

        rtb = new JButton("返回主界面");
        rtb.addActionListener(this);
        rtb.setBounds(150+200+300+30+30+40+150-150,0,100,30);
        this.add(rtb);

        this.setLayout(null);
        //给窗口设置标题
        this.setTitle("银行家算法模拟");
        //设置窗体大小
        this.setSize(150+200+300+30+30+40+150,380);
        //设置窗体初始位置
        this.setLocation(200, 150);
        //设置当关闭窗口时，保证JVM也退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //显示窗体
        this.setVisible(true);
        this.setResizable(true);
        startUpdateThread();
    }
    private void initData(){
        //初始化资源名称数组
        String[] temp = new String[sourceNum];
        System.arraycopy(sourceName, 0, temp, 0, sourceNum);
        sourceName = temp;
    }
    private void initTables(){
        //初始化max表格
        String[][] tableValues = new String[processNum][sourceNum];
        String[][] tableValues1 = new String[processNum][sourceNum];
        String[][] tableValues2 = new String[processNum][sourceNum];
        this.max = new JTable(tableValues,sourceName);
        this.need = new JTable(tableValues1,sourceName);
        this.allocation = new JTable(tableValues2,sourceName);

        max.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i=0;i<sourceNum;i++){
            max.getColumnModel().getColumn(i).setWidth(150/sourceNum);
            allocation.getColumnModel().getColumn(i).setWidth(150/sourceNum);
            need.getColumnModel().getColumn(i).setWidth(150/sourceNum);
        }

        max.setBounds(150+200,70,150,130);
        allocation.setBounds(150+200+150+30,70,150,130);
        need.setBounds(150+200+300+30+30,70,150,130);

        this.maxHead = this.max.getTableHeader();
        this.allocationHead = this.allocation.getTableHeader();
        this.needHead = this.need.getTableHeader();

        maxHead.setBounds(150+200,50,150,20);
        allocationHead.setBounds(150+200+150+30,50,150,20);
        needHead.setBounds(150+200+300+30+30,50,150,20);

        max.setBackground(Color.LIGHT_GRAY);
        allocation.setBackground(Color.LIGHT_GRAY);
        need.setBackground(Color.LIGHT_GRAY);

        this.add(max);
        this.add(maxHead);
        this.add(need);
        this.add(needHead);
        this.add(allocation);
        this.add(allocationHead);

    }
    private void updateNeed(){
        for (int i=0;i<processNum;i++){
            for (int j=0;j<sourceNum;j++){
                need.setValueAt(Need[i][j]+"",i,j);
            }
        }
    }
    private void updateWork(){
        for (int i=0;i<processNum;i++){
            for (int j=0;j<sourceNum;j++){
                Work[j] += Allocation[i][j];
            }
        }

    }
    private void updateAvail(){
        for (int i=0;i<sourceNum;i++){
            Available[i] -= Work[i];
        }
    }
    private void updateAllocation(){
        for (int i=0;i<processNum;i++){
            for (int j=0;j<sourceNum;j++){
                allocation.setValueAt(Allocation[i][j]+"",i,j);
            }
        }
    }

    private void initArrays(){
        Max= new int[processNum][sourceNum];//最大需求矩阵
        Allocation=new int[processNum][sourceNum];//系统已分配矩阵
        Need=new int[processNum][sourceNum];//还需要资源矩阵
        Available=new int[sourceNum];//可用资源矩阵
        Request=new int[sourceNum];//请求资源向量
        Work=new int[sourceNum];//存放系统可提供资源量
        Finish=new int[sourceNum]; //标记系统是否有足够的资源分配给各个进程
        Security=new int[sourceNum];//存放安全序列
        String[] num = jtf2.getText().split(" ");
        for (int i=0;i<num.length;i++){
            Available[i] = Integer.parseInt(num[i]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Objects.equals(e.getActionCommand(), "确定")){
            sourceNum = Integer.parseInt(jtf1.getText());
            initData();
            initTables();
        }
        if(Objects.equals(e.getActionCommand(), "输入完成")){
            //得到有多少行
            for (int i=0;i<max.getRowCount();i++){
                if(max.getValueAt(i,0) == null){
                    processNum = i;
                    break;
                }
            }
            //System.out.println(processNum+"   "+sourceNum);
            initArrays();
            //初始化max,allocation
            for (int i=0;i<processNum;i++){
                for (int j=0;j<sourceNum;j++){
                    Max[i][j] = Integer.parseInt((String) max.getValueAt(i,j));
                    Allocation[i][j] = Integer.parseInt((String) allocation.getValueAt(i,j));
                    Need[i][j] = Max[i][j]-Allocation[i][j];
                }
            }
            //更新need
            updateNeed();
            updateWork();
            updateAvail();
            b.initForGUI(Max,Allocation,Need,Available,Work,processNum,sourceNum);
            b.showdata();
            if(b.safe()!=1)
            {
                System.exit(0);
            }
        }
        if(Objects.equals(e.getActionCommand(), "请求分配")){
            //最后一位为进程号
            String[] num = jtf3.getText().split(" ");
            int[] input = new int[num.length];
            for (int i=0;i<num.length;i++){
                input[i] = Integer.parseInt(num[i]);
            }
            System.out.println(Arrays.toString(input));
            b.bank(input);
            Allocation = b.getAllocation();
            updateNeed();
            updateWork();

        }
        if(Objects.equals(e.getActionCommand(), "重置")){
            b = new Banker();
        }
        if (Objects.equals(e.getActionCommand(),"返回主界面")){
            new welcomePage();
            dispose();
        }
    }
    public void startUpdateThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                        if (b.output!=null){
                            jta1.setText(b.output.toString());
                        }
                        if (Allocation != null){
                            updateAllocation();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }
}
