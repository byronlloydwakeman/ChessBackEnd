package chessbackend.TestClasses;

public class StopWatch
{
    private long startTime = 0;
    private long stopTime = 0;

    public StopWatch()
    {
        startTime = System.currentTimeMillis();
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        stopTime = System.currentTimeMillis();
        System.out.println("StopWatch: " + getElapsedTime() + " milliseconds.");
        System.out.println("StopWatch: " + getElapsedTimeSecs() + " seconds.");
    }

    /**
     * @param process_name
     */
    public void stop(String process_name) {
        stopTime = System.currentTimeMillis();
        System.out.println(process_name + " StopWatch: " + getElapsedTime() + " milliseconds.");
        System.out.println(process_name + " StopWatch: " + getElapsedTimeSecs() + " seconds.");
    }

    //elaspsed time in milliseconds
    public long getElapsedTime() {
        return stopTime - startTime;
    }

    //elaspsed time in seconds
    public double getElapsedTimeSecs() {
        double elapsed;
        elapsed = ((double)(stopTime - startTime)) / 1000;
        return elapsed;
    }
}
