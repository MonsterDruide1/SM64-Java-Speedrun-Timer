package objects;

public class CheckpointParserError extends Exception{
	
	private static final long serialVersionUID = 1L;
	private int line;
	private String description;
	
	public CheckpointParserError(int line,String description) {
		this.line = line;
		this.description = description;
	}
	
	public int getLine() {
		return line;
	}
	
	public String getDescription() {
		return description;
	}

}
