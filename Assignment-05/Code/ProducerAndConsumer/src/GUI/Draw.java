package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import classes.CareTaker;
import classes.ColorManager;
import classes.Item;
import classes.Line;
import classes.Machine;
import classes.Originator;
import classes.Point;
import classes.Queues;
 
public class Draw {
	
 public static ArrayList<Object> shapes = new ArrayList<Object>();
 private static ArrayList<Point> points = new ArrayList<>();
 private static ArrayList<Line> lines = new ArrayList<>();
 private static ArrayList<Thread> threads = new ArrayList<>();
 private static boolean btnCheck = false;
 private static int x,y,mid=0,qid=0,itemID=0,itemsNumber=5;
 
 
 public static Originator originator = new Originator();
 public static CareTaker careTaker = new CareTaker();
 public static JButton btnAddMachine = new JButton("Add machine");
 public static JButton btnAddQueue = new JButton("Add queue");
 public static JButton btnConnect = new JButton("Connect Qs and Ms");
 public static JButton btnDeleteElement = new JButton("Delete Element");
 public static JButton btnNewSimulation = new JButton("New Simulation");
 public static JButton btnPlay = new JButton("Play");
 public static JButton btnReplay = new JButton("Replay");
 
 public static void main(String[] args) {
 
// Create a frame
 
JFrame frame = new JFrame("Production Line Simulation Program");
 
// Add a component with a custom paint method

JPanel panel = new JPanel(new BorderLayout());
JPanel panel1 = new JPanel();

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//buttons
panel.setBackground(Color.white);
panel1.setBackground(Color.DARK_GRAY);

frame.setBackground(Color.white);
frame.setResizable(false);


btnAddQueue.setBounds(0, 670, 100, 31);
btnAddMachine.setBounds(100, 670, 150, 31);
btnConnect.setBounds(250, 670, 150, 31);
btnDeleteElement.setBounds(400, 670, 150, 31);
btnNewSimulation.setBounds(550, 670, 150, 31);
btnPlay.setBounds(700, 670, 150, 31);
btnReplay.setBounds(850, 670, 150, 31);

//btnNewButton_1.setBackground(Color.RED);
btnAddMachine.setForeground(Color.DARK_GRAY);
btnAddQueue.setForeground(Color.DARK_GRAY);
btnConnect.setForeground(Color.DARK_GRAY);
btnDeleteElement.setForeground(Color.red);
btnNewSimulation.setForeground(Color.BLUE);
btnPlay.setForeground(Color.black);
btnReplay.setForeground(Color.green);
//btnNewButton.setBackground(Color.RED);

// NewSimulation
btnNewSimulation.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		shapes.clear();
		lines.clear();
		mid=0;qid=0;itemID=0;
		careTaker.clear();
	}
	});


btnReplay.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		//Clear All Queues and threads
		btnCheck = true;
		btnPlay.doClick();
		btnCheck = false;
	}
	}
);
	

//Play Simulation
btnPlay.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		//Clear All Queues and threads
		btnAddMachine.setEnabled(false);
		btnAddQueue.setEnabled(false);
		btnConnect.setEnabled(false);
		btnDeleteElement.setEnabled(false);
		btnNewSimulation.setEnabled(false);
		btnPlay.setEnabled(false);
		btnReplay.setEnabled(false);
		for (int i = 0; i < shapes.size(); i++) {
			Object obj1 = shapes.get(i);
			if(obj1.toString().equals("queue")){
				Queues q = (Queues)(obj1);
				Queue<Item> emptyList = new LinkedList<>();
				q.setItemList(emptyList);
			}
		}
		
		threads.clear();
		
		
		//Generate Items
		Random random = new Random();
		// 5 to 10 items
		itemsNumber = random.nextInt(6);
		itemsNumber = itemsNumber + 5;
		Object [] arr = new Object [2];
		Queue<Item> itemList = new LinkedList<>();
		
		if (btnCheck) {
			itemsNumber = (int) originator.getStateFromMemento(careTaker.get(0))[1];
		}else {
			careTaker.clear();
		}
		
		for (int i = 0 ; i < itemsNumber ; i++) {
			Item nextItem = new Item();
			

			
			if (btnCheck) {
				arr = originator.getStateFromMemento(careTaker.get(i));
				nextItem.setColor((Color)(arr[0]));
				
				//nextItem.setColor(SavedColors.get(i));
			}
			else {
				
				Color x = ColorManager.generateNewItemColor();
				nextItem.setColor(x);
				
				originator.setState(x);
				originator.setitemsNumber(itemsNumber);
				careTaker.add(originator.saveStateToMemento());
			
				
				//Color x = ColorManager.generateNewItemColor();
				//nextItem.setColor(x);
				//SavedColors.add(x);
			}

			nextItem.setId(itemID++);
			itemList.add(nextItem);
		}
		//Add to Q0 (oldest queue)
		for (int i = 0; i < shapes.size(); i++) {
			Object obj1 = shapes.get(i);
			   if(obj1.toString().equals("queue")){
				   Queues q = (Queues)(obj1);
				   q.setItemList(itemList);
				   break;
			   }
		}
		
		
		//Run
		// Find output queue (latest queue)
		Queues lastQueue = null;
		//Check End
		for (int i = shapes.size()-1; i > -1; i--) {
			Object obj1 = shapes.get(i);
			if(obj1.toString().equals("queue")){
				lastQueue = (Queues)(obj1);
				break;
						   
			}
		}

		for (int i = 0; i < shapes.size(); i++) {
			Object obj1 = shapes.get(i);
			   if(obj1.toString().equals("machine")){
				   Machine m = (Machine)(obj1);
				   m.setSimEndCondition(lastQueue, itemsNumber);
				   threads.add(m.startSimulation());
				   
			   }
		}
		
		for (int i = 0; i < threads.size() ; i++) {
			threads.get(i).start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
			}
		
		}
		
		/*
		for (int i = 0; i < threads.size() ; i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		*/

		/*
	
		if(lastQueue != null) {
			while (lastQueue.getItemList().size() <5) {
				System.out.println("Size" + lastQueue.getItemList().size() );
			}
		}
		while (lastQueue.getItemList().size() < itemsNumber) {
			for (int i = 0; i < shapes.size(); i++) {
				Object obj1 = shapes.get(i);
				   if(obj1.toString().equals("machine")){
					   Machine m = (Machine)(obj1);
					   m.startSimulation();
					   
				   }
			}
		}
		*/

		

		
	}
});
// Add Machine
btnAddMachine.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		Machine m = new Machine();
		m.setColor(ColorManager.defaultMachineColor());
		m.setId(mid++);
		m.setRuntime(m.getRandRuntime());
		m.setR(70);
		m.setX(x);
		m.setY(y);
		shapes.add(m);
		//System.out.println("ma");
	}
});
// Add Queue
btnAddQueue.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		Queues q = new Queues();
		q.setColor(ColorManager.defaultQueueColor());
		q.setId(qid++);
		q.setL(50);
		q.setW(90);
		q.setX(x);
		q.setY(y);
		shapes.add(q);
		//System.out.println("qq");
	}
});
// Connect Machine and Queue
btnConnect.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		
		//Selected second
		Point p2 = points.get(points.size()-1);
		//Selected first
		Point p1 = points.get(points.size()-2);
		Object obj1=null;
		Object obj2=null;
		int i=0,j=0;
		//Find first object and its index
		for( i=0;i<shapes.size();i++){ 
	        if (isMouseInShape(p1.getX(),p1.getY(),shapes.get(i))) {
	          obj1 = shapes.get(i);
	          break;
	         // this.drawAll();
	        }
	     }
		//Find second object and its index
	     for( j=0;j<shapes.size();j++){ 
	        if (isMouseInShape(p2.getX(),p2.getY(),shapes.get(j) )) {
	          obj2 = shapes.get(j) ;
	          break;
	         // this.drawAll();
	        }
	     }
	     //If machine is clicked first, then the queue is an output queue
	     if(obj1.toString().equals("machine") && obj2.toString().equals("queue") ){
	         Machine m = (Machine) obj1 ;
	         Queues q = (Queues) obj2 ;
	         
	         //If the q is already in the Qin or Qout of the machine, return (A machine and queue cannot have more than one connection).
	         if (m.isQinElement(q.getUniqueID()) || m.isQoutElement(q.getUniqueID())){
	        	 return;
	         }
	         
	         //Queue q receives output of Machine m
	         m.getQout().add(q);
	         
	         /*
	         //Machine m is an input source for Queue q
	         q.getMin().add(m);
	         */
	         
	         //Machine m is currently a local variable so after adding q to the Qout list, the machine must be removed then added again (with its Qout updated)
	         shapes.remove(i);
	         shapes.add(i, m);
	         
	         /*
	         //Queue q is currently a local variable so after adding m to the Min list, the queue must be removed then added again (with its Min updated)
	         shapes.remove(j);
	         shapes.add(j, q);
	         */
	         
	         //Add connecting line between queue and machine
	         Line l1 = new Line(m.getX(), m.getY(), q.getX(),q.getY());
	         l1.setInputID(m.getUniqueID());
	         l1.setOutputID(q.getUniqueID());
	         lines.add(l1);
	         
	       }
	     
	       //Else, if queue is clicked first, then the queue is an input queue
	       else if(obj1.toString().equals("queue") && obj2.toString().equals("machine") ){
	    	   Machine m = (Machine) obj2 ;
		       Queues q = (Queues) obj1 ;
		       
		       //If the q is already in the Qin or Qout of the machine, return (A machine and queue cannot have more than one connection).
		       if (m.isQinElement(q.getUniqueID()) || m.isQoutElement(q.getUniqueID())){
		        	return;
		       }
		       
		       //Queue q is an input source for Machine m
		       m.getQin().add(q);
		       
		       /*
		       //Machine m receives output of Queue q
		       q.getMout().add(m);
		       */
		       
		       //Machine m is currently a local variable so after adding q to the Qin list, the machine must be removed then added again (with its Qin updated)
		       shapes.remove(j);
		       shapes.add(j, m);
		       
		       /*
		       //Queue q is currently a local variable so after adding m to the Mout list, the queue must be removed then added again (with its Mout updated)
		       shapes.remove(i);
		       shapes.add(i, q);
		       */
		       
		       //Add connecting line between queue and machine
		       Line l2 = new Line(q.getX(), q.getY(), m.getX(),m.getY());
		       l2.setInputID(q.getUniqueID());
		       l2.setOutputID(m.getUniqueID());
		       lines.add(l2);

	       }
		
		
	}
});
// Delete Element
btnDeleteElement.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		
		Point p1 = points.get(points.size()-1);
		Object obj1=null;

		//Start loops from end of list so that if two shapes overlap, the latest shape to be added is removed
		int i=0;
		for( i=shapes.size() - 1;i>-1;i--){ 
	        if (isMouseInShape(p1.getX(),p1.getY(),shapes.get(i))) {
	          obj1 = shapes.get(i);
	          break;
	        }
	     }
		if (obj1 == null) {
			for( i=lines.size() - 1;i>-1;i--){ 
		        if (isMouseInShape(p1.getX(),p1.getY(),lines.get(i))) {
		          obj1 = lines.get(i);
		          break;
		        }
		     }
		}
		//No object selected so end method execution
		if (obj1 == null) {
			return;
		}
	     
		 //If selected element is a machine, delete it along with any lines connected to it.
	     if(obj1.toString().equals("machine")){
	         Machine m = (Machine) obj1 ;
	         
	         shapes.remove(i);
	         //Remove lines between machine and all input queues
	         for (int qCounter = 0; qCounter < m.getQin().size() ; qCounter++) {
	        	 Queues currentQueue = (Queues) m.getQin().get(qCounter) ;
	        	 //Loop in lines list
	        	 for (int lineCounter = 0 ; lineCounter < lines.size() ; lineCounter++) {
	        		 //If inputID of line is the same as the unique ID of the current queue in the loop then remove that line
	        		 if (lines.get(lineCounter).getInputID().equals(currentQueue.getUniqueID())) {
	        			 lines.remove(lineCounter);
	        			 break;
	        		 }
	        	 }
	         }
	       //Remove lines between machine and all output queues
	         for (int qCounter = 0; qCounter < m.getQout().size() ; qCounter++) {
	        	 Queues currentQueue = (Queues) m.getQout().get(qCounter) ;
	        	 //Loop in lines list
	        	 for (int lineCounter = 0 ; lineCounter < lines.size() ; lineCounter++) {
	        		 //If outputID of line is the same as the unique ID of the current queue in the loop then remove that line
	        		 if (lines.get(lineCounter).getOutputID().equals(currentQueue.getUniqueID())) {
	        			 lines.remove(lineCounter);
	        			 break;
	        		 }
	        	 }
	         }
	         //Clear Qin and Qout
	         m.getQin().clear();
	         m.getQout().clear();
	         
	       }
	       //Else, if selected element is a queue, go through all connected machines and removed it from their queues (Qin or Qout) then delete it 
	       //along with any lines connected to it.
	       else if(obj1.toString().equals("queue") ){
		       Queues q = (Queues) obj1 ;
		       
		       shapes.remove(i);
		       
		       //Loop in lines list
	        	 for (int lineCounter = 0 ; lineCounter < lines.size() ; lineCounter++) {
	        		 //If inputID of line is the same as the unique ID of the current queue in the loop then remove that line and remove it from
	        		 //the Qin list of the machine which has ID that is identical to the outputID of the line
	        		 if (lines.get(lineCounter).getInputID().equals(q.getUniqueID())) {
	        			 //Loop in shapes to find machine object which is the output of the current queue
	        			 for (int mCounter = 0; mCounter < shapes.size() ; mCounter++) {
	        				 //If the current shape object is a machine, create machine m to reference it easier
	        				 if(shapes.get(mCounter).toString().equals("machine")) {
	        					 Machine m =  (Machine)(shapes.get(mCounter)) ;
	        					 //If the current machine's Unique ID is the same as the output ID of the current line then remove the current queue
	        					 //from that machine's Qin List
	        					 if (lines.get(lineCounter).getOutputID().equals( m.getUniqueID() ) ) {
		        					m.removeQinElement(q.getUniqueID());
		        					//Add the machine again after updating its Qin
		        					shapes.remove(mCounter);
		        					shapes.add(mCounter, m);
		        					break;
		        				 }
	        				 }
	        				 
	        			 }
	        			 lines.remove(lineCounter);

	        		 }
	        	 }
		       
		       //Loop in lines list
	        	 for (int lineCounter = 0 ; lineCounter < lines.size() ; lineCounter++) {
	        		 //If outputID of line is the same as the unique ID of the current queue in the loop then remove that line and remove it from
	        		 //the Qout list of the machine which has ID that is identical to the inputID of the line
	        		 if (lines.get(lineCounter).getOutputID().equals(q.getUniqueID())) {
	        			//Loop in shapes to find machine object which is the input of the current queue
	        			 for (int mCounter = 0; mCounter < shapes.size() ; mCounter++) {
	        				 //If the current shape object is a machine, create machine m to reference it easier
	        				 if(shapes.get(mCounter).toString().equals("machine")) {
	        					 Machine m =  (Machine)(shapes.get(mCounter)) ;
	        					 //If the current machine's Unique ID is the same as the input ID of the current line then remove the current queue
	        					 //from that machine's Qout List
	        					 if (lines.get(lineCounter).getInputID().equals( m.getUniqueID() ) ) {
		        					m.removeQoutElement(q.getUniqueID());
		        					//Add the machine again after updating its Qout
		        					shapes.remove(mCounter);
		        					shapes.add(mCounter, m);
		        					break;
		        				 }
	        				 }
	        				 
	        			 }
	        			 lines.remove(lineCounter);
	        		 }
	        	 }


	       }
	     
	      //Else, if selected element is a line, delete the line and remove the connection associated with it (if queue is an input for the machine, then
	      //remove it from Qin, otherwise remove it from Qout)
	      else if(obj1.toString().equals("line") ){
	    	  Line l = (Line) obj1 ;
		       
		      lines.remove(i);
		       
		     //If the machine is the line input, then remove the queue from the machine's Qout.
		     //Loop in shapes to find machine object.
  			 for (int mCounter = 0; mCounter < shapes.size() ; mCounter++) {
  				//If the current shape object is a machine, create machine m to reference it easier
  				if(shapes.get(mCounter).toString().equals("machine")) {
  					Machine m =  (Machine)(shapes.get(mCounter)) ;
					//If the current machine's Unique ID is the same as the input ID of the current line then remove the queue
					//from that machine's Qout List
					if (l.getInputID().equals( m.getUniqueID() ) ) {
						m.removeQoutElement(l.getOutputID());
						//Add the machine again after updating its Qout
						shapes.remove(mCounter);
						shapes.add(mCounter, m);
						break;
					}
					//Else, if the current machine's Unique ID is the same as the output ID of the current line then remove the queue
					//from that machine's Qin List
					else if (l.getOutputID().equals( m.getUniqueID() ) ) {
						m.removeQinElement(l.getInputID());
						//Add the machine again after updating its Qin
						shapes.remove(mCounter);
						shapes.add(mCounter, m);
						break;
					}
  			 }
   
	       }
	     }
	     
	     /*
	     //Debugging check
	     ////////////////////////////////////////////////////////////////////////////////
	     for (int testI = 0 ; testI < shapes.size() ; testI++) {
	    	 if(shapes.get(testI).toString().equals("machine")) {
				 Machine currentM = (Machine)(shapes.get(testI));
				 System.out.println("M"+currentM.getId());
				 LinkedList<Queues> currentQin = currentM.getQin();
				 System.out.println(" Qin");
				 for (int testJ = 0; testJ < currentQin.size() ; testJ++) {
					 System.out.println("  Q"+currentQin.get(testJ).getId());
				 }
				 LinkedList<Queues> currentQout = currentM.getQout();
				 System.out.println(" Qout");
				 for (int testJ = 0; testJ < currentQout.size() ; testJ++) {
					 System.out.println("  Q"+currentQout.get(testJ).getId());
				 }
	    	 }	
	     }
	     System.out.println("////////////////////////////////////////////////////////////");
	     ////////////////////////////////////////////////////////////////////////////////
	      */
	}
});
// Display the frame

panel1.add(btnAddMachine);
panel1.add(btnAddQueue);
panel1.add(btnConnect);
panel1.add(btnDeleteElement);
panel1.add(btnNewSimulation);
panel1.add(btnPlay);
panel1.add(btnReplay);

panel.add(new CustomPaintComponent());
panel.addMouseListener(new MouseAdapter() {
    @Override //I override only one method for presentation
    public void mousePressed(MouseEvent e) {
    	x = e.getX();
    	y = e.getY();
    	Point p = new Point() ;
    	p.setX(x);
    	p.setY(y);
    	points.add(p);
        //System.out.println(e.getX() + "," + e.getY());
    }
});

frame.add(panel, BorderLayout.CENTER);
frame.add(panel1, BorderLayout.PAGE_END);


int frameWidth = 900;
int frameHeight = 750;
 
frame.setSize(frameWidth, frameHeight);
frame.setVisible(true);
 
  }

 
@SuppressWarnings("serial")
static class CustomPaintComponent extends JComponent {
 
@Override	
public void paintComponent(Graphics g) {
 
 super.paintComponent(g);
  //Graphics2D g2d = (Graphics2D)g;
 repaint();
 
 
 for (int i = 0; i < shapes.size(); i++) {
	Object shape = shapes.get(i);
	 if(shape.toString().equals("machine")) {
		 Machine m = (Machine) shape ;
		 int r =m.getR();
		 int xn,yn;
		 xn = m.getX()-(r/2);
		 yn = m.getY()-(r/2);
		 g.setColor(m.getColor());
		 g.fillOval(xn,yn,r,r);
		 g.setColor(Color.black);
		 
		 g.drawString("M"+m.getId(), xn+28, yn+38);
	 }
	 else if(shape.toString().equals("queue")) {
		 Queues q = (Queues) shape;
		 g.setColor(q.getColor());
		 g.fillRect(q.getX(), q.getY(), q.getW() ,q.getL());
		 g.setColor(Color.black);
		 
		 g.drawString("Q"+q.getId(), q.getX()+37, q.getY()+30);
		 g.drawString("Items# "+q.getItemList().size(), q.getX()+29, q.getY()+40);

		 //g2d.drawRect(q.getX(), q.getY(), q.getL(), q.getW());
	 }
}
 for (int i = 0; i < lines.size(); i++) {
	Line l = lines.get(i); 
	 g.setColor(Color.BLACK);
	 
	g.drawLine(l.getX0(), l.getY0(), l.getX1(), l.getY1());
	g.drawLine(l.getX1(), l.getY1(), l.getX1()+10, l.getY1()+7);
	g.drawLine(l.getX1(), l.getY1(), l.getX1()+10, l.getY1()-7);
}


 
}
 
  }
private static boolean isMouseInShape(int mx,int my,Object shape){
    if(shape.toString().equals("machine")){
        // a circle
    	Machine m = (Machine) shape ;
        int dx=mx-m.getX() ;
        int dy=my-m.getY();
        // to see if mouse is inside circle
        if(dx*dx+dy*dy<m.getR()*m.getR()){
            //  mouse inside this circle
            return(true);
        }
    }else if(shape.toString().equals("queue")){
        // a rectangle
    	Queues q = (Queues) shape;
        int rLeft=q.getX();
        int rRight=q.getX()+q.getW();
        int rTop=q.getY();
        int rBott=q.getY()+q.getL();
        // to see if mouse is inside rectangle
        if( mx>rLeft && mx<rRight && my>rTop && my<rBott){
            return true;
        }
    }else if (shape.toString().equals("line")) {
    	Line l = (Line) shape;
    	int x0 = l.getX0();
    	int y0 = l.getY0();
    	int x1 = l.getX1();
    	int y1 = l.getY1();
        // to avoid dividing by zero
        if ((Math.abs(x1 - x0)) < 30){
          if ( ((my>=y0 && my<=y1) || (my<=y0 && my>=y1)) && ((mx>=x0 && mx<=x1) || (mx<=x0 && mx>=x1)) ){
            return(true);
          }
        }else{
          double slope = (y1-y0)*1.0/(x1-x0);
          double intercept = y0 - (slope*x0)*1.0;
          // to see if mouse is inside rectangle
          if( (Math.abs(my - (slope*mx + intercept))<8 )&& ((my>=y0 && my<=y1) || (my<=y0 && my>=y1)) && ((mx>=x0 && mx<=x1) || (mx<=x0 && mx>=x1)) ){
              return(true);
          }
        }
    }
    
    // the mouse isn't in any of the shapes
    return false;
}


}