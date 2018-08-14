package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;

import objects.Checkpoint;
import objects.CheckpointParserError;

public class Loader {
	
	public File fileSelector() {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setMultiSelectionEnabled(false);
		int result = jFileChooser.showOpenDialog(null);
		if(!(result==JFileChooser.APPROVE_OPTION)) {
			System.exit(0);
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
