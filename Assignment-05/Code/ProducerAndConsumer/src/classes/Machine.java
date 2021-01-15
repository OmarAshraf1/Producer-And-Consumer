package classes;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;

import GUI.Draw;

public class Machine implements Runnable {
	private int x;
	private int y ;
	private int r ;
	private Color color ;
	private int id ;
	private String type = "Machine";
	private LinkedList<Queues> qin = new LinkedList<>();
	private LinkedList<Queues> qout = new LinkedList<>() ;
	private String uniqueID = UUID.randomUUID().toString();
	private int machineRunTime; // in ms
	private boolean inputReady;
	private Queues lastQueue;
	private int totalSimItems;
	
	public boolean isInputReady() {
		return inputReady;
	}
	public void setInputReady(boolean inputReady) {
		this.inputReady = inputReady;
	}
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
	
	//Radius and Color (Machine is a circle)
	////////////////////////////////////////////
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
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
	
	//Input Queues and Output Queues
	////////////////////////////////////////////
	public LinkedList<Queues> getQin() {
		return qin;
	}
	public void setQin(LinkedList<Queues> qin) {
		this.qin = qin;
	}
	public LinkedList<Queues> getQout() {
		return qout;
	}
	public void setQout(LinkedList<Queues> qout) {
		this.qout = qout;
	}
	////////////////////////////////////////////
	
	//toString method (used to determine whether the element is a machine or a queue)
	////////////////////////////////////////////
	@Override
	public String toString() {
		return "machine" ;
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
	
	//Remove queue from Qin and Qout by unique ID
	////////////////////////////////////////////
	public void removeQinElement(String uniqueID) {
		for (int i = 0; i < qin.size() ; i++) {
			if (qin.get(i).getUniqueID().equals(uniqueID)){
				qin.remove(i);
				break;
			}
		}
		
	}
	public void removeQoutElement(String uniqueID) {
		for (int i = 0; i < qout.size() ; i++) {
			if (qout.get(i).getUniqueID().equals(uniqueID)){
				qout.remove(i);
				break;
			}
		}
		
	}
	////////////////////////////////////////////
	
	//Search if queue is already in Qin or Qout by unique ID
	////////////////////////////////////////////
	public boolean isQinElement(String uniqueID) {
		for (int i = 0; i < qin.size() ; i++) {
			if (qin.get(i).getUniqueID().equals(uniqueID)){
				return true;
			}
		}
		return false;
			
	}
	public boolean isQoutElement(String uniqueID) {
		for (int i = 0; i < qout.size() ; i++) {
			if (qout.get(i).getUniqueID().equals(uniqueID)){
				return true;
			}
		}
		return false;
			
	}
	////////////////////////////////////////////
	
	//Handle machine runtime
	////////////////////////////////////////////
	public int getRuntime() {
		return machineRunTime;
	}
	public void setRuntime(int machineRunTime) {
		this.machineRunTime = machineRunTime;
	}
	public int getRandRuntime() {
		Random rand = new Random();
		int runtime;
		
		do {
			runtime = rand.nextInt(10); //max 10 seconds
		}while(runtime<4);
		
		return runtime*1000; // In ms
	}
	
	////////////////////////////////////////////	
	
	//Handle run
	////////////////////////////////////////////
	public Thread startSimulation()  
	  {  

		Thread thread = new Thread(this);  
	    //thread.start(); 
	    return thread;
	  }
	public void setSimEndCondition(Queues lastQueue, int totalSimItems) {
		this.lastQueue = lastQueue;
		this.totalSimItems = totalSimItems;
	}
	public boolean checkSimEnd() {
		if (lastQueue.getItemList().size() == totalSimItems) {
			return true;
		}else {
			return false;
		}
		
	}
	@SuppressWarnings("unchecked")
	synchronized Item consumeItem() throws InterruptedException {
		while(! inputReady) {
			return null;
		}
		Random random = new Random();
		int selectedQueueID;
		Queues currentQueue;
		Queue<Item> items;
		int activeReaders;
		boolean beingRead = true;
		Item returnedItem = null;
		do {
			selectedQueueID = random.nextInt(qin.size());
			currentQueue = qin.get(selectedQueueID);
			items = currentQueue.getItemList();
			
		}while(items.isEmpty());
		//One second delay before it picks up the next item
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		while (beingRead) {
			synchronized(items) {
				beingRead = false;
				activeReaders = currentQueue.getActiveReaders();
				if (activeReaders > 0) {
						beingRead = true;
				}
				else {
						activeReaders++;
						currentQueue.setActiveReaders(activeReaders);
						if (!items.isEmpty()) {
							returnedItem = items.remove();
						}
						
						activeReaders--;
						currentQueue.setActiveReaders(activeReaders);
						
				}
			}
		}
		
		
		return returnedItem;
		
	}
	 void checkQueues() {
		inputReady = false;
		for (int i = 0 ; i < qin.size() ; i++) {
			if (!qin.get(i).getItemList().isEmpty()) {
				
				InputReadyObserver obs = new InputReadyObserver(this);
				obs.update();
				break;
				//return true;
			}
		}
		//return false;
		/*
		if(inputReady == false) {
			Thread.currentThread().interrupt();
		}
		*/
		//notifyAll();
	}
	@SuppressWarnings("unchecked")
	synchronized void produceItem(Item processedItem) {
		Random random = new Random();
		int selectedQueueID;
		Queues currentQueue;
		Queue<Item> items;
		selectedQueueID = random.nextInt(qout.size());
		currentQueue = qout.get(selectedQueueID);
		//One second delay before it picks up the next item
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
		synchronized(currentQueue) {
			items = currentQueue.getItemList();
			items.add(processedItem);
			currentQueue.setItemList(items);
		}
		
	}
	
	@Override
	public void run() {
		for(;;) {
			checkQueues();
			Item currentItem;
			try {
				currentItem = consumeItem();
			} catch (InterruptedException e) {
				currentItem = null;
				Thread.currentThread().interrupt();
			}
			if (currentItem != null) {
				this.setColor(currentItem.getColor());
				try {
					Thread.sleep(this.getRuntime());
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					//break;
				}
				produceItem(currentItem);
				this.setColor(ColorManager.defaultMachineColor());
				//One second delay before it picks up the next item
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			if(checkSimEnd()) {
				Thread.currentThread().interrupt();
				Draw x = new Draw();
				x.btnAddMachine.setEnabled(true);
				x.btnAddQueue.setEnabled(true);
				x.btnConnect.setEnabled(true);
				x.btnDeleteElement.setEnabled(true);
				x.btnNewSimulation.setEnabled(true);
				x.btnPlay.setEnabled(true);
				x.btnReplay.setEnabled(true);
				break;
			}
		}
		//System.out.println("Machine " + this.getId() + " has finished");
		
		//System.out.println(Thread.currentThread().isAlive());
		/*
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		*/
		
		
		/*
		for(;;) {
			synchronized (queue) {
				while (queue.isEmpty()) {
					queue.wait();
				}
				Object object = queue.dequeue();
			}
			//Consume object
		 */
			 
		}
	////////////////////////////////////////////
}
	
	
	

