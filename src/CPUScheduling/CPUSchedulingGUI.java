package CPUScheduling;

import CPUScheduling.Algorithm.FCFS;
import CPUScheduling.Algorithm.HRN;
import CPUScheduling.Algorithm.RR;
import CPUScheduling.Algorithm.SJF;
import GUI.welcomePage;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class CPUSchedulingGUI extends JFrame implements ActionListener {

    //定义组件
    private JButton jb2,reset,help=null;
    private JPanel jp1,jp6=null;
    private JTextField jtf1=null;
    private JTextArea jta1 = null;
    private JLabel jlb1=null;
    private Choice cC = null;
    private JTable jTable;
    private JTableHeader jTableHeader;
    private JScrollPane jScrollPane;
    private FCFS fcfs = new FCFS();
    private RR rr = new RR();
    private SJF rr1 = new SJF();
    private HRN rr2 = new HRN();
    private JButton rtb;
    public CPUSchedulingGUI()
    {
        //创建组件
        jb2=new JButton("确认");
        reset = new JButton("重置");
        help = new JButton("帮助");

        cC = new Choice();
        cC.add("FCFS");
        cC.add("RR");
        cC.add("SJF");
        cC.add("HRN");

        //设置监听
        jb2.addActionListener(this);
        reset.addActionListener(this);
        help.addActionListener(this);


        jp1=new JPanel();
        jp6=new JPanel();


        jlb1=new JLabel("请输入时间片长度：");

        jta1 = new JTextArea(300,300);
        jta1.setEditable(false);

        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(jta1);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        jtf1=new JTextField(5);
        //加入到JPanel中
        jp1.add(jlb1);
        jp1.add(jtf1);


        jp6.add(cC);
        jp6.add(jb2);
        jp6.add(reset);
        jp6.add(help);

        this.jp1.setBounds(0,0,350,30);
        this.jp6.setBounds(0,360,370,30);
        this.jScrollPane.setBounds(370,40,310,330);

        //加入JFrame中
        this.add(jp1);
        this.add(jp6);
        this.add(jScrollPane);

        rtb = new JButton("返回主界面");
        rtb.addActionListener(this);
        rtb.setBounds(700-150,0,100,30);
        this.add(rtb);

        initTable();
        //设置布局管理器
        this.setLayout(null);
        //给窗口设置标题
        this.setTitle("处理机调度");
        //设置窗体大小
        this.setSize(700,480);
        //设置窗体初始位置
        this.setLocation(200, 150);
        //设置当关闭窗口时，保证JVM也退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //显示窗体
        this.setVisible(true);
        this.setResizable(true);
        startUpdateThread();
    }
    public void initTable(){
        String[] columnNames = {"进程名","优先级","到达时间","进程运行时间"};
        String[][] tableValues = new String[19][columnNames.length];// 定义数组，用来存储表格数据
        this.jTable = new JTable(tableValues,columnNames);
        jTable.setBounds(10,60,350,300);
        jTable.setBackground(Color.LIGHT_GRAY);
        this.jTableHeader = this.jTable.getTableHeader();
        jTableHeader.setBounds(10,40,350,20);
        this.add(jTable);
        this.add(jTableHeader);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Objects.equals(e.getActionCommand(), "确认")){
            int tableLevels = jTable.getRowCount();
            for (int i=0;i<tableLevels;i++){
                if(jTable.getValueAt(i,0) == null){
                    tableLevels = i;
                    break;
                }
            }
            int select = cC.getSelectedIndex();
            Object[][] valueAt =new Object[tableLevels][4];
            for (int i=0;i<tableLevels;i++){
                for (int j=0;j<4;j++){
                    valueAt[i][j] =jTable.getValueAt(i,j);
                }
            }
            System.out.println(Arrays.deepToString(valueAt));

            switch (select){
                case 0:
                    fcfs.initProcess(valueAt);
                    new Thread(fcfs).start();
                    break;
                case 1:
                    if(Objects.equals(jtf1.getText(), "")){
                        JOptionPane.showMessageDialog(null,"请输入时间片长度");
                    }
                    else {
                        rr.setRRTime(Integer.parseInt(jtf1.getText()));
                        rr.initProcess(valueAt);
                        new Thread(rr).start();
                    }
                    break;
                case 2:
                    rr1.initProcess(valueAt);
                    new Thread(rr1).start();
                    break;
                case 3:
                    rr2.initProcess(valueAt);
                    new Thread(rr2).start();
                    break;
            }
        }
        if (Objects.equals(e.getActionCommand(), "重置")){
            fcfs = new FCFS();
            rr = new RR();
            rr1 = new SJF();
            rr2 = new HRN();
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
                        if(fcfs.output != null){
                            jta1.setText(fcfs.output.toString());
                        }
                        if (rr.output != null){
                            jta1.setText(rr.output.toString());
                        }
                        if(rr1.output != null){
                            jta1.setText(rr1.output.toString());
                        }
                        if (rr2.output != null){
                            jta1.setText(rr2.output.toString());
                        }

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }
    public static void main(String[] args) {
        new CPUSchedulingGUI();
    }
}
