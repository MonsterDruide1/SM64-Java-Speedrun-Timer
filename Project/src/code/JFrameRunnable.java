package code;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class JFrameRunnable implements Runnable {
	
	private boolean end = false;

	@Override
	public void run() {
		final JFrame frame = new JFrame("Log");
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JTextArea logFrame = new JTextArea();
		JScrollPane scroll = new JScrollPane(logFrame);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar vertical = scroll.getVerticalScrollBar();
		panel.add(scroll,BorderLayout.CENTER);
		JButton button = new JButton(Main.getInLang("other_file"));
		panel.add(button,BorderLayout.PAGE_END);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.data.remove("file_always");
				Main.loader.saveSettings(Main.data);
				frame.setVisible(false);
				frame.dispose();
				Main.restart=true;
				end=true;
			}
		});
		panel.setSize(400, 400);
		frame.add(panel);
		frame.setSize(new Dimension(400,400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		while((!end)&&(Main.allChecksReady==false||!Main.log.isEmpty())) {
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
