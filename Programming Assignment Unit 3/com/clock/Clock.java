package com.clock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Clock {
    private LocalDateTime currentTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
    private volatile boolean running = true;

    public Clock() {
        // Initialize with current time
        this.currentTime = LocalDateTime.now();
    }

    public synchronized void updateTime() {
        this.currentTime = LocalDateTime.now();
    }

    public synchronized void displayTime() {
        if (currentTime != null) {
            System.out.println(currentTime.format(formatter));
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        this.running = false;
    }
}
