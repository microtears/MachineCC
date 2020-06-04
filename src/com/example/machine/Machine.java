package com.example.machine;

public interface Machine {
    void powerOn();

    void powerOff();

    void start();

    void start(Configuration configuration, Callback callback);

    void pause();

    boolean isRunning();

    boolean isPowerOn();

    interface Callback {
        void onFinish();
    }
}
