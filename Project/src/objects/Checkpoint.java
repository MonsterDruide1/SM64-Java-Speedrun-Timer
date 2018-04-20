package objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import code.Main;

public class Checkpoint {
	
	public String[] name;
	public String[] type;
	public String[] hexCheckpoint;
	public String[] nameOfHexCheckpoint;
	public int done = 0;
	
	public Checkpoint(String all) {
		String[] alternatives = all.split(" <--><--> ");
		name=new String[alternatives.length];
		type=new String[alternatives.length];
		hexCheckpoint=new String[alternatives.length];
		nameOfHexCheckpoint = new String[alternatives.length];
		int i=0;
		for (String alternative : alternatives) {
			String[] allArray = alternative.split("\\|\\|"); //escapes | -> ||=funktioniert nicht, muss \\|\\|
			name[i] = allArray[0];
			type[i] = allArray[1];
			hexCheckpoint[i] = allArray[2];
			if(type[i].equals("action")) {
				nameOfHexCheckpoint[i]=Main.actions[0][Arrays.asList(Main.actions[1]).indexOf(hexCheckpoint[i])];
			}
			else if(type[i].equals("enter")) {
				List<Integer> valueList = new ArrayList<Integer>();
				for (int integer : Main.levels.value) {
				    valueList.add(integer);
				}
				System.out.println(Integer.parseInt(hexCheckpoint[i]));
				nameOfHexCheckpoint[i]=Main.levels.name[valueList.indexOf(Integer.parseInt(hexCheckpoint[i]))];
			}
			i++;
		}
	}
	
	public Checkpoint(String[] name, String[]type, String[] hexCheckpoint) {
		this.name = name;
		this.type = type;
		this.hexCheckpoint = hexCheckpoint;
	}
	
	public String getAll() {
		String returns = "";
		for (int i=0; i<name.length; i++) {
			returns+=this.name[i]+"||"+this.type[i]+"||"+this.hexCheckpoint[i];
			if (name.length>1 && i!=(name.length-1)) {
				returns+=" <--><--> ";
			}
		}
		return returns;
	}
	
	public String getAllHR() {
		String returns = "";
		for (int i=0; i<name.length; i++) {
			returns+="Name: "+this.name[i]+", Typ: "+this.type[i]+", Bedingung: "+this.nameOfHexCheckpoint[i]+"\n";
			if (name.length>1 && i!=(name.length-1)) {
				returns+="Alternative: \n";
			}
		}
		return returns;
	}

}
