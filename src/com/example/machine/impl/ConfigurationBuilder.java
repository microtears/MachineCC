package com.example.machine.impl;

import com.example.machine.*;

import java.util.Objects;

public class ConfigurationBuilder implements Configuration {
    private Level level = Level.lv1;
    private Time time = Time.min30;
    private Mode mode = Mode.washing;
    private WaterVolume waterVolume = WaterVolume.vol10;

    public void setLevel(Level mLevel) {
        this.level = mLevel;
    }

    public void setTime(Time mTime) {
        this.time = mTime;
    }

    public void setMode(Mode mMode) {
        this.mode = mMode;
    }

    public void setWaterVolume(WaterVolume mWaterVolume) {
        this.waterVolume = mWaterVolume;
    }


    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public Mode getModel() {
        return mode;
    }

    @Override
    public WaterVolume getWaterVolume() {
        return waterVolume;
    }

    public Configuration build() {
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurationBuilder that = (ConfigurationBuilder) o;
        return level == that.level &&
                time == that.time &&
                mode == that.mode &&
                waterVolume == that.waterVolume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, time, mode, waterVolume);
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "level=" + level +
                ", time=" + time +
                ", mode=" + mode +
                ", waterVolume=" + waterVolume +
                '}';
    }
}
