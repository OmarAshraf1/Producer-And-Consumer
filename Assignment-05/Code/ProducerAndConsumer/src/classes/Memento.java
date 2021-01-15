package classes;

import java.awt.Color;

public class Memento {
	private Color state;
	private int itemsNumber;
	public Memento(Color state, int itemsNumber) {
		this.state = state;
		this.itemsNumber = itemsNumber;
	}

	public Color getState() {
		return state;
	}
	public int getitemsNumber() {
		return itemsNumber;
	}
}
