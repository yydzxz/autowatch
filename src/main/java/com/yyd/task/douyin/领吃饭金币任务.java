package com.yyd.task.douyin;

import com.yyd.*;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;

@TaskAnnotation(cron = "", 优先级 = App.高优先级, 所属app = App.DOU_YIN)
public class 领吃饭金币任务 extends ITask {

    public 领吃饭金币任务(int 当前正在运行的userId) {
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
        时间段列表.add(new 任务可以开始的时间段(5,0,0, 9,0 ,0));
        时间段列表.add(new 任务可以开始的时间段(11,0,0, 14, 0, 0));
        时间段列表.add(new 任务可以开始的时间段(17, 0, 0, 20, 0, 0));
        时间段列表.add(new 任务可以开始的时间段(21, 0, 0, 23, 59, 59));
    }

    @Override
    public void run() {
        try {
            CommonOperate.退出所有App();
            DouYinOperate.打开抖音极速版(当前正在运行的userId);
            DouYinOperate.点击视频页最底下中间那个按钮打开任务页();
            DouYinOperate.赚钱任务页面划动到最底部();
            CommonOperate.单击(400, 1022, 300, 20, 3500,"点击吃饭补贴");
            CommonOperate.单击(500, 1670, 200, 20, 3500,"领取夜宵补贴");
            CommonOperate.单击(443, 1212, 200, 20, 35000,"看视频再领金币");
            CommonOperate.返回(2);
            CommonOperate.返回(2);
            DouYinOperate.退出抖音极速版(当前正在运行的userId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            CommonOperate.退出所有App();
        }
    }
}
