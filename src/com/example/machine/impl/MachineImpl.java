package com.example.machine.impl;

import com.example.machine.Configuration;
import com.example.machine.Machine;

import java.util.concurrent.atomic.AtomicBoolean;

public class MachineImpl implements Machine {
    private Callback callback;
    private Configuration configuration;
    private final AtomicBoolean isPaused = new AtomicBoolean(true);
    private final AtomicBoolean isPowerOff = new AtomicBoolean(true);


    @Override
    public void powerOn() {
        if (isPowerOff.compareAndSet(true, false)) {
            System.out.println("开机");
        }
    }

    @Override
    public void powerOff() {
        finish();
        if (isPowerOff.compareAndSet(false, true)) {
            System.out.println("关机");
        }
    }


    @Override
    public void start() {
        if (configuration != null) {
            start(configuration, callback);
        }
    }

    @Override
    public void start(Configuration configuration, Callback callback) {
        if (isRunning() && this.configuration != configuration) {
            System.out.println("运行中不能修改选项");
            return;
        }
        if (isPowerOn() && isPaused.compareAndSet(true, false)) {
            System.out.println("运行");
            System.out.println(configuration.toString());
            this.configuration = configuration;
            this.callback = callback;
        }
    }

    @Override
    public void pause() {
        if (isPowerOn() && isPaused.compareAndSet(false, true)) {
            System.out.println("暂停");
        }
    }

    @Override
    public boolean isRunning() {
        return !isPaused.get();
    }

    @Override
    public boolean isPowerOn() {
        return !isPowerOff.get();
    }

    private void finish() {
        if (isPowerOn() && callback != null) {
            callback.onFinish();
        }
    }
}
