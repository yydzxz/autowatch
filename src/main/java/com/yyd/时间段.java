package com.yyd;

import java.time.LocalTime;

public class 时间段 {
    /**
     * HH:mm:ss
     */
    private int startHour;

    private int startMinute;

    private int startSecond;
    /**
     * HH:mm:ss
     */
    private int endHour;

    private int endMinute;

    private int endSecond;

    private int 该时间段已经执行过几次;

    public 时间段(int startHour, int startMinute, int startSecond, int endHour, int endMinute, int endSecond) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.startSecond = startSecond;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.endSecond = endSecond;
    }

    public boolean 当前是否处于时间段内(){
        LocalTime now = LocalTime.now();
        return (now.getHour() >= startHour
        && now.getMinute() >= startMinute
        && now.getSecond() >= startSecond
        && now.getHour() <= endHour
        && now.getMinute() <= endMinute
        && now.getSecond() <= endSecond);
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getStartSecond() {
        return startSecond;
    }

    public void setStartSecond(int startSecond) {
        this.startSecond = startSecond;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public int getEndSecond() {
        return endSecond;
    }

    public void setEndSecond(int endSecond) {
        this.endSecond = endSecond;
    }

    public int get该时间段已经执行过几次() {
        return 该时间段已经执行过几次;
    }

    public void set该时间段已经执行过几次(int 该时间段已经执行过几次) {
        this.该时间段已经执行过几次 = 该时间段已经执行过几次;
    }
}
