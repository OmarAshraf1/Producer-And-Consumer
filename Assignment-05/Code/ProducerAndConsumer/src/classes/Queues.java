package classes;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class Queues {
	private int x;
	private int y ;
	private int l ;
	private int w;
	private Color color;
	private int id;
	private String type= "Queue";
	private String uniqueID = UUID.randomUUID().toString();
	private Queue<Item> itemList = new LinkedList<>();
	private int activeReaders = 0;
	
	//Coordinates
	////////////////////////////////////////////
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	////////////////////////////////////////////
	
	//Length, Width and Color (Queue is a rectangle)
	////////////////////////////////////////////
	public int getL() {
		return l;
	}
	public void setL(int l) {
		this.l = l;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	////////////////////////////////////////////
	
	//ID and type
	////////////////////////////////////////////
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	////////////////////////////////////////////
	
	/*
	//Input Machines and Output Machines
	////////////////////////////////////////////
	public LinkedList<Machine> getMin() {
		return Min;
	}
	public void setMin(LinkedList<Machine> Min) {
		this.Min = Min;
	}
	public LinkedList<Machine> getMout() {
		return Mout;
	}
	public void setMout(LinkedList<Machine> Mout) {
		this.Mout = Mout;
	}
	////////////////////////////////////////////
	*/
	
	//toString method (used to determine whether the element is a machine or a queue)
	////////////////////////////////////////////
	@Override
	public String toString() {
		return "queue" ;
	}
	////////////////////////////////////////////
	
	//Unique ID (not seen by user)
	////////////////////////////////////////////
	public String getUniqueID() {
		return uniqueID;
	}
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	////////////////////////////////////////////
	
	//Handle Itemlist
	////////////////////////////////////////////
	public Queue getItemList() {
		return itemList;
	}
	public void setItemList(Queue itemList) {
		this.itemList = itemList;
	}
	public int itemListSize() {
		return itemList.size();
	}
	////////////////////////////////////////////
	
	//Handle beingRead boolean
	////////////////////////////////////////////
	public int getActiveReaders() {
		return activeReaders;
	}
	public void setActiveReaders(int activeReaders) {
		this.activeReaders = activeReaders;
	}
	////////////////////////////////////////////
	
}
