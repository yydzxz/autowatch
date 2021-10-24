package com.yyd.task;

import com.yyd.util.LogUtil;
import com.yyd.任务可以开始的时间段;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class ITask implements Runnable{

    public Random random = new Random();

    public int 当前正在运行的userId;

    public List<任务可以开始的时间段> 时间段列表 = new ArrayList<>();

    public String 任务名;

    /**
     * TODO 这些循环执行的任务才需要的字段，移动到一个新的接口去，比如 ILongTask
     * 小于0代表无限
     */
    public long 这个任务每次执行的最长时间;

    // 之所以取名“这次这个任务的开始时间” 和 “上次这个任务的结束时间”，
    // 是因为任务开始执行后，就是当前任务的执行过程中，属于这次任务
    // 而一旦任务执行完后，这次这个任务就完成了，在时间上属于上次的任务了
    public long 这次这个任务的开始时间;
    public long 上次这个任务的结束时间;

    public ITask(int 当前正在运行的userId) {
        初始化时间段();
        this.当前正在运行的userId = 当前正在运行的userId;
        this.这个任务每次执行的最长时间 = 这个任务每次执行的最长时间();
        this.任务名 = this.getClass().getSimpleName();
    }

    public boolean 这个任务这次是否已经执行了足够时间(){
        if(这个任务每次执行的最长时间 < 0){
            return false;
        }else {
            return System.currentTimeMillis() - 这次这个任务的开始时间 > 这个任务每次执行的最长时间;
        }
    }

    public void 添加时间段(int startHour, int startMinute, int startSecond, int endHour, int endMinute, int endSecond){
        时间段列表.add(new 任务可以开始的时间段(startHour, startMinute, startSecond, endHour, endMinute, endSecond));
    }

    public void 添加时间段(int startHour, int startMinute, int startSecond, int endHour, int endMinute, int endSecond, int 该时间段最多可以执行几次){
        时间段列表.add(new 任务可以开始的时间段(startHour, startMinute, startSecond, endHour, endMinute, endSecond, 该时间段最多可以执行几次));
    }

    public List<任务可以开始的时间段> 任务只在这几个时间段执行() {
        return 时间段列表;
    }

    public void 设置当前正在运行的userId(int 当前正在运行的userId){
        this.当前正在运行的userId = 当前正在运行的userId;
    }

    public void doRun(){
        这次这个任务的开始时间 = System.currentTimeMillis();
        LogUtil.log("[" + 任务名 + "] 开始执行了");
        run();
        上次这个任务的结束时间 = System.currentTimeMillis();
        LogUtil.log("[" + 任务名 + "] 结束执行了");
    }

    public int get当前正在运行的userId() {
        return 当前正在运行的userId;
    }

    public void set当前正在运行的userId(int 当前正在运行的userId) {
        this.当前正在运行的userId = 当前正在运行的userId;
    }

    public String get任务名() {
        return 任务名 + "-" + 当前正在运行的userId;
    }

    public void set任务名(String 任务名) {
        this.任务名 = 任务名;
    }

    public abstract boolean 任务满足开始条件();

    public abstract long 这个任务每次执行的最长时间();

    public abstract void 初始化时间段();
}
