package code;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class JFrameRunnable implements Runnable {

	@Override
	public void run() {
		JFrame frame = new JFrame("Log");
		JTextArea logFrame = new JTextArea();
		frame.add(logFrame);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		while(Main.allChecksReady==false||!Main.log.isEmpty()) {
			if(!Main.log.isEmpty()) {
				logFrame.append(Main.log.remove(0)+"\n");
				frame.pack();
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
		frame.pack();
	}

}
