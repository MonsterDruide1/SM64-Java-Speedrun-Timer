package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;

import objects.Checkpoint;
import objects.Levels;

public class Main {
	
	static File saved;
	public static String[][] actions;
	public static Levels levels;
	public static HashMap<String, String> language;
	public static List<String> log;
	public static boolean allChecksReady = false;
	
	public static void main(String[] args) {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setMultiSelectionEnabled(false);
		int result = jFileChooser.showOpenDialog(null);
		if(!(result==JFileChooser.APPROVE_OPTION)) {
			System.exit(0);
		}
		saved=jFileChooser.getSelectedFile();
		try {
			allChecksReady=false;
			log = new ArrayList<String>();
			new Thread(new JFrameRunnable()).start();
			String[] files = new File("src/xml/lang").list();
			for(int i=0;i<files.length;i++) {
				if(files[i].split(".xml")[0].equals(System.getProperty("user.language"))) {
					language = Parser.parseLanguage("lang/"+files[i]);
				}
			}
			if(language==null) {
				language = Parser.parseLanguage("lang/en.xml");
			}
			log.add(getInLang("read_data"));
			String[][] actionsWithHex = Parser.parseAttributes("MarioActions.xml", "Action", new String[]{"name","value"});
			actions = Parser.cutHex(actionsWithHex, 1);
			String[][] levelsParsed = Parser.parseAttributes("Levels.xml", "Level", new String[]{"name","value"});
			levels = Parser.toLevel(levelsParsed);
			List<Checkpoint> checks = new ArrayList<Checkpoint>();
			log.add(getInLang("read_checkpoints"));
			BufferedReader br = new BufferedReader(new FileReader(saved));
			String read = "";
			while((read=br.readLine())!=null) {
				Checkpoint actual = new Checkpoint(read);
				checks.add(actual);
				if(!read.equals(actual.getAll())) {
					log.add(getInLang("error_at"));
					log.add(read);
					log.add(getInLang("converted_to"));
					log.add(actual.getAll());
					br.close();
					return;
				}
			}
			br.close();
			log.add(getInLang("starting_run"));
			Run.run(checks);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getInLang(String key) {
		return language.get(key);
	}

}
