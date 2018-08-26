package code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FileGeneratorGUI implements Runnable {
	
	int window;
	String type;
	JFrame frame;
	JPanel result;
	static int searchForID;
	int numberOfPanels;
	
	static String[] types = new String[] {"","action","enter"};
	
	static List<JTextArea> textAreas = new ArrayList<JTextArea>();
	static List<JComboBox<String>> comboboxen = new ArrayList<JComboBox<String>>();
	static List<JLabel> labels = new ArrayList<JLabel>();
	static List<JButton> buttons = new ArrayList<JButton>();
	
	public FileGeneratorGUI(int window) {
		//1 - Main
		//2 - Search
		this.window=window;
	}
	public FileGeneratorGUI(int window, String type) {
		this.window=window;
		this.type = type;
	}

	@Override
	public void run() {
		switch(window) {
		case 1:
			frame = new JFrame("Generator");
			frame.setLayout(new BorderLayout());
			JPanel scroll = new JPanel();
			JScrollPane contentscroll = new JScrollPane(scroll);
			GridBagLayout layout = new GridBagLayout();
			layout.columnWeights=new double[] {1.0,Double.MIN_VALUE};
			layout.rowWeights=new double[] {0.0,0.0,Double.MIN_VALUE};
			layout.columnWidths = new int[]{0, 0};
			layout.rowHeights = new int[]{0, 0, 0};
			scroll.setLayout(layout);
			GridBagConstraints mainc = new GridBagConstraints();
			mainc.fill=GridBagConstraints.HORIZONTAL;
			mainc.anchor=GridBagConstraints.NORTH;
			JPanel firstPanel = generatePanel(0);
			scroll.add(firstPanel,mainc);
			numberOfPanels=1;
			JButton newPanelButton = new JButton("+");
			mainc.gridy=1;
			scroll.add(newPanelButton,mainc);
			JPanel content = new JPanel(new GridBagLayout());
			GridBagConstraints framec = new GridBagConstraints();
			framec.fill=GridBagConstraints.BOTH;
			framec.anchor=GridBagConstraints.NORTH;
			framec.weightx=1.0;
			framec.weighty=1.0;
			content.add(contentscroll, framec);
			JButton save = new JButton(Main.getInLang("save_close"));
			framec.fill=GridBagConstraints.HORIZONTAL;
			framec.gridy=1;
			framec.weighty=0.0;
			content.add(save,framec);
			frame.add(content,BorderLayout.CENTER);
			frame.setSize(400,400);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			newPanelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JButton newPanelButton = (JButton) scroll.getComponent(numberOfPanels);
					scroll.remove(numberOfPanels);
					double[] result = new double[layout.rowWeights.length+1];
					int[] result2 = new int[layout.rowHeights.length+1];
				    result[0] = 0.0;
				    result2[0] = 0;
				    for(int i = 1; i < layout.rowWeights.length+1; i++) {
				        result[i] = layout.rowWeights[i - 1];
				        result2[0] = layout.rowHeights[i-1];
				    }
				    layout.rowWeights=result;
				    layout.rowHeights=result2;
					scroll.setLayout(layout);
					mainc.anchor=GridBagConstraints.NORTH;
					scroll.add(generatePanel(numberOfPanels),mainc);
					mainc.gridy++;
					scroll.add(newPanelButton, mainc);
					frame.revalidate();
					numberOfPanels++;
				}
			});
			save.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean canSave = true;
					for(int array=0;array<3;array++) {
						if(array==0) {
							for(JTextArea area : textAreas) {
								if(area.getText().isEmpty()) {
									canSave=false;
									new Thread(new Runnable() {
										@Override
										public void run() {
											try {
												Border border = BorderFactory.createLineBorder(Color.red,2);
												Border nothing = BorderFactory.createEmptyBorder();
												area.setBorder(border);
												Thread.sleep(1000);
												area.setBorder(nothing);
												Thread.sleep(1000);
												area.setBorder(border);
												Thread.sleep(1000);
												area.setBorder(nothing);
											}
											catch(Exception e) {
												e.printStackTrace();
											}
										}
									}).start();
								}
							}
						}
						if(array==1) {
							for(JComboBox<String> combobox : comboboxen) {
								if(types[combobox.getSelectedIndex()].equals("")) {
									new Thread(new Runnable() {
										@Override
										public void run() {
											try {
												Border border = BorderFactory.createLineBorder(Color.red,2);
												Border nothing = BorderFactory.createEmptyBorder();
												combobox.setBorder(border);
												Thread.sleep(1000);
												combobox.setBorder(nothing);
												Thread.sleep(1000);
												combobox.setBorder(border);
												Thread.sleep(1000);
												combobox.setBorder(nothing);
											}
											catch(Exception e) {
												e.printStackTrace();
											}
										}
									}).start();
								}
							}
						}
						if(array==2) {
							for(JLabel area : labels) {
								if(area.getText().isEmpty()) {
									canSave=false;
									new Thread(new Runnable() {
										@Override
										public void run() {
											try {
												Border border = BorderFactory.createLineBorder(Color.red,2);
												Border nothing = BorderFactory.createEmptyBorder();
												area.setBorder(border);
												Thread.sleep(1000);
												area.setBorder(nothing);
												Thread.sleep(1000);
												area.setBorder(border);
												Thread.sleep(1000);
												area.setBorder(nothing);
											}
											catch(Exception e) {
												e.printStackTrace();
											}
										}
									}).start();
								}
							}
						}
					}
					if(canSave) {
						FileGenerator.save(textAreas,comboboxen,labels);
						frame.setVisible(false);
						frame.dispose();
						Main.main(Main.args);
					}
				}
			});
			break;
		case 2:
			frame = new JFrame(Main.getInLang("search"));
			frame.setSize(600,400);
			JPanel panel = new JPanel(new GridBagLayout());
			JTextArea area = new JTextArea("");
			area.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					FileGenerator.updateSearchUI(area.getText().toUpperCase(),type);
				}
				@Override
				public void insertUpdate(DocumentEvent arg0) {
					FileGenerator.updateSearchUI(area.getText().toUpperCase(),type);
				}
				@Override
				public void removeUpdate(DocumentEvent arg0) {
					FileGenerator.updateSearchUI(area.getText().toUpperCase(),type);
				}
			});
			
			frame.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.fill=GridBagConstraints.BOTH;
			c.anchor=GridBagConstraints.PAGE_START;
			c.weightx=1.0;
			c.weighty=0.1;

			GridBagConstraints pc = new GridBagConstraints();
			pc.fill=GridBagConstraints.BOTH;
			pc.anchor=GridBagConstraints.PAGE_START;
			pc.weightx=1.0;
			pc.weighty=1.0;
			panel.add(area,pc);
			
			result = new JPanel(new GridBagLayout());
			JScrollPane scrollpane = new JScrollPane(result);
			scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			frame.add(panel,c);
			c.gridy=1;
			c.weighty=10.0;
			c.fill=GridBagConstraints.BOTH;
			frame.add(scrollpane, c);
			frame.setVisible(true);
		    frame.repaint();
		    FileGenerator.updateSearchUI(area.getText().toUpperCase(), type);
			break;
		}
		
	}

	
	public static JPanel generatePanel(final int id) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill=GridBagConstraints.HORIZONTAL;
		c.anchor=GridBagConstraints.PAGE_START;
		JTextArea area = new JTextArea("");
		area.setFont(area.getFont().deriveFont(16f));
		textAreas.add(area);
		
		JComboBox<String> combobox = new JComboBox<String>();
		for(String type : types) {
			combobox.addItem(type);
		}
		comboboxen.add(combobox);
		
		JLabel label = new JLabel(" ");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setBorder(new EmptyBorder(5,0,0,0));
		labels.add(label);
		
		JButton button = new JButton(Main.getInLang("select"));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String type = types[comboboxen.get(id).getSelectedIndex()];
				if(type.equals("")) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Border border = BorderFactory.createLineBorder(Color.red,2);
								Border nothing = BorderFactory.createEmptyBorder();
								comboboxen.get(id).setBorder(border);
								Thread.sleep(1000);
								comboboxen.get(id).setBorder(nothing);
								Thread.sleep(1000);
								comboboxen.get(id).setBorder(border);
								Thread.sleep(1000);
								comboboxen.get(id).setBorder(nothing);
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
				else {
					searchForID=id;
					FileGenerator.selectHex(type);
				}
			}
		});
		buttons.add(button);
		
		combobox.setPreferredSize(new Dimension(0,combobox.getPreferredSize().height));
		area.setPreferredSize(new Dimension(0,combobox.getPreferredSize().height));
		label.setPreferredSize(new Dimension(0,label.getPreferredSize().height));
		button.setPreferredSize(new Dimension(0,button.getPreferredSize().height));
		c.weightx=3.0;
		panel.add(area,c);
		c.weightx=1.0;
		c.gridx=1;
		panel.add(combobox,c);
		c.weightx=1.5;
		c.gridx=2;
		panel.add(label,c);
		c.weightx=0.5;
		c.gridx=3;
		panel.add(button,c);
		
		return panel;
	}
	public void updateSearchUI(final List<String> resultList, final List<String> names) {
		result.removeAll();
		GridBagConstraints c= new GridBagConstraints();
		result.setLayout(new GridBagLayout());
		for(int i=0;i<resultList.size();i++) {
			final int finali = i;
			c.fill=GridBagConstraints.HORIZONTAL;
			c.gridy=i;
			c.weightx=1.0;
			c.weighty=1.0;
			
			c.gridx=0;
			result.add(new JLabel(names.get(i)),c);
			c.gridx=1;
			result.add(new JLabel(resultList.get(i)),c);
			c.gridx=2;
			JButton button = new JButton(Main.getInLang("select"));
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(type.equals("action")) {
						FileGeneratorGUI.labels.get(searchForID).setText(Main.actions[1][Arrays.asList(Main.actions[1]).indexOf(resultList.get(finali))]);
						FileGenerator.main.frame.repaint();
					}
					else if(type.equals("enter")) {
						List<Integer> valueList = new ArrayList<Integer>();
						for (int integer : Main.levels.value) {
						    valueList.add(integer);
						}
						FileGeneratorGUI.labels.get(searchForID).setText(""+Main.levels.value[valueList.indexOf(Integer.parseInt(resultList.get(finali)))]);
						FileGenerator.main.frame.repaint();
					}
					frame.setVisible(false);
					frame.dispose();
				}
			});
			result.add(button,c);
			frame.repaint();
		}
	}
	
}
