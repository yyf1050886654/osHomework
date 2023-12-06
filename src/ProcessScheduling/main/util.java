package ProcessScheduling.main;

import ProcessScheduling.frame.MyFrame;

public class util {
	/**
	* @Title: showInfo
	* @Description: 将字符串添加到显示框显示
	* @param string   
	* @return void    返回类型
	* @throws
	*/ 
	public synchronized static void showInfo(String string) {
		//将字符串添加到显示框
		MyFrame.textArea.append(string);
		//显示框滚动条自动跟随文本最后一行
		MyFrame.textArea.setCaretPosition(MyFrame.textArea.getDocument().getLength());
	}

public static void clear() {
		MyFrame.proList.clear();
		MyFrame.comList.clear();
		MyFrame.conList.clear();
		MyFrame.setEmpty(false);
		MyFrame.setFull(false);
	}
}
