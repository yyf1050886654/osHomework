package DiskScheduling;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.swing.*;
/**
 * @author HuangAn
 * @date 2021/12/28
 */

public class Demo extends JFrame implements ActionListener{	//主面板

	JButton btnFCFS;	//先来先服务算法（FCFS）
	JButton btnSSTF;	//最短寻道时间优先算法（SSTF）
	JButton btnSCAN;		//扫描算法（SCAN）
	//JButton btnCSCAN;		//循环扫描算法（C-SCAN）
	JPanel jMainPanel = new JPanel();	//按钮面板
	JPanel buttons = new JPanel();		//绘图面板
	JLabel jLabel1;
	JLabel jLabel2;
	StringBuffer s;

	int start;
	int[] arr;

	int fcfsNUM=0;
	int sstfNUM=0;
	int scanNUM=0;
	private int length=10;
	private int orient;
	public Demo(int orient){
		this.orient = orient;
	}

	public Demo(int start, int[] arr,int orient,int length){
		super("绘制形状");
		this.orient = orient;
		this.start=start;
		this.arr=arr;
		this.length = length;
		StringBuffer stringBuffer=new StringBuffer();

		for (int tt=0;tt<arr.length;tt++){
			stringBuffer.append(arr[tt]);
			stringBuffer.append(" ");
		}

		this.s=stringBuffer;


		int fcfsNUM=0;    //先来先服务算法（FCFS）
		int temp=start;
		for (int i=0;i<arr.length;i++){
			fcfsNUM+= Math.abs(arr[i] - temp);
			temp=arr[i];
		}
		this.fcfsNUM=fcfsNUM;



		List<Object> resultList = Arrays.stream(arr).boxed().collect(Collectors.toList());//最短寻道时间优先算法（SSTF）
		int min=300;
		int temp1 = 0;
		int jiLuID=0;
		int start1=start;
		int[] newArr=new int[length];
		for (int i=0;i<arr.length;i++){
			for (int j=0;j<resultList.size();j++){

				if (min>=Math.abs((int)(resultList.get(j))-start1)){
					min=Math.abs((int)(resultList.get(j))-start1);
					temp1=(int)resultList.get(j);
					jiLuID=j;
				}
			}
			newArr[i]=temp1;
			min=300;
			resultList.remove(jiLuID);
			start1=temp1;
		}
		int sstfNUM=0;
		int temp2=start;
		for (int i=0;i<newArr.length;i++){
			sstfNUM+= Math.abs(newArr[i] - temp2);
			temp2=newArr[i];
		}
		this.sstfNUM=sstfNUM;



		int[] scanArr=new int[length];   //扫描算法（SCAN）
		for (int u=0;u<arr.length;u++){
			scanArr[u]=arr[u];
		}

		Arrays.sort(scanArr);
		int[] newArrscan=new int[length+1];
		if (orient == 1){
			int pp=0;
			while (scanArr[pp]<start){
				pp++;
			}
			for (int j=pp;j>0;j--){
				newArrscan[pp-j]=scanArr[j-1];
			}
			newArrscan[pp]=0;
			for (int o=pp+1,h=pp;h<scanArr.length;h++){
				newArrscan[o]=scanArr[h];
				o++;
			}
		}
		else {
			int i=length-1;
			while (scanArr[i]>start){
				i--;
			}
			for (int j=i;j<length-1;j++){
				newArrscan[j-i] = scanArr[j+1];
			}
			newArrscan[length-1-i]=200;
			for (int o=length-1-i+1,h=i;h>=0;h--){
				newArrscan[o]=scanArr[h];
				o++;
			}
		}
		int scanNUM=0;
		int temp3=start;
		for (int i1=0;i1<newArrscan.length;i1++){
			scanNUM+= Math.abs(newArrscan[i1] - temp3);
			temp3=newArrscan[i1];
		}
		this.scanNUM=scanNUM;






		btnFCFS = new JButton("FCFS");
		btnSSTF = new JButton("SSTF");
		btnSCAN = new JButton("SCAN");
		//btnCSCAN = new JButton("CSCAN");


		btnFCFS.addActionListener(this);
		btnSSTF.addActionListener(this);
		btnSCAN.addActionListener(this);
		//btnCSCAN.addActionListener(this);


        this.setLayout(null);

		buttons.add(btnFCFS);
		buttons.add(btnSSTF);
		buttons.add(btnSCAN);
		//buttons.add(btnCSCAN);



		jMainPanel.setLayout(new BorderLayout());		//绘图面板设置布局
        buttons.setBounds(20,0,1000,100);
        jMainPanel.setBounds(20,100,1000,300);

        jLabel1=new JLabel("磁盘队列为：");
        jLabel1.setBounds(20,0,200,20);

         buttons.add(jLabel1);

		this.getContentPane().add(buttons);
		this.getContentPane().add(jMainPanel);



		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100,50);
		setSize(1200,400);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("FCFS")){
			jLabel1.setText("    先来先服务算法总共移动磁道数："+fcfsNUM+"  。  随机磁道序列："+s);
			jMainPanel.removeAll();
				jMainPanel.add(new FCFS(start,arr,length));
				jMainPanel.repaint();
				jMainPanel.validate();

		}
		if(ae.getActionCommand().equals("SSTF")){
			jLabel1.setText("最短寻道时间优先算法总共移动磁道数："+sstfNUM+"  。  随机磁道序列："+s);

			jMainPanel.removeAll();
				jMainPanel.add(new SSTF(start,arr,length));
				jMainPanel.repaint();
				jMainPanel.validate();

		}

		if(ae.getActionCommand().equals("SCAN")){
			jLabel1.setText("         扫描算法总共移动磁道数："+scanNUM+"  。  随机磁道序列："+s);
			jMainPanel.removeAll();
			jMainPanel.add(new SCAN(start,arr,orient,length));
			jMainPanel.repaint();
			jMainPanel.validate();
		}
	}
}

class FCFS extends JPanel{		//先来先服务算法（FCFS）

	public int start;
	public int[] x;
	private int length;
	public FCFS(int start,int[] x,int length) {
		this.length = length;
		this.start=start;
		int[] newArr;
		newArr=x;
		this.x = newArr;
	}

	public void paintComponent(Graphics g){
		g.drawString(String.valueOf(start),start*4+2,10);
		g.drawLine(start*4,0,x[0]*4,20);
		for (int i=0;i<length-1;i++){
			g.drawLine(x[i]*4,20+20*i,x[i+1]*4,40+20*i);
		}
			for (int i=0;i<x.length;i++){
			g.drawString(String.valueOf(x[i]),x[i]*4+2,20*(i+1));
		}
	}
}

class SSTF extends JPanel{		//最短寻道时间优先算法（SSTF）

	public int start;
	public int[] x;
	private int length;


	public SSTF(int start,int[] x,int length) {
		this.start=start;
		this.length = length;
		List<Object> resultList = Arrays.stream(x).boxed().collect(Collectors.toList());
		int min=300;
		int temp = 0;

		int jiLuID=0;


		//int[] newArr=new int[10];
		int[] newArr=new int[length];


		for (int i=0;i<x.length;i++){
			for (int j=0;j<resultList.size();j++){

				if (min>=Math.abs((int)(resultList.get(j))-start)){
					min=Math.abs((int)(resultList.get(j))-start);
					temp=(int)resultList.get(j);
					jiLuID=j;
				}
			}
			newArr[i]=temp;
			min=300;
			resultList.remove(jiLuID);
			start=temp;
		}

		this.x = newArr;
	}

	public void paintComponent(Graphics g){

		g.drawString(String.valueOf(start),start*4+2,10);
		g.drawLine(start*4,       0,x[0]*4,20);
		for (int i=0;i<length-1;i++){
			g.drawLine(x[i]*4,20+20*i,x[i+1]*4,40+20*i);
		}
		for (int i=0;i<x.length;i++){
			g.drawString(String.valueOf(x[i]),x[i]*4+2,20*(i+1));
		}
	}
}

class SCAN extends JPanel{		//扫描算法（SCAN）

	public int start;
	public int[] x;
	private int orient;
	private int length;


	public SCAN(int start,int[] y,int orient,int length) {
		this.length = length;
		this.start=start;
		this.orient = orient;
		int[] x=new int[length];
		for (int u=0;u<y.length;u++){
			x[u]=y[u];
		}

		Arrays.sort(x);
		int[] newArr=new int[length+1];
		if (orient == 1){
			int i=0;
			while (x[i]<start){
				i++;
			}

			for (int j=i;j>0;j--){
				newArr[i-j]=x[j-1];
			}
			newArr[i]=0;
			for (int o=i+1,h=i;h<x.length;h++){
				newArr[o]=x[h];
				o++;
			}
		}
		else {
			int i=length-1;
			while (x[i]>start){
				i--;
			}
			for (int j=i;j<length-1;j++){
				newArr[j-i] = x[j+1];
			}
			newArr[length-1-i]=200;
			for (int o=length-1-i+1,h=i;h>=0;h--){
				newArr[o]=x[h];
				o++;
			}
		}


		this.x =newArr;
	}

	public void paintComponent(Graphics g){

		g.drawString(String.valueOf(start),start*4+2,10);
		g.drawLine(start*4,       0,x[0]*4,20);
		for (int i=0;i<length;i++){
			g.drawLine(x[i]*4,20+20*i,x[i+1]*4,40+20*i);
		}
		for (int i=0;i<x.length;i++){
			g.drawString(String.valueOf(x[i]),x[i]*4+2,20*(i+1));
		}
	}
}