package code;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import objects.Levels;

public class Parser {
	
	public static String[][] parse(String name, String tagName, String[] neededAttributes) {
		try {
			Document dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("src/xml/"+name);
			Element docEle = dom.getDocumentElement();
			NodeList nl = docEle.getElementsByTagName(tagName);
			if(nl != null && nl.getLength() > 0) {
				String[][] returns = new String[neededAttributes.length][nl.getLength()];
				for(int i = 0 ; i < nl.getLength();i++) {
					Element el = (Element)nl.item(i);
					for(int attributeNr=0; attributeNr<neededAttributes.length; attributeNr++){
						returns[attributeNr][i]=el.getAttribute(neededAttributes[attributeNr]);
					}
				}
				return returns;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[][] cutHex(String[][] array, int tagIndex) {
		for(int i=0;i<array[0].length; i++) {
			array[tagIndex][i]=cutHex(array[tagIndex][i]);
		}
		return array;
	}
	
	public static String cutHex(String string) {
		String returns = string.substring(2);
		returns = returns.replaceFirst("^0+(?!$)", "");
		return returns;
	}
	
	public static Levels toLevel(String[][] array) {
		String[] names = new String[array[0].length];
		int[] values = new int[array[0].length];
		for(int i=0; i<array[0].length; i++) {
			names[i]=array[0][i];
			values[i]=Integer.parseInt(array[1][i]);
		}
		return new Levels(names,values);
	}

}
