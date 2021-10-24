package com.yyd.douyin;

import com.yyd.App;
import com.yyd.DouYinOperate;
import com.yyd.DouYinTask;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.任务可以开始的时间段;

import java.io.IOException;
import java.time.LocalDateTime;

@TaskAnnotation(cron = "", 优先级 = App.高优先级)
public class 领取每20分钟一次的宝箱 extends ITask {
    @Override
    public boolean 任务满足开始条件() {
        return App.执行队列中上一个任务的结束时间 != null && LocalDateTime.now().minusMinutes(21).compareTo(App.执行队列中上一个任务的结束时间) > 0;
    }

    @Override
    public long 这个任务每次执行的最长时间() {
        return -1;
    }

    @Override
    public void 初始化时间段() {
        时间段列表.add(new 任务可以开始的时间段(0,0,0, 23,59 ,59, -1));
    }

    @Override
    public void run() {
        try {
            DouYinOperate.退出抖音极速版(当前正在运行的userId);
            DouYinOperate.打开抖音极速版(当前正在运行的userId);
            DouYinTask.领取任务页宝箱金币("右");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            DouYinOperate.退出抖音极速版(当前正在运行的userId);
        }
    }
}
