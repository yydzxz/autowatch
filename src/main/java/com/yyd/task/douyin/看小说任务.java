package com.yyd.task.douyin;

import com.yyd.App;
import com.yyd.CommonOperate;
import com.yyd.DouYinOperate;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;
import com.yyd.util.LogUtil;

@TaskAnnotation(cron = "", 优先级 = App.高优先级)
public class 看小说任务 extends ITask {
    public 看小说任务(int 当前正在运行的userId) {
        super(当前正在运行的userId);
    }

    @Override
    public boolean 任务满足开始条件() {
        return true;
    }

    @Override
    public long 这个任务每次执行的最长时间() {
        return 30 * 60 * 1000;
    }

    @Override
    public void 初始化时间段() {
        添加时间段(0, 0,0,23,59 ,59, 1);
    }

    @Override
    public void run() {
        try {
            DouYinOperate.退出抖音极速版(当前正在运行的userId);
            DouYinOperate.打开抖音极速版(当前正在运行的userId);
            DouYinOperate.点击视频页最底下中间那个按钮打开任务页();
            DouYinOperate.赚钱任务页面划动到最底部();
            CommonOperate.单击(899, 1259, 2, 2, 5000, "点击看小说");
            CommonOperate.单击(135.2, 700.5, 400, 100, 5000, "点击书架上的小说");
            while (!这个任务这次是否已经执行了足够时间()){
                DouYinOperate.左划(6, 6);
            }
            DouYinOperate.退出抖音极速版(当前正在运行的userId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            CommonOperate.退出所有App();
        }

    }
}
