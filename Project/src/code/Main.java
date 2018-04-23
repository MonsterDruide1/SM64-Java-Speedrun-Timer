package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import objects.Checkpoint;
import objects.Levels;

public class Main {
	
	static File saved = new File("D://Eigene_Dateien//Downloads//speedrun.txt");
	public static String[][] actions;
	public static Levels levels;
	public static boolean german = System.getProperty("user.language").equals("de");
	public static HashMap<String, String> language;
	
	public static void main(String[] args) {
		try {
			String[] files = new File("src/xml/lang").list();
			for(int i=0;i<files.length;i++) {
				if(files[i].split(".xml")[0].equals(System.getProperty("user.language"))) {
					language = Parser.parseLanguage("lang/"+files[i]);
				}
			}
			if(language==null) {
				language = Parser.parseLanguage("lang/en.xml");
			}
			printInLang("read_data");
			String[][] actionsWithHex = Parser.parseAttributes("MarioActions.xml", "Action", new String[]{"name","value"});
			actions = Parser.cutHex(actionsWithHex, 1);
			String[][] levelsParsed = Parser.parseAttributes("Levels.xml", "Level", new String[]{"name","value"});
			levels = Parser.toLevel(levelsParsed);
			List<Checkpoint> checks = new ArrayList<Checkpoint>();
			printInLang("read_checkpoints");
			BufferedReader br = new BufferedReader(new FileReader(saved));
			String read = "";
			while((read=br.readLine())!=null) {
				Checkpoint actual = new Checkpoint(read);
				checks.add(actual);
				if(!read.equals(actual.getAll())) {
					printInLang("error_at");
					System.out.println(read);
					printInLang("converted_to");
					System.out.println(actual.getAll());
					System.exit(0);
				}
			}
			br.close();
			printInLang("starting_run");
			Run.run(checks);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printInLang(String key) {
		System.out.println(language.get(key));
	}

}
