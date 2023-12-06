package GUI;

import CPUScheduling.CPUSchedulingGUI;
import DiskScheduling.DiskSchedulingGUI;
import FileScheduling.FileSchedulingGUI;
import MemoryManagement.memoryManagementGUI;
import ProcessScheduling.ConsumerAndProducerGUI;
import ProcessScheduling.banker.BankerGUI;
import ProcessScheduling.main.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;


public class welcomePage extends JFrame implements ActionListener
{
	     //定义组件
		JButton jb1,jb2,jb3,jb4,jb5,jb6=null;
		JLabel jlb1=null;
		public static void main(String[] args) {
			// TODO Auto-generated method stub

			welcomePage welcomePage =new welcomePage();
		}
		
	    //构造函数
		public welcomePage()    //不能申明为void!!!!!否则弹不出新界面
		{
			//创建组件
			jb1=new JButton("处理机调度作业");
			jb2=new JButton("生产者消费者模拟");
			jb3=new JButton("银行家算法模拟");
			jb4=new JButton("内存管理模拟");
			jb5=new JButton("磁盘移臂调度");
			jb6=new JButton("文件管理模拟");

			jb1.addActionListener(this);
			jb2.addActionListener(this);
			jb3.addActionListener(this);
			jb4.addActionListener(this);
			jb5.addActionListener(this);
			jb6.addActionListener(this);

			jlb1=new JLabel("电计2003班--杨逸帆--20201081112");

			jlb1.setBounds(10,10,300,30);
			jb1.setBounds(100,50,200,30);
			jb2.setBounds(100,100,200,30);
			jb3.setBounds(100,150,200,30);
			jb4.setBounds(100,200,200,30);
			jb5.setBounds(100,250,200,30);
			jb6.setBounds(100,300,200,30);

			this.add(jlb1);
			this.add(jb1);
			this.add(jb2);
			this.add(jb3);
			this.add(jb4);
			this.add(jb5);
			this.add(jb6);


			//设置布局管理器
			this.setLayout(null);
			this.setTitle("操作系统课程设计");
			this.setSize(400,390);
			this.setLocation(200, 200);		
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.setResizable(true);

		}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (Objects.equals(e.getActionCommand(), "处理机调度作业")){
			dispose();
			new CPUSchedulingGUI();
		}
		if (Objects.equals(e.getActionCommand(), "生产者消费者模拟")){
			dispose();
			new ConsumerAndProducerGUI();
		}
		if (Objects.equals(e.getActionCommand(), "银行家算法模拟")){
			dispose();
			new BankerGUI();
		}
		if (Objects.equals(e.getActionCommand(), "内存管理模拟")){
			dispose();
			new memoryManagementGUI();
		}
		if (Objects.equals(e.getActionCommand(), "磁盘移臂调度")){
			dispose();
			new DiskSchedulingGUI();
		}
		if (Objects.equals(e.getActionCommand(), "文件管理模拟")){
			dispose();
			new FileSchedulingGUI();
		}
	}

}
