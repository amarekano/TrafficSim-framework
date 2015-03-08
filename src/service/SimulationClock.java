import java.util.Observable;

public class SimulationClock extends Observable {
    private long startTime;         /* Time Clock Started. */
    private long delayTime;         /* Tracks total time clock has paused for. */
    private long delayCurrent;      /* Current pause tracker. */
    private long tickRate;          /* Number of ticks per second. */

    private static final SimulationClock instance = new SimulationClock();

    /* Wagwan. */
    private SimulationClock() {
        this.delayTime = 0;
        this.tickRate  = 1;
        this.startTime = System.currentTimeMillis();
    }

    /* Constructor for custom tickrate. */
    private SimulationClock(long tickRate) {
        this();
        this.tickRate = tickRate;
    }

    public static SimulationClock getInstance() {
        return this.instance;
    }

    public void resetClock() {
        this.startTime = System.currentTimeMillis();
        this.delayTime = 0;
    }

    public void pauseClock() {
        this.delayCurrent = System.currentTimeMillis();
    }

    public void startClock() {
        this.delayTime += System.currentTimeMillis() - this.delayCurrent;
    }

    public void incrementClock() {
        this.startTime -= (1000 / this.tickRate);
        setChanged();
        notifyObservers();
    }

    public long getTime() {
        long currentTick = System.currentTimeMillis() - this.startTime - this.delayTime;
        return (currentTick / (1000 / this.tickRate));
    }
}
