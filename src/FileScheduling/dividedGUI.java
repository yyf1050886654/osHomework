package FileScheduling;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class dividedGUI extends JFrame{
    private List<JButton> JButtonList = new ArrayList<>();
    private JButton buttonFlagRed, buttonFlagGreen,buttonFlagBlue;
    private JLabel jLabelFlagGreen, jLabelFlagRed,jLabelFlagBlue;
    private List<Integer> list;
    public dividedGUI(List<Integer> list){
        this.list = list;
        initDiskBlock();
        printColor();
        this.setTitle("当前索引块的索引分布");
        this.setLayout(null);
        this.setSize(1000,1000);
        this.setLocation(300, 250);

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
        jLabelFlagGreen = new JLabel("空闲磁盘块");
        jLabelFlagRed = new JLabel("已占用磁盘块");
        jLabelFlagBlue = new JLabel("索引磁盘块");

        buttonFlagRed = new JButton("1");
        buttonFlagGreen = new JButton("0");
        buttonFlagBlue = new JButton("1");

        buttonFlagGreen.setBackground(Color.GREEN);
        buttonFlagGreen.setOpaque(true);
        buttonFlagGreen.setBorderPainted(false);

        buttonFlagRed.setBackground(Color.red);
        buttonFlagRed.setOpaque(true);
        buttonFlagRed.setBorderPainted(false);

        buttonFlagBlue.setBackground(Color.blue);
        buttonFlagBlue.setOpaque(true);
        buttonFlagBlue.setBorderPainted(false);

        buttonFlagGreen.setBounds(860, 15+20*32+32-50, 70, 20);
        buttonFlagRed.setBounds(860, 15+20*32+32, 70, 20);
        buttonFlagBlue.setBounds(860, 15+20*32+32+50, 70, 20);

        jLabelFlagGreen.setBounds(860, 15+20*32+32-75, 100, 20);
        jLabelFlagRed.setBounds(860, 15+20*32+32-25, 100, 20);
        jLabelFlagBlue.setBounds(860, 15+20*32+32+25, 100, 20);

        this.add(buttonFlagGreen);
        this.add(buttonFlagRed);
        this.add(buttonFlagBlue);


        this.add(jLabelFlagGreen);
        this.add(jLabelFlagRed);
        this.add(jLabelFlagBlue);
    }

    public void printColor(){
        for (int j=0;j<list.size()-1;j++){
            JButtonList.get(list.get(j)).setBackground(Color.red);
            JButtonList.get(list.get(j)).setOpaque(true);
            JButtonList.get(list.get(j)).setBorderPainted(false);

        }
        JButtonList.get(list.get(list.size()-1)).setBackground(Color.BLUE);
        JButtonList.get(list.get(list.size()-1)).setOpaque(true);
        JButtonList.get(list.get(list.size()-1)).setBorderPainted(false);
    }

    public static void main(String[] args) {

    }
}
