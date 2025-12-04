import com.clock.Clock;

public class Main {
    public static void main(String[] args) {
        Clock clock = new Clock();

        Thread updateThread = new Thread(() -> {
            while (clock.isRunning()) {
                clock.updateTime();
            }
        }, "UpdateThread");

        Thread displayThread = new Thread(() -> {
            while (clock.isRunning()) {
                clock.displayTime();
                try {
                    // Sleep for a short interval to update the display
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Display thread interrupted");
                }
            }
        }, "DisplayThread");

        updateThread.setPriority(Thread.MIN_PRIORITY); // Priority 1
        displayThread.setPriority(Thread.MAX_PRIORITY); // Priority 10

        System.out.println("Starting Clock Application...");
        System.out.println("Display Thread Priority: " + displayThread.getPriority());
        System.out.println("Update Thread Priority: " + updateThread.getPriority());
        System.out.println("Press Ctrl+C to stop.");

        updateThread.start();
        displayThread.start();
    }
}
