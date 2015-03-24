@@ -6,19 +6,25 @@ public class SimulationClock extends Observable {
    private long delayCurrent;      /* Current pause tracker. */
    private long tickRate;          /* Number of ticks per second. */

    private static final SimulationClock instance = new SimulationClock();

    /* Wagwan. */
    public SimulationClock() {
    private SimulationClock() {
        this.delayTime = 0;
        this.tickRate  = 1;
        this.startTime = System.currentTimeMillis();
    }

    /* Constructor for custom tickrate. */
    public SimulationClock(long tickRate) {
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
@@ -34,6 +40,7 @@ public class SimulationClock extends Observable {

    public void incrementClock() {
        this.startTime -= (1000 / this.tickRate);
        setChanged();
        notifyObservers();
    }
