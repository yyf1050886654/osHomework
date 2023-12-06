package ProcessScheduling.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
* @ClassName: MyHelp
* @Description: 帮助窗口
* @author OuO
* @date 2020年5月1日 上午10:15:50
*/ 
public class MyHelp extends JFrame{

	/**
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	JTextArea textArea = null;
	JScrollPane jsp = null;
	
	/**
	* @Title: showHelpWin
	* @Description: 点击帮助按钮时弹出帮助窗口
	* @param file 帮助文档
	* @throws Exception   
	* @return void    返回类型
	* @throws
	*/ 
	public void showHelpWin(File file) throws Exception {
		this.setVisible(true);
		this.setSize(580, 300);
		this.setLocation(300,250);
		textArea = new JTextArea();
		jsp = new JScrollPane(textArea);
		textArea.setText(null);
		textArea.setEditable(false);
		//读取文件
		InputStream is=MyHelp.class.getClassLoader().getResourceAsStream("help.txt");
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader buf = new BufferedReader(isr);
		
		String str = null;
		while ((str = buf.readLine()) != null) {
			textArea.append(str);
			textArea.append("\r\n");
		}
		add(jsp);
		buf.close();
	}
}
