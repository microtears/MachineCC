package com.example.machine.impl;

import com.example.machine.Machine;

public class FinishTip implements Machine.Callback {
    @Override
    public void onFinish() {
        System.out.println("洗衣完成");
    }
}
