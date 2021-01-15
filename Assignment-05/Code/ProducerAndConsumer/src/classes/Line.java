package classes;

public class Line {
	private int x0;
	private int y0;
	private int x1;
	private int y1;
	private String inputID;
	private String outputID;
	
	//First Point Coordinates
	////////////////////////////////////////////
	public int getX0() {
		return x0;
	}
	public void setX0(int x0) {
		this.x0 = x0;
	}
	public int getY0() {
		return y0;
	}
	public void setY0(int y0) {
		this.y0 = y0;
	}
	////////////////////////////////////////////
	
	//Second Point Coordinates
	////////////////////////////////////////////
	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	public int getY1() {
		return y1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
	}
	////////////////////////////////////////////
	
	//Constructor
	////////////////////////////////////////////
	public Line(int x0, int y0, int x1, int y1) {
		
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}
	////////////////////////////////////////////
	

	
	//Handle input and output ID's
	////////////////////////////////////////////
	public String getInputID() {
		return inputID;
	}
	public void setInputID(String inputID) {
		this.inputID = inputID;
	}
	public String getOutputID() {
		return outputID;
	}
	public void setOutputID(String outputID) {
		this.outputID = outputID;
	}
	////////////////////////////////////////////
	
	//toString method (used to determine whether the element is a machine or a queue)
	////////////////////////////////////////////
	@Override
	public String toString() {
		return "line" ;
	}
	////////////////////////////////////////////
}
