package ProcessScheduling;

import GUI.welcomePage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ConsumerAndProducerGUI extends JFrame implements ActionListener {
    private JButton jButton,rtb;
    private JLabel jlb1,jlb2;
    private JTextField consumerTextField,producerTextField;
    private JTextArea textArea;
    private JScrollPane jScrollPane;
    private ConsumerAndProducer test4 = new ConsumerAndProducer();
    public ConsumerAndProducerGUI(){
        init();
        this.setLayout(null);
        this.setTitle("生产者-消费者模拟");
        this.setSize(530,400);
        this.setLocation(200, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
        this.setResizable(true);
        startUpdateThread();
    }
    private void init(){
        jButton = new JButton("确认输入");
        jButton.addActionListener(this);

        rtb = new JButton("返回主界面");
        rtb.addActionListener(this);

        jlb1 = new JLabel("请输入生产者数量：");
        jlb2 = new JLabel("请输入消费者数量：");

        consumerTextField = new JTextField(10);
        producerTextField = new JTextField(10);
        textArea = new JTextArea(300,300);
        textArea.setEditable(false);

        consumerTextField.setBounds(50,50,100,40);
        producerTextField.setBounds(50,150,100,40);
        jButton.setBounds(50,200,100,40);
        //textArea.setBounds(370,25,300,500);
        rtb.setBounds(50,250,100,40);

        jlb1.setBounds(50,10,200,30);
        jlb2.setBounds(50,100,200,30);

        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(textArea);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setBounds(200,25,300,300);
        this.add(jScrollPane);

        this.add(jButton);
        this.add(consumerTextField);
        this.add(producerTextField);
        this.add(jScrollPane);
        this.add(jlb1);
        this.add(jlb2);
        this.add(rtb);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Objects.equals(e.getActionCommand(), "确认输入")){
            int consumerNum = Integer.parseInt(consumerTextField.getText());
            int producerNum = Integer.parseInt(producerTextField.getText());
            for (int i=0;i<consumerNum;i++){
                Thread thread = new Thread(test4.new Consumer());
                thread.setName("Consumer"+i);
                thread.start();
            }
            for (int i=0;i<producerNum;i++){
                Thread thread = new Thread(test4.new Producer());
                thread.setName("Producer"+i);
                thread.start();
            }
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
                    textArea.setText(test4.outputbuff.toString());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException en) {
                        en.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new ConsumerAndProducerGUI();
    }
}
