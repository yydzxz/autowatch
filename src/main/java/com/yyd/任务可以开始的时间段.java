package com.yyd;

import java.time.LocalTime;

/**
 * 时间段只决定任务可以开始的时间， 而具体执行多久，任务类里面自己决定
 */
public class 任务可以开始的时间段 {

    private int startHour;

    private int startMinute;

    private int startSecond;

    private int endHour;

    private int endMinute;

    private int endSecond;

    private volatile int 该时间段已经执行过几次;

    /**
     * 小于0表示无数次
     */
    private int 该时间段最多可以执行几次;

    public 任务可以开始的时间段(int startHour, int startMinute, int startSecond, int endHour, int endMinute, int endSecond) {
        new 任务可以开始的时间段(startHour, startMinute, startSecond, endHour, endMinute, endSecond, 1);
    }

    public 任务可以开始的时间段(int startHour, int startMinute, int startSecond, int endHour, int endMinute, int endSecond, int 该时间段最多可以执行几次) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.startSecond = startSecond;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.endSecond = endSecond;
        this.该时间段最多可以执行几次 = 该时间段最多可以执行几次;
    }

    public boolean 当前是否处于时间段内(){
        LocalTime now = LocalTime.now();
        LocalTime startTime = LocalTime.of(startHour, startMinute, startSecond);
        LocalTime endTime = LocalTime.of(endHour, endMinute, endSecond);
        return now.compareTo(startTime) >= 0 && now.compareTo(endTime) <= 0;
    }

    public void 当前时间段执行次数加1(){
        this.该时间段已经执行过几次 = this.该时间段已经执行过几次 + 1;
    }

    public void 执行次数清零(){
        this.该时间段已经执行过几次 = 0;
        System.out.println();
    }

    public String 当前时间段字符串(){
        return startHour + ":" + startMinute + ":" + startSecond + "~~~" + endHour + ":" + endMinute + ":" + endSecond;
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

    public int get该时间段最多可以执行几次() {
        return 该时间段最多可以执行几次;
    }

    public void set该时间段最多可以执行几次(int 该时间段最多可以执行几次) {
        this.该时间段最多可以执行几次 = 该时间段最多可以执行几次;
    }
}
