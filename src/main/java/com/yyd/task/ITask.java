package com.yyd.task;

import com.google.gson.Gson;
import com.yyd.CommonOperate;
import com.yyd.任务可以开始的时间段;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class ITask implements Runnable{
    static Logger log = LoggerFactory.getLogger(ITask.class);

    public Gson gson = new Gson();

    public Random random = new Random();

    public int 当前正在运行的userId;

    public List<任务可以开始的时间段> 时间段列表 = new ArrayList<>();

    public String 任务名;

    /**
     * TODO 这些循环执行的任务才需要的字段，移动到一个新的接口去，比如 ILongTask
     * 小于0代表无限
     */
    public long 这个任务每次执行的最长时间;

    public long 这个任务的开始时间;
    public long 这个任务的结束时间;

    public volatile boolean  今天这个任务是否执行 = true;

    public int 顺序 = 0;

    public ITask(int 当前正在运行的userId) {
        初始化时间段();
        this.当前正在运行的userId = 当前正在运行的userId;
        this.这个任务每次执行的最长时间 = 这个任务每次执行的最长时间();
        this.任务名 = this.getClass().getSimpleName() + "-" + 当前正在运行的userId;
    }

    public boolean 这个任务这次是否已经执行了足够时间(){
        if(这个任务每次执行的最长时间 < 0){
            return false;
        }else {
            return System.currentTimeMillis() - 这个任务的开始时间 > 这个任务每次执行的最长时间;
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
        try {
            这个任务的开始时间 = System.currentTimeMillis();
            log.info("[" + get任务名() + "] 开始执行了");
            run();
            这个任务的结束时间 = System.currentTimeMillis();
            log.info("[" + get任务名() + "] 结束执行了");
        }catch (Exception e){
            log.info(e.getMessage());
            e.printStackTrace();
        }finally {
            CommonOperate.退出所有App();
        }
    }

    public int get当前正在运行的userId() {
        return 当前正在运行的userId;
    }

    public void set当前正在运行的userId(int 当前正在运行的userId) {
        this.当前正在运行的userId = 当前正在运行的userId;
    }

    public String get任务名() {
        return 任务名;
    }

    public void set任务名(String 任务名) {
        this.任务名 = 任务名;
    }

    public boolean is今天这个任务是否执行() {
        return 今天这个任务是否执行;
    }

    public void set今天这个任务是否执行(boolean 今天这个任务是否执行) {
        this.今天这个任务是否执行 = 今天这个任务是否执行;
    }

    public void 第二天初始化(){
        List<任务可以开始的时间段> 任务可执行时间段列表 = this.任务只在这几个时间段执行();
        for (任务可以开始的时间段 任务可以开始的时间段 : 任务可执行时间段列表){
            任务可以开始的时间段.执行次数清零();
        }
        log.info("{}下所有时间段的执行次数已被清零", this.get任务名());
        set今天这个任务是否执行(true);
    }


    public long get这个任务的开始时间() {
        return 这个任务的开始时间;
    }

    public void set这个任务的开始时间(long 这个任务的开始时间) {
        this.这个任务的开始时间 = 这个任务的开始时间;
    }

    public long get这个任务的结束时间() {
        return 这个任务的结束时间;
    }

    public void set这个任务的结束时间(long 这个任务的结束时间) {
        this.这个任务的结束时间 = 这个任务的结束时间;
    }

    public abstract boolean 任务满足开始条件();

    public abstract long 这个任务每次执行的最长时间();

    public abstract void 初始化时间段();

    public int get顺序() {
        return 顺序;
    }

    public void set顺序(int 顺序) {
        this.顺序 = 顺序;
    }
}
