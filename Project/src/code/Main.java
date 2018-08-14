package code;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import objects.Checkpoint;
import objects.CheckpointParserError;
import objects.Levels;

public class Main {
	
	static File saved;
	public static String[][] actions;
	public static Levels levels;
	public static HashMap<String, String> language;
	public static List<String> log;
	public static boolean allChecksReady = false;
	
	public static void main(String[] args) {
		Loader loader = new Loader();
		saved = loader.fileSelector();
		try {
			allChecksReady=false;
			log = new ArrayList<String>();
			new Thread(new JFrameRunnable()).start();
			language = loader.language();
			log.add(getInLang("read_data"));
			String[][] actionsWithHex = Parser.parseAttributes("MarioActions.xml", "Action", new String[]{"name","value"});
			actions = Parser.cutHex(actionsWithHex, 1);
			String[][] levelsParsed = Parser.parseAttributes("Levels.xml", "Level", new String[]{"name","value"});
			levels = Parser.toLevel(levelsParsed);
			log.add(getInLang("read_checkpoints"));
			List<Checkpoint> checks = null;
			try {
				checks = loader.checkpoints(saved);
			}
			catch(CheckpointParserError e) {
				log.add(language.get("error_head"+" "+e.getLine()+" "+e.getDescription()));
				checks=null;
			}
			if(checks==null) {
				return;
			}
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
