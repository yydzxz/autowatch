package com.yyd;

import com.yyd.annotations.TaskAnnotation;
import com.yyd.douyin.ITask;
import com.yyd.util.ClassUtil;
import com.yyd.util.LogUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App {
    public static final int 低优先级 = 0;
    public static final int 高优先级 = 1;
    public static List<ITask> 低优先级队列 = new ArrayList<>();
    public static List<ITask> 高优先级队列 = new ArrayList<>();
    public static volatile long 当前任务的开始时间;
    public static volatile ITask 当前正在执行的任务;
    public static LocalDateTime 执行队列中上一个任务的结束时间;

    static{
        init();
    }

    /**
     * 把com.yyd包下的类初始化为任务，放入队列
     */
    public static void init() {
        try {
            List<Class<?>> classList = ClassUtil.listClass("com.yyd");
            for (Class<?> clazz : classList){
                TaskAnnotation taskAnnotation = clazz.getAnnotation(TaskAnnotation.class);
                if(taskAnnotation != null){
                    ITask task = (ITask)clazz.newInstance();
                    if(taskAnnotation.优先级() == 低优先级){
                        低优先级队列.add(task);
                    }else {
                        高优先级队列.add(task);
                    }
                }
            }
            for(ITask task : 低优先级队列){
                LogUtil.log("底优先级队列任务:" + task.get任务名());
            }
            for(ITask task : 高优先级队列){
                LogUtil.log("高优先级队列任务:" + task.get任务名());
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            LocalDate 用于判定是否到了第二天的开始时间 = LocalDate.now();
            while (true){
                boolean 到了第二天 = LocalDate.now().compareTo(用于判定是否到了第二天的开始时间) > 0;
                用于判定是否到了第二天的开始时间 = LocalDate.now();
                if(到了第二天){
                    for (ITask task : 高优先级队列){
                        List<任务可以开始的时间段> 任务可执行时间段列表 = task.任务只在这几个时间段执行();
                        for (任务可以开始的时间段 任务可以开始的时间段 : 任务可执行时间段列表){
                            任务可以开始的时间段.执行次数清零();
                        }
                    }
                    for (ITask task : 低优先级队列){
                        List<任务可以开始的时间段> 任务可执行时间段列表 = task.任务只在这几个时间段执行();
                        for (任务可以开始的时间段 任务可以开始的时间段 : 任务可执行时间段列表){
                            任务可以开始的时间段.执行次数清零();
                        }
                    }
                    LogUtil.log("第二天到了，任务已经执行次数清零");
                }
                try {
                    TimeUnit.MINUTES.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (true){
            for (ITask task : 高优先级队列){
                如果任务处于可以执行时间段以内则执行(task);
            }
            for (ITask task : 低优先级队列){
                如果任务处于可以执行时间段以内则执行(task);
                //低优先级任务每执行完一个，都break出去，重新查看高优先级队列是否有可以执行的任务
                break;
            }
        }
    }

    public static void 如果任务处于可以执行时间段以内则执行(ITask task){
        List<任务可以开始的时间段> 这个任务可执行的时间段列表 = task.任务只在这几个时间段执行();
        任务可以开始的时间段 这次的任务执行时间段 = null;
        for (任务可以开始的时间段 任务可以开始的时间段 : 这个任务可执行的时间段列表){
            if(任务可以开始的时间段.当前是否处于时间段内()){
                这次的任务执行时间段 = 任务可以开始的时间段;
                break;
            }
        }
        if(这次的任务执行时间段 != null && (这次的任务执行时间段.get该时间段最多可以执行几次() < 0 || 这次的任务执行时间段.get该时间段已经执行过几次() < 这次的任务执行时间段.get该时间段最多可以执行几次())){
            if(task.任务满足开始条件()){
                当前正在执行的任务 = task;
                当前任务的开始时间 = System.currentTimeMillis();
                LogUtil.log("现在任务【" + task.get任务名() + "】满足开始条件，并且处于可开始时间段： " +这次的任务执行时间段.当前时间段字符串());
                task.doRun();
                这次的任务执行时间段.当前时间段执行次数加1();
                执行队列中上一个任务的结束时间 = LocalDateTime.now();
            }else {
                LogUtil.log("现在任务【" + task.get任务名() + "】处于可开始时间段： " +这次的任务执行时间段.当前时间段字符串() + ", 但是不满足开始条件");
            }
        }
    }
}
