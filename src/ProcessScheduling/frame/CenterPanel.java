package ProcessScheduling.frame;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class CenterPanel extends JPanel{
	
	/**
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;

	public CenterPanel() {
		this.setBackground(Color.WHITE);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.gray);
		//画出生产者队列中的所有产品（不是线程）
		for(int i=0;i<MyFrame.proList.size();i++) {
			g.fill3DRect(35, i*20+5, 200, 20, true);
			g.drawString(MyFrame.proList.get(i), 235, i*20+15);
		}
		
		if(MyFrame.isFull()) {
			//若公共缓冲池已满，将公共缓冲池里的缓冲区变为黄色
			g.setColor(Color.YELLOW);
		}
		//画出公共缓冲池中所有缓冲区（不是线程）
		for(int i=0;i<MyFrame.comList.size();i++) {
			g.fill3DRect(285, i*20+5, 200, 20, true);
			g.drawString(MyFrame.comList.get(i), 485, i*20+15);
		}
		
		//将画笔颜色调回灰色
		g.setColor(Color.gray);
		// 画出消费者队列中所有已取得的任务（不是线程）
		for (int i = 0; i < MyFrame.conList.size(); i++) {
			g.fill3DRect(535, i * 20 + 5, 200, 20, true);
			g.drawString(MyFrame.conList.get(i), 735, i*20+15);
		}
		
		if(MyFrame.isEmpty()) {
			//若公共缓冲池为空
			g.setColor(Color.red);
			g.fill3DRect(285, 5, 200, 200, true);
			g.setColor(Color.black);
			g.drawString("Empty", 385, 100);
		}		
	}	
}

