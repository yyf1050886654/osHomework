package FileScheduling;

import GUI.welcomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FileSchedulingGUI extends JFrame implements ActionListener{
    private List<JButton> JButtonList = new ArrayList<>();
    private JButton buttonFlagRed, buttonFlagGreen,buttonFlagBlue;
    private JButton button1, button2,button3,ask;
    private JTextField jTextField;
    private JLabel jLabelFlagGreen, jLabelFlagRed,jLabelFlagBlue;
    Disk disk;
    private JButton rtb;
    public FileSchedulingGUI(){
        disk = new Disk();
        initDiskBlock();

        this.setLayout(null);
        this.setTitle("磁盘管理");
        this.setSize(1000,1000);
        this.setLocation(200, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setLocationRelativeTo(null);//自动居中，不能写在this.setSize()前面
        this.setVisible(true);
        this.setResizable(true);
    }

    private void initDiskBlock(){
        for (int i=0;i<22;i++){
            for (int j=0;j<22;j++){
                JButton jButton = new JButton();
                jButton.setBounds(15 + j * 37, 15 + i * 32, 32, 28);
                jButton.setBackground(Color.green);
                //mac系统特供
                jButton.setOpaque(true);
                jButton.setBorderPainted(false);
                JButtonList.add(jButton);
                this.add(jButton);
            }
        }
        for (int i=0;i<16;i++){
            JButton jButton = new JButton();
            jButton.setBackground(Color.green);
            //mac系统特供
            jButton.setOpaque(true);
            jButton.setBorderPainted(false);
            jButton.setBounds(15 + i * 37, 15+21*32+32, 32, 28);
            JButtonList.add(jButton);
            this.add(jButton);
        }

        button1 = new JButton("随机生成50个");
        button2 = new JButton("删除奇数块");
        button3 = new JButton("随机生成");
        ask = new JButton("按题目要求创建");

        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        ask.addActionListener(this);


        buttonFlagRed = new JButton("1");
        buttonFlagGreen = new JButton("0");
        buttonFlagBlue = new JButton("1");

        jLabelFlagGreen = new JLabel("空闲磁盘块");
        jLabelFlagRed = new JLabel("已占用磁盘块");
        jLabelFlagBlue = new JLabel("索引磁盘块");

        jTextField = new JTextField(10);

        buttonFlagGreen.setBackground(Color.GREEN);
        buttonFlagGreen.setOpaque(true);
        buttonFlagGreen.setBorderPainted(false);

        buttonFlagRed.setBackground(Color.red);
        buttonFlagRed.setOpaque(true);
        buttonFlagRed.setBorderPainted(false);

        buttonFlagBlue.setBackground(Color.blue);
        buttonFlagBlue.setOpaque(true);
        buttonFlagBlue.setBorderPainted(false);

        button1.setBounds(860,100,100,40);
        button2.setBounds(860,160,100,40);
        button3.setBounds(860,220,100,40);
        ask.setBounds(840,500,130,40);

        jTextField.setBounds(860,300,100,40);

        buttonFlagGreen.setBounds(860, 15+20*32+32-50, 70, 20);
        buttonFlagRed.setBounds(860, 15+20*32+32, 70, 20);
        buttonFlagBlue.setBounds(860, 15+20*32+32+50, 70, 20);

        jLabelFlagGreen.setBounds(860, 15+20*32+32-75, 100, 20);
        jLabelFlagRed.setBounds(860, 15+20*32+32-25, 100, 20);
        jLabelFlagBlue.setBounds(860, 15+20*32+32+25, 100, 20);

        rtb = new JButton("返回主界面");
        rtb.addActionListener(this);
        rtb.setBounds(1000-150,0,100,30);
        this.add(rtb);


        this.add(buttonFlagGreen);
        this.add(buttonFlagRed);
        this.add(buttonFlagBlue);

        this.add(jLabelFlagGreen);
        this.add(jLabelFlagRed);
        this.add(jLabelFlagBlue);

        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(ask);

        this.add(jTextField);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(e.getActionCommand(), "随机生成50个")){
            setColor(50);
        }
        if (Objects.equals(e.getActionCommand(), "删除奇数块")){
            int count=0;
            int listSize = disk.indexBlockList.size();
            for (int i=0;i<listSize;i+=2){
                System.out.println("文件："+(i+1)+"-------------------------");
                int temp = disk.getIndexBlockListId(disk.indexBlockList.get(count));
                List<DiskBlock> list = disk.release(disk.indexBlockList.get(temp));
                for(int j=0;j<list.size();j++){
                    JButtonList.get(list.get(j).getDiskNum()).setBackground(Color.green);
                    JButtonList.get(list.get(j).getDiskNum()).setOpaque(true);
                    JButtonList.get(list.get(j).getDiskNum()).setBorderPainted(false);
                }
                //JButtonList.get(list.get(list.size()-1).getDiskNum()).getActionListeners();
                JButtonList.get(list.get(list.size()-1).getDiskNum()).removeActionListener(JButtonList.get(list.get(list.size()-1).getDiskNum()).getActionListeners()[0]);
                disk.indexBlockList.remove(temp);
                count++;
            }
        }
        if (Objects.equals(e.getActionCommand(), "随机生成")){
            int num = Integer.parseInt(jTextField.getText());
            setColor(num);
        }
        if (Objects.equals(e.getActionCommand(),"按题目要求创建")){
            //7k
            temp(disk.requestIndexBlock((int) 7/2));
            //5k
            temp(disk.requestIndexBlock((int) 5/2));
            //2k
            temp(disk.requestIndexBlock((int) 2/2));
            //9k
            temp(disk.requestIndexBlock((int) 9/2));
            //3.5k
            temp(disk.requestIndexBlock((int) 3.5/2));
        }
        if (Objects.equals(e.getActionCommand(),"返回主界面")){
            new welcomePage();
            dispose();
        }

    }
    public void temp(List<Integer> list){
        for (int j=0;j<list.size()-1;j++){
            JButtonList.get(list.get(j)).setBackground(Color.red);
            JButtonList.get(list.get(j)).setOpaque(true);
            JButtonList.get(list.get(j)).setBorderPainted(false);

        }
        JButtonList.get(list.get(list.size()-1)).setBackground(Color.BLUE);
        JButtonList.get(list.get(list.size()-1)).setOpaque(true);
        JButtonList.get(list.get(list.size()-1)).setBorderPainted(false);
        JButtonList.get(list.get(list.size()-1)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new dividedGUI(list);
            }
        });
    }
    public void setColor(int num){
        Random random = new Random();
        for (int i=0;i<num;i++){
            int randomNum = random.nextInt(4)+1;
            System.out.println("文件："+(i+1)+"------随机数为："+randomNum+"---------------------");
            List<Integer> list = disk.requestIndexBlock(randomNum);
            for (int j=0;j<list.size()-1;j++){
                JButtonList.get(list.get(j)).setBackground(Color.red);
                JButtonList.get(list.get(j)).setOpaque(true);
                JButtonList.get(list.get(j)).setBorderPainted(false);

            }
            JButtonList.get(list.get(list.size()-1)).setBackground(Color.BLUE);
            JButtonList.get(list.get(list.size()-1)).setOpaque(true);
            JButtonList.get(list.get(list.size()-1)).setBorderPainted(false);
            JButtonList.get(list.get(list.size()-1)).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new dividedGUI(list);
                }
            });
            //System.out.println(JButtonList.get(list.get(list.size()-1)).getActionListeners());
        }

    }

    public static void main(String[] args) {
        FileSchedulingGUI gui = new FileSchedulingGUI();
    }
}
