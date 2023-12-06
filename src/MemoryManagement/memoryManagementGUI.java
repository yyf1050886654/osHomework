package MemoryManagement;

import GUI.welcomePage;
import MemoryManagement.FIFO.FIFO;
import MemoryManagement.LRU.LRU;
import MemoryManagement.OPT.OPT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class memoryManagementGUI extends JFrame implements ActionListener {

    //定义组件
    JButton jb1,jb2,jb3=null;
    JPanel jp1,jp2,jp3,jp4,jp5,jp6=null;
    JTextField jtf1,jtf2=null;
    JTextArea jta,jta1 = null;
    JLabel jlb1,jlb2,jlb3,jlb4=null;
    Choice cC = null;
    List<Integer> list = new ArrayList<>();
    JScrollPane jScrollPane;
    private JButton rtb;
    public memoryManagementGUI()
    {
        //创建组件
        jb1=new JButton("随机产生序列");
        jb2=new JButton("确认执行");
        jb3=new JButton("确认输入");

        cC = new Choice();
        cC.add("OPT");
        cC.add("LRU");
        cC.add("FIFO");

        //设置监听
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);


        jp1=new JPanel();
        jp2=new JPanel();
        jp3=new JPanel();
        jp4=new JPanel();
        jp5=new JPanel();
        jp6=new JPanel();


        jlb1=new JLabel("请输入要分配的页框数：");
        jlb2=new JLabel("请输入要随机产生的访问序列的长度：");
        jlb3=new JLabel("已生成页面访问顺序如下：");

        jta = new JTextArea(100,100);
        //jta.setEditable(false);

        jta1 = new JTextArea(300,300);
        jta1.setEditable(false);


        jtf1=new JTextField(5);
        jtf2=new JTextField(5);
        //加入到JPanel中
        jp1.add(jlb1);
        jp1.add(jtf1);

        jp2.add(jlb2);
        jp2.add(jtf2);

        jp3.add(jb1);
        jp3.add(jb3);
        jp4.add(jlb3);


        jp6.add(cC);
        jp6.add(jb2);

        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(jta1);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.jp1.setBounds(0,0,350,30);
        this.jp2.setBounds(0,30,350,30);
        this.jp3.setBounds(0,60,350,30);
        this.jp4.setBounds(0,90,350,30);
        this.jta.setBounds(20,120,300,100);
        this.jp6.setBounds(0,320,300,30);
        //this.jta1.setBounds(370,25,300,300);
        this.jScrollPane.setBounds(370,25,300,300);

        //加入JFrame中
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        this.add(jta);
        this.add(jp6);
        //this.add(jta1);
        this.add(jScrollPane);

        rtb = new JButton("返回主界面");
        rtb.addActionListener(this);
        rtb.setBounds(700-150,330,100,30);
        this.add(rtb);

        //设置布局管理器
        this.setLayout(null);
        //给窗口设置标题
        this.setTitle("页面调度");
        //设置窗体大小
        this.setSize(700,400);
        //设置窗体初始位置
        this.setLocation(200, 150);
        //设置当关闭窗口时，保证JVM也退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //显示窗体
        this.setVisible(true);
        this.setResizable(true);

    }
    public void createList(){
        String[] num = jta.getText().split(" ");
        for (int i=0;i<num.length;i++){
            list.add(Integer.valueOf(num[i]));
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(Objects.equals(e.getActionCommand(), "随机产生序列")){
            list.clear();
            int pageLength = Integer.parseInt(jtf2.getText());
            Random random = new Random();
            for(int i=0;i<pageLength;i++){
                list.add(random.nextInt(9)+1);
            }
            jta.setText(list.toString());
            System.out.println(list);
        }
        if (Objects.equals(e.getActionCommand(),"确认输入")){
            list.clear();
            createList();
        }
        if(Objects.equals(e.getActionCommand(), "确认执行")){
            if(list.isEmpty()){
                JOptionPane.showMessageDialog(null,"请先生成随机序列或输入序列～","提示消息",JOptionPane.WARNING_MESSAGE);
            }
            else {
                System.out.println(list);
                int pageNum = Integer.parseInt(jtf1.getText());
                int select = cC.getSelectedIndex();
                switch (select){
                    case 0:
                        OPT opt = new OPT(pageNum,list);
                        jta1.setText(opt.simulate()+"\n"+"缺页次数为："+opt.pageMissCount+"\n"+"缺页率为："+opt.calculateMissRatio());
                        break;
                    case 1:
                        LRU lru = new LRU(pageNum,list);
                        jta1.setText(lru.simulate()+"\n"+"缺页次数为："+(lru.pageMissCount+pageNum)+"\n"+"缺页率为："+lru.calculateMissRatio());
                        break;
                    case 2:
                        FIFO fifo = new FIFO(pageNum,list);
                        jta1.setText(fifo.simulate()+"\n"+"缺页次数为："+(fifo.pageMissCount+pageNum)+"\n"+"缺页率为："+fifo.calculateMissRatio());
                        break;

                }
            }

        }
        if (Objects.equals(e.getActionCommand(),"返回主界面")){
            new welcomePage();
            dispose();
        }

    }

    public static void main(String[] args) {
        memoryManagementGUI ms=new memoryManagementGUI();
    }
}
    




