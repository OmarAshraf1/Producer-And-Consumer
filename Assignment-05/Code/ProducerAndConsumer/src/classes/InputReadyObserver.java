package classes;

public class InputReadyObserver extends Observer{

	
	public InputReadyObserver(Machine machine){
	      this.machine = machine;
	     
	   }
	@Override
	public void update() {
		machine.setInputReady(true); ;
		
	}
	

}
