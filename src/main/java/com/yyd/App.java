package com.yyd;

import com.yyd.annotations.TaskAnnotation;
import com.yyd.douyin.ITask;
import com.yyd.util.ClassUtil;

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
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        while (true){
            if(!高优先级队列.isEmpty()){
                for (ITask task : 高优先级队列){
//                    int hour = LocalDateTime.now().getHour()

                    当前正在执行的任务 = task;
                    当前任务的开始时间 = System.currentTimeMillis();
                    task.run();
                }
            }else {
                for (ITask task : 低优先级队列){
                    当前正在执行的任务 = task;
                    当前任务的开始时间 = System.currentTimeMillis();
                    task.run();
                }
            }
        }
    }
}
