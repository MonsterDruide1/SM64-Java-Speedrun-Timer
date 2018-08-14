package code;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class JFrameRunnable implements Runnable {

	@Override
	public void run() {
		JFrame frame = new JFrame("Log");
		JTextArea logFrame = new JTextArea();
		JScrollPane scroll = new JScrollPane(logFrame);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar vertical = scroll.getVerticalScrollBar();
		frame.add(scroll);
		frame.setSize(new Dimension(400,400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		while(Main.allChecksReady==false||!Main.log.isEmpty()) {
			if(!Main.log.isEmpty()) {
				logFrame.append(Main.log.remove(0)+"\n");
				vertical.setValue(vertical.getMaximum());
			}
			else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		logFrame.append(Main.getInLang("end"));
		vertical.setValue(vertical.getMaximum());
	}

}
