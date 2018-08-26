package code;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class FileGenerator {
	
	static FileGeneratorGUI main;
	static FileGeneratorGUI hexSearch;
	static File file;
	
	public static void newFile(File path) {
		String[][] actionsWithHex = Parser.parseAttributes("MarioActions.xml", "Action", new String[]{"name","value"});
		Main.actions = Parser.cutHex(actionsWithHex, 1);
		String[][] levelsParsed = Parser.parseAttributes("Levels.xml", "Level", new String[]{"name","value"});
		Main.levels = Parser.toLevel(levelsParsed);
		String filename = JOptionPane.showInputDialog(Main.getInLang("new_file_name"));
		file = new File(path.getAbsolutePath()+"//"+filename);
		
		main = new FileGeneratorGUI(1);
		
		new Thread(main).start();
	}
	
	public static void selectHex(String type) {
		hexSearch = new FileGeneratorGUI(2,type);
		new Thread(hexSearch).start();
	}

	public static void updateSearchUI(String text,String type) {
		List<String> result = new ArrayList<String>();
		List<String> names = new ArrayList<String>();
		switch(type) {
			case "action":
				String[] actionHex = Main.actions[1];
				for(int i=0;i<actionHex.length;i++) {
					String action = actionHex[i];
					if(action.contains(text)){
						result.add(action);
						names.add(Main.actions[0][i]);
					}
				}
				String[] namesActionHex = Main.actions[0];
				for(int i=0;i<namesActionHex.length;i++) {
					String name = namesActionHex[i].toUpperCase();
					if(name.contains(text)){
						result.add(Main.actions[1][i]+"");
						names.add(namesActionHex[i]);
					}
				}
				break;
			case "enter":
				int[] levelHex = Main.levels.value;
				for(int i=0;i<levelHex.length;i++) {
					int level = levelHex[i];
					if((level+"").contains(text)){
						result.add(level+"");
						names.add(Main.levels.name[i]);
					}
				}
				String[] namesHex = Main.levels.name;
				for(int i=0;i<namesHex.length;i++) {
					String name = namesHex[i].toUpperCase();
					if(name.contains(text)){
						result.add(Main.levels.value[i]+"");
						names.add(namesHex[i]);
					}
				}
				break;
		}
		hexSearch.updateSearchUI(result,names);
				
	}

	public static void save(List<JTextArea> textAreas, List<JComboBox<String>> comboboxen, List<JLabel> labels) {
		try {
			FileWriter fw = new FileWriter(file);
			for(int i=0;i<textAreas.size();i++) {
				fw.write(textAreas.get(i).getText()+"||"+FileGeneratorGUI.types[comboboxen.get(i).getSelectedIndex()]+"||"+labels.get(i).getText()+"\n");
				fw.flush();
			}
			fw.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
