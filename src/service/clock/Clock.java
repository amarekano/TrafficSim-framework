package service.clock;

public class Clock implements Runnable{

	private SimulationClock clock;
	
	public Thread t;
	boolean suspended=false;
	
	public Clock()
	{
		clock = SimulationClock.getInstance();
	}
	
	public void run()
	{
		while(true)
		{
			try {
				Thread.sleep(clock.getInterval());
				clock.incrementClock();
				synchronized(this){
					while(suspended){
						wait();
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void start(){
		if(t==null){
			t=new Thread(this);
			t.start();
		}
	}
	
	public void suspend(){
		suspended=true;
	}
	
	public synchronized void resume(){
		suspended=false;
		notify();
	}

	
}
