package com.yyd;

import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;
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

    /**
     * 如果没有应用分身，那么用户id只有一个0
     * 如果开启了应用分身，那么分身应用对应的用户id根据手机不同而不同，小米是999
     */
    public static int[] 用户id列表 = new int[]{0, 999};

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
                    for(int 用户id : 用户id列表){
                        ITask task = (ITask)clazz.getConstructor(int.class).newInstance(用户id);
                        if(taskAnnotation.优先级() == 低优先级){
                            低优先级队列.add(task);
                            LogUtil.log("底优先级队列任务:" + task.get任务名());
                        }else {
                            高优先级队列.add(task);
                            LogUtil.log("高优先级队列任务:" + task.get任务名());
                        }
                    }
                }
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
            for (ITask 低优先级任务 : 低优先级队列){
                如果任务处于可以执行时间段以内则执行(低优先级任务);
                for (ITask 高优先级任务 : 高优先级队列){
                    如果任务处于可以执行时间段以内则执行(高优先级任务);
                }
            }
        }
    }

    public static void 如果任务处于可以执行时间段以内则执行(ITask task){
        List<任务可以开始的时间段> 这个任务可执行的时间段列表 = task.任务只在这几个时间段执行();
        任务可以开始的时间段 这次的任务可以开始的时间段 = null;
        for (任务可以开始的时间段 任务可以开始的时间段 : 这个任务可执行的时间段列表){
            if(任务可以开始的时间段.当前是否处于时间段内()){
                这次的任务可以开始的时间段 = 任务可以开始的时间段;
                break;
            }
        }
        if(这次的任务可以开始的时间段 != null && (这次的任务可以开始的时间段.get该时间段最多可以执行几次() < 0 || 这次的任务可以开始的时间段.get该时间段已经执行过几次() < 这次的任务可以开始的时间段.get该时间段最多可以执行几次())){
            if(task.任务满足开始条件()){
                当前正在执行的任务 = task;
                当前任务的开始时间 = System.currentTimeMillis();
                LogUtil.log("现在任务【" + task.get任务名() + "】满足开始条件，并且处于可开始时间段： " +这次的任务可以开始的时间段.当前时间段字符串());
                try {
                    task.doRun();
                }catch (Exception e){
                    LogUtil.log(e.getMessage());
                    e.printStackTrace();
                }finally {
                    CommonOperate.退出所有App();
                }
                这次的任务可以开始的时间段.当前时间段执行次数加1();
                执行队列中上一个任务的结束时间 = LocalDateTime.now();
            }else {
                LogUtil.log("现在任务【" + task.get任务名() + "】处于可开始时间段： " +这次的任务可以开始的时间段.当前时间段字符串() + ", 但是不满足开始条件");
            }
        }
    }
}
