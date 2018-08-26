package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import objects.Checkpoint;
import objects.CheckpointParserError;

public class Loader {
	
	boolean save = false;
	
	boolean createNew = false;
	
	public HashMap<String,String> getSettings(){
		HashMap<String,String> returns = new HashMap<String,String>();
		String OS=System.getProperty("os.name").toUpperCase();
		String appdataAll;
		if(OS.contains("WIN")){
			appdataAll=System.getenv("Appdata");
		}
		else if(OS.contains("MAC")){
			appdataAll=System.getProperty("user.home");
			appdataAll+="//Library//Application Support";
		}
		else if(OS.contains("LINUX")){
			appdataAll=System.getProperty("user.home");
			appdataAll+="/.config";
		}
		else {
			appdataAll = "";
			JOptionPane.showMessageDialog(null, "Diese App ist für dieses Betriebssystem (noch) nicht verfügbar!");
			System.exit(0);
		}
		Main.appdata = new File(appdataAll+"//SM64-Timer//Data.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(Main.appdata));
			String s;
			while((s = br.readLine())!=null) {
				String[] both = s.split("  ====  ");
				returns.put(both[0], both[1]);
			}
			br.close();
		}
		catch(FileNotFoundException e) {
			try {
				System.out.println(Files.createDirectories(new File(appdataAll+"//SM64-Timer").toPath()));
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try {
				FileWriter fw = new FileWriter(Main.appdata);
				fw.write("");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		catch(IOException e) {
			
		}
		return returns;
	}
	
	public void saveSettings(HashMap<String,String> settings) {
		try {
			FileWriter fw = new FileWriter(Main.appdata);
			for(Map.Entry<String, String> entry : Main.data.entrySet()) {
			    String key = entry.getKey();
			    String value = entry.getValue();
				System.out.println(key+","+value);

			    fw.write(key+"  ====  "+value+"\n");
			    fw.flush();
			}
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public File fileSelector() {
		if(Main.data.containsKey("file_always")) {
			return new File(Main.data.get("file_always"));
		}
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setMultiSelectionEnabled(false);
		
		JLabel label = new JLabel(Main.language.get("file_always"));
		label.setBorder(new EmptyBorder(2,0,0,0));
		
		JCheckBox checkBox = new JCheckBox();
		checkBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.SELECTED) {
					save=true;
				}
				else {
					save=false;
				}
			}
			
		});
		
		JButton button = new JButton(Main.language.get("new_file"));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createNew=true;
				jFileChooser.cancelSelection();
				FileGenerator.newFile(jFileChooser.getCurrentDirectory());
			}
		});
		
		((JPanel)((JPanel)jFileChooser.getComponent(3)).getComponent(3)).add(label, 0);
		((JPanel)((JPanel)jFileChooser.getComponent(3)).getComponent(3)).add(checkBox, 1);
		((JPanel)((JPanel)jFileChooser.getComponent(3)).getComponent(3)).add(button, 2);
		
		int result = jFileChooser.showOpenDialog(null);
		if(!(result==JFileChooser.APPROVE_OPTION)) {
			if(createNew) {
				return null;
			}
			else {
				System.exit(0);
			}
		}
		if(save == true) {
			Main.data.put("file_always", jFileChooser.getSelectedFile().getAbsolutePath());
			System.out.println("SAVE");
			saveSettings(Main.data);
		}
		return jFileChooser.getSelectedFile();
	}
	
	public HashMap<String, String> language() {
		HashMap<String, String> language = null;
		String[] files = new File("src/xml/lang").list();
		for(int i=0;i<files.length;i++) {
			if(files[i].split(".xml")[0].equals(System.getProperty("user.language"))) {
				language = Parser.parseLanguage("lang/"+files[i]);
			}
		}
		if(language==null) {
			language = Parser.parseLanguage("lang/en.xml");
		}
		return language;
	}
	
	public List<Checkpoint> checkpoints(File saved) throws CheckpointParserError{
		try {
			List<Checkpoint> checks = new ArrayList<Checkpoint>();
			BufferedReader br = new BufferedReader(new FileReader(saved));
			String read = "";
			int i=1;
			while((read=br.readLine())!=null) {
				try {
					Checkpoint actual = new Checkpoint(read);
					checks.add(actual);
					if(!read.equals(actual.getAll())) {
						Main.log.add(Main.getInLang("error_at"));
						Main.log.add(read);
						Main.log.add(Main.getInLang("converted_to"));
						Main.log.add(actual.getAll());
						br.close();
						return null;
					}
				}
				catch(CheckpointParserError e) {
					br.close();
					throw new CheckpointParserError(i,e.getDescription());
				}
				i++;
			}
			br.close();
			return checks;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
