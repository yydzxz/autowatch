package com.yyd.task.douyin;

import com.yyd.App;
import com.yyd.CommonOperate;
import com.yyd.DouYinOperate;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;
import com.yyd.util.MyDateUtil;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@TaskAnnotation(cron = "", 优先级 = App.高优先级)
public class 搜索任务 extends ITask {

    Random random = new Random();

    List<String> 用于搜索的列表;

    public 搜索任务(int 当前正在运行的userId) {
        super(当前正在运行的userId);
        用于搜索的列表 = new ArrayList<>();
        用于搜索的列表.add("zhoujielun");
        用于搜索的列表.add("lol");
        用于搜索的列表.add("s11");
        用于搜索的列表.add("hupu");
        用于搜索的列表.add("swift");
        用于搜索的列表.add("doinb");
        用于搜索的列表.add("faker");
        用于搜索的列表.add("rng");
        用于搜索的列表.add("edg");
    }

    @Override
    public boolean 任务满足开始条件() {
        return true;
    }

    @Override
    public long 这个任务每次执行的最长时间() {
        return -1;
    }

    @Override
    public void 初始化时间段() {
        添加时间段(0, 0,0,23,59 ,59, 3);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++){
                DouYinOperate.退出抖音极速版(当前正在运行的userId);
                DouYinOperate.打开抖音极速版(当前正在运行的userId);
                CommonOperate.单击(1001, 153.2, 2, 10, 3000, "点击放大镜标志的搜索按钮");

                String 搜索词 = 用于搜索的列表.get(random.nextInt(用于搜索的列表.size()));
                Runtime.getRuntime().exec("adb shell input keyboard text " + 搜索词);
                System.out.println("输入搜索词语" + 搜索词);
                TimeUnit.SECONDS.sleep(5);

                CommonOperate.单击(975, 1829, 2, 2, 4, "点击右下角搜索确认");
                CommonOperate.单击(400, 300, 2, 400, 4000, "随机选中一个搜索结果");
                DouYinOperate.上划(15, 5);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DouYinOperate.退出抖音极速版(当前正在运行的userId);
        }
    }
}
