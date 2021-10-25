package com.yyd.task.douyin;

import com.yyd.*;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;

@TaskAnnotation(cron = "", 优先级 = App.高优先级, 所属app = App.DOU_YIN)
public class 领睡觉金币任务 extends ITask {

    public 领睡觉金币任务(int 当前正在运行的userId) {
        super(当前正在运行的userId);
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
        添加时间段(20,0,0, 23,59 ,59);
    }

    @Override
    public void run() {
        try {
            CommonOperate.退出所有App();
            DouYinOperate.打开抖音极速版(当前正在运行的userId);
            DouYinOperate.点击视频页最底下中间那个按钮打开任务页();
            DouYinOperate.赚钱任务页面划动到最底部();
            CommonOperate.单击(835, 650, 20, 5, 2500,"点击睡觉赚金币");
            CommonOperate.单击(400, 1760, 20, 10, 2500,"点击我睡觉了");
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            DouYinOperate.退出抖音极速版(当前正在运行的userId);
        }
    }
}
