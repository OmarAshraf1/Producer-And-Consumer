package classes;

import java.awt.Color;

public class Originator {
	private Color state;
	private int itemsNumber;


	public Color getState() {
		return state;
	}

	public void setState(Color state) {
		this.state = state;
	}
	public int getitemsNumber() {
		return itemsNumber;
	}

	public void setitemsNumber(int itemsNumber) {
		this.itemsNumber = itemsNumber;
	}
	public Memento saveStateToMemento() {
		return new Memento(state, itemsNumber);
	}
	
	public Object[] getStateFromMemento(Memento memento) {
		state = memento.getState();
		itemsNumber = memento.getitemsNumber();
		Object[] arr = {state,itemsNumber};
		return arr;
	}
}
