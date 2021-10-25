package com.yyd;

import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;
import com.yyd.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * TODO 每天会弹一个青少年模式
 */
public class App {
    static Logger log = LoggerFactory.getLogger(App.class);

    public static final String DOU_YIN = "douyin";
    public static final String KUAI_SHOU = "kuaishou";

    public static final int 主用户id = 0;
    public static final int 分身用户id = 999;



    public static final int 低优先级 = 0;
    public static final int 高优先级 = 1;
    public static List<ITask> 低优先级队列 = new ArrayList<>();
    public static List<ITask> 高优先级队列 = new ArrayList<>();
    public static volatile ITask 当前正在执行的任务;
    public static volatile ITask 上个任务;
//    public static volatile LocalDateTime 上个任务的开始时间;
//    public static LocalDateTime 上个任务的结束时间;

    /**
     * 如果没有应用分身，那么用户id只有一个0
     * 如果开启了应用分身，那么分身应用对应的用户id根据手机不同而不同，小米是999
     */
    public static int[] 用户id列表 = new int[]{主用户id, 分身用户id};

    static{
        init();
    }

    /**
     * 把com.yyd包下的类初始化为任务，放入队列
     */
    public static void init() {
        try {
            List<Class<?>> classList = ClassUtil.listClass("com.yyd.task");
            for (Class<?> clazz : classList){
                TaskAnnotation taskAnnotation = clazz.getAnnotation(TaskAnnotation.class);
                if(taskAnnotation != null){
                    for(int 用户id : 用户id列表){
                        ITask task = (ITask)clazz.getConstructor(int.class).newInstance(用户id);
                        if(taskAnnotation.优先级() == 低优先级){
                            低优先级队列.add(task);
                            log.info("低优先级队列任务:" + task.get任务名());
                        }else {
                            高优先级队列.add(task);
                            log.info("高优先级队列任务:" + task.get任务名());
                        }
                    }
                }
            }
            低优先级队列.sort(Comparator.comparingInt(ITask::get顺序));
            高优先级队列.sort(Comparator.comparingInt(ITask::get顺序));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            LocalDate 用于判定是否到了第二天的开始时间 = LocalDate.now();
            while (true){
                boolean 到了第二天 = LocalDate.now().compareTo(用于判定是否到了第二天的开始时间) > 0;
                if(到了第二天){
                    用于判定是否到了第二天的开始时间 = LocalDate.now();
                    for (ITask task : 高优先级队列){
                        task.第二天初始化();
                    }
                    for (ITask task : 低优先级队列){
                        task.第二天初始化();
                    }
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
            当前正在执行的任务 = task;
            if(!task.任务满足开始条件()){
                log.info("现在任务【" + task.get任务名() + "】处于可开始时间段： " +这次的任务可以开始的时间段.当前时间段字符串() + ", 但是不满足开始条件");
                return;
            }
            if(!task.is今天这个任务是否执行()){
                log.info("现在任务【" + task.get任务名() + "】处于可开始时间段： " +这次的任务可以开始的时间段.当前时间段字符串() + ", 但是今天不执行");
                return;
            }
            task.set这个任务的开始时间(System.currentTimeMillis());
            log.info("现在任务【" + task.get任务名() + "】满足开始条件，并且处于可开始时间段： " +这次的任务可以开始的时间段.当前时间段字符串());
            task.doRun();
            这次的任务可以开始的时间段.当前时间段执行次数加1();
            上个任务 = task;
            task.set这个任务的结束时间(System.currentTimeMillis());
        }
    }

    public static class 命令输入处理任务 implements Runnable{
        @Override
        public void run() {
            String command = "";
            while (true){
                Scanner scanner = new Scanner(System.in);
                if(scanner.hasNextLine()){
                    try {
                        command = scanner.nextLine().trim();
                        log.info("收到命令:" + command);
                        if(command.startsWith("今天不执行")){
                            String 任务名 = command.split(" ")[1];
                            String 用户id = command.split(" ")[2];
                            ITask task = 根据用户id和任务名查找任务(任务名, 用户id);
                            task.set今天这个任务是否执行(false);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static ITask 根据用户id和任务名查找任务(String 任务名, String 用户id){
        ITask 找到的任务 = null;
        for (ITask task : 低优先级队列){
            if(task.get任务名().equals(任务名 + "-" + 用户id)){
                找到的任务 = task;
            }
        }
        for (ITask task : 高优先级队列){
            if(task.get任务名().equals(任务名 + "-" + 用户id)){
                找到的任务 = task;
            }
        }
        return 找到的任务;
    }


    public static void a(){
        //TODO 读取 今天执行的任务
    }
}
