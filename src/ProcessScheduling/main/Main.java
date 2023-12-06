package ProcessScheduling.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;
import javax.swing.JOptionPane;

import GUI.welcomePage;
import ProcessScheduling.frame.MyFrame;

public class Main implements ActionListener{

	static MyFrame myFrame;
	static ProducerConsumer producerConsumer;
	public static void main(String[] args) {
		myFrame=new MyFrame();
		new ProducerConsumer();
	}
	public Main(int i){
		myFrame=new MyFrame();
		new ProducerConsumer();
	}
	public Main(){

	}
	/* (non-Javadoc)
	* Title: actionPerformed
	* Description: 监听事件，点击按钮时做出相应反应
	* @param e
	* @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	*/ 
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==MyFrame.startButton) {
			//点击开始按钮
			//将显示框清空
			MyFrame.textArea.setText("");
			util.clear();
			String numbers=(String) MyFrame.jcb.getSelectedItem();
			producerConsumer=new ProducerConsumer();
			producerConsumer.setMAX(Integer.valueOf(numbers));
			producerConsumer.run();
			
		}
		
		if(e.getSource()==MyFrame.helpButton) {
			//点击按钮
			File file=new File("./source/help.text");
			try {
				new MyHelp().showHelpWin(file);
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(myFrame, "无法打开帮助文档",
					"帮助", JOptionPane.PLAIN_MESSAGE);
			}			
		}
		if (Objects.equals(e.getActionCommand(),"返回主界面")){
			new welcomePage();
		}
	}
}
