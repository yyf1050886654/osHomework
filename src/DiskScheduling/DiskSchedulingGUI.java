package DiskScheduling;

import GUI.welcomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class DiskSchedulingGUI extends JFrame implements ActionListener {

    //定义组件
    JButton jb1,jb2=null;
    JRadioButton jrb1,jrb2=null;
    JPanel jp1,jp2,jp3,jp4,jp5,jp6=null;
    JTextField jtf1,jtf2=null;
    JLabel jlb1,jlb2,jlb3,jlb4=null;
    Choice cC1,cC2 = null;
    private JButton rtb;
    public DiskSchedulingGUI()
    {
        //创建组件
        jb1=new JButton("确认输入");
        //设置监听
        jb1.addActionListener(this);


        jp1=new JPanel();
        jp2=new JPanel();
        jp3=new JPanel();
        jp4=new JPanel();
        jp5=new JPanel();
        jp6=new JPanel();

        jlb1=new JLabel("请输入磁盘访问序列（空格间隔，范围0～200，不重复）：");
        jlb2=new JLabel("请选择调度算法：");
        jlb3=new JLabel("请指定磁头起始位置：");
        jlb4=new JLabel("请指定磁头移动方向：");

        cC1 = new Choice();
        cC1.add("FCFS");
        cC1.add("SSTF");
        cC1.add("SCAN");
        cC1.add("CSCAN");

        cC2 = new Choice();
        cC2.add("磁道序号递增");
        cC2.add("磁道序号递减");

        jtf1=new JTextField(30);
        jtf2=new JTextField(10);
        rtb = new JButton("返回主界面");
        rtb.addActionListener(this);
        //rtb.setBounds(700-150,330,100,30);
        //this.add(rtb);
        //加入到JPanel中
        jp1.add(jlb1);

        jp2.add(jtf1);

        jp3.add(jlb2);
        jp3.add(cC1);

        jp4.add(jlb3);
        jp4.add(jtf2);

        jp5.add(jlb4);
        jp5.add(cC2);

        jp6.add(jb1);
        jp6.add(rtb);

        //加入JFrame中
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        this.add(jp5);
        this.add(jp6);


        //设置布局管理器
        this.setLayout(new GridLayout(6,1));
        //给窗口设置标题
        this.setTitle("磁盘调度");
        //设置窗体大小
        this.setSize(400,220);
        //设置窗体初始位置
        this.setLocation(200, 150);
        //设置当关闭窗口时，保证JVM也退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //显示窗体
        this.setVisible(true);
        this.setResizable(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Objects.equals(e.getActionCommand(), "确认输入")){
            String disks = jtf1.getText();
            int select1 = cC1.getSelectedIndex();
            int initLocation = Integer.parseInt(jtf2.getText());
            //获取方向
            int select2 = cC2.getSelectedIndex();
            String[] num = disks.split(" ");
            int[] disksList = new int[num.length];
            for(int i=0;i<num.length;i++){
                disksList[i] = Integer.parseInt(num[i]);
            }
            new Demo(initLocation,disksList,select2,disksList.length);
        }
        if (Objects.equals(e.getActionCommand(),"返回主界面")){
            new welcomePage();
            dispose();
        }
    }
    public static void main(String[] args) {
        DiskSchedulingGUI ms=new DiskSchedulingGUI();
    }
}
