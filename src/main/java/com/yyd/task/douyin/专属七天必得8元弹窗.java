package com.yyd.task.douyin;

import com.yyd.App;
import com.yyd.CommonOperate;
import com.yyd.DouYinOperate;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@TaskAnnotation(cron = "", 优先级 = App.高优先级, 所属app = App.DOU_YIN)
public class 专属七天必得8元弹窗 extends ITask {
    public 专属七天必得8元弹窗(int 当前正在运行的userId) {
        super(当前正在运行的userId);
        set顺序(-1);
    }

    @Override
    public boolean 任务满足开始条件() {
        //这个任务有设备绑定，不能双开领取，所以还是自己手动领取好了，这个任务暂时就不让执行了。后面考虑是否删除掉这个类
        return false;
    }

    @Override
    public long 这个任务每次执行的最长时间() {
        return -1;
    }

    @Override
    public void 初始化时间段() {
        添加时间段(0, 0,0,23,59 ,59, 1);
    }

    @Override
    public void run() {
        try {
            CommonOperate.退出所有App();
            DouYinOperate.打开抖音极速版(当前正在运行的userId);
            DouYinOperate.点击视频页最底下中间那个按钮打开任务页();
            TimeUnit.SECONDS.sleep(5);
            CommonOperate.单击(438, 1250, 100, 10, 5000, "领取");
            CommonOperate.返回(2);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            CommonOperate.退出所有App();
        }

    }
}
