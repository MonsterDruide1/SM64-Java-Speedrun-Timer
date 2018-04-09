package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import objects.Checkpoint;
import objects.Levels;

public class Main {
	
	static File saved = new File("D://Eigene_Dateien//Downloads//speedrun.txt");
	public static String[][] actions;
	public static Levels levels;
	
	public static void main(String[] args) {
		try {
			String[][] actionsWithHex = Parser.parse("MarioActions.xml", "Action", new String[]{"name","value"});
			actions = Parser.cutHex(actionsWithHex, 1);
			String[][] levelsParsed = Parser.parse("Levels.xml", "Level", new String[]{"name","value"});
			levels = Parser.toLevel(levelsParsed);
			List<Checkpoint> checks = new ArrayList<Checkpoint>();
			BufferedReader br = new BufferedReader(new FileReader(saved));
			String read = "";
			while((read=br.readLine())!=null) {
				Checkpoint actual = new Checkpoint(read);
				checks.add(actual);
				if(!read.equals(actual.getAll())) {
					System.out.println("Fehler bei ");
					System.out.println(read);
					System.out.println("Wurde zu ");
					System.out.println(actual.getAll());
					System.exit(0);
				}
			}
			br.close();
			System.out.println("Starte Run");
			Run.run(checks);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
