package classes;

import java.util.ArrayList;

public class CareTaker {
	 public static ArrayList<Memento> mementoList = new ArrayList<Memento>();

	public Memento get(int index) {
		return mementoList.get(index);
	}

	public void add(Memento state) {
		mementoList.add(state);
	}
	public void clear() {
		mementoList.clear();
	}
	 

}
